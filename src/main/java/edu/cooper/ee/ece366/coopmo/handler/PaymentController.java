package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/pay")
public class PaymentController {
    private static ConcurrentHashMap<Long, Payment> paymentDB = new ConcurrentHashMap<>();

    @GetMapping("/createPayment")
    public Payment createPayment(
            @RequestParam(value = "fromUserId", defaultValue = "") Long fromUserId,
            @RequestParam(value = "toUserId", defaultValue = "") Long toUserId,
            @RequestParam(value = "amount", defaultValue = "") Long amount,
            @RequestParam(value = "type", defaultValue = "") Long type) {
        Payment newPayment = new Payment((long) paymentDB.size(), fromUserId, toUserId, amount, type);
        paymentDB.put(newPayment.id, newPayment);
        return newPayment;
    }
}
