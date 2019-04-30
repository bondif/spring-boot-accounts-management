package com.bondif.accountsmanagementworkshop.domain;

import com.bondif.accountsmanagementworkshop.dao.AccountRepository;
import com.bondif.accountsmanagementworkshop.dao.OperationRepository;
import com.bondif.accountsmanagementworkshop.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class BankDomainImpl implements IBankDomain {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public BankDomainImpl(AccountRepository accountRepository,
                          OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public Account retrieveAccount(String accountCode) {
        Optional<Account> account = accountRepository.findById(accountCode);

        if (!account.isPresent()) throw new RuntimeException("This account doesn't exist");

        return account.get();
    }

    @Override
    public void deposit(String accountCode, double amount) {
        Account account = retrieveAccount(accountCode);

        Deposit deposit = new Deposit(new Date(), amount, account);
        operationRepository.save(deposit);

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Override
    public void withdrawal(String accountCode, double amount) {
        Account account = retrieveAccount(accountCode);

        double overdraft = 0;
        if (account instanceof CheckingAccount)
            overdraft = ((CheckingAccount) account).getOverdraft();

        if (account.getBalance() + overdraft < amount)
            throw new RuntimeException("Insufficient balance");

        Withdrawal deposit = new Withdrawal(new Date(), amount, account);
        operationRepository.save(deposit);

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    @Override
    public void transfer(String senderAccountCode, String receiverAccountCode, double amount) {
        deposit(senderAccountCode, amount);
        withdrawal(receiverAccountCode, amount);
    }

    @Override
    public Page<Operation> operations(String accountCode, int page, int size) {
        return operationRepository.findAllByAccount_CodeOrderByCreatedAtDesc(accountCode, PageRequest.of(page, size));
    }
}
