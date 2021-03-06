package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.config.MyUserDetails;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.EmptyFieldException;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.message.Message;
import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/bank", consumes = "application/json", produces = "application/json")
public class BankAccountController extends BaseController {
    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/createBankAccount")
    public ResponseEntity<?> createBankAccount(@RequestBody CreateBankAccountRequest bankAccountRequest) throws InValidFieldValueException, EmptyFieldException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;
        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }
        if (!checkValidRoutingNumberByDigit(bankAccountRequest.getRoutingNumber()))
            throw new InValidFieldValueException("Invalid Routing Number");

        bankAccountRequest.setUserId(userId);
        if (bankAccountRequest.getBalance() == null)
            throw new EmptyFieldException("Empty Field");

        Message respMessage = new Message();
        BankAccount bankAccount = bankAccountService.createBankAccount(bankAccountRequest);
        respMessage.setData(bankAccount);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @PostMapping("/getBankAccountBalance")
    public ResponseEntity<?> getBankAccountBalance(@RequestBody String bankAccountId) throws EmptyFieldException, InValidFieldValueException {
        if (bankAccountId.isEmpty())
            throw new EmptyFieldException("Empty Bank Account Id");
        Message respMessage = new Message();
        respMessage.setData(bankAccountService.getBankAccountBalance(bankAccountId));
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    private boolean checkValidRoutingNumberByDigit(@RequestParam(value = "routingNumber", defaultValue = "") long routingNumber) {
        return (routingNumber / 1000000000) < 1;
    }

    public static class CreateBankAccountRequest {
        private String userId;
        private final String nickname;
        private final long routingNumber;
        private final long balance;
        private final long accountNumber;

        public CreateBankAccountRequest(String nickname, long accountNumber, long routingNumber, long balance) {
            this.nickname = nickname;
            this.routingNumber = routingNumber;
            this.balance = balance;
            this.accountNumber = accountNumber;
        }

        public String getNickname() {
            return nickname;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public long getRoutingNumber() {
            return routingNumber;
        }

        public long getAccountNumber() {
            return accountNumber;
        }

        public Long getBalance() {
            return balance;
        }
    }
}
