package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

import java.util.concurrent.atomic.AtomicLong;

public class BankAccount {
    @Id
    private Long id;
    private Long routingNumber;
    private AtomicLong balance;

    public BankAccount(long id, long routingNumber, long balance) {
        this.id = id;
        this.routingNumber = routingNumber;
        this.balance = new AtomicLong(balance);
    }

    public Long getId() {
        return id;
    }

    public Long getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(long routingNumber) {
        this.routingNumber = routingNumber;
    }

    public Long getBalance() {
        return balance.get();
    }

    public void setBalance(long newBalance) {
        balance.getAndSet(newBalance);
    }

    public void incrementBalance(long amount) {
        balance.getAndAdd(amount);
    }

    public void decrementBalance(long amount) {
        balance.getAndAdd(-amount);
    }
}
