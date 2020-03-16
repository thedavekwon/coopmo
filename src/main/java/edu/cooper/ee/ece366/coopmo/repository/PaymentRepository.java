package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends PagingAndSortingRepository<Payment, String> {
    @Query("SELECT p FROM Payment p WHERE p.type = 0 ORDER BY p.timestamp DESC")
    List<Payment> getLatestPublicPayments();
}
