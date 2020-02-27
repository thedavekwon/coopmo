package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankAccountController {
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("createBankAccount")
    public ResponseEntity<String> createBankAccount(
            @RequestParam(value = "routingNumber", defaultValue = "") Long routingNumber,
            @RequestParam(value = "balance", defaultValue = "") Long balance) {
        if ((routingNumber / 1000000000) < 1) {
            return ResponseEntity.badRequest().body("Routing number must be 9 digits");
        }
        if (balance <= 0) {
            return ResponseEntity.badRequest().body("Balance can not be below 0 dollars");
        }
        BankAccount newBankAccount = new BankAccount(routingNumber, balance);
        bankAccountRepository.save(newBankAccount);

        return ResponseEntity.status(HttpStatus.OK).body("Your new BankAccount has been created");
    }

    @GetMapping("getBalance")
    public ResponseEntity<String> getBalance(
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId) {
        if (!(bankAccountRepository.existsById(bankAccountId))) {
            return ResponseEntity.badRequest().body("Bank Account Id not valid");
        }
        BankAccount curBankAccount = bankAccountRepository.findById(bankAccountId).get();
        return ResponseEntity.status(HttpStatus.OK).body("Your balance is $" + curBankAccount.getBalance());
    }

    @PostMapping("incrementBalance")
    public ResponseEntity<String> incrementBalance(
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount) {
        if (!(bankAccountRepository.existsById(bankAccountId)))
            return ResponseEntity.badRequest().body("Bank Account Id not valid");
        BankAccount curBankAccount = bankAccountRepository.findById(bankAccountId).get();
        curBankAccount.incrementBalance(amount);
        return ResponseEntity.status(HttpStatus.OK).body("Your balance has been set to $" + curBankAccount.getBalance());
    }
}
