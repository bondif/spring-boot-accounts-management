package com.bondif.accountsmanagementworkshop.domain.exceptions;

public class AccountDoesNotExistException extends RuntimeException {
    @Override
    public String getMessage() {
        return "This account doesn't exist";
    }
}
