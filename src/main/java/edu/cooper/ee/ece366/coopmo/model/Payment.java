package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

public class Payment {

    @Id
    public Long id;
    public Long fromUserId;
    public Long toUserId;
    public Long amount;
    public Long type; // public/friends/private

    public Payment(Long id, Long fromUserId, Long toUserId, Long amount, Long type) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.type = type;
    }
}
