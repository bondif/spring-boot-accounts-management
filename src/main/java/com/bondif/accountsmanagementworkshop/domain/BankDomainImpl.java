package com.bondif.accountsmanagementworkshop.domain;

import com.bondif.accountsmanagementworkshop.dao.AccountRepository;
import com.bondif.accountsmanagementworkshop.dao.OperationRepository;
import com.bondif.accountsmanagementworkshop.domain.exceptions.AccountDoesNotExistException;
import com.bondif.accountsmanagementworkshop.domain.exceptions.InsufficientBalanceException;
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
    public Page<Operation> allOperations(int page, int size) {
        return operationRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }

    @Override
    public Account retrieveAccount(String accountCode) {
        Optional<Account> account = accountRepository.findById(accountCode);

        if (!account.isPresent()) throw new AccountDoesNotExistException();

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
            throw new InsufficientBalanceException();

        Withdrawal withdrawal = new Withdrawal(new Date(), amount, account);
        operationRepository.save(withdrawal);

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    @Override
    public void transfer(String senderAccountCode, String receiverAccountCode, double amount) {
        deposit(receiverAccountCode, amount);
        withdrawal(senderAccountCode, amount);
    }

    @Override
    public Page<Operation> operations(String accountCode, int page, int size) {
        return operationRepository.findAllByAccount_CodeOrderByCreatedAtDesc(accountCode, PageRequest.of(page, size));
    }
}
