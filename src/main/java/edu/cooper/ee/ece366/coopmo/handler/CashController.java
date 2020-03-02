package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.Cash;
import edu.cooper.ee.ece366.coopmo.service.CashService;
import net.minidev.json.JSONObject;
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
    @ResponseBody
    public ResponseEntity<?> createCashOut(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount) {
        JSONObject respBody = new JSONObject();

        ResponseEntity<?> response = checkEmpty(userId, "userId", respBody);
        if (response != null) return response;
        response = checkEmpty(bankAccountId, "bankAccountId", respBody);
        if (response != null) return response;
        response = checkPositive(amount, "amount", respBody);
        if (response != null) return response;

        int ret = cashService.createCashTranscation(userId, bankAccountId, amount, Cash.Out);

        ResponseEntity<?> x = getStringResponseEntity(ret, respBody);
        if (x != null) return x;

        respBody.put("message", "CashOut request succeed");
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    @PostMapping("createCashIn")
    public ResponseEntity<?> createCashIn(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "bankAccountId", defaultValue = "") String bankAccountId,
            @RequestParam(value = "amount", defaultValue = "") Long amount) {
        JSONObject respBody = new JSONObject();
        ResponseEntity<?> response = checkEmpty(userId, "userId", respBody);
        if (response != null) return response;
        response = checkEmpty(bankAccountId, "bankAccountId", respBody);
        if (response != null) return response;
        response = checkPositive(amount, "amount", respBody);
        if (response != null) return response;

        int ret = cashService.createCashTranscation(userId, bankAccountId, amount, Cash.In);

        ResponseEntity<?> x = getStringResponseEntity(ret, respBody);
        if (x != null) return x;

        respBody.put("message", "CashIn request succeed");
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    private ResponseEntity<?> getStringResponseEntity(int ret, JSONObject respBody) {
        switch (ret) {
            case -1:
                respBody.put("message", "Invalid userId");
                return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
            case -2:
                respBody.put("message", "Invalid bankAccountId");
                return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
            case -3:
                respBody.put("message", "Invalid amount");
                return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
