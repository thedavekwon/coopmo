package edu.cooper.ee.ece366.coopmo.handler;

import com.google.gson.JsonObject;
import edu.cooper.ee.ece366.coopmo.model.Cash;
import edu.cooper.ee.ece366.coopmo.service.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cash", produces = "application/json")
public class CashController extends BaseController {
    private final CashService cashService;

    @Autowired
    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @PostMapping("createCash")
    @ResponseBody
    public ResponseEntity<?> createCash(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount,
            @RequestParam(value = "type", defaultValue = "") String type)
            throws BaseExceptionHandler.InvalidBalanceException, BaseExceptionHandler.InValidFieldValueException {
        JsonObject respBody = new JsonObject();

        ResponseEntity<?> response = checkEmpty(userId, "userId", respBody);
        if (response != null) return response;
        response = checkEmpty(bankAccountId, "bankAccountId", respBody);
        if (response != null) return response;
        response = checkPositive(amount, "amount", respBody);
        if (response != null) return response;

        Cash.CashType cashType;
        try {
            cashType = Cash.CashType.valueOf(type);
        } catch (IllegalArgumentException e) {
            respBody.addProperty("message", "Invalid Cash Type");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }

        Cash newCash = cashService.createCash(userId, bankAccountId, amount, cashType);

        respBody.addProperty("message", "CashOut request succeed");
        if (cashType == Cash.CashType.OUT) {
            respBody.addProperty("message", "CashOut request succeed");
        } else {
            respBody.addProperty("message", "CashIn request succeed");
        }
        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
    }
}
