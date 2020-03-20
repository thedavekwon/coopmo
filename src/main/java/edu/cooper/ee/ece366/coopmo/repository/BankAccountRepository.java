package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, String> {
}
