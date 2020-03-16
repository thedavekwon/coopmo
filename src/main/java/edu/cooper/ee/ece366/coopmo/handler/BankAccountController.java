package edu.cooper.ee.ece366.coopmo.handler;

import com.google.gson.JsonObject;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.EmptyFieldException;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldException;
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
    public ResponseEntity<?> createBankAccount(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "routingNumber", defaultValue = "") long routingNumber,
            @RequestParam(value = "balance", defaultValue = "") long balance) throws InValidFieldException, EmptyFieldException {
        if (!checkValidRoutingNumberByDigit(routingNumber))
            throw new InValidFieldException("Invalid Routing Number");

        if (userId.isEmpty() || balance < 0)
            throw new EmptyFieldException("Empty Field");

        BankAccount newBankAccount = bankAccountService.createBankAccount(userId, routingNumber, balance);
        return new ResponseEntity<>(newBankAccount, HttpStatus.OK);
    }

    @GetMapping("getBalance")
    public ResponseEntity<?> getBalance(
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId) {
        JsonObject respBody = new JsonObject();
        ResponseEntity<?> response = checkEmpty(bankAccountId, "userId", respBody);
        if (response != null) return null;

        long ret = bankAccountService.getBalance(bankAccountId);
        if (ret == -1) {
            respBody.addProperty("message", "Invalid bankAccountId");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
        respBody.addProperty("message", "Bankaccount getBalance request succeed");
        respBody.addProperty("balance", ret);
        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
    }

    private boolean checkValidRoutingNumberByDigit(@RequestParam(value = "routingNumber", defaultValue = "") Long routingNumber) {
        return (routingNumber / 1000000000) < 1;
    }
}
