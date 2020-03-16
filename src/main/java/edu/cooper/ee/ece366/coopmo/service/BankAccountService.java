package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.BankAccountRepository;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    public BankAccount createBankAccount(String userId, long routingNumber, long balance) {
        Optional<User> curUser = userRepository.findById(userId);
        if (curUser.isEmpty()) {
            return null;
        }
        if (!checkValidRoutingNumberWithBankApi(routingNumber)) {
            return null;
        }
        BankAccount bankAccount = new BankAccount(curUser.get(), routingNumber, balance);
        curUser.get().addBankAccount(bankAccount);
        bankAccountRepository.save(bankAccount);
        userRepository.save(curUser.get());
        return bankAccount;
    }

    // return error code
    // -1 : Invalid bankAccountId
    public long getBalance(String bankAccountId) {
        Optional<BankAccount> curBankAccount = bankAccountRepository.findById(bankAccountId);
        return curBankAccount.map(BankAccount::getBalance).orElse(-1L);
    }

    private boolean checkValidRoutingNumberWithBankApi(long routingNumber) {
        return true;
    }
}
