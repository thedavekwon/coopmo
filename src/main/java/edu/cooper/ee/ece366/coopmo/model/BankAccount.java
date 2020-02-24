package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

public class BankAccount {
    @Id
    public Long id;
    public Long routingNumber;
    private Long balance;

    //TODO: ID
    public BankAccount(long routingNumber, long balance) {
        this.routingNumber = routingNumber;
        this.balance = balance;
    }

    public long getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(long routingNumber) {
        this.routingNumber = routingNumber;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
