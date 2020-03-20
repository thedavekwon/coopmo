package edu.cooper.ee.ece366.coopmo.handler;


import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldTypeException;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.model.Transaction;
import edu.cooper.ee.ece366.coopmo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/transaction", consumes = "application/json", produces = "application/json")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/likeTransaction")
    public ResponseEntity<?> likePayment(@RequestBody Map<String, String> request) throws InValidFieldTypeException, InValidFieldValueException, IllegalArgumentException {
        if (!request.containsKey("userId") || !request.containsKey("transactionId") || !request.containsKey("transactionType"))
            throw new InValidFieldTypeException("Invalid Request Field");
        Transaction.TransactionType transactionType = Transaction.TransactionType.valueOf(request.get("transactionType"));
        Transaction curTransaction = transactionService.likePayment(request.get("userId"), request.get("transactionId"), transactionType);
        return new ResponseEntity<>(curTransaction, HttpStatus.OK);
    }
}
