package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.CashOut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CashOutController {
    @GetMapping("CashOut")
    public CashOut createCashOut(
            @RequestParam(value = "bankAccountId", defaultValue = "") Long bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount) {
        CashOut newCashOut = new CashOut(bankAccountId, amount);
        return newCashOut;
    }
}
