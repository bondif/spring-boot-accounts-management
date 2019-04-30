package com.bondif.accountsmanagementworkshop.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@DiscriminatorValue("CA")
public class CheckingAccount extends Account implements Serializable {
    private Double summit;

    public CheckingAccount() {
    }

    public CheckingAccount(String code, Double amount, Date createdAt, Client client, Double summit) {
        super(code, amount, createdAt, client);
        this.summit = summit;
    }

    public Double getSummit() {
        return summit;
    }

    public void setSummit(Double summit) {
        this.summit = summit;
    }
}
