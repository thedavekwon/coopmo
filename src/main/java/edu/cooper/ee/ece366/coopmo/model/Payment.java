package edu.cooper.ee.ece366.coopmo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Payment extends Transaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(updatable = false, nullable = false)
    private long amount;

    @Column(updatable = false, nullable = false)
    private String comment;

    @Column(updatable = false, nullable = false)
    private PaymentType type;

    @Column(updatable = false, nullable = false)
    @OrderBy
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    protected Timestamp timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User fromUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User toUser;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> likes;

    public Payment() {
    }

    public Payment(User fromUser, User toUser, long amount, PaymentType type, String comment) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.type = type;
        this.comment = comment;
        timestamp = new Timestamp(System.currentTimeMillis());
        likes = new HashSet<>();
    }

    public long getAmount() {
        return amount;
    }

    public User getFromUser() { return fromUser; }

    public User getToUser() { return toUser; }

    public String getId() {
        return id;
    }

    public PaymentType getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public Set<User> getLikes() {
        return likes;
    }

    @Override
    public int compareTo(Transaction o) {
        return getTimestamp().compareTo(o.getTimestamp());
    }

    public enum PaymentType {
        PUBLIC, FRIEND, PRIVATE
    }
}
