package edu.cooper.ee.ece366.coopmo.model;

import java.sql.Timestamp;

public abstract class Transaction implements Comparable<Transaction> {
    public abstract int compareTo(Transaction o);

    protected abstract Timestamp getTimestamp();
}
