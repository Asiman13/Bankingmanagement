package com.example.bankmanagement.entity;

import com.example.bankmanagement.Type.AccountStatus;
import com.example.bankmanagement.Type.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String currency;
    private BigDecimal dailyWithdrawalLimit;
    private BigDecimal interestRate;
    @Column(nullable=false)
    private BigDecimal balance=BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private AccountStatus status=AccountStatus.ACTIVE;
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id",nullable=false)
    private customer customer;
    public boolean isActive() {
        return this.status == AccountStatus.ACTIVE;
    }

}
