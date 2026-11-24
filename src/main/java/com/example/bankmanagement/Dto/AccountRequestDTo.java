package com.example.bankmanagement.Dto;

import com.example.bankmanagement.Type.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequestDTo {

    @NotNull
    private Long customerId;
    @NotNull
    private AccountType accountType;
    @NotBlank
    private String currency;
    private BigDecimal interestRate;

    private BigDecimal balance=BigDecimal.ZERO;
}

