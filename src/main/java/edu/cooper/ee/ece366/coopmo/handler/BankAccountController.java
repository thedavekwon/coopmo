package edu.cooper.ee.ece366.coopmo.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.EmptyFieldException;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/bank", produces = "application/json")
public class BankAccountController extends BaseController {
    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("createBankAccount")
    public ResponseEntity<?> createBankAccount(@RequestBody CreateBankAccountRequest bankAccountRequest) throws InValidFieldValueException, EmptyFieldException {
        if (!checkValidRoutingNumberByDigit(bankAccountRequest.getRoutingNumber()))
            throw new InValidFieldValueException("Invalid Routing Number");

        if (bankAccountRequest.getUserId().isEmpty() || bankAccountRequest.getBalance() < 0)
            throw new EmptyFieldException("Empty Field");

        BankAccount bankAccount = bankAccountService.createBankAccount(bankAccountRequest);
        return new ResponseEntity<>(bankAccount, HttpStatus.OK);
    }

    @PostMapping("getBankAccountBalance")
    public ResponseEntity<?> getBankAccountBalance(@RequestBody String bankAccountId) throws EmptyFieldException, InValidFieldValueException {
        if (bankAccountId.isEmpty())
            throw new EmptyFieldException("Empty Bank Account Id");
        long ret = bankAccountService.getBankAccountBalance(bankAccountId);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    private boolean checkValidRoutingNumberByDigit(@RequestParam(value = "routingNumber", defaultValue = "") Long routingNumber) {
        return (routingNumber / 1000000000) < 1;
    }

    public static class CreateBankAccountRequest {
        @JsonProperty("userId")
        private String userId;

        @JsonProperty("routingNumber")
        private Long routingNumber;

        @JsonProperty("balance")
        private Long balance;

        public CreateBankAccountRequest(String userId, Long routingNumber, Long balance) {
            this.userId = userId;
            this.routingNumber = routingNumber;
            this.balance = balance;
        }

        public String getUserId() {
            return userId;
        }

        public Long getRoutingNumber() {
            return routingNumber;
        }

        public Long getBalance() {
            return balance;
        }
    }
}
