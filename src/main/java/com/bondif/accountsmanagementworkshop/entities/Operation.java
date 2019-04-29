package com.bondif.accountsmanagementworkshop.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "operations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 1)
public abstract class Operation implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @NotNull
    private Date createdAt;

    @NonNull
    private Double amount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_code")
    private Account account;

    public Operation() {
    }

    public Operation(@NotNull Date createdAt, @NonNull Double amount, @NotNull Account account) {
        this.createdAt = createdAt;
        this.amount = amount;
        this.account = account;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
