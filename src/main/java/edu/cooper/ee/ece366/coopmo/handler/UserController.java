package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static edu.cooper.ee.ece366.coopmo.CoopmoApplication.bankAccountDB;
import static edu.cooper.ee.ece366.coopmo.CoopmoApplication.userDB;


@RestController
@RequestMapping("/user")
public class UserController {
    // TODO(error handling if something is missing)
    // @PostMapping("/createUser")
    @GetMapping("/createUser")
    public User createUser(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "handle", defaultValue = "") String handle) {
        User newUser = new User((long) userDB.size(), name, username, password, email, handle);
        userDB.put(newUser.getId(), newUser);
        return newUser;
    }

    // Debug Purpose
    @GetMapping("/getUserSize")
    public Integer getUserSize() {
        return userDB.size();
    }

    @GetMapping("/getUserWithId")
    public User getUserWithId(@RequestParam(value = "id", defaultValue = "") Long id) {
        return userDB.get(id);
    }

    @GetMapping("/requestCashOut")
    public boolean requestCashOut(long userId, long bankId, long amount) {
        User curUser = userDB.get(userId);
        BankAccount curBankAccount = bankAccountDB.get(bankId);
        if (curUser == null || curBankAccount == null) return false;

        boolean ret = false;
        synchronized (userDB) {
            if (curUser.checkBankAccount(bankId) && curUser.getBalance() > amount) {
                curUser.decrementBalance(amount);
                curBankAccount.incrementBalance(amount);
                ret = true;
            }
        }
        return ret;
    }
}
