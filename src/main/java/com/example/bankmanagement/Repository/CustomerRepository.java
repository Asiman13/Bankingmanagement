package com.example.bankmanagement.Repository;

import com.example.bankmanagement.entity.customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<customer,Long> {
    boolean existsByEmail(String email);
}
