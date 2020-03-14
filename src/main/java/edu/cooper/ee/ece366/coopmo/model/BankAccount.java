package edu.cooper.ee.ece366.coopmo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    @Column(nullable = false)
    private long routingNumber;

    @Column(nullable = false)
    private long balance;

    public BankAccount() {
    }

    public BankAccount(long _routingNumber, long _balance) {
        routingNumber = _routingNumber;
        balance = _balance;
    }

    public String getId() {
        return id;
    }

    public long getRoutingNumber() {
        return routingNumber;
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
}
