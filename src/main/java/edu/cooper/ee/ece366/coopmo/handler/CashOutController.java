package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.CashOut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static edu.cooper.ee.ece366.coopmo.CoopmoApplication.cashOutDB;

@RestController
@RequestMapping("/cashout")
public class CashOutController {
    @GetMapping("createCashOut")
    public CashOut createCashOut(
            @RequestParam(value = "bankAccountId", defaultValue = "") Long bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount) {
        CashOut newCashOut = new CashOut(cashOutDB.size(), bankAccountId, amount);
        cashOutDB.put(newCashOut.getId(), newCashOut);
        return newCashOut;
    }
}
