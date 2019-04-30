package com.bondif.accountsmanagementworkshop.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "accounts")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 2)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account implements Serializable {
    @Id
    @Size(max = 191)
    private String code;

    @NonNull
    private Double balance;

    @NonNull
    private Date createdAt;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "client_code")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Collection<Operation> operations;

    public Account() {
    }

    public Account(String code, @NonNull Double balance, @NotNull Date createdAt, @NonNull Client client) {
        this.code = code;
        this.balance = balance;
        this.createdAt = createdAt;
        this.client = client;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Collection<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Collection<Operation> operations) {
        this.operations = operations;
    }
}
