package edu.cooper.ee.ece366.coopmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(updatable = false, nullable = false)
    private long routingNumber;

    @Column(updatable = false, nullable = false)
    private long accountNumber;

    @Column(nullable = false)
    private long balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public BankAccount() {
    }

    public BankAccount(User user, long accountNumber, long routingNumber, long balance) {
        this.user = user;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.balance = balance;
    }

    public long getRoutingNumber() {
        return routingNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long newBalance) {
        balance = newBalance;
    }

    public void incrementBalance(long amount) {
        balance = balance + amount;
    }

    public void decrementBalance(long amount) {
        balance = balance - amount;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }
}
