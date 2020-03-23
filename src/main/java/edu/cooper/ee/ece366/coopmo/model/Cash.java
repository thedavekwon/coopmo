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
    @Column(updatable = false, nullable = false)
    @OrderBy
    protected Timestamp timestamp;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference(value = "user")
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference(value = "bankAccount")
    @JoinColumn
    private BankAccount bankAccount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<User> likes;

    public Cash() {
    }

    public Cash(User user, BankAccount bankAccount, long amount, CashType type) {
        this.user = user;
        this.bankAccount = bankAccount;
        this.type = type;
        this.amount = amount;
        this.likes = new HashSet<>();
        this.timestamp = new Timestamp(System.currentTimeMillis());
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