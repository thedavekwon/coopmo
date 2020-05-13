package edu.cooper.ee.ece366.coopmo.handler;


import edu.cooper.ee.ece366.coopmo.config.MyUserDetails;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.message.Message;
import edu.cooper.ee.ece366.coopmo.message.NotificationMessage;
import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.Transaction;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.service.NotificationService;
import edu.cooper.ee.ece366.coopmo.service.TransactionService;
import edu.cooper.ee.ece366.coopmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/transaction", consumes = "application/json", produces = "application/json")
public class TransactionController extends BaseController {
    private final TransactionService transactionService;
    private final NotificationService notificationService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, NotificationService notificationService,
                                 UserService userService) {
        this.transactionService = transactionService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @PostMapping(path = "/likeTransaction", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> likePayment(@RequestBody LikePaymentRequest likePaymentRequest) throws InValidFieldValueException, IllegalArgumentException, BaseExceptionHandler.EmptyFieldException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;
        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }



        String transactionId = likePaymentRequest.getTransactionId();
        String transactionType = likePaymentRequest.getTransactionType();

        System.out.println(userId);
        System.out.println(transactionId);
        System.out.println(transactionType);

        Message respMessage = new Message();

        checkEmpty(transactionId, "transactionId");
        checkEmpty(transactionType, "transactionType");

        Transaction.TransactionType transactionType1 = Transaction.TransactionType.valueOf(transactionType);
        Transaction curTransaction = transactionService.likePayment(userId, transactionId, transactionType1);
        respMessage.setData(curTransaction);
        if (curTransaction instanceof Payment) {
            User user = userService.checkValidUserId(userId);
            notificationService.notify(new NotificationMessage(user, "PAYMENTLIKE"),
                    ((Payment) curTransaction).getFromUser().getUsername());
            notificationService.notify(new NotificationMessage(user, "PAYMENTLIKE"),
                    ((Payment) curTransaction).getToUser().getUsername());
        }
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    public static class LikePaymentRequest {
        private String transactionId;
        private String transactionType;

        public LikePaymentRequest(String transactionId, String transactionType) {
            this.transactionId = transactionId;
            this.transactionType = transactionType;
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
