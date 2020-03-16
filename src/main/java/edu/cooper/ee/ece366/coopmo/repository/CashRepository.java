package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.Cash;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashRepository extends CrudRepository<Cash, String> {
    @Query("SELECT c FROM Cash c ORDER BY c.timestamp DESC")
    List<Cash> getLatestCashList();
}
