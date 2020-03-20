package edu.cooper.ee.ece366.coopmo.service;


import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.model.Cash;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.BankAccountRepository;
import edu.cooper.ee.ece366.coopmo.repository.CashRepository;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CashService {
    private final CashRepository cashRepository;

    private final BankAccountRepository bankAccountRepository;

    private final UserRepository userRepository;

    @Autowired
    public CashService(CashRepository cashRepository, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.cashRepository = cashRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    // return error code
    // -1 : Invalid userId
    // -2 : Invalid bankAccountId
    // -3 : Invalid amount
    public int createCash(String userId, String bankAccountId, long amount, Cash.CashType type) {
        Optional<User> curUser = userRepository.findById(userId);
        Optional<BankAccount> curBankAccount = bankAccountRepository.findById(bankAccountId);

        if (curUser.isEmpty()) return -1;
        if (curBankAccount.isEmpty()) return -2;

        if (type == Cash.CashType.OUT) {
            if (curUser.get().getBalance() < amount) return -3;
            curUser.get().decrementBalance(amount);
            curBankAccount.get().incrementBalance(amount);
        } else if (type == Cash.CashType.IN) {
            if (curBankAccount.get().getBalance() < amount) return -3;
            curUser.get().incrementBalance(amount);
            curBankAccount.get().decrementBalance(amount);
        }
        Cash newCash = new Cash(curUser.get(), curBankAccount.get(), amount, type);
        curUser.get().addCash(newCash);
        cashRepository.save(newCash);
        return 0;
    }
}
