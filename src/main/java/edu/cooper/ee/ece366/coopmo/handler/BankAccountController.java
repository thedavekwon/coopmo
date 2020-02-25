package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import org.springframework.web.bind.annotation.*;

import static edu.cooper.ee.ece366.coopmo.CoopmoApplication.bankAccountDB;

@RestController
@RequestMapping("/bank")
public class BankAccountController {
    @GetMapping("createBankAccount")
    public BankAccount createBankAccount(
            @RequestParam(value = "routingNumber", defaultValue = "") Long routingNumber,
            @RequestParam(value = "balance", defaultValue = "") Long balance) {
        BankAccount newBankAccount = new BankAccount(bankAccountDB.size(), routingNumber, balance);
        bankAccountDB.put(newBankAccount.getId(), newBankAccount);
        return newBankAccount;
    }

    @GetMapping("getBalance")
    public long getBalance(
            @RequestParam(value = "bankAccountId", defaultValue = "") Long bankAccountId) {
        return bankAccountDB.get(bankAccountId).getBalance();
    }

    @PostMapping("setBalance")
    public void setBalance(
            @RequestParam(value = "bankAccountId", defaultValue = "") Long bankAccountId,
            @RequestParam(value = "balance", defaultValue = "") Long balance) {
        synchronized (bankAccountDB) {
            BankAccount curBankAccount = bankAccountDB.get(bankAccountId);
            curBankAccount.setBalance(balance);
        }
    }

}
