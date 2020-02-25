package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {
    @GetMapping("BankAccount")
    public BankAccount createBankAccount(
            @RequestParam(value = "routingNumber", defaultValue = "") Long routingNumber,
            @RequestParam(value = "balance", defaultValue = "") Long balance) {
        BankAccount newBankAccount = new BankAccount(routingNumber, balance);
        return newBankAccount;
    }
}
