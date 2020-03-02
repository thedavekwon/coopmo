package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BankAccount {
    @Id
    private final String id;
    private final long routingNumber;
    private AtomicLong balance;

    public BankAccount(long routingNumber, long balance) {
        this.id = UUID.randomUUID().toString();
        this.routingNumber = routingNumber;
        this.balance = new AtomicLong(balance);
    }

    public String getId() {
        return id;
    }

    public long getRoutingNumber() {
        return routingNumber;
    }

    public long getBalance() {
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
