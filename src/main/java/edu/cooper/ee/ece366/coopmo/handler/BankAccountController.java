package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static edu.cooper.ee.ece366.coopmo.CoopmoApplication.bankAccountDB;

@RestController
@RequestMapping("/bank")
public class BankAccountController {
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("createBankAccount")
    public BankAccount createBankAccount(
            @RequestParam(value = "routingNumber", defaultValue = "") Long routingNumber,
            @RequestParam(value = "balance", defaultValue = "") Long balance) {
        BankAccount newBankAccount = new BankAccount(routingNumber, balance);
        bankAccountDB.put(newBankAccount.getId(), newBankAccount);
        return newBankAccount;
    }

    @GetMapping("getBalance")
    public long getBalance(
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId) {
        return bankAccountDB.get(bankAccountId).getBalance();
    }

    @PostMapping("setBalance")
    public void setBalance(
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId,
            @RequestParam(value = "balance", defaultValue = "") Long balance) {
        synchronized (bankAccountDB) {
            BankAccount curBankAccount = bankAccountDB.get(bankAccountId);
            curBankAccount.setBalance(balance);
        }
    }

}
