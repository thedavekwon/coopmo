package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static edu.cooper.ee.ece366.coopmo.CoopmoApplication.paymentDB;

@RestController
@RequestMapping("/pay")
public class PaymentController {
    @GetMapping("/createPayment")
    public Payment createPayment(
            @RequestParam(value = "fromUserId", defaultValue = "") Long fromUserId,
            @RequestParam(value = "toUserId", defaultValue = "") Long toUserId,
            @RequestParam(value = "amount", defaultValue = "") Long amount,
            @RequestParam(value = "type", defaultValue = "") Long type) {
        Payment newPayment = new Payment((long) paymentDB.size(), fromUserId, toUserId, amount, type);
        paymentDB.put(newPayment.getId(), newPayment);
        return newPayment;
    }
}
