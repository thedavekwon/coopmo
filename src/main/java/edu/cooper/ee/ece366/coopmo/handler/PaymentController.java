package edu.cooper.ee.ece366.coopmo.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.service.PaymentService;
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
        JsonObject respBody = new JsonObject();
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
            respBody.addProperty("message", "Invalid Payment Type");
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }

        int ret = paymentService.createPayment(fromUserId, toUserId, amount, paymentType);
        switch (ret) {
            case -1:
                respBody.addProperty("message", "Invalid fromUserId");
                return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
            case -2:
                respBody.addProperty("message", "Invalid toUserId");
                return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
            case -3:
                respBody.addProperty("message", "Invalid amount");
                return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }
        respBody.addProperty("message", "Payment request succeed");
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    @GetMapping("/getLatestPublicPayment")
    @ResponseBody
    public ResponseEntity<?> getLatestPublicPayment(@RequestParam(value = "n", defaultValue = "") long n) {
        JsonObject respBody = new JsonObject();
        JsonObject friend_json = new JsonObject();
        ResponseEntity<?> response = checkPositive(n, "n", respBody);
        if (response != null) return response;
        friend_json.add("friendList", new Gson().toJsonTree(paymentService.getLatestPublicPayment(n)));
        respBody.add("messagePayload", friend_json);
        respBody.addProperty("message", "Successfully got latest public payments");
        respBody.put("data", paymentService.getLatestPublicPayment(n));
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    @GetMapping("/getLatestPrivatePayment")
    @ResponseBody
    public ResponseEntity<?> getLatestPrivatePayment(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "n", defaultValue = "") int n
    ) {
        JsonObject respBody = new JsonObject();
        JsonObject friend_json = new JsonObject();
        ResponseEntity<?> response = checkPositive(n, "n", respBody);
        JSONObject respBody = new JSONObject();
        ResponseEntity<?> response = checkPositive((long) n, "n", respBody);
        if (response != null) return response;
        response = checkEmpty(userId, "userId", respBody);
        if (response != null) return response;
        respBody.put("data", paymentService.getLatestPrivatePayment(userId, n));
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    @GetMapping("/getLatestFriendPayment")
    @ResponseBody
    public ResponseEntity<?> getLatestFriendPayment(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "n", defaultValue = "") int n
    ) {
        JSONObject respBody = new JSONObject();
        ResponseEntity<?> response = checkPositive((long) n, "n", respBody);
        if (response != null) return response;
        response = checkEmpty(userId, "userId", respBody);
        if (response != null) return response;
        respBody.put("data", paymentService.getLatestFriendPayment(userId, n));
        friend_json.add("friendList", new Gson().toJsonTree(paymentService.getLatestPrivatePayment(userId, n)));
        respBody.add("messagePayload", friend_json);
        respBody.addProperty("message", "Successfully got latest private payments");
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }
}
