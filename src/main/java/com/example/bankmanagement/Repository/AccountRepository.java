package com.example.bankmanagement.Repository;

import com.example.bankmanagement.Type.TransactionType;
import com.example.bankmanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
