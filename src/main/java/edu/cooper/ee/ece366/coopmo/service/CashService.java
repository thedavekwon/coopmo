package edu.cooper.ee.ece366.coopmo.service;


import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler;
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
    private final UserService userService;
    private final BankAccountService bankAccountService;

    private final CashRepository cashRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Autowired
    public CashService(UserService userService, BankAccountService bankAccountService,
                       CashRepository cashRepository, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.userService = userService;
        this.bankAccountService = bankAccountService;
        this.cashRepository = cashRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    public Cash createCash(String userId, String bankAccountId, long amount, Cash.CashType type)
            throws BaseExceptionHandler.InValidFieldValueException, BaseExceptionHandler.InvalidBalanceException {
        User curUser = userService.checkValidUserId(userId);
        BankAccount curBankAccount = bankAccountService.checkValidBankAccountId(bankAccountId);
        if (type == Cash.CashType.OUT) {
            if (curUser.getBalance() < amount)
                throw new BaseExceptionHandler.InvalidBalanceException("user balance is less than requested amount");
            curUser.decrementBalance(amount);
            curBankAccount.incrementBalance(amount);
        } else if (type == Cash.CashType.IN) {
            if (curBankAccount.getBalance() < amount)
                throw new BaseExceptionHandler.InvalidBalanceException("bank account balance is less than requested amount");
            curUser.incrementBalance(amount);
            curBankAccount.decrementBalance(amount);
        }
        Cash newCash = new Cash(curUser, curBankAccount, amount, type);
        cashRepository.save(newCash);
        return newCash;
    }

    public Cash checkValidCashId(String cashId) throws BaseExceptionHandler.InValidFieldValueException {
        Optional<Cash> curCash = cashRepository.findById(cashId);
        if (curCash.isEmpty())
            throw new BaseExceptionHandler.InValidFieldValueException("Invalid Payment Id");
        return curCash.get();
    }
}
