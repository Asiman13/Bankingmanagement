package com.example.bankmanagement.Exception;

public class AccountClosedException extends RuntimeException{
    public AccountClosedException(String message){
        super(message);
    }
}
