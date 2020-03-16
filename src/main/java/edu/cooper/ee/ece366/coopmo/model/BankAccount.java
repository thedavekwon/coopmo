package edu.cooper.ee.ece366.coopmo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(nullable = false)
    private long routingNumber;

    @Column(nullable = false)
    private long balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(nullable = false)
    private User user;

    public BankAccount() {
    }

    public BankAccount(User _user, long _routingNumber, long _balance) {
        user = _user;
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
