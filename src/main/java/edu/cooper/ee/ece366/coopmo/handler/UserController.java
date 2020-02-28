package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import edu.cooper.ee.ece366.coopmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // TODO(error handling if something is missing)
    // TODO(duplicate in username and email)
    // @PostMapping("/createUser")
    @GetMapping("/createUser")
    public User createUser(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "handle", defaultValue = "") String handle) {

        User newUser = new User(name, username, password, email, handle);
//        userDB.put(newUser.getId(), newUser);
        this.userRepository.save(newUser);
        return newUser;
    }

    // Debug Purpose
    @GetMapping("/getUserSize")
    public Long getUserSize() {
        return this.userRepository.count();
    }


    @GetMapping("/getUserWithId")
    public User getUserWithId(@RequestParam(value = "id", defaultValue = "") String id) {
        Optional<User> curUser = userRepository.findById(id);
        return curUser.orElse(null);
    }

    @GetMapping("/requestCashOut")
    public boolean requestCashOut(String userId, String bankId, long amount) {
//        User curUser = userDB.get(userId);
//        BankAccount curBankAccount = bankAccountDB.get(bankId);
//        if (curUser == null || curBankAccount == null) return false;
//
//        boolean ret = false;
//        synchronized (userDB) {
//            if (curUser.checkBankAccount(bankId) && curUser.getBalance() > amount) {
//                curUser.decrementBalance(amount);
//                curBankAccount.incrementBalance(amount);
//                ret = true;
//            }
//        }
        return false;
    }

    //service implemented
    @GetMapping("/acceptIncomingFriendRequest")
    public ResponseEntity<String> acceptIncomingRequest(
            @RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        if(id.equals("") || friendId.equals("")){
            return ResponseEntity.badRequest().body("No User ID and/or Friend ID found");
        }
        else
        {
            if(userService.acceptIncomingRequest(id,friendId))
                return ResponseEntity.status(HttpStatus.OK).body("Accepted Incoming Friend Request");
            else
                return ResponseEntity.badRequest().body("No User with provided ID and/or Friend ID found in Incoming Requests");
        }
    }

    //service implemented
    @GetMapping("/sendOutgoingFriendRequest")
    public ResponseEntity<String> sendOutgoingFriendRequest(
            @RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        if(id.equals("") || friendId.equals("")){
            return ResponseEntity.badRequest().body("No User ID and/or Friend ID provided");
        }
        else
        {
            if(userService.sendOutgoingFriendRequest(id, friendId))
                return ResponseEntity.status(HttpStatus.OK).body("Sent Outgoing Friend Request");
            else
                return ResponseEntity.badRequest().body("No User with provided ID and/or Friend ID found");
        }
    }


}
