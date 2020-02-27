package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class CashOut {
    @Id
    private final String id;
    private String bankAccountId;
    private Long amount;

    public CashOut(String bankAccountId, long amount) {
        this.id = UUID.randomUUID().toString();
        this.bankAccountId = bankAccountId;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(long newAmount) {
        this.amount = newAmount;
    }
}