package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Cash {
    public static final Boolean In = true;
    public static final Boolean Out = false;


    @Id
    private final String id;
    private final String userId;
    private final String bankAccountId;
    private final Long amount;

    public Cash(String userId, String bankAccountId, long amount) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
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

    public Long getAmount() {
        return amount;
    }
}