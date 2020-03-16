package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldException;
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

    public BankAccount createBankAccount(String userId, long routingNumber, long balance) throws InValidFieldException {
        Optional<User> curUser = userRepository.findById(userId);
        if (curUser.isEmpty())
            throw new InValidFieldException("Invalid User Id");

        if (!checkValidRoutingNumberWithBankApi(routingNumber))
            throw new InValidFieldException("Invalid Routing Number");

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
