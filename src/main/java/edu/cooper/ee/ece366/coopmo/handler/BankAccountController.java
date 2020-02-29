package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.service.BankAccountService;
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
    public ResponseEntity<String> createBankAccount(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "routingNumber", defaultValue = "") Long routingNumber,
            @RequestParam(value = "balance", defaultValue = "") Long balance) {
        if (checkValidRoutingNumberByDigit(routingNumber))
            return ResponseEntity.badRequest().body("Routing number must be 9 digits");

        ResponseEntity<String> response = checkEmpty(userId, "userId");
        if (response != null) return response;
        response = checkPositive(balance, "balance");
        if (response != null) return response;
        int ret = bankAccountService.createBankAccount(userId, routingNumber, balance);
        switch (ret) {
            case -1:
                return ResponseEntity.badRequest().body("Invalid userId");
            case -2:
                return ResponseEntity.badRequest().body("Invalid routingNumber");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Your new BankAccount has been created");
    }

    @GetMapping("getBalance")
    public ResponseEntity<String> getBalance(
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId) {
        ResponseEntity<String> response = checkEmpty(bankAccountId, "userId");
        if (response != null) return null;

        long ret = bankAccountService.getBalance(bankAccountId);
        if (ret == -1) {
            return ResponseEntity.badRequest().body("Invalid bankAccountId");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Your balance is $" + ret);
    }

    private boolean checkValidRoutingNumberByDigit(@RequestParam(value = "routingNumber", defaultValue = "") Long routingNumber) {
        return (routingNumber / 1000000000) < 1;
    }
}
