package com.example.bankmanagement.Controller;

import com.example.bankmanagement.Dto.TransactionRequestDto;
import com.example.bankmanagement.Repository.AccountRepository;
import com.example.bankmanagement.Service.TransactionService;
import com.example.bankmanagement.entity.Account;
import com.example.bankmanagement.entity.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/Transaction")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;
    }
    @PostMapping("/deposit")
    public Transaction deposit(@RequestBody TransactionRequestDto request){
        return transactionService.deposit(request);
    }
    @PostMapping("/withdraw")
    public Transaction withdraw( @RequestBody TransactionRequestDto request){
        return transactionService.withdraw(request);
    }
    @PostMapping("/transfer")
    public Transaction transfer( @RequestBody TransactionRequestDto request){
        return transactionService.transfer(request);
    }
    @GetMapping("/history/{accountId}")
    public List<Transaction> getTransactionHistory(
            @PathVariable Long accountId) {
        return transactionService.getTransactionalHistory(accountId);
    }
    @GetMapping("/{accountId}/statement")
    public List<Transaction> getAccountStatement(
            @PathVariable Long accountId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {

        return transactionService.getAccountStatement(
                accountId, startDate, endDate
        );
    }
}
