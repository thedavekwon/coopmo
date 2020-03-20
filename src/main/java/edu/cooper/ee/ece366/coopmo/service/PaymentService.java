package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.CashRepository;
import edu.cooper.ee.ece366.coopmo.repository.PaymentRepository;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeSet;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final CashRepository cashRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository, CashRepository cashRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
    }

    // return error code
    // -1: Invalid fromUserId
    // -2: Invalid toUserId
    // -3: Invalid amount
    public int createPayment(String fromUserId, String toUserId, Long amount, Payment.PaymentType type) {
        Optional<User> fromUser = userRepository.findById(fromUserId);
        Optional<User> toUser = userRepository.findById(toUserId);

        if (fromUser.isEmpty()) return -1;
        if (toUser.isEmpty()) return -2;
        if (fromUser.get().getBalance() < amount) return -3;

        Payment newPayment = new Payment(fromUser.get(), toUser.get(), amount, type);
        fromUser.get().decrementBalance(amount);
        toUser.get().incrementBalance(amount);
        fromUser.get().addFromPayment(newPayment);
        toUser.get().addToPayment(newPayment);
        paymentRepository.save(newPayment);
        return 0;
    }

    public ArrayList<Payment> getLatestPublicPayment(int n) {
        ArrayList<Payment> payments = (ArrayList<Payment>) paymentRepository.getLatestPublicPayments();
        if (payments.size() < n) return payments;
        return new ArrayList<>(payments.subList(payments.size() - n, payments.size()));
    }

    // TODO(change to heap)
    public ArrayList<Payment> getLatestFriendPayment(String userId, int n) {
        TreeSet<Payment> paymentList = new TreeSet<>();
        Optional<User> curUser = userRepository.findById(userId);
        if (curUser.isEmpty()) return null;
        paymentList.addAll(curUser.get().getFromPaymentSet());
        paymentList.addAll(curUser.get().getToPaymentSet());
        for (User friend : curUser.get().getFriendSet()) {
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
}
