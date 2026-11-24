package com.example.bankmanagement.Service;

import com.example.bankmanagement.Dto.TransactionRequestDto;
import com.example.bankmanagement.Exception.*;
import com.example.bankmanagement.Repository.AccountRepository;
import com.example.bankmanagement.Repository.TransactionRepository;
import com.example.bankmanagement.Type.AccountType;
import com.example.bankmanagement.Type.TransactionType;
import com.example.bankmanagement.entity.Account;
import com.example.bankmanagement.entity.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    public TransactionService(TransactionRepository transactionRepository,AccountRepository accountRepository){
        this.transactionRepository=transactionRepository;
        this.accountRepository=accountRepository;
    }
    @Transactional
    public Transaction deposit(TransactionRequestDto request){
        Account account=accountRepository.findById(request.getSourceAccountId()).orElseThrow(()->new AccountNotFoundException("Account not found"));
        if(!account.isActive()){
            throw new RuntimeException("Account is not active");
        }
        if(request.getAmount()==null){
            throw new InsufficientBalanceException("Amount musst be greater than zero ");
        }
        account.setBalance((account.getBalance().add(request.getAmount())));
        Transaction transaction=new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTargetBalanceAfter(account.getBalance());
        transaction.setTargetAccount(account);
        return transactionRepository.save(transaction);
    }
    @Transactional
    public Transaction withdraw(TransactionRequestDto request){
        Account account=accountRepository.findById(request.getSourceAccountId()).orElseThrow(()->new AccountNotFoundException("Account not found"));
        if(!account.isActive()){
            throw new AccountFrozenException("Account is not actice");
        }
        if (account.getAccountType() == AccountType.CURRENT) {
            LocalDate today = LocalDate.now();

            BigDecimal totalWithdrawals = transactionRepository.getTotalWithdrawals(
                    account.getId(),
                    TransactionType.WITHDRAW,
                    today.atStartOfDay(),
                    today.atTime(23, 59, 59)
            );

            if (totalWithdrawals.add(request.getAmount())
                    .compareTo(account.getDailyWithdrawalLimit()) > 0) {
                throw new RuntimeException("Daily withdrawal limit exceeded");
            }
        }
        if(request.getAmount()==null || request.getAmount().compareTo(BigDecimal.ZERO)<=0){
            throw new InsufficientBalanceException("Amount must be greather than zero");
        }
        if(account.getBalance().compareTo(request.getAmount()) < 0){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        Transaction transaction=new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setAmount(request.getAmount());
        transaction.setSourceAccount(account);
        transaction.setSourceBalanceAfter(account.getBalance());
        return transactionRepository.save(transaction);
    }
    @Transactional
    public Transaction transfer(TransactionRequestDto request){
        Account source=accountRepository.findById(request.getSourceAccountId()).orElseThrow(()->new AccountNotFoundException("Source account not found"));
        Account target=accountRepository.findById(request.getTargetAccountId()).orElseThrow(()->new AccountNotFoundException("Target account not found"));
        if(!source.isActive() || !target.isActive()){
            throw new AccountClosedException("Both account must be active");
        }
        if(source.getId().equals(target.getId())){
            throw new SameAccountTransferException("Cannot transfer to the same account");
        }
        if(request.getAmount()==null || request.getAmount().compareTo(BigDecimal.ZERO)<=0){
            throw new InsufficientBalanceException("Amount must be greather than zero");
        }
        if(source.getBalance().compareTo(request.getAmount())<0){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        source.setBalance(source.getBalance().subtract(request.getAmount()));
        target.setBalance(target.getBalance().add(request.getAmount()));

        Transaction transaction=new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(TransactionType.TRANSFER);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setSourceAccount(source);
        transaction.setTargetAccount(target);
        transaction.setSourceBalanceAfter(source.getBalance());
        transaction.setTargetBalanceAfter(target.getBalance());
        return transactionRepository.save(transaction);
    }
    public List<Transaction> getTransactionalHistory(Long accountId){
        if (!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException("Account not found");
        }
        return transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId,accountId);
    }
    public List<Transaction> getAccountStatement(
            Long accountId,
            LocalDateTime startDate,
            LocalDateTime endDate) {
        if (!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException("Account not found");
        }
        if (startDate == null || endDate == null ||
                startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }
        return transactionRepository.findAccountStatement(
                accountId,
                startDate,
                endDate
        );
    }
}
