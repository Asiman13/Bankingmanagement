package com.example.bankmanagement.Controller;


import com.example.bankmanagement.Service.ExchangeRateService;
import com.example.bankmanagement.entity.ExchangeRate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }
    @PostMapping
    public ExchangeRate addExchangeRate(@RequestBody ExchangeRate exchangeRate) {
        return exchangeRateService.addExchangeRate(exchangeRate);
    }
    @GetMapping
    public ExchangeRate getExchangeRate(
            @RequestParam String from,
            @RequestParam String to) {
        return exchangeRateService.getExchangeRate(from, to);
    }
}

