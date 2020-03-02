package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import edu.cooper.ee.ece366.coopmo.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;


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

    private boolean validateEmail(String email) {
        // email regex taken from https://howtodoinjava.com/regex/java-regex-validate-email-address/
        String EMAIL_REGEX = "^(.+)@(.+)$";
        return Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }

    @PostMapping("/createUser")
    @ResponseBody
    public ResponseEntity<?> createUser(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "handle", defaultValue = "") String handle) {
        JSONObject respBody = new JSONObject();

        if (name.equals("") || username.equals("") || password.equals("") || email.equals("") || handle.equals("")) {
            respBody.put("message", "One or more of submitted name, username, password, email, or handle is empty");
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }

        if (!validateEmail(email)) {
            respBody.put("message", "Please enter a valid email address");
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }

        ArrayList<Integer> errors = userService.check_if_taken(username, email, handle);
        if (errors.isEmpty()) {
            User new_user = userService.createUser(name, username, password, email, handle);
            if (new_user == null) {
                respBody.put("user", new_user);
                respBody.put("message", "Error Creating User");
                return new ResponseEntity<>(respBody, HttpStatus.SERVICE_UNAVAILABLE);
            } else {
                respBody.put("user", new_user);
                respBody.put("message", "Successfully created user");
                return new ResponseEntity<>(respBody, HttpStatus.OK);
            }

        } else {
            ArrayList<String> error_msg = new ArrayList<>();
            for (Integer error : errors) {
                switch (error) {
                    case -1:
                        error_msg.add("Username Taken");
                        break;
                    case -2:
                        error_msg.add("Email Taken");
                        break;
                    default:
                        error_msg.add("Handle taken");
                        break;
                }
            }
            respBody.put("error messages", error_msg);
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUserWithUsername")
    @ResponseBody
    public ResponseEntity<?> getUserWithUsername(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) {
        JSONObject respBody = new JSONObject();
        if (username.equals("") || password.equals("")) {
            respBody.put("message", "One or more of submitted name, username, password, email, or handle is empty");
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }

        User curUser = userService.getUserWithUsername(username, password);
        if (curUser == null) {
            respBody.put("message", "Wrong username or password");
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }
        respBody.put("user", curUser);
        respBody.put("message", "Successfully created user");
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    @GetMapping("/getUserFriendList")
    @ResponseBody
    public ResponseEntity<?> getUserFriendList(
            @RequestParam(value = "userId", defaultValue = "") String userId) {
        JSONObject respBody = new JSONObject();
        if (userId.equals("")) {
            respBody.put("message", "userId is empty");
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }

        ConcurrentHashMap<String, Boolean> userFriendList = userService.getUserFriendList(userId);
        respBody.put("friendList", new ArrayList<>(userFriendList.keySet()));
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    @GetMapping("/getUserIncomingFriendRequest")
    @ResponseBody
    public ResponseEntity<?> getUserIncomingFriendRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId) {
        JSONObject respBody = new JSONObject();
        if (userId.equals("")) {
            respBody.put("message", "userId is empty");
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }

        ConcurrentHashMap<String, Boolean> userIncomingFriendRequest = userService.getUserIncomingFriendRequest(userId);
        respBody.put("userIncomingFriendRequest", new ArrayList<>(userIncomingFriendRequest.keySet()));
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }

    @GetMapping("/getUserOutgoingFriendRequest")
    @ResponseBody
    public ResponseEntity<?> getUserOutgoingFriendRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId) {
        JSONObject respBody = new JSONObject();
        if (userId.equals("")) {
            respBody.put("message", "userId is empty");
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }

        ConcurrentHashMap<String, Boolean> userOutgoingFriendRequest = userService.getUserOutgoingFriendRequest(userId);
        respBody.put("userOutgoingFriendRequest", new ArrayList<>(userOutgoingFriendRequest.keySet()));
        return new ResponseEntity<>(respBody, HttpStatus.OK);
    }


    // Debug Purpose
    @GetMapping("/getUserSize")
    public Long getUserSize() {
        return this.userRepository.count();
    }

    // Debug Purpose
    @GetMapping("/getUserWithId")
    public User getUserWithId(@RequestParam(value = "id", defaultValue = "") String id) {
        Optional<User> curUser = userRepository.findById(id);
        return curUser.orElse(null);
    }

    @GetMapping("/editProfile")
    public ResponseEntity<?> editProfile(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "newName", defaultValue = "") String newName,
            @RequestParam(value = "newUsername", defaultValue = "") String newUsername,
            @RequestParam(value = "newPassword", defaultValue = "") String newPassword,
            @RequestParam(value = "newEmail", defaultValue = "") String newEmail,
            @RequestParam(value = "newHandle", defaultValue = "") String newHandle
    ) {
        if (userId.equals("")) {
            return ResponseEntity.badRequest().body("No id provided");
        } else if (newName.equals("") || newUsername.equals("") || newPassword.equals("") || newEmail.equals("") || newHandle.equals("")) {
            //This should actually be handled on the client side
            return ResponseEntity.badRequest().body("Please fill out all of the fields");
        } else {
            ArrayList<Integer> errors = userService.editProfile(userId, newName, newUsername, newPassword, newEmail, newHandle);
            if (errors.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("Profile updated");
            } else {
                Iterator<Integer> error = errors.iterator();
                StringBuilder error_msg = new StringBuilder();
                String[] pos_errors = {"No userId found.", "Username already exists.", "Email already used by other user.",
                        "Handle already exists."};
                while (error.hasNext()) {
                    error_msg.append(pos_errors[error.next()]);
                }
                return ResponseEntity.badRequest().body(error_msg.toString());
            }
        }
    }

    @PostMapping("/requestCashOut")
    public ResponseEntity<String> requestCashOut(@RequestParam(value = "userId", defaultValue = "") String userId, @RequestParam(value = "bankId", defaultValue = "") String bankId, @RequestParam(value = "amount", defaultValue = "0") long amount) {
        if (amount < 0) {
            return ResponseEntity.badRequest().body("Cannot cash out a negative amount of money");
        }

        if (userId.equals("") || bankId.equals("")) {
            return ResponseEntity.badRequest().body("User ID or bank ID not valid");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Valid cash out request");
    }


    @GetMapping("/acceptIncomingRequest")
    public ResponseEntity<String> acceptIncomingRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        if (userId.equals("") || friendId.equals("")) {
            return ResponseEntity.badRequest().body("No User ID and/or Friend ID found");
        } else {
            if (userService.acceptIncomingRequest(userId, friendId) == 0)
                return ResponseEntity.status(HttpStatus.OK).body("Accepted Incoming Friend Request");
            else
                return ResponseEntity.badRequest().body("No User with provided ID and/or Friend ID found in Incoming Requests");
        }
    }

    @PostMapping("/sendOutgoingFriendRequest")
    public ResponseEntity<String> sendOutgoingFriendRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        if (userId.equals("") || friendId.equals("")) {
            return ResponseEntity.badRequest().body("No User ID and/or Friend ID provided");
        } else {
            int ret_val = userService.sendOutRequest(userId, friendId);
            if (ret_val == 0)
                return ResponseEntity.status(HttpStatus.OK).body("Sent Outgoing Friend Request");
            else if (ret_val == -1)
                return ResponseEntity.badRequest().body("No User with provided ID and/or Friend ID found");
            else
                return ResponseEntity.badRequest().body("Already Friends");
        }
    }

    @GetMapping("/cancelFriendRequest")
    public ResponseEntity<String> cancelOutgoingFriendRequest(
            @RequestParam(value = "id", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        if (userId.equals("") || friendId.equals("")) {
            return ResponseEntity.badRequest().body("No User ID and/or Friend ID provided");
        } else {
            int ret_val = userService.cancelOutgoingFriendRequest(userId, friendId);
            if (ret_val == 0) {
                return ResponseEntity.status(HttpStatus.OK).body("Cancelled Outgoing Friend Request");
            } else if (ret_val == -1)
                return ResponseEntity.badRequest().body("No User with provided ID and/or Friend ID found");
            else
                return ResponseEntity.badRequest().body("User not found in outgoing friends requests");
        }
    }

    @GetMapping("/declineFriendRequest")
    public ResponseEntity<String> declineFriendRequest(
            @RequestParam(value = "id", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        if (userId.equals("") || friendId.equals("")) {
            return ResponseEntity.badRequest().body("No User ID and/or Friend ID provided");
        } else {
            int ret_val = userService.cancelOutgoingFriendRequest(friendId, userId);
            if (ret_val == 0) {
                return ResponseEntity.status(HttpStatus.OK).body("Declined Incoming Friend Request");
            } else if (ret_val == -1)
                return ResponseEntity.badRequest().body("No User with provided ID and/or Friend ID found");
            else
                return ResponseEntity.badRequest().body("User not found in incoming friend requests");
        }
    }
}
