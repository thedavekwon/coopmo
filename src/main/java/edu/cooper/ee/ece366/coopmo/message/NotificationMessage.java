package edu.cooper.ee.ece366.coopmo.message;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.User;

import java.sql.Timestamp;

public class NotificationMessage {
    private final NotificationType notificationType;
    private final String message;
    private final Timestamp timestamp;

    public NotificationMessage(User fromUser, Payment payment) {
        this.notificationType = NotificationType.PAYMENT;
        this.message = fromUser.getHandle() + " sent $" + payment.getAmount() / 100.0;
        this.timestamp = payment.getTimestamp();
    }

    public NotificationMessage(User fromUser) {
        this.notificationType = NotificationType.FRIEND;
        this.message = "Friend Request from " + fromUser.getHandle();
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public enum NotificationType {
        PAYMENT, FRIEND
    }
}
