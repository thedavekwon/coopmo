package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

public class CashOut {
    @Id
    public Long id;
    public Long bankAccountId;
    private Long amount;

    //TODO: ID
    public CashOut(long bankAccountId, long amount) {
        this.bankAccountId = bankAccountId;
        this.amount = amount;
    }

    public long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long newAmount) {
        this.amount = newAmount;
    }
}