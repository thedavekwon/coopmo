package edu.cooper.ee.ece366.coopmo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cash extends Transaction {
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

    @Column(updatable = false, nullable = false)
    protected Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "user")
    @JoinColumn(nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "bankAccount")
    @JoinColumn(nullable = false)
    private BankAccount bankAccount;

    @OneToMany
    @JsonIgnore
    private Set<User> likes;

    public Cash() {
    }

    public Cash(User _user, BankAccount _bankAccount, long _amount, CashType _type) {
        user = _user;
        bankAccount = _bankAccount;
        type = _type;
        amount = _amount;
        likes = new HashSet<>();
        timestamp = new Timestamp(System.currentTimeMillis());
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

    @Override
    public int compareTo(Transaction o) {
        return getTimestamp().compareTo(o.getTimestamp());
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public Set<User> getLikes() {
        return likes;
    }

    public enum CashType {
        IN, OUT
    }
}