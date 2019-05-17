package com.bondif.accountsmanagementworkshop.dao;

import com.bondif.accountsmanagementworkshop.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    Page<Operation> findAllByAccount_CodeOrderByCreatedAtDesc(String code, Pageable pageable);
    Page<Operation> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
