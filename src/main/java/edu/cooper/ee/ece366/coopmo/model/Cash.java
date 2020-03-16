package edu.cooper.ee.ece366.coopmo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Cash {
    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private CashType type;

    @Column(nullable = false)
    private long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "user")
    @JoinColumn(nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "bankAccount")
    @JoinColumn(nullable = false)
    private BankAccount bankAccount;

    public Cash() {
    }

    public Cash(User _user, BankAccount _bankAccount, long _amount, CashType _type) {
        user = _user;
        bankAccount = _bankAccount;
        type = _type;
        amount = _amount;
    }

    public long getAmount() {
        return amount;
    }

    public String getId() {
        return id;
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