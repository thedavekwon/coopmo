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
    public ResponseEntity<?> createPayment(
            @RequestParam(value = "fromUserId", defaultValue = "") String fromUserId,
            @RequestParam(value = "toUserId", defaultValue = "") String toUserId,
            @RequestParam(value = "amount", defaultValue = "") Long amount,
            @RequestParam(value = "type", defaultValue = "") Integer type) {
        JSONObject respBody = new JSONObject();
        ResponseEntity<?> response = checkEmpty(fromUserId, "fromUserId", respBody);
        if (response != null) return response;
        response = checkEmpty(toUserId, "toUserId", respBody);
        if (response != null) return response;
        response = checkPositive(amount, "amount", respBody);
        if (response != null) return response;
        if (type < 0 || type > 3) {
            respBody.put("message", "Invalid Payment Type");
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }

        int ret = paymentService.createPayment(fromUserId, toUserId, amount, type);
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
    public ResponseEntity<?> getLatestPublicPayment() {
        JSONObject respBody = new JSONObject();
        ArrayList<Payment> latestPublicPayment = paymentService.getLatestPublicPayment(10);
        respBody.put("friendList", latestPublicPayment);
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }
}
