package edu.cooper.ee.ece366.coopmo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    protected Timestamp timestamp;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )

    @Column(updatable = false, nullable = false)
    private String id;

    @Column(updatable = false, nullable = false)
    private CashType type;

    @Column(updatable = false, nullable = false)
    private long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "user")
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "bankAccount")
    @JoinColumn
    private BankAccount bankAccount;

    @OneToMany(fetch = FetchType.LAZY)
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