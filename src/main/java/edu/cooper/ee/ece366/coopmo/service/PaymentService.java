package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.PaymentRepository;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    // return error code
    // -1: Invalid fromUserId
    // -2: Invalid toUserId
    // -3: Invalid amount
    public int createPayment(String fromUserId, String toUserId, Long amount, Payment.PaymentType type) {
        Optional<User> fromUser = userRepository.findById(fromUserId);
        Optional<User> toUser = userRepository.findById(toUserId);

        if (!fromUser.isPresent()) return -1;
        if (!toUser.isPresent()) return -2;

        synchronized (userRepository) {
            if (fromUser.get().getBalance() < amount) return -3;
            Payment newPayment = new Payment(fromUserId, toUserId, amount, type);
            fromUser.get().decrementBalance(amount);
            toUser.get().incrementBalance(amount);
            paymentRepository.save(newPayment);
            userRepository.getPaymentListMap().get(fromUserId).add(newPayment.getId());
            userRepository.getPaymentListMap().get(toUserId).add(newPayment.getId());
        }

        return 0;
    }

    public ArrayList<Payment> getLatestPublicPayment(long n) {
        ArrayList<Payment> payments = new ArrayList<>();
        synchronized (paymentRepository.getPaymentMap()) {
            final NavigableSet<String> paymentDescendingOrder = paymentRepository.getPaymentMap().descendingKeySet();
            for (String s : paymentDescendingOrder) {
                Optional<Payment> curPayment = paymentRepository.findById(s);
                if (!curPayment.isPresent()) continue;
                if (curPayment.get().getType() == Payment.PaymentType.PUBLIC) payments.add(curPayment.get());
                if (payments.size() > n) break;
            }
        }
        return payments;
    }

    public ArrayList<Payment> getLatestPrivatePayment(String userId, int n) {
        List<String> paymentList;
        synchronized (userRepository.getPaymentListMap()) {
            int curSize = userRepository.getPaymentListMap().get(userId).size();
            if (curSize < n) paymentList = userRepository.getPaymentListMap().get(userId);
            else paymentList = userRepository.getPaymentListMap().get(userId).subList(curSize - n, curSize);
        }
        ArrayList<Payment> payments = new ArrayList<>();
        paymentList.forEach(paymentId -> {
            Optional<Payment> payment = paymentRepository.findById(paymentId);
            payment.ifPresent(payments::add);
        });
        return payments;
    }

    public ArrayList<Payment> getLatestFriendPayment(String userId, int n) {
        TreeSet<Payment> paymentList = new TreeSet<>();
        for (String friendId : Collections.list(userRepository.getFriendMap().get(userId).keys())) {
            int curSize = userRepository.getPaymentListMap().get(friendId).size();
            int cnt = 0;
            ListIterator<String> iterator = userRepository.getPaymentListMap().get(friendId).listIterator(curSize);
            while (iterator.hasPrevious()) {
                String paymentId = iterator.previous();
                Optional<Payment> payment = paymentRepository.findById(paymentId);
                if (payment.isPresent()) {
                    if (payment.get().getType() != Payment.PaymentType.PRIVATE) {
                        paymentList.add(payment.get());
                        cnt++;
                        if (cnt >= n) break;
                    }
                }
            }
        }
        if (paymentList.size() < n) return new ArrayList<>(paymentList);
        return new ArrayList<>(new ArrayList<>(paymentList).subList(paymentList.size() - n, paymentList.size()));
    }
}
