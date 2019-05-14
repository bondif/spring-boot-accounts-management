package com.bondif.accountsmanagementworkshop.domain.exceptions;

public class ClientDoesNotExistException extends Exception {
    @Override
    public String getMessage() {
        return "This account doesn't exist";
    }
}
