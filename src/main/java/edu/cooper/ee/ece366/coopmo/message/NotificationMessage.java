package edu.cooper.ee.ece366.coopmo.message;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.User;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Locale;

public class NotificationMessage {
    private final String message;
    private final Timestamp timestamp;
    private final String type;

    public NotificationMessage(User fromUser, Payment payment) {
        //https://stackoverflow.com/questions/2379221/java-currency-number-format
        this.message = fromUser.getHandle() + " sent " + NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                .format(payment.getAmount() / 100.0);
        this.timestamp = payment.getTimestamp();
        this.type = "PAYMENT";
    }

    public NotificationMessage(User fromUser, String type) {
        this.type = type;
        if (this.type == "FRIENDREQUEST") {
            this.message = "Friend Request from " + fromUser.getHandle();
        } else {
            this.message = "Friend Request Accepted by " + fromUser.getHandle();
        }
        this.timestamp = new Timestamp(System.currentTimeMillis());
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
}
