package com.bondif.accountsmanagementworkshop.domain;

import com.bondif.accountsmanagementworkshop.dao.AccountRepository;
import com.bondif.accountsmanagementworkshop.domain.exceptions.AccountDoesNotExistException;
import com.bondif.accountsmanagementworkshop.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AccountDomainImpl implements IAccountDomain {

    private AccountRepository accountRepository;

    public AccountDomainImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<Account> accounts(int page, int size) {
        return accountRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }

    @Override
    public Account retrieveAccount(String code) throws AccountDoesNotExistException {
        Optional<Account> account = accountRepository.findById(code);

        if (!account.isPresent()) throw new AccountDoesNotExistException();

        return account.get();
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void removeAccount(String code) {
        accountRepository.deleteById(code);
    }
}
