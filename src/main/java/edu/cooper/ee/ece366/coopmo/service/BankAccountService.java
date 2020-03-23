package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.handler.BankAccountController;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
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

    public BankAccount createBankAccount(BankAccountController.CreateBankAccountRequest createBankAccountRequest) throws InValidFieldValueException {
        Optional<User> user = userRepository.findById(createBankAccountRequest.getUserId());
        if (user.isEmpty())
            throw new InValidFieldValueException("Invalid User Id");
        BankAccount bankAccount = new BankAccount(user.get(), createBankAccountRequest.getRoutingNumber(), createBankAccountRequest.getBalance());
        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }

    public long getBankAccountBalance(String bankAccountId) throws InValidFieldValueException {
        Optional<BankAccount> curBankAccount = bankAccountRepository.findById(bankAccountId);
        if (curBankAccount.isEmpty())
            throw new InValidFieldValueException("Invalid Bank Account Id");
        return curBankAccount.get().getBalance();
    }

    private boolean checkValidRoutingNumberWithBankApi(long routingNumber) {
        return true;
    }

    public void addBankAccount(String userId, BankAccount bankAccount) throws InValidFieldValueException {
        Optional<User> curUser = userRepository.findById(userId);
        if (curUser.isEmpty())
            throw new InValidFieldValueException("Invalid User Id");
        bankAccountRepository.save(bankAccount);
        curUser.get().addBankAccount(bankAccount);
    }
}
