package com.bondif.accountsmanagementworkshop.dao;

import com.bondif.accountsmanagementworkshop.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
