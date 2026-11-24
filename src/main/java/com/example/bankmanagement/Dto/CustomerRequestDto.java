package com.example.bankmanagement.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDto {
    @NotBlank(message="First name is required")
    @Size(max=50)
    private String firstname;
    @NotBlank(message="Last name is required")
    @Size(max=50)
    private String lastname;
    @NotBlank(message="Email is required")
    @Email(message="Invalid email format")
    private String email;
    @Size(max=20)
    private String phone;
}
