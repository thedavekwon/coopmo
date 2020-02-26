package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import org.springframework.web.bind.annotation.*;

import static edu.cooper.ee.ece366.coopmo.CoopmoApplication.bankAccountDB;

@RestController
@RequestMapping("/bank")
public class BankAccountController {
    @GetMapping("createBankAccount")
    public ResponseEntity<String> createBankAccount(
            @RequestParam(value = "routingNumber", defaultValue = "") Long routingNumber,
            @RequestParam(value = "balance", defaultValue = "") Long balance) {
        if((routingNumber / 1000000000.0) < 1){
            return ResponseEntity.badRequest().body("Routing number must be 9 digits");
        }
        if(balance <= 0){
            return ResponseEntity,badRequest().body("Balance can not be below 0 dollars");
        }
        BankAccount newBankAccount = new BankAccount(routingNumber, balance);
        bankAccountDB.put(newBankAccount.getId(), newBankAccount);

        return ResponseEntity.status(HttpStatus.OK).body("Your new BankAccount has been created");
    }

    @GetMapping("getBalance")
    public ResponseEntity<String> getBalance(
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId) {
        if (!(bankAccountDB.containsKey(bankAccountId))) {
            return ResponseEntity.badRequest().body("Bank Account Id not valid");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Your balance is $" + bankAccountDB.get(bankAccountId).getBalance());
    }

    @PostMapping("incrementBalance")
    public ResponseEntity<String> incrementBalance(
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount) {
        if(!(bankAccountDB.containsKey(bankAccountId))){
            return ResponseEntity.badRequest().body("Bank Account Id not valid");
        }

        synchronized (bankAccountDB) {
            BankAccount curBankAccount = bankAccountDB.get(bankAccountId);
            curBankAccount.incrementBalance(amount);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Your balance has been set to $" + curBankAccount.getBalance());
    }

}
