package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.message.Message;
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

    @PostMapping(path = "/createCash", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> createCash(
            @RequestBody CreateCashRequest createCashRequest)
            throws BaseExceptionHandler.InvalidBalanceException, BaseExceptionHandler.InValidFieldValueException, BaseExceptionHandler.EmptyFieldException {
        String userId = createCashRequest.getUserId();
        String bankAccountId = createCashRequest.getBankAccountId();
        Long amount = createCashRequest.getAmount();
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
        private String userId;
        private String bankAccountId;
        private Long amount;
        private String type;

        public CreateCashRequest(String userId, String bankAccountId, Long amount, String type) {
            this.userId = userId;
            this.bankAccountId = bankAccountId;
            this.amount = amount;
            this.type = type;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getBankAccountId() {
            return bankAccountId;
        }

        public void setBankAccountId(String bankAccountId) {
            this.bankAccountId = bankAccountId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
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
