package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.Cash;
import edu.cooper.ee.ece366.coopmo.service.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cash")
public class CashController extends BaseController {
    private final CashService cashService;

    @Autowired
    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @GetMapping("createCashOut")
    public ResponseEntity<String> createCashOut(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount) {

        ResponseEntity<String> response = checkEmpty(userId, "userId");
        if (response != null) return response;
        response = checkEmpty(bankAccountId, "bankAccountId");
        if (response != null) return response;
        response = checkPositive(amount, "amount");
        if (response != null) return response;

        int ret = cashService.createCashTranscation(userId, bankAccountId, amount, Cash.Out);

        ResponseEntity<String> x = getStringResponseEntity(ret);
        if (x != null) return x;

        return ResponseEntity.status(HttpStatus.OK).body("Your new CashOut request has been created");
    }

    @PostMapping("createCashIn")
    public ResponseEntity<String> createCashIn(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount) {

        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount can not be below 0 dollars");
        }

        int ret = cashService.createCashTranscation(userId, bankAccountId, amount, Cash.In);

        ResponseEntity<String> x = getStringResponseEntity(ret);
        if (x != null) return x;

        return ResponseEntity.status(HttpStatus.OK).body("Your new CashIn request has been created");
    }

    private ResponseEntity<String> getStringResponseEntity(int ret) {
        switch (ret) {
            case -1:
                return ResponseEntity.badRequest().body("Invalid userId");
            case -2:
                return ResponseEntity.badRequest().body("Invalid bankAccountId");
            case -3:
                return ResponseEntity.badRequest().body("Invalid amount");
        }
        return null;
    }
}
