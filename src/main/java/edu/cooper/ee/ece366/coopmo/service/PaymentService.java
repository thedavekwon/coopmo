package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.PaymentRepository;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Integer createPayment(String fromUserId, String toUserId, Long amount, Integer type) {
        Optional<User> fromUser = userRepository.findById(fromUserId);
        Optional<User> toUser = userRepository.findById(toUserId);
        if (!fromUser.isPresent()) {
            return -1;
        }

        if (!toUser.isPresent()) {
            return -2;
        }

        synchronized (userRepository) {
            if (fromUser.get().getBalance() < amount) {
                return -3;
            }
            Payment newPayment = new Payment(fromUserId, toUserId, amount, type);
            fromUser.get().decrementBalance(amount);
            toUser.get().incrementBalance(amount);
            paymentRepository.save(newPayment);
        }

        return 0;
    }


}
