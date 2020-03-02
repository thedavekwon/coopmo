package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.service.PaymentService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
public class PaymentController extends BaseController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/createPayment")
    public ResponseEntity<?> createPayment(
            @RequestParam(value = "fromUserId", defaultValue = "") String fromUserId,
            @RequestParam(value = "toUserId", defaultValue = "") String toUserId,
            @RequestParam(value = "amount", defaultValue = "") Long amount,
            @RequestParam(value = "type", defaultValue = "") String type) {
        JSONObject respBody = new JSONObject();
        ResponseEntity<?> response = checkEmpty(fromUserId, "fromUserId", respBody);
        if (response != null) return response;
        response = checkEmpty(toUserId, "toUserId", respBody);
        if (response != null) return response;
        response = checkPositive(amount, "amount", respBody);
        if (response != null) return response;
        Payment.PaymentType paymentType;
        try {
            paymentType = Payment.PaymentType.valueOf(type);
        } catch (IllegalArgumentException e) {
            respBody.put("message", "Invalid Payment Type");
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }

        int ret = paymentService.createPayment(fromUserId, toUserId, amount, paymentType);
        switch (ret) {
            case -1:
                respBody.put("message", "Invalid fromUserId");
                return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
            case -2:
                respBody.put("message", "Invalid toUserId");
                return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
            case -3:
                respBody.put("message", "Invalid amount");
                return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }
        respBody.put("message", "Payment request succeed");
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    @GetMapping("/getLatestPublicPayment")
    @ResponseBody
    public ResponseEntity<?> getLatestPublicPayment(@RequestParam(value = "n", defaultValue = "") long n) {
        JSONObject respBody = new JSONObject();
        ResponseEntity<?> response = checkPositive(n, "n", respBody);
        if (response != null) return response;
        respBody.put("friendList", paymentService.getLatestPublicPayment(n));
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    @GetMapping("/getLatestPrivatePayment")
    @ResponseBody
    public ResponseEntity<?> getLatestPrivatePayment(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "n", defaultValue = "") long n
    ) {
        JSONObject respBody = new JSONObject();
        ResponseEntity<?> response = checkPositive(n, "n", respBody);
        if (response != null) return response;
        response = checkEmpty(userId, "userId", respBody);
        if (response != null) return response;
        respBody.put("friendList", paymentService.getLatestPrivatePayment(userId, n));
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }
}
