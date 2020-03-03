package edu.cooper.ee.ece366.coopmo.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
            @RequestParam(value = "balance", defaultValue = "") long balance) {
        if (!checkValidRoutingNumberByDigit(routingNumber))
            return ResponseEntity.badRequest().body("Routing number must be 9 digits");

        JsonObject respBody = new JsonObject();
        ResponseEntity<?> response = checkEmpty(userId, "userId", respBody);
        if (response != null) return response;
        response = checkPositive(balance, "balance", respBody);
        if (response != null) return response;

        BankAccount newBankAccount = bankAccountService.createBankAccount(userId, routingNumber, balance);
        if (newBankAccount == null) {
            respBody.addProperty("message", "Invalid userId or routingNumber");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
        JsonObject bankJson = new JsonObject();
        bankJson.add("bankAccount", new Gson().toJsonTree(newBankAccount));
        respBody.add("messagePayload", bankJson);
        respBody.addProperty("message", "Bankaccount request succeed");
        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
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
