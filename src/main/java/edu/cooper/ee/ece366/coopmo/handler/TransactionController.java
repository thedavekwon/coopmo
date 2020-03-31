package edu.cooper.ee.ece366.coopmo.handler;


import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.message.Message;
import edu.cooper.ee.ece366.coopmo.model.Transaction;
import edu.cooper.ee.ece366.coopmo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/transaction", consumes = "application/json", produces = "application/json")
public class TransactionController extends BaseController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/likeTransaction")
    public ResponseEntity<?> likePayment(@RequestBody LikePaymentRequest likePaymentRequest) throws InValidFieldValueException, IllegalArgumentException, BaseExceptionHandler.EmptyFieldException {
        String userId = likePaymentRequest.getUserId();
        String transactionId = likePaymentRequest.getTransactionId();
        String transactionType = likePaymentRequest.getTransactionType();

        Message respMessage = new Message();

        checkEmpty(userId, "userId");
        checkEmpty(transactionId, "transactionId");
        checkEmpty(transactionType, "transactionType");

        Transaction.TransactionType transactionType1 = Transaction.TransactionType.valueOf(transactionType);
        Transaction curTransaction = transactionService.likePayment(userId, transactionId, transactionType1);
        respMessage.setData(curTransaction);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    public static class LikePaymentRequest {
        private String userId;
        private String transactionId;
        private String transactionType;

        public LikePaymentRequest(String userId, String transactionId, String transactionType) {
            this.userId = userId;
            this.transactionId = transactionId;
            this.transactionType = transactionType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }
    }
}
