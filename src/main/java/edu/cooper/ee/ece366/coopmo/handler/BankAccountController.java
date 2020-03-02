package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.service.BankAccountService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankAccountController extends BaseController {
    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("createBankAccount")
    public ResponseEntity<?> createBankAccount(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "routingNumber", defaultValue = "") Long routingNumber,
            @RequestParam(value = "balance", defaultValue = "") Long balance) {
        if (checkValidRoutingNumberByDigit(routingNumber))
            return ResponseEntity.badRequest().body("Routing number must be 9 digits");

        JSONObject respBody = new JSONObject();
        ResponseEntity<?> response = checkEmpty(userId, "userId", respBody);
        if (response != null) return response;
        response = checkPositive(balance, "balance", respBody);
        if (response != null) return response;

        int ret = bankAccountService.createBankAccount(userId, routingNumber, balance);
        switch (ret) {
            case -1:
                respBody.put("message", "Invalid userId");
                return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
            case -2:
                respBody.put("message", "Invalid routingNumber");
                return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }
        respBody.put("message", "Bankaccount request succeed");
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    @GetMapping("getBalance")
    public ResponseEntity<?> getBalance(
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId) {
        JSONObject respBody = new JSONObject();
        ResponseEntity<?> response = checkEmpty(bankAccountId, "userId", respBody);
        if (response != null) return null;

        long ret = bankAccountService.getBalance(bankAccountId);
        if (ret == -1) {
            respBody.put("message", "Invalid bankAccountId");
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }
        respBody.put("message", "Bankaccount getBalance request succeed");
        respBody.put("balance", ret);
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    private boolean checkValidRoutingNumberByDigit(@RequestParam(value = "routingNumber", defaultValue = "") Long routingNumber) {
        return (routingNumber / 1000000000) < 1;
    }
}
