package edu.cooper.ee.ece366.coopmo.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.EmptyFieldException;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import edu.cooper.ee.ece366.coopmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;


// TODO (set all handler produce json and consumes json after converting)
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
        // https://howtodoinjava.com/regex/java-regex-validate-email-address/
        String EMAIL_REGEX = "^(.+)@(.+)$";
        return Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }

    @PostMapping(path = "/createUser", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody User user) throws EmptyFieldException, InValidFieldValueException {
        if (user.getName().equals("") || user.getUsername().equals("") || user.getPassword().equals("") || user.getEmail().equals("") || user.getHandle().equals("")) {
            throw new EmptyFieldException("Empty Field");
        }

        if (!validateEmail(user.getEmail())) {
            throw new InValidFieldValueException("Invalid Email Address");
        }

        userService.check_if_taken(user.getUsername(), user.getEmail(), user.getHandle());
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/getUserWithUsername")
    @ResponseBody
    public ResponseEntity<?> getUserWithUsername(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) throws EmptyFieldException, InValidFieldValueException {
        JsonObject respBody = new JsonObject();
        JsonObject userJson = new JsonObject();

        if (username.equals("") || password.equals("")) {
            throw new EmptyFieldException("Empty Field");
        }

        User curUser = userService.getUserWithUsername(username, password);
        return new ResponseEntity<>(curUser, HttpStatus.OK);
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

        Set<User> friendList = userService.getUserFriendSet(userId);
        if (friendList == null) return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(friendList, HttpStatus.OK);
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
        Set<BankAccount> bankAccountList = userService.getBankAccountSet(userId);
        if (bankAccountList == null) return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
//        respBody.addProperty("message", "get Bank Account List succeed");
//        bankAccountJson.add("bankAccountList", new Gson().toJsonTree(bankAccountList));
//        respBody.add("messagePayload", bankAccountJson);
        return new ResponseEntity<>(bankAccountList, HttpStatus.OK);
    }

    @GetMapping(path = "/getUserIncomingFriendRequest")
    @ResponseBody
    public ResponseEntity<?> getUserIncomingFriendRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId) {
        JsonObject respBody = new JsonObject();
        if (userId.equals("")) {
            respBody.addProperty("message", "userId is empty");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
        Set<User> incomingFriendRequestList = userService.getIncomingFriendRequestSet(userId);
        if (incomingFriendRequestList == null) return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        respBody.addProperty("message", "Successfully returned user's incoming friend request list");
        return new ResponseEntity<>(incomingFriendRequestList, HttpStatus.OK);
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

        Set<User> outgoingFriendRequestList = userService.getOutgoingFriendRequestSet(userId);
        if (outgoingFriendRequestList == null) return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        respBody.addProperty("message", "Successfully returned user's outgoing friend request list");
        return new ResponseEntity<>(outgoingFriendRequestList, HttpStatus.OK);
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
    ) throws InValidFieldValueException {
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
                ArrayList<String> errorMsg = new ArrayList<>();
                String[] posErrors = {"No userId found.", "Username already exists.", "Email already used by other user.",
                        "Handle already exists."};
                errors.forEach(e -> errorMsg.add(posErrors[-e - 1]));
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
            @RequestParam(value = "friendId", defaultValue = "") String friendId) throws InValidFieldValueException {
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
            @RequestParam(value = "friendId", defaultValue = "") String friendId) throws InValidFieldValueException {
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
                respBody.addProperty("message", "Duplicate Friend Request");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }


    @PostMapping(path = "/sendOutRequestWithUsername")
    public ResponseEntity<?> sendOutRequestWithUsername(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "friendUsername", defaultValue = "") String friendUsername) throws InValidFieldValueException {
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

    @PostMapping(path = "/cancelOutgoingFriendRequest")
    public ResponseEntity<?> cancelOutgoingFriendRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) throws InValidFieldValueException {
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

    @PostMapping(path = "/declineFriendRequest")
    public ResponseEntity<?> declineFriendRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) throws InValidFieldValueException {
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
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) throws InValidFieldValueException {
        JsonObject respBody = new JsonObject();
        if (userId.equals("") || friendId.equals("")) {
            respBody.addProperty("message", "No User ID and/or Friend ID provided");
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        } else {
            int ret_val = userService.deleteFriend(userId, friendId);
            if (ret_val == 0) {
                respBody.addProperty("message", "Deleted Friend");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.OK);
            } else if (ret_val == -1) {
                respBody.addProperty("message", "No User with provided ID and/or Friend ID found");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            } else {
                respBody.addProperty("message", "The two user id's provided are not friends");
                return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping(path = "/findUsers", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> findUsers(@RequestBody FindUsersRequest request) throws EmptyFieldException {
        if (request.getMatch().equals(""))
            throw new EmptyFieldException("Empty Field");
        Set<User> users = userService.findUsers(request);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public static class FindUsersRequest {
        private String match;
        private Type type;

        FindUsersRequest(String match, Type type) {
            this.match = match;
            this.type = type;
        }

        public String getMatch() {
            return match;
        }

        public boolean isEmail() {
            return type == Type.EMAIL;
        }

        public boolean isUsername() {
            return type == Type.USERNAME;
        }

        public boolean isHandle() {
            return type == Type.HANDLE;
        }

        private enum Type {
            HANDLE, USERNAME, EMAIL
        }
    }
}
