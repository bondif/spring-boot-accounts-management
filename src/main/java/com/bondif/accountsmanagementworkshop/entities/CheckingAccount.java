package com.bondif.accountsmanagementworkshop.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@DiscriminatorValue("CA")
public class CheckingAccount extends Account implements Serializable {
    private Double overdraft;

    public CheckingAccount() {
    }

    public CheckingAccount(String code, Double amount, Date createdAt, Client client, Double overdraft) {
        super(code, amount, createdAt, client);
        this.overdraft = overdraft;
    }

    public Double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(Double overdraft) {
        this.overdraft = overdraft;
    }
}
