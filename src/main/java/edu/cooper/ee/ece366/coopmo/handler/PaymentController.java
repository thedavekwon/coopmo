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

        Payment newPayment = paymentService.createPayment(fromUserId, toUserId, amount, paymentType);
        respMessage.setData(newPayment);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getLatestPublicPayment")
    @ResponseBody
    public ResponseEntity<?> getLatestPublicPayment(@RequestParam(value = "n", defaultValue = "") int n) throws InValidFieldValueException {
        Message respMessage = new Message();
        checkPositive((long) n, "n");
        respMessage.setData(paymentService.getLatestPublicPayment(n));
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getLatestPrivatePayment")
    @ResponseBody
    public ResponseEntity<?> getLatestPrivatePayment(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "n", defaultValue = "") int n
    ) throws InValidFieldValueException, BaseExceptionHandler.EmptyFieldException {
        Message respMessage = new Message();
        checkPositive((long) n, "n");
        checkEmpty(userId, "userId");
        respMessage.setData(transactionService.getLatestTransaction(userId, n));
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getLatestFriendPayment")
    @ResponseBody
    public ResponseEntity<?> getLatestFriendPayment(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "n", defaultValue = "") int n
    ) throws InValidFieldValueException, BaseExceptionHandler.EmptyFieldException {
        Message respMessage = new Message();
        checkPositive((long) n, "n");
        checkEmpty(userId, "userId");

        respMessage.setData(paymentService.getLatestFriendPayment(userId, n));
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    public static class CreatePaymentRequest {
        private String fromUserId;
        private String toUserId;
        private Long amount;
        private String type;

        public CreatePaymentRequest(String fromUserId, String toUserId, Long amount, String type) {
            this.fromUserId = fromUserId;
            this.toUserId = toUserId;
            this.amount = amount;
            this.type = type;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
