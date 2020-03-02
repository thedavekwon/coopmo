package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.service.PaymentService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/pay")
public class PaymentController extends BaseController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/createPayment")
    public ResponseEntity<String> createPayment(
            @RequestParam(value = "fromUserId", defaultValue = "") String fromUserId,
            @RequestParam(value = "toUserId", defaultValue = "") String toUserId,
            @RequestParam(value = "amount", defaultValue = "") Long amount,
            @RequestParam(value = "type", defaultValue = "") Integer type) {

        ResponseEntity<String> response = checkEmpty(fromUserId, "fromUserId");
        if (response != null) return response;
        response = checkEmpty(toUserId, "toUserId");
        if (response != null) return response;
        response = checkPositive(amount, "amount");
        if (response != null) return response;
        if (type < 0 || type > 3) {
            return ResponseEntity.badRequest().body("Type of transaction invalid");
        }

        int ret = paymentService.createPayment(fromUserId, toUserId, amount, type);
        switch (ret) {
            case -1:
                return ResponseEntity.badRequest().body("Invalid fromUserId");
            case -2:
                return ResponseEntity.badRequest().body("Invalid toUserId");
            case -3:
                return ResponseEntity.badRequest().body("Invalid amount");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Payment has been created");
    }

    @GetMapping("/getLatestPublicPayment")
    @ResponseBody
    public ResponseEntity<?> getLatestPublicPayment() {
        JSONObject respBody = new JSONObject();
        ArrayList<Payment> latestPublicPayment = paymentService.getLatestPublicPayment(10);
        respBody.put("friendList", latestPublicPayment);
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }
}
