package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.config.MyUserDetails;
import edu.cooper.ee.ece366.coopmo.message.Message;
import edu.cooper.ee.ece366.coopmo.model.Cash;
import edu.cooper.ee.ece366.coopmo.service.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/cash", produces = "application/json")
public class CashController extends BaseController {
    private final CashService cashService;

    @Autowired
    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @PostMapping(path = "/createCash", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> createCash(
            @RequestBody CreateCashRequest createCashRequest)
            throws BaseExceptionHandler.InvalidBalanceException, BaseExceptionHandler.InValidFieldValueException, BaseExceptionHandler.EmptyFieldException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;
        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }
        String bankAccountId = createCashRequest.getBankAccountId();
        long amount = createCashRequest.getAmount();
        String type = createCashRequest.getType();

        Message respMessage = new Message();

        checkEmpty(userId, "userId");
        checkEmpty(bankAccountId, "bankAccountId");
        checkPositive(amount, "amount");

        Cash.CashType cashType;
        try {
            cashType = Cash.CashType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new BaseExceptionHandler.InValidFieldValueException("Invalid Cash Type");
        }

        Cash newCash = cashService.createCash(userId, bankAccountId, amount, cashType);

        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    public static class CreateCashRequest {
        private String bankAccountId;
        private long amount;
        private String type;

        public CreateCashRequest(String bankAccountId, long amount, String type) {
            this.bankAccountId = bankAccountId;
            this.amount = amount;
            this.type = type;
        }

        public String getBankAccountId() {
            return bankAccountId;
        }

        public void setBankAccountId(String bankAccountId) {
            this.bankAccountId = bankAccountId;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
