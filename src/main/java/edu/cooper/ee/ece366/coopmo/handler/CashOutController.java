package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.CashOut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static edu.cooper.ee.ece366.coopmo.CoopmoApplication.bankAccountDB;
import static edu.cooper.ee.ece366.coopmo.CoopmoApplication.cashOutDB;

@RestController
@RequestMapping("/cashout")
public class CashOutController {
    @GetMapping("createCashOut")
    public ResponseEntity<String> createCashOut(
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount) {

        if (!(bankAccountDB.containsKey(bankAccountId))) {
            return ResponseEntity.badRequest().body("Bank Account Id not valid");
        }
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount can not be below 0 dollars");
        }
        //
        CashOut newCashOut = new CashOut(bankAccountId, amount);
        cashOutDB.put(newCashOut.getId(), newCashOut);
        return ResponseEntity.status(HttpStatus.OK).body("Your new CashOut request has been created");
    }
}
