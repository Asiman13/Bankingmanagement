package com.example.bankmanagement.Service;

import com.example.bankmanagement.Repository.ExcahngeRateRepository;
import com.example.bankmanagement.entity.ExchangeRate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExchangeRateService {

    private final ExcahngeRateRepository exchangeRateRepository;

    public ExchangeRateService(ExcahngeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public ExchangeRate addExchangeRate(ExchangeRate exchangeRate) {
        exchangeRate.setUpdatedAt(LocalDateTime.now());
        return exchangeRateRepository.save(exchangeRate);
    }

    public ExchangeRate getExchangeRate(String from, String to) {
        return exchangeRateRepository
                .findByFromCurrencyAndToCurrency(from, to)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));
    }
}

