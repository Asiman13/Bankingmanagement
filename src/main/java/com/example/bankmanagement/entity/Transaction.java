package com.example.bankmanagement.entity;

import com.example.bankmanagement.Type.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(updatable=false)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(updatable=false)
    private TransactionType type;
    @Column(updatable=false)
    private LocalDateTime transactionDate;
    private BigDecimal sourceBalanceAfter;
    private BigDecimal targetBalanceAfter;
    @ManyToOne
    private Account sourceAccount;
    @ManyToOne
    private Account targetAccount;
}
