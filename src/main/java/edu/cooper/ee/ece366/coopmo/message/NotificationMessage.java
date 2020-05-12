package edu.cooper.ee.ece366.coopmo.message;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.User;

import java.sql.Timestamp;

public class NotificationMessage {
    private final String message;
    private final String referenceId;
    private final Timestamp timestamp;
    private final String type;

    public NotificationMessage(User fromUser, Payment payment) {
        this.message = fromUser.getHandle() + " sent $" + payment.getAmount() / 100.0;
        this.timestamp = payment.getTimestamp();
        this.type = "PAYMENT";
        this.referenceId = fromUser.getId();
    }

    public NotificationMessage(User fromUser, String type) {
        this.type = type;
        if (this.type.equals("FRIENDREQUEST")) {
            this.message = "Friend Request from " + fromUser.getHandle();
        } else {
            this.message = "Friend Request Accepted by " + fromUser.getHandle();
        }
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.referenceId = fromUser.getId();
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public String getReferenceId() { return referenceId; }
}
