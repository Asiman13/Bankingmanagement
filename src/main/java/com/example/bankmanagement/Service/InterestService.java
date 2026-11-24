package com.example.bankmanagement.Service;

import com.example.bankmanagement.Exception.AccountNotFoundException;
import com.example.bankmanagement.Repository.AccountRepository;
import com.example.bankmanagement.Type.AccountType;
import com.example.bankmanagement.entity.Account;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InterestService {
    private final AccountRepository accountRepository;
    @Transactional
    public void calculateinterest(Long accountId){
        Account account=accountRepository.findById(accountId).orElseThrow(()->new AccountNotFoundException("Account not found"));
        if(account.getAccountType()!= AccountType.SAVINGS){
            throw new RuntimeException("Interest can only be calculated for savings accounts");
        }
        BigDecimal balance=account.getBalance();
        BigDecimal rate=account.getInterestRate();
        BigDecimal interest=balance.multiply(rate).divide(BigDecimal.valueOf(100),2);
        account.setBalance(balance.add(interest));
        accountRepository.save(account);
    }
}
