package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

public class Payment {

    @Id
    public String id;
    public String fromUserId;
    public String toUserId;
    public long amount;
    public int type; // public/friends/private

    public Payment() {
    }

    public Payment(String fromUserId, String toUserId, long amount, int type) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.type = type;
    }
}
