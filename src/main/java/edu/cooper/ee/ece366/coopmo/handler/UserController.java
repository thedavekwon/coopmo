package edu.cooper.ee.ece366.coopmo.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import edu.cooper.ee.ece366.coopmo.service.UserService;
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
@RequestMapping(path = "/user", produces = "application/json")
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

    @PostMapping(path = "/createUser")
    @ResponseBody
    public ResponseEntity<?> createUser(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "handle", defaultValue = "") String handle) {
        JsonObject respBody = new JsonObject();
        if (name.equals("") || username.equals("") || password.equals("") || email.equals("") || handle.equals("")) {
            respBody.addProperty("message", "One or more of submitted name, username, password, email, or handle is empty");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }

        if (!validateEmail(email)) {
            respBody.addProperty("message", "Please enter a valid email address");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }

        ArrayList<Integer> errors = userService.check_if_taken(username, email, handle);
        if (errors.isEmpty()) {
            User newUser = userService.createUser(name, username, password, email, handle);
            if (newUser == null) {
                respBody.addProperty("message", "Error Creating User");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.SERVICE_UNAVAILABLE);
            } else {
                JsonObject userJson = new JsonObject();
                userJson.add("user", new Gson().toJsonTree(newUser));
                respBody.add("messagePayload", userJson);
                respBody.addProperty("message", "Successfully created user");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
            }

        } else {
            ArrayList<String> error_msg = new ArrayList<>();
            for (Integer error : errors) {
                switch (error) {
                    case -2:
                        error_msg.add("Username Taken");
                        break;
                    case -3:
                        error_msg.add("Email Taken");
                        break;
                    case -4:
                        error_msg.add("Handle taken");
                        break;
                    default:
                        error_msg.add("Unknown error");
                }
            }
            respBody.add("errorMessages", new Gson().toJsonTree(error_msg));
            respBody.addProperty("message", "Error Creating User");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getUserWithUsername")
    @ResponseBody
    public ResponseEntity<?> getUserWithUsername(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) {
        JsonObject respBody = new JsonObject();
        JsonObject userJson = new JsonObject();

        if (username.equals("") || password.equals("")) {
            respBody.addProperty("message", "One or more of submitted name, username, password, email, or handle is empty");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }

        User curUser = userService.getUserWithUsername(username, password);
        if (curUser == null) {
            respBody.addProperty("message", "Wrong username or password");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }

        userJson.add("user", new Gson().toJsonTree(curUser));
        respBody.add("messagePayload", userJson);
        respBody.addProperty("message", "Successfully returned user data");
        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/getUserFriendList")
    @ResponseBody
    public ResponseEntity<?> getUserFriendList(
            @RequestParam(value = "userId", defaultValue = "") String userId) {
        JsonObject respBody = new JsonObject();
        JsonObject friendJson = new JsonObject();
        if (userId.equals("")) {
            respBody.addProperty("message", "userId is empty");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }

        ConcurrentHashMap<String, Boolean> userFriendList = userService.getUserFriendList(userId);
        friendJson.add("friendList", new Gson().toJsonTree(new ArrayList<>(userFriendList.keySet())));
        respBody.add("messagePayload", friendJson);
        respBody.addProperty("message", "successfully returned user's friend list");
        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/getUserBankAccountList")
    @ResponseBody
    public ResponseEntity<?> getUserBankAccountList(
            @RequestParam(value = "userId", defaultValue = "") String userId) {
        JsonObject respBody = new JsonObject();
        JsonObject bankAccountJson = new JsonObject();
        if (userId.equals("")) {
            respBody.addProperty("message", "userId is empty");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
        respBody.addProperty("message", "get Bank Account List succeed");
        bankAccountJson.add("bankAccountList", new Gson().toJsonTree(userService.getUserBankAccountList(userId)));
        respBody.add("messagePayload", bankAccountJson);
        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/getUserIncomingFriendRequest")
    @ResponseBody
    public ResponseEntity<?> getUserIncomingFriendRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId) {
        JsonObject respBody = new JsonObject();
        JsonObject incomingFriendRequestJson = new JsonObject();
        if (userId.equals("")) {
            respBody.addProperty("message", "userId is empty");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }

        ConcurrentHashMap<String, Boolean> userIncomingFriendRequest = userService.getUserIncomingFriendRequest(userId);
        incomingFriendRequestJson.add("userIncomingFriendRequestList", new Gson().toJsonTree(new ArrayList<>(userIncomingFriendRequest.keySet())));
        respBody.add("messagePayload", incomingFriendRequestJson);
        respBody.addProperty("message", "Successfully returned user's incoming friend request list");
        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/getUserOutgoingFriendRequest")
    @ResponseBody
    public ResponseEntity<?> getUserOutgoingFriendRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId) {
        JsonObject respBody = new JsonObject();
        JsonObject outgoingFriendRequestJson = new JsonObject();
        if (userId.equals("")) {
            respBody.addProperty("message", "userId is empty");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }

        ConcurrentHashMap<String, Boolean> userOutgoingFriendRequest = userService.getUserOutgoingFriendRequest(userId);
        outgoingFriendRequestJson.add("userOutgoingFriendRequest", new Gson().toJsonTree(new ArrayList<>(userOutgoingFriendRequest.keySet())));
        respBody.add("messagePayload", outgoingFriendRequestJson);
        respBody.addProperty("message", "Successfully returned user's outgoing friend request list");
        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
    }


    // Debug Purpose
    @GetMapping(path = "/getUserSize")
    public Long getUserSize() {
        return userRepository.count();
    }

    // Debug Purpose
    @GetMapping(path = "/getUserWithId")
    public User getUserWithId(@RequestParam(value = "userId", defaultValue = "") String userId) {
        Optional<User> curUser = userRepository.findById(userId);
        return curUser.orElse(null);
    }

    @GetMapping(path = "/getUserBalance")
    @ResponseBody
    public ResponseEntity<?> getUserBalance(@RequestParam(value = "userId", defaultValue = "") String userId) {
        JsonObject respBody = new JsonObject();
        if (userId.equals("")) {
            respBody.addProperty("message", "userId is empty");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
        Long balance = userService.getUserBalance(userId);
        if (balance == null) {
            respBody.addProperty("message", "userId is empty");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
        respBody.addProperty("messagePayload", balance);
        respBody.addProperty("message", "Successfully returned user's balance");
        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
    }

    @PostMapping(path = "/editProfile")
    public ResponseEntity<?> editProfile(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "newName", defaultValue = "") String newName,
            @RequestParam(value = "newUsername", defaultValue = "") String newUsername,
            @RequestParam(value = "newPassword", defaultValue = "") String newPassword,
            @RequestParam(value = "newEmail", defaultValue = "") String newEmail,
            @RequestParam(value = "newHandle", defaultValue = "") String newHandle
    ) {
        JsonObject respBody = new JsonObject();
        if (userId.equals("")) {
            respBody.addProperty("message", "No id provided");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        } else if (newName.equals("") || newUsername.equals("") || newPassword.equals("") || newEmail.equals("") || newHandle.equals("")) {
            //This should actually be handled on the client side
            respBody.addProperty("message", "All fields were not filled out properly");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        } else {
            ArrayList<Integer> errors = userService.editProfile(userId, newName, newUsername, newPassword, newEmail, newHandle);
            if (errors.isEmpty()) {
                respBody.addProperty("message", "Profile Updated");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
            } else {
                Iterator<Integer> error = errors.iterator();
                ArrayList<String> errorMsg = new ArrayList<>();
                String[] posErrors = {"No userId found.", "Username already exists.", "Email already used by other user.",
                        "Handle already exists."};
                while (error.hasNext()) {
                    errorMsg.add(posErrors[error.next()]);
                }
                respBody.add("errorMessages", new Gson().toJsonTree(errorMsg));
                respBody.addProperty("message", "Error editing profile");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping(path = "/requestCashOut")
    public ResponseEntity<?> requestCashOut(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "bankId", defaultValue = "") String bankId,
            @RequestParam(value = "amount", defaultValue = "0") long amount
    ) {
        JsonObject respBody = new JsonObject();
        if (amount < 0) {
            respBody.addProperty("message", "Cannot cash out a negative amount of money");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }

        if (userId.equals("") || bankId.equals("")) {
            respBody.addProperty("message", "User ID or bank ID not valid");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
        respBody.addProperty("message", "Valid cash out request");
        return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
    }


    @PostMapping(path = "/acceptIncomingRequest")
    public ResponseEntity<?> acceptIncomingRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        JsonObject respBody = new JsonObject();
        if (userId.equals("") || friendId.equals("")) {
            respBody.addProperty("message", "No User ID and/or Friend ID found");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        } else {
            if (userService.acceptIncomingRequest(userId, friendId) == 0) {
                respBody.addProperty("message", "Accepted Incoming Friend Request");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
            } else {
                respBody.addProperty("message", "No User with provided ID and/or Friend ID found in Incoming Requests");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping(path = "/sendOutRequest")
    public ResponseEntity<?> sendOutRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        JsonObject respBody = new JsonObject();

        if (userId.equals("") || friendId.equals("")) {
            respBody.addProperty("message", "No User ID and/or Friend ID provided");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        } else {
            int ret_val = userService.sendOutRequest(userId, friendId);
            if (ret_val == 0) {
                respBody.addProperty("message", "Sent Outgoing Friend Request");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
            } else if (ret_val == -1) {
                respBody.addProperty("message", "No User with provided ID and/or Friend ID found");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            } else {
                respBody.addProperty("message", "Already Friends");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping(path = "/sendOutRequestWithUsername")
    public ResponseEntity<?> sendOutRequestWithUsername(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "friendUsername", defaultValue = "") String friendUsername) {
        JsonObject respBody = new JsonObject();
        if (username.equals("") || friendUsername.equals("")) {
            respBody.addProperty("message", "No Username and/or Friend Username provided");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        } else {
            int ret_val = userService.sendOutRequestWithUsername(username, friendUsername);
            if (ret_val == 0) {
                respBody.addProperty("message", "Sent Outgoing Friend Request");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
            } else if (ret_val == -1) {
                respBody.addProperty("message", "No User with provided ID and/or Friend ID found");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            } else {
                respBody.addProperty("message", "Already Friends");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping(path = "/cancelOutgoingFriendRequest")
    public ResponseEntity<?> cancelOutgoingFriendRequest(
            @RequestParam(value = "id", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        JsonObject respBody = new JsonObject();
        if (userId.equals("") || friendId.equals("")) {
            respBody.addProperty("message", "No User ID and/or Friend ID provided");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        } else {
            int ret_val = userService.cancelOutgoingFriendRequest(userId, friendId);
            if (ret_val == 0) {
                respBody.addProperty("message", "Cancelled Outgoing Friend Request");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
            } else if (ret_val == -1) {
                respBody.addProperty("message", "No User with provided ID and/or Friend ID found");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            } else {
                respBody.addProperty("message", "User not found in outgoing friends requests");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping(path = "/declineFriendRequest")
    public ResponseEntity<?> declineFriendRequest(
            @RequestParam(value = "id", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        JsonObject respBody = new JsonObject();
        if (userId.equals("") || friendId.equals("")) {
            respBody.addProperty("message", "No User ID and/or Friend ID provided");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        } else {
            int ret_val = userService.cancelOutgoingFriendRequest(friendId, userId);
            if (ret_val == 0) {
                respBody.addProperty("message", "Declined Incoming Friend Request");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
            } else if (ret_val == -1) {
                respBody.addProperty("message", "No User with provided ID and/or Friend ID found");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            } else {
                respBody.addProperty("message", "User not found in incoming friend requests");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping(path = "/deleteFriend")
    public ResponseEntity<?> deleteFriend(
            @RequestParam(value = "id", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        JsonObject respBody = new JsonObject();
        if (userId.equals("") || friendId.equals("")) {
            respBody.addProperty("message", "No User ID and/or Friend ID provided");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
        else{
            int ret_val = userService.deleteFriend(userId, friendId);
            if(ret_val == 0){
                respBody.addProperty("message", "Deleted Friend");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
            }
            else if(ret_val == -1){
                respBody.addProperty("message", "No User with provided ID and/or Friend ID found");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            }
            else
            {
                respBody.addProperty("message", "The two user id's provided are not friends");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
