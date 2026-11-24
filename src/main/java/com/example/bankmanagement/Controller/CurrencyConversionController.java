package com.example.bankmanagement.Controller;

import com.example.bankmanagement.Dto.TransactionRequestDto;
import com.example.bankmanagement.Service.CurrencyConversionService;
import com.example.bankmanagement.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/CurrencyConversion")
@RequiredArgsConstructor
public class CurrencyConversionController {
    private final CurrencyConversionService currencyConversionService;
    @PostMapping("/convert")
    public Transaction convert(@RequestBody TransactionRequestDto request){
        return currencyConversionService.convertCurrency(request);
    }

}
