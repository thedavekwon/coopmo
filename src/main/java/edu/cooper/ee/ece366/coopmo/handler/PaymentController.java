package edu.cooper.ee.ece366.coopmo.handler;

import com.google.gson.JsonObject;
import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.repository.PaymentRepository;
import edu.cooper.ee.ece366.coopmo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/pay", produces = "application/json")
public class PaymentController extends BaseController {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @PostMapping(path = "/createPayment")
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
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }

        int ret = paymentService.createPayment(fromUserId, toUserId, amount, paymentType);
        switch (ret) {
            case -1:
                respBody.addProperty("message", "Invalid fromUserId");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            case -2:
                respBody.addProperty("message", "Invalid toUserId");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            case -3:
                respBody.addProperty("message", "Invalid amount");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
        respBody.addProperty("message", "Payment request succeed");
        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/getPaymentWithId")
    public Payment getPaymentWithId(@RequestParam(value = "paymentId", defaultValue = "") String paymentId) {
        Optional<Payment> curPayment = paymentRepository.findById(paymentId);
        return curPayment.orElse(null);
    }

    @GetMapping(path = "/getLatestPublicPayment")
    @ResponseBody
    public ResponseEntity<?> getLatestPublicPayment(@RequestParam(value = "n", defaultValue = "") int n) {
        JsonObject respBody = new JsonObject();
        JsonObject friend_json = new JsonObject();
        ResponseEntity<?> response = checkPositive((long) n, "n", respBody);
        if (response != null) return response;
        return new ResponseEntity<>(paymentService.getLatestPublicPayment(n), HttpStatus.OK);
    }

    @GetMapping(path = "/getLatestPrivatePayment")
    @ResponseBody
    public ResponseEntity<?> getLatestPrivatePayment(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "n", defaultValue = "") int n
    ) {
        JsonObject respBody = new JsonObject();
        JsonObject friend_json = new JsonObject();
        ResponseEntity<?> response = checkPositive((long) n, "n", respBody);
        if (response != null) return response;
        response = checkEmpty(userId, "userId", respBody);
        if (response != null) return response;
//        friend_json.add("LatestPrivatePayment", new Gson().toJsonTree(paymentService.getLatestPrivatePayment(userId, n)));
//        respBody.add("messagePayload", friend_json);
//        respBody.addProperty("message", "Successfully got latest private payments");
//        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
        if (response != null) return response;
        return new ResponseEntity<>(paymentService.getLatestPrivatePayment(userId, n), HttpStatus.OK);
    }

    @GetMapping(path = "/getLatestFriendPayment")
    @ResponseBody
    public ResponseEntity<?> getLatestFriendPayment(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "n", defaultValue = "") int n
    ) {
        JsonObject respBody = new JsonObject();
        JsonObject friend_json = new JsonObject();
        ResponseEntity<?> response = checkPositive((long) n, "n", respBody);
        if (response != null) return response;
        response = checkEmpty(userId, "userId", respBody);
        if (response != null) return response;
//        friend_json.add("LatestFriendPayment", new Gson().toJsonTree(paymentService.getLatestFriendPayment(userId, n)));
//        respBody.add("messagePayload", friend_json);
//        respBody.addProperty("message", "Successfully got latest friend payments");
//        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
        if (response != null) return response;
        return new ResponseEntity<>(paymentService.getLatestFriendPayment(userId, n), HttpStatus.OK);
    }
}
