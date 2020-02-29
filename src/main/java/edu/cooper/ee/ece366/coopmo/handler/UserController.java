package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import edu.cooper.ee.ece366.coopmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/user")
public class UserController {

    private boolean validateEmail(String email){
        String EMAIL_REGEX = "^(.+)@(.+)$";
        return Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "handle", defaultValue = "") String handle) {

        if (name.equals("") || username.equals("") || password.equals("") || email.equals("") || handle.equals("")) {
            return ResponseEntity.badRequest().body("One or more of submitted name, username, password, email, or handle is empty");
        }

        if (!validateEmail(email)) {
            return ResponseEntity.badRequest().body("Please enter a valid email address");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Valid user creation request");
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

    @PostMapping("/requestCashOut")
    public ResponseEntity<String> requestCashOut(@RequestParam(value = "userId", defaultValue = "") String userId, @RequestParam(value = "bankId", defaultValue = "") String bankId, @RequestParam(value = "amount", defaultValue = "0") long amount) {
        if (amount < 0) {
            return ResponseEntity.badRequest().body("Cannot cash out a negative amount of money");
        }

        if (userId.equals("") || bankId.equals("") ){
            return ResponseEntity.badRequest().body("User ID or bank ID not valid");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Valid cash out request");
    }

    //service implemented
    @PostMapping("/acceptIncomingFriendRequest")
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
    @PostMapping("/sendOutgoingFriendRequest")
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
