package com.example.bankmanagement.Repository;

import com.example.bankmanagement.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExcahngeRateRepository extends JpaRepository<ExchangeRate,Long> {
    Optional<ExchangeRate> findByFromCurrencyAndToCurrency(
            String fromCurrency,
            String toCurrency
    );
}
