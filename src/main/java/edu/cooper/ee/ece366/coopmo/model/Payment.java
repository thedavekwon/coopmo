package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.UUID;

public class Payment implements Comparable<Payment> {
    private final String fromUserId;
    @Id
    private final String id;
    private final String toUserId;
    private final long amount;
    private final PaymentType type; // public/friends/private
    private final Timestamp timestamp;

    public Payment(String fromUserId, String toUserId, long amount, PaymentType type) {
        this.id = UUID.randomUUID().toString();
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
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

    public String getFromUserId() {
        return fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public PaymentType getType() {
        return type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(Payment o) {
        return getTimestamp().compareTo(o.getTimestamp());
    }

    public enum PaymentType {
        PUBLIC, FRIEND, PRIVATE
    }
}
