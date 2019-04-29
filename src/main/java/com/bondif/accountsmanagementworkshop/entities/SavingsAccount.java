package com.bondif.accountsmanagementworkshop.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@DiscriminatorValue("SA")
public class SavingsAccount extends Account implements Serializable {
    private Double rate;

    public SavingsAccount() {
    }

    public SavingsAccount(String code, Double amount, Date createdAt, Client client, Double rate) {
        super(code, amount, createdAt, client);
        this.rate = rate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
