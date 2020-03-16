package edu.cooper.ee.ece366.coopmo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Payment implements Comparable<Payment> {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private long amount;

    @Column(nullable = false)
    private PaymentType type;

    @Column(nullable = false)
    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "fromUser")
    @JoinColumn(nullable = false)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "toUser")
    @JoinColumn(nullable = false)
    private User toUser;

    public Payment() {
    }

    public Payment(User _fromUser, User _toUser, long _amount, PaymentType _type) {
        fromUser = _fromUser;
        toUser = _toUser;
        amount = _amount;
        type = _type;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public long getAmount() {
        return amount;
    }

    public String getId() {
        return id;
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
