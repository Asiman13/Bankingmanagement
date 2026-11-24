package com.example.bankmanagement.Controller;

import com.example.bankmanagement.Dto.AccountRequestDTo;
import com.example.bankmanagement.Service.AccountService;
import com.example.bankmanagement.entity.Account;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Account")
public class AccountController {
    private final AccountService accountService;
    public AccountController(AccountService accountService){
        this.accountService=accountService;
    }
    @PostMapping("/add")
    public Account createAccount(@Valid @RequestBody AccountRequestDTo account){
        return accountService.createAccount(account);
    }
    @GetMapping
    public List<Account> getAlllaccount(){
        return accountService.getAllAccount();
    }
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id ){
        accountService.deleteAccount(id);
    }
    @PutMapping("/{id}/freeze")
    public void freezeAccount(@PathVariable Long id){
        accountService.freezeAccount(id);
    }
    @PutMapping("/{id}/setactive")
    public void setactiveAccount(@PathVariable Long id){
        accountService.setactiveAccount(id);
    }
    @PutMapping("/{id}/close")
    public void closeAccount(@PathVariable Long id){
        accountService.closeAccount(id);
    }
}
