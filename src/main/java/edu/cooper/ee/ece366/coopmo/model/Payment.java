package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Payment {

    @Id
    private String id;
    private String fromUserId;
    private String toUserId;
    private Long amount;
    private Integer type; // public/friends/private

    public Payment(String fromUserId, String toUserId, Long amount, Integer type) {
        this.id = UUID.randomUUID().toString();
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
