package com.example.bankmanagement.Service;

import com.example.bankmanagement.Dto.AccountRequestDTo;
import com.example.bankmanagement.Exception.AccountClosedException;
import com.example.bankmanagement.Exception.AccountFrozenException;
import com.example.bankmanagement.Exception.AccountNotFoundException;
import com.example.bankmanagement.Exception.CustomerNotFoundException;
import com.example.bankmanagement.Repository.AccountRepository;
import com.example.bankmanagement.Repository.CustomerRepository;
import com.example.bankmanagement.Type.AccountStatus;
import com.example.bankmanagement.Type.AccountType;
import com.example.bankmanagement.entity.Account;
import com.example.bankmanagement.entity.customer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    public AccountService(AccountRepository accountRepository,CustomerRepository customerRepository){
        this.accountRepository=accountRepository;
        this.customerRepository=customerRepository;
    }
    public Account createAccount(AccountRequestDTo dto) {
        customer c = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        Account account = new Account();
        account.setCustomer(c);
        account.setCurrency(dto.getCurrency());
        account.setAccountType(dto.getAccountType());
        account.setBalance(dto.getBalance());
        if (dto.getAccountType() == AccountType.CURRENT) {
            account.setDailyWithdrawalLimit(new BigDecimal("1000"));
        } else if (dto.getAccountType() == AccountType.SAVINGS) {
            account.setInterestRate(new BigDecimal("5"));
        }
        return accountRepository.save(account);
    }
    public Account getAccountById(Long id){
        return accountRepository.findById(id).orElseThrow(()->new AccountNotFoundException("Account not found"));
    }
    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }
    public void deleteAccount(Long id){
        Account a=getAccountById(id);
        accountRepository.delete(a);
    }
    public void freezeAccount(Long id){
        Account account=getAccountById(id);
        if(account.getStatus()!= AccountStatus.ACTIVE){
            throw new AccountFrozenException("Only active account can be frozen");
        }
        account.setStatus(AccountStatus.FROZEN);
        accountRepository.save(account);
    }
    public void setactiveAccount(Long id){
        Account account=getAccountById(id);
        if (account.getStatus()!=AccountStatus.FROZEN){
            throw new AccountFrozenException("Only frozen account can be actived");
        }
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
    }
    public void closeAccount(Long id){
       Account account=getAccountById(id);
       if(account.getStatus()==AccountStatus.CLOSED){
           throw new AccountClosedException("Account is already closed");
       }
       account.setStatus(AccountStatus.CLOSED);
       accountRepository.save(account);
    }
    public Account calculateInterest(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if (account.getAccountType() != AccountType.SAVINGS) {
            throw new RuntimeException("Interest is only for savings accounts");
        }
        if (!account.isActive()) {
            throw new AccountFrozenException("Account is not active");
        }
        BigDecimal interest = account.getBalance()
                .multiply(account.getInterestRate())
                .divide(new BigDecimal("100"));
        account.setBalance(account.getBalance().add(interest));
        return accountRepository.save(account);
    }
}
