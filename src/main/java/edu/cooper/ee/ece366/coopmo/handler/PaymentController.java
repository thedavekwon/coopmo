package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PaymentController {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/createPayment")
    public ResponseEntity<String> createPayment(
            @RequestParam(value = "fromUserId", defaultValue = "") String fromUserId,
            @RequestParam(value = "toUserId", defaultValue = "") String toUserId,
            @RequestParam(value = "amount", defaultValue = "") Long amount,
            @RequestParam(value = "type", defaultValue = "") Integer type) {
//        if (!(userDB.containsKey(fromUserId))) {
//            return ResponseEntity.badRequest().body("User Id not valid");
//        }
//
//        if (!(userDB.containsKey(toUserId))) {
//            return ResponseEntity.badRequest().body("User Id not valid");
//        }
//
//        if ((userDB.get(fromUserId) == (userDB.get(toUserId)))) {
//            return ResponseEntity.badRequest().body("fromUserID and toUserID fields can not be the same");
//        }

        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount can not be below 0 dollars");
        }

//        if (userDB.get(fromUserId).getBalance() < amount) {
//            return ResponseEntity.badRequest().body("From User does not have enough balance");
//        }

        if (type < 0 || type > 3) {
            return ResponseEntity.badRequest().body("Type of transaction invalid");
        }

        Payment newPayment = new Payment(fromUserId, toUserId, amount, type);
        paymentRepository.save(newPayment);
        return ResponseEntity.status(HttpStatus.OK).body("Payment has been created");
    }
}
