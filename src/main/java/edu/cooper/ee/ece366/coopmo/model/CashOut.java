package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

public class CashOut {
    @Id
    private Long id;
    private Long bankAccountId;
    private Long amount;

    public CashOut(long id, long bankAccountId, long amount) {
        this.id = id;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(long newAmount) {
        this.amount = newAmount;
    }
}