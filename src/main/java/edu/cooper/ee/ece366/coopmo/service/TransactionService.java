package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.model.Cash;
import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.Transaction;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.CashRepository;
import edu.cooper.ee.ece366.coopmo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

@Service
public class TransactionService {
    private final UserService userService;
    private final CashService cashService;
    private final PaymentService paymentService;

    private final CashRepository cashRepository;
    private final PaymentRepository paymentRepository;


    @Autowired
    public TransactionService(UserService userService, CashService cashService, PaymentService paymentService,
                              CashRepository cashRepository, PaymentRepository paymentRepository) {
        this.userService = userService;
        this.cashService = cashService;
        this.paymentService = paymentService;
        this.cashRepository = cashRepository;
        this.paymentRepository = paymentRepository;
    }

    public Transaction likePayment(String userId, String transactionId, Transaction.TransactionType transactionType)
            throws InValidFieldValueException {
        User curUser = userService.checkValidUserId(userId);
        if (transactionType == Transaction.TransactionType.PAY) {
            Payment curPayment = paymentService.checkValidPaymentId(transactionId);
            curPayment.getLikes().add(curUser);
            paymentRepository.save(curPayment);
            return curPayment;
        } else {
            Cash curCash = cashService.checkValidCashId(transactionId);
            curCash.getLikes().add(curUser);
            cashRepository.save(curCash);
            return curCash;
        }
    }

    // TODO(use heap)
    public Set<Transaction> getLatestTransaction(String userId, int n) throws InValidFieldValueException {
        TreeSet<Transaction> transactions = new TreeSet<>();
        User curUser = userService.checkValidUserId(userId);
        transactions.addAll(curUser.getFromPaymentSet());
        transactions.addAll(curUser.getToPaymentSet());
        transactions.addAll(curUser.getCashSet());
        if (transactions.size() < n) return transactions.descendingSet();
        Set<Transaction> ret = new TreeSet<>();
        Iterator<Transaction> it = transactions.descendingIterator();
        while (it.hasNext()) {
            ret.add(it.next());
            if (ret.size() >= n) break;
        }
        return ret;
    }
}
