package com.bondif.accountsmanagementworkshop.domain.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Insufficient balance!";
    }
}