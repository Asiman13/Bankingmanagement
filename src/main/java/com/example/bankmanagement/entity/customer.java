package com.example.bankmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="First name cannot be empty")
    @Column(nullable=false,length = 50)
    private String firstname;
    @NotBlank(message="Last name cannot be empty")
    @Column(nullable=false,length=50)
    private String lastname;
    @NotBlank(message="Email cannot be empty")
    @Email(message="Invalid email format")
    @Column(nullable=false,unique=true,length=100)
    private String email;
    @Column(length=20)
    private String phone;
    @Column(nullable=false,updatable=false)
    private LocalDateTime time;
    @PrePersist
    public void onCreate(){
        time=LocalDateTime.now();
    }
    @OneToMany(mappedBy="customer",
    cascade=CascadeType.ALL)
    private List<Account> accounts=new ArrayList<>();
}


