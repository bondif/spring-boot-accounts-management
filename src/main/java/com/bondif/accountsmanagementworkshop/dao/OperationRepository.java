package com.bondif.accountsmanagementworkshop.dao;

import com.bondif.accountsmanagementworkshop.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
