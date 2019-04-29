package com.bondif.accountsmanagementworkshop.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@DiscriminatorValue("W")
public class Withdrawal extends Operation implements Serializable {
    public Withdrawal() {
    }

    public Withdrawal(Date createdAt, Double amount, Account account) {
        super(createdAt, amount, account);
    }
}
