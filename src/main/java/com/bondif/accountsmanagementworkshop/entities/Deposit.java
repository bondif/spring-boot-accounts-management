package com.bondif.accountsmanagementworkshop.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@DiscriminatorValue("D")
public class Deposit extends Operation implements Serializable {
    public Deposit() {
    }

    public Deposit(Date createdAt, Double amount, Account account) {
        super(createdAt, amount, account);
    }
}
