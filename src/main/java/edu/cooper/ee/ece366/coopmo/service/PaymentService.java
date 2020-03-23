package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler;
import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.CashRepository;
import edu.cooper.ee.ece366.coopmo.repository.PaymentRepository;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeSet;

@Service
public class PaymentService {
    private final UserService userService;

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final CashRepository cashRepository;

    @Autowired
    public PaymentService(UserService userService, PaymentRepository paymentRepository, UserRepository userRepository, CashRepository cashRepository) {
        this.userService = userService;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
    }

    // return error code
    // -1: Invalid fromUserId
    // -2: Invalid toUserId
    // -3: Invalid amount
    @Transactional
    public Payment createPayment(String fromUserId, String toUserId, Long amount, Payment.PaymentType type)
            throws BaseExceptionHandler.InValidFieldValueException, BaseExceptionHandler.InvalidBalanceException {
        User fromUser = userService.checkValidUserId(fromUserId);
        User toUser = userService.checkValidUserId(toUserId);
        if (fromUser.getBalance() < amount)
            throw new BaseExceptionHandler.InvalidBalanceException("from User Balance is less than requested amount");

        Payment newPayment = new Payment(fromUser, toUser, amount, type);
        fromUser.decrementBalance(amount);
        toUser.incrementBalance(amount);
        paymentRepository.save(newPayment);
        return newPayment;
    }

    public ArrayList<Payment> getLatestPublicPayment(int n) {
        ArrayList<Payment> payments = (ArrayList<Payment>) paymentRepository.getLatestPublicPayments();
        if (payments.size() < n) return payments;
        return new ArrayList<>(payments.subList(payments.size() - n, payments.size()));
    }

    // TODO(change to heap)
    public ArrayList<Payment> getLatestFriendPayment(String userId, int n) throws BaseExceptionHandler.InValidFieldValueException {
        TreeSet<Payment> paymentList = new TreeSet<>();
        User curUser = userService.checkValidUserId(userId);
        paymentList.addAll(curUser.getFromPaymentSet());
        paymentList.addAll(curUser.getToPaymentSet());
        for (User friend : curUser.getFriendSet()) {
            int cnt = 0;
            for (Payment payment : friend.getFromPaymentSet()) {
                if (payment.getType() != Payment.PaymentType.PRIVATE) {
                    paymentList.add(payment);
                    cnt++;
                    if (cnt >= n) break;
                }
            }
            cnt = 0;
            for (Payment payment : friend.getToPaymentSet()) {
                if (payment.getType() != Payment.PaymentType.PRIVATE) {
                    paymentList.add(payment);
                    cnt++;
                    if (cnt >= n) break;
                }
            }
        }
        if (paymentList.size() < n) return new ArrayList<>(paymentList);
        return new ArrayList<>(new ArrayList<>(paymentList).subList(paymentList.size() - n, paymentList.size()));
    }

    public Payment checkValidPaymentId(String paymentId) throws BaseExceptionHandler.InValidFieldValueException {
        Optional<Payment> curPayment = paymentRepository.findById(paymentId);
        if (curPayment.isEmpty())
            throw new BaseExceptionHandler.InValidFieldValueException("Invalid Payment Id");
        return curPayment.get();
    }
}
