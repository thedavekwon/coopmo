package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.Cash;
import edu.cooper.ee.ece366.coopmo.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CashRepository extends CrudRepository<Cash, String> {
    @Query(value = "SELECT * FROM cash c WHERE c.user_id = :userId " +
            "ORDER BY c.timestamp DESC LIMIT 20", nativeQuery = true)
    List<Cash> getLatestCash(String userId);

    @Query(value = "SELECT * FROM cash c WHERE c.user_id = :userId AND c.timestamp < :timestamp " +
            "ORDER BY c.timestamp DESC LIMIT 20", nativeQuery = true)
    List<Cash> getLatestCashFrom(String userId, Timestamp timestamp);
}
