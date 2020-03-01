package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.BankAccountRepository;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    // return error code
    // -1 : Invalid userId
    // -2 : Invalid routing Number
    public int createBankAccount(String userId, Long routingNumber, Long balance) {
        Optional<User> curUser = userRepository.findById(userId);
        if (!curUser.isPresent()) {
            return -1;
        }
        if (!checkValidRoutingNumberWithBankApi(routingNumber)) {
            return -1;
        }
        BankAccount bankAccount = new BankAccount(routingNumber, balance);
        userRepository.getBankAccountList().get(userId).add(bankAccount.getId());
        bankAccountRepository.save(bankAccount);
        return 0;
    }

    // return error code
    // -1 : Invalid bankAccountId
    public long getBalance(String bankAccountId) {
        Optional<BankAccount> curBankAccount = bankAccountRepository.findById(bankAccountId);
        if (!curBankAccount.isPresent()) {
            return -1L;
        }
        return curBankAccount.get().getBalance();
    }

    private boolean checkValidRoutingNumberWithBankApi(Long routingNumber) {
        return true;
    }
}
