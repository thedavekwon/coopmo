package edu.cooper.ee.ece366.coopmo.model;

import java.sql.Timestamp;

public abstract class Transaction implements Comparable<Transaction> {
    public abstract int compareTo(Transaction o);

    protected abstract Timestamp getTimestamp();

    public enum TransactionType {
        CASH, PAY
    }

    public static final int AMOUNT = 20;
}
