package edu.cooper.ee.ece366.coopmo.handler;

import com.google.gson.JsonObject;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.service.PaymentService;
import edu.cooper.ee.ece366.coopmo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pay", produces = "application/json")
public class PaymentController extends BaseController {
    private final PaymentService paymentService;
    private final TransactionService transactionService;

    @Autowired
    public PaymentController(PaymentService paymentService, TransactionService transactionService) {
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }

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
    ) throws InValidFieldValueException {
        JsonObject respBody = new JsonObject();
        JsonObject friend_json = new JsonObject();
        ResponseEntity<?> response = checkPositive((long) n, "n", respBody);
        if (response != null) return response;
        response = checkEmpty(userId, "userId", respBody);
        if (response != null) return response;
        return new ResponseEntity<>(transactionService.getLatestTransaction(userId, n), HttpStatus.OK);
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
