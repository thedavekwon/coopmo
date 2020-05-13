package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.handler.BankAccountController;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.BankAccountRepository;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BankAccountService {
    private final UserService userService;

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(UserService userService, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.userService = userService;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public BankAccount createBankAccount(BankAccountController.CreateBankAccountRequest createBankAccountRequest) throws InValidFieldValueException {
        User curUser = userService.checkValidUserId(createBankAccountRequest.getUserId());
        BankAccount bankAccount = new BankAccount(curUser, createBankAccountRequest.getNickname(), createBankAccountRequest.getAccountNumber(), createBankAccountRequest.getRoutingNumber(), createBankAccountRequest.getBalance());
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

    public BankAccount checkValidBankAccountId(String bankAccountId) throws InValidFieldValueException {
        Optional<BankAccount> curBankAccount = bankAccountRepository.findById(bankAccountId);
        if (curBankAccount.isEmpty())
            throw new InValidFieldValueException("Bank Account Id");
        return curBankAccount.get();
    }
}
