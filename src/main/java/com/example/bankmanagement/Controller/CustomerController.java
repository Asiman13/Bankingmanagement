package com.example.bankmanagement.Controller;

import com.example.bankmanagement.Dto.CustomerRequestDto;
import com.example.bankmanagement.Service.CustomerService;
import com.example.bankmanagement.entity.customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Customer")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }
    @PostMapping("/add")
    public ResponseEntity<customer> registerCustomer(@Valid @RequestBody CustomerRequestDto request){
        customer c=new customer();
        c.setFirstname(request.getFirstname());
        c.setLastname(request.getLastname());
        c.setEmail(request.getEmail());
        c.setPhone(request.getPhone());
        customer saved=customerService.registerCustomer(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

}
