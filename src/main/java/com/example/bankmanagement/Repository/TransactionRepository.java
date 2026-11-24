package com.example.bankmanagement.Repository;

import com.example.bankmanagement.Type.TransactionType;
import com.example.bankmanagement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findBySourceAccountIdOrTargetAccountId(
            Long sourceAccountId,
            Long targetAccountId
    );

    List<Transaction> findByTransactionDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    @Query("""
    SELECT COALESCE(SUM(t.amount), 0)
    FROM Transaction t
    WHERE t.sourceAccount.id = :accountId
    AND t.type = :type
    AND t.transactionDate BETWEEN :startDate AND :endDate
""")
    BigDecimal getTotalWithdrawals(
            @Param("accountId") Long accountId,
            @Param("type") TransactionType type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    @Query("""
    SELECT t FROM Transaction t
    WHERE (t.sourceAccount.id = :accountId
    OR t.targetAccount.id = :accountId)
    AND t.transactionDate BETWEEN :startDate AND :endDate
    ORDER BY t.transactionDate DESC
""")
    List<Transaction> findAccountStatement(
            @Param("accountId") Long accountId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
