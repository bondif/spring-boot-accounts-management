package com.bondif.accountsmanagementworkshop.dao;

import com.bondif.accountsmanagementworkshop.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
