package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.message.Message;
import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.service.PaymentService;
import edu.cooper.ee.ece366.coopmo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@CrossOrigin
@RequestMapping(path = "/pay", produces = "application/json")
public class PaymentController extends BaseController {
    private final PaymentService paymentService;
    private final TransactionService transactionService;

    @Autowired
    public PaymentController(PaymentService paymentService, TransactionService transactionService) {
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/createPayment", consumes = "application/json")
    public ResponseEntity<?> createPayment(
            @RequestBody CreatePaymentRequest createPaymentRequest) throws InValidFieldValueException, BaseExceptionHandler.InvalidBalanceException, BaseExceptionHandler.EmptyFieldException, BaseExceptionHandler.InValidFieldTypeException {
        String fromUserId = createPaymentRequest.getFromUserId();
        String toUserId = createPaymentRequest.getToUserId();
        Long amount = createPaymentRequest.getAmount();
        String type = createPaymentRequest.getType();
        String comment = createPaymentRequest.getComment();

        Message respMessage = new Message();

        checkEmpty(fromUserId, "fromUserId");
        checkEmpty(toUserId, "toUserId");
        checkPositive(amount, "amount");


        Payment.PaymentType paymentType;
        try {
            paymentType = Payment.PaymentType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new BaseExceptionHandler.InValidFieldTypeException("Invalid Payment Type");
        }

        Payment newPayment = paymentService.createPayment(fromUserId, toUserId, amount, paymentType, comment);
        respMessage.setData(newPayment);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getLatestPublicPayment")
    @ResponseBody
    public ResponseEntity<?> getLatestPublicPayment() {
        Message respMessage = new Message();
        respMessage.setData(paymentService.getLatestPublicPayment());
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getLatestPublicPaymentFrom")
    @ResponseBody
    public ResponseEntity<?> getLatestPublicPaymentFrom(
            @RequestParam(value = "timestamp", defaultValue = "") Timestamp timestamp
    ) {
        Message respMessage = new Message();
        respMessage.setData(paymentService.getLatestPublicPaymentFrom(timestamp));
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getLatestPrivatePayment")
    @ResponseBody
    public ResponseEntity<?> getLatestPrivatePayment(
            @RequestParam(value = "userId", defaultValue = "") String userId
    ) throws InValidFieldValueException, BaseExceptionHandler.EmptyFieldException {
        Message respMessage = new Message();
        checkEmpty(userId, "userId");
        respMessage.setData(transactionService.getLatestTransaction(userId));
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getLatestPrivatePaymentFrom")
    @ResponseBody
    public ResponseEntity<?> getLatestPrivatePaymentFrom(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "timestamp", defaultValue = "") Timestamp timestamp
    ) throws InValidFieldValueException, BaseExceptionHandler.EmptyFieldException {
        Message respMessage = new Message();
        checkEmpty(userId, "userId");
        respMessage.setData(transactionService.getLatestTransactionFrom(userId, timestamp));
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getLatestFriendPaymentFrom")
    @ResponseBody
    public ResponseEntity<?> getLatestFriendPayment(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "timestamp", defaultValue = "") Timestamp timestamp
    ) throws InValidFieldValueException, BaseExceptionHandler.EmptyFieldException {
        Message respMessage = new Message();
        checkEmpty(userId, "userId");

        respMessage.setData(paymentService.getLatestFriendPaymentFrom(userId, timestamp));
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getLatestFriendPayment")
    @ResponseBody
    public ResponseEntity<?> getLatestFriendPayment(
            @RequestParam(value = "userId", defaultValue = "") String userId
    ) throws InValidFieldValueException, BaseExceptionHandler.EmptyFieldException {
        Message respMessage = new Message();
        checkEmpty(userId, "userId");

        respMessage.setData(paymentService.getLatestFriendPayment(userId));
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    public static class CreatePaymentRequest {
        private final String fromUserId;
        private final String toUserId;
        private final Long amount;
        private final String type;
        private final String comment;

        public CreatePaymentRequest(String fromUserId, String toUserId, Long amount, String type, String comment) {
            this.fromUserId = fromUserId;
            this.toUserId = toUserId;
            this.amount = amount;
            this.type = type;
            this.comment = comment;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public String getToUserId() {
            return toUserId;
        }

        public Long getAmount() {
            return amount;
        }

        public String getType() {
            return type;
        }

        public String getComment() {
            return comment;
        }
    }

}
