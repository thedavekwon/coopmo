package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.CashOut;
import edu.cooper.ee.ece366.coopmo.repository.CashOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cashout")
public class CashOutController {
    private final CashOutRepository cashOutRepository;

    @Autowired
    public CashOutController(CashOutRepository cashOutRepository) {
        this.cashOutRepository = cashOutRepository;
    }


    @GetMapping("createCashOut")
    public ResponseEntity<String> createCashOut(
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount) {
        // TODO (move to service layer)
//        if (!(bankAccountDB.containsKey(bankAccountId))) {
//            return ResponseEntity.badRequest().body("Bank Account Id not valid");
//        }
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount can not be below 0 dollars");
        }
        //
        CashOut newCashOut = new CashOut(bankAccountId, amount);
        cashOutRepository.save(newCashOut);
        return ResponseEntity.status(HttpStatus.OK).body("Your new CashOut request has been created");
    }
}
