package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.CashOut;
import edu.cooper.ee.ece366.coopmo.repository.CashOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static edu.cooper.ee.ece366.coopmo.CoopmoApplication.cashOutDB;

@RestController
@RequestMapping("/cashout")
public class CashOutController {
    private final CashOutRepository cashOutRepository;

    @Autowired
    public CashOutController(CashOutRepository cashOutRepository) {
        this.cashOutRepository = cashOutRepository;
    }


    @GetMapping("createCashOut")
    public CashOut createCashOut(
            @RequestParam(value = "bankAccountId", defaultValue = "") Long bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount) {
        CashOut newCashOut = new CashOut(bankAccountId, amount);
        cashOutDB.put(newCashOut.getId(), newCashOut);
        return newCashOut;
    }
}
