package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler;
import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.Transaction;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class PaymentService {
    private final UserService userService;

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(UserService userService, PaymentRepository paymentRepository) {
        this.userService = userService;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment createPayment(String fromUserId, String toUserId, long amount, Payment.PaymentType type, String comment)
            throws BaseExceptionHandler.InValidFieldValueException, BaseExceptionHandler.InvalidBalanceException {
        User fromUser = userService.checkValidUserId(fromUserId);
        User toUser = userService.checkValidUserId(toUserId);
        if (fromUser.getBalance() < amount)
            throw new BaseExceptionHandler.InvalidBalanceException("from User Balance is less than requested amount");

        Payment newPayment = new Payment(fromUser, toUser, amount, type, comment);
        fromUser.decrementBalance(amount);
        toUser.incrementBalance(amount);
        paymentRepository.save(newPayment);
        return newPayment;
    }

    public ArrayList<Payment> getLatestPublicPayment() {
        return (ArrayList<Payment>) paymentRepository.findTop20ByTypeEqualsOrderByTimestampDesc(Payment.PaymentType.PUBLIC);
    }

    public ArrayList<Payment> getLatestPublicPaymentFrom(Timestamp timestamp) {
        return (ArrayList<Payment>) paymentRepository.findTop20ByTypeEqualsAndTimestampLessThanOrderByTimestampDesc(Payment.PaymentType.PUBLIC, timestamp);
    }

    public ArrayList<Payment> getLatestFriendPayment(String userId) throws BaseExceptionHandler.InValidFieldValueException {
        User curUser = userService.checkValidUserId(userId);

        TreeSet<Payment> paymentList = new TreeSet<>(paymentRepository.getLatestFriendPayment(curUser.getId()));
        for (User friend : curUser.getFriendSet()) {
            paymentList.addAll(paymentRepository.getLatestFriendPayment(friend.getId()));
        }

        ArrayList<Payment> returnArray;
        if (paymentList.size() < Transaction.AMOUNT) {
            returnArray = new ArrayList<>(paymentList);
        } else {
            returnArray = new ArrayList<>(new ArrayList<>(paymentList).subList(paymentList.size() - Transaction.AMOUNT, paymentList.size()));
        }

        return returnArray;
    }

    public ArrayList<Payment> getLatestFriendPaymentFrom(String userId, Timestamp timestamp) throws BaseExceptionHandler.InValidFieldValueException {
        User curUser = userService.checkValidUserId(userId);

        TreeSet<Payment> paymentList = new TreeSet<>(paymentRepository.getLatestFriendPaymentFrom(curUser.getId(), timestamp));
        for (User friend : curUser.getFriendSet()) {
            paymentList.addAll(paymentRepository.getLatestFriendPaymentFrom(friend.getId(), timestamp));
        }

        ArrayList<Payment> returnArray;
        if (paymentList.size() < Transaction.AMOUNT) {
            returnArray = new ArrayList<>(paymentList);
        } else {
            returnArray = new ArrayList<>(new ArrayList<>(paymentList).subList(paymentList.size() - Transaction.AMOUNT, paymentList.size()));
        }

        return returnArray;

    }

    public Payment checkValidPaymentId(String paymentId) throws BaseExceptionHandler.InValidFieldValueException {
        Optional<Payment> curPayment = paymentRepository.findById(paymentId);
        if (curPayment.isEmpty())
            throw new BaseExceptionHandler.InValidFieldValueException("Invalid Payment Id");
        return curPayment.get();
    }
}
