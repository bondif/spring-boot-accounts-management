package com.bondif.accountsmanagementworkshop.domain;

import com.bondif.accountsmanagementworkshop.domain.exceptions.AccountDoesNotExistException;
import com.bondif.accountsmanagementworkshop.entities.Account;
import org.springframework.data.domain.Page;

public interface IAccountDomain {
    public Page<Account> accounts(int page, int size);

    public Account retrieveAccount(String code) throws AccountDoesNotExistException;

    public void saveAccount(Account account);

    public void updateAccount(Account account);

    public void removeAccount(String code);
}
