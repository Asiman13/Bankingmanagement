package com.example.bankmanagement.Service;

import com.example.bankmanagement.Exception.AccountNotFoundException;
import com.example.bankmanagement.Repository.CustomerRepository;
import com.example.bankmanagement.entity.customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }
    public customer registerCustomer(customer c){
        if (customerRepository.existsByEmail(c.getEmail())){
            throw new AccountNotFoundException("Customer with this email already exists ");
        }
        return customerRepository.save(c);
    }
}
