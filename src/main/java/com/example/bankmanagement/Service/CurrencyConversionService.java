package com.example.bankmanagement.Service;

import com.example.bankmanagement.Dto.TransactionRequestDto;
import com.example.bankmanagement.Exception.AccountFrozenException;
import com.example.bankmanagement.Exception.AccountNotFoundException;
import com.example.bankmanagement.Exception.InsufficientBalanceException;
import com.example.bankmanagement.Exception.SameAccountTransferException;
import com.example.bankmanagement.Repository.AccountRepository;
import com.example.bankmanagement.Repository.ExcahngeRateRepository;
import com.example.bankmanagement.Repository.TransactionRepository;
import com.example.bankmanagement.Type.TransactionType;
import com.example.bankmanagement.entity.Account;
import com.example.bankmanagement.entity.ExchangeRate;
import com.example.bankmanagement.entity.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CurrencyConversionService {
    private final AccountRepository accountRepository;
    private final ExcahngeRateRepository excahngeRateRepository;
    private final TransactionRepository transactionRepository;
    public CurrencyConversionService(
            AccountRepository accountRepository,
            ExcahngeRateRepository exchangeRepository,
            TransactionRepository transactionRepository
    ){
        this.accountRepository=accountRepository;
        this.excahngeRateRepository=exchangeRepository;
        this.transactionRepository=transactionRepository;
    }
    @Transactional
    public Transaction convertCurrency(TransactionRequestDto request){
        Account source=accountRepository.findById(request.getSourceAccountId()).orElseThrow(()->new AccountNotFoundException("Account not found"));
        Account target=accountRepository.findById(request.getTargetAccountId()).orElseThrow(()->new AccountNotFoundException("Account not found"));
        if(!source.isActive() || !target.isActive()){
            throw new AccountFrozenException("Account is not active");
        }
        if(source.getId().equals(target.getId())){
            throw new SameAccountTransferException("Cannot same currency to the same account");
        }
        if(request.getAmount()==null || request.getAmount().compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Invalid amount");
        }
        if(source.getBalance().compareTo(request.getAmount())<0){
            throw new InsufficientBalanceException("Insufficient baalnce");
        }
        ExchangeRate exchangeRate=excahngeRateRepository.findByFromCurrencyAndToCurrency(
                source.getCurrency(),
                target.getCurrency()
        ).orElseThrow(()->new RuntimeException("Exchange rate not found"));
        BigDecimal convertedAmount=request.getAmount().multiply(exchangeRate.getRate()).setScale(2);
        source.setBalance(source.getBalance().subtract(request.getAmount()));
        target.setBalance(target.getBalance().add(convertedAmount));
        Transaction transaction=new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(TransactionType.CURRENCY_CONVERSION);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setSourceAccount(source);
        transaction.setTargetAccount(target);
        transaction.setSourceBalanceAfter(source.getBalance());
        transaction.setTargetBalanceAfter(target.getBalance());
        return transactionRepository.save(transaction);
    }
}
