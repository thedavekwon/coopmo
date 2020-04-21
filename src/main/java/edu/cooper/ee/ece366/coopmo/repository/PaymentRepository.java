package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PaymentRepository extends PagingAndSortingRepository<Payment, String> {
    List<Payment> findTop20ByTypeEqualsOrderByTimestampDesc(Payment.PaymentType type);

    List<Payment> findTop20ByTypeEqualsAndTimestampLessThanOrderByTimestampDesc(Payment.PaymentType type, Timestamp timestamp);

    @Query(value = "SELECT * FROM payment p WHERE p.type < 2 AND (p.from_user_id = :userId OR p.to_user_id = :userId) " +
            "ORDER BY p.timestamp DESC LIMIT 20", nativeQuery = true)
    List<Payment> getLatestFriendPayment(String userId);

    @Query(value = "SELECT * FROM payment p WHERE (p.type < 2 AND (p.from_user_id = :userId OR p.to_user_id = :userId)) AND (p.timestamp < :timestamp) " +
            "ORDER BY p.timestamp DESC LIMIT 20", nativeQuery = true)
    List<Payment> getLatestFriendPaymentFrom(String userId, Timestamp timestamp);

    @Query(value = "SELECT * FROM payment p WHERE (p.from_user_id = :userId OR p.to_user_id = :userId) " +
            "ORDER BY p.timestamp DESC LIMIT 20", nativeQuery = true)
    List<Payment> getLatestPrivatePayment(String userId);

    @Query(value = "SELECT * FROM payment p WHERE (p.from_user_id = :userId OR p.to_user_id = :userId) AND (p.timestamp < :timestamp) " +
            "ORDER BY p.timestamp DESC LIMIT 20", nativeQuery = true)
    List<Payment> getLatestPrivatePaymentFrom(String userId, Timestamp timestamp);
}
