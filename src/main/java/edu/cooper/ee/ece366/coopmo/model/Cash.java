package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.UUID;

public class Cash {
    private final long amount;

    @Id
    private final String id;
    private final String userId;
    private final String bankAccountId;
    private final CashType type;
    private final Timestamp timestamp;

    public Cash(String userId, String bankAccountId, long amount, CashType type) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public long getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public CashType getType() {
        return type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public enum CashType {
        IN, OUT
    }
}