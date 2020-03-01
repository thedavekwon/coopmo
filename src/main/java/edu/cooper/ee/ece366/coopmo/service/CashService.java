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
    public CashService(CashRepository cashRepository,
                       BankAccountRepository bankAccountRepository,
                       UserRepository userRepository) {
        this.cashRepository = cashRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    // return error code
    // -1 : Invalid userId
    // -2 : Invalid bankAccountId
    // -3 : Invalid amount
    public Integer createCashTranscation(String userId, String bankAccountId, Long amount, Boolean type) {
        Optional<User> curUser = userRepository.findById(userId);
        Optional<BankAccount> curBankAccount = bankAccountRepository.findById(bankAccountId);

        if (!curUser.isPresent()) {
            return -1;
        }
        if (!curBankAccount.isPresent()) {
            return -2;
        }
        if (type == Cash.Out) {
            synchronized (userRepository) {
                if (curUser.get().getBalance() < amount) {
                    return -3;
                }
                Cash newCash = new Cash(userId, bankAccountId, amount);

                userRepository.getCashList().get(userId).add(newCash.getId());
                curUser.get().decrementBalance(amount);
                curBankAccount.get().incrementBalance(amount);
                cashRepository.save(newCash);
            }
        } else if (type == Cash.In) {
            synchronized (bankAccountRepository) {
                if (curBankAccount.get().getBalance() < amount) {
                    return -3;
                }
                Cash newCash = new Cash(userId, bankAccountId, amount);

                userRepository.getCashList().get(userId).add(newCash.getId());
                curUser.get().decrementBalance(amount);
                curBankAccount.get().incrementBalance(amount);
                cashRepository.save(newCash);
            }
        }
        return 0;
    }
}
