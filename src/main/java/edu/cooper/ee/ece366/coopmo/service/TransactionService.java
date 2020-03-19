package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.model.Cash;
import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.Transaction;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.CashRepository;
import edu.cooper.ee.ece366.coopmo.repository.PaymentRepository;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final CashRepository cashRepository;

    @Autowired
    public TransactionService(CashRepository cashRepository, PaymentRepository paymentRepository, UserRepository userRepository) {
        this.cashRepository = cashRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    public Transaction likePayment(String userId, String transactionId, Transaction.TransactionType transactionType) throws InValidFieldValueException {
        Optional<User> curUser = userRepository.findById(userId);
        if (curUser.isEmpty())
            throw new InValidFieldValueException("Invalid UserId");

        if (transactionType == Transaction.TransactionType.PAY) {
            Optional<Payment> curPayment = paymentRepository.findById(transactionId);
            curPayment.get().getLikes().add(curUser.get());
            paymentRepository.save(curPayment.get());
            return curPayment.get();
        } else {
            Optional<Cash> curCash = cashRepository.findById(transactionId);
            curCash.get().getLikes().add(curUser.get());
            cashRepository.save(curCash.get());
            return curCash.get();
        }
    }
}
