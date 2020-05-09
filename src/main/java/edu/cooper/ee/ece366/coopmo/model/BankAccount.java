package edu.cooper.ee.ece366.coopmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(updatable = false, nullable = false)
    private long routingNumber;

    @Column(updatable = false, nullable = false)
    private long accountNumber;

    @Column(nullable = false)
    private long balance;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public BankAccount() {
    }

    public BankAccount(User user, String nickname, long accountNumber, long routingNumber, long balance) {
        this.user = user;
        this.nickname = nickname;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public String getNickname() {
        return nickname;
    }

    public void setBalance(long newBalance) {
        balance = newBalance;
    }

    public void incrementBalance(long amount) {
        balance = balance + amount;
    }

    public void decrementBalance(long amount) {
        balance = balance - amount;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }
}
