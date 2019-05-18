package com.bondif.accountsmanagementworkshop.domain;

import com.bondif.accountsmanagementworkshop.entities.Account;
import com.bondif.accountsmanagementworkshop.entities.Operation;
import org.springframework.data.domain.Page;

public interface IOperationDomain {
    public Page<Operation> allOperations(int page, int size);
    public void deposit(String accountCode, double amount);
    public void withdrawal(String accountCode, double amount);
    public void transfer(String senderAccountCode, String receiverAccountCode, double amount);
    public Page<Operation> operations(String accountCode, int page, int size);
}
