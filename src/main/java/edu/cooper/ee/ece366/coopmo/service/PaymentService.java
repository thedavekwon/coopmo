package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.PaymentRepository;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeSet;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

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
        paymentRepository.save(newPayment);
        return 0;
    }

    // TODO()
    public ArrayList<Payment> getLatestPublicPayment(long n) {
        return null;
    }

    // TODO()
    public ArrayList<Payment> getLatestPrivatePayment(String userId, int n) {
        ArrayList<Payment> payments = new ArrayList<>();
//        synchronized (userRepository.getPaymentListMap()) {
//            int curSize = userRepository.getPaymentListMap().get(userId).size();
//            if (curSize < n) paymentList = userRepository.getPaymentListMap().get(userId);
//            else paymentList = userRepository.getPaymentListMap().get(userId).subList(curSize - n, curSize);
//        }
//        ArrayList<Payment> payments = new ArrayList<>();
//        paymentList.forEach(paymentId -> {
//            Optional<Payment> payment = paymentRepository.findById(paymentId);
//            payment.ifPresent(payments::add);
//        });
        return payments;
    }

    // TODO()
    public ArrayList<Payment> getLatestFriendPayment(String userId, int n) {
        TreeSet<Payment> paymentList = new TreeSet<>();
//        List<String> curUserPaymentList;
//        int cnt;
//        synchronized (userRepository.getPaymentListMap()) {
//            int curSize = userRepository.getPaymentListMap().get(userId).size();
//            if (curSize < n) curUserPaymentList = userRepository.getPaymentListMap().get(userId);
//            else curUserPaymentList = userRepository.getPaymentListMap().get(userId).subList(curSize - n, curSize);
//            ArrayList<Payment> payments = new ArrayList<>();
//            curUserPaymentList.forEach(paymentId -> {
//                Optional<Payment> payment = paymentRepository.findById(paymentId);
//                payment.ifPresent(payments::add);
//            });
//            paymentList.addAll(payments);
//
//            for (String friendId : Collections.list(userRepository.getFriendMap().get(userId).keys())) {
//                curSize = userRepository.getPaymentListMap().get(friendId).size();
//                cnt = 0;
//                ListIterator<String> iterator = userRepository.getPaymentListMap().get(friendId).listIterator(curSize);
//                while (iterator.hasPrevious()) {
//                    String paymentId = iterator.previous();
//                    Optional<Payment> payment = paymentRepository.findById(paymentId);
//                    if (payment.isPresent()) {
//                        if (payment.get().getType() != Payment.PaymentType.PRIVATE) {
//                            paymentList.add(payment.get());
//                            cnt++;
//                            if (cnt >= n) break;
//                        }
//                    }
//                }
//            }
//        }
        if (paymentList.size() < n) return new ArrayList<>(paymentList);
        return new ArrayList<>(new ArrayList<>(paymentList).subList(paymentList.size() - n, paymentList.size()));
    }
}
