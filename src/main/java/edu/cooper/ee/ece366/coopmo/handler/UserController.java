package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.EmptyFieldException;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.message.Message;
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
        Message respMessage = new Message();

        if (user.getName().equals("") || user.getUsername().equals("") || user.getPassword().equals("") || user.getEmail().equals("") || user.getHandle().equals("")) {
            throw new EmptyFieldException("Empty Field");
        }

        if (!validateEmail(user.getEmail())) {
            throw new InValidFieldValueException("Invalid Email Address");
        }

        userService.check_if_taken(user.getUsername(), user.getEmail(), user.getHandle());
        userService.addUser(user);
        respMessage.setData(user);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getUserFriendList")
    @ResponseBody
    public ResponseEntity<?> getUserFriendList(
            @RequestParam(value = "userId", defaultValue = "") String userId) throws EmptyFieldException {
        Message respMessage = new Message();

        if (userId.equals("")) {
            throw new EmptyFieldException("userId is empty");
        }

        Set<User> friendList = userService.getUserFriendSet(userId);
        respMessage.setData(friendList);
        if (friendList == null) return new ResponseEntity<>(respMessage, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getUserBankAccountList")
    @ResponseBody
    public ResponseEntity<?> getUserBankAccountList(
            @RequestParam(value = "userId", defaultValue = "") String userId) throws EmptyFieldException {
        Message respMessage = new Message();

        if (userId.equals("")) {
            throw new EmptyFieldException("userId is empty");
        }

        Set<BankAccount> bankAccountList = userService.getBankAccountSet(userId);
        respMessage.setData(bankAccountList);

        if (bankAccountList == null) return new ResponseEntity<>(respMessage, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getUserIncomingFriendRequest")
    @ResponseBody
    public ResponseEntity<?> getUserIncomingFriendRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId) throws EmptyFieldException {
        Message respMessage = new Message();

        if (userId.equals("")) {
            throw new EmptyFieldException("userId is empty");
        }

        Set<User> incomingFriendRequestList = userService.getIncomingFriendRequestSet(userId);
        respMessage.setData(incomingFriendRequestList);
        if (incomingFriendRequestList == null) return new ResponseEntity<>(respMessage, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getUserOutgoingFriendRequest")
    @ResponseBody
    public ResponseEntity<?> getUserOutgoingFriendRequest(
            @RequestParam(value = "userId", defaultValue = "") String userId) throws EmptyFieldException {
        Message respMessage = new Message();

        if (userId.equals("")) {
            throw new EmptyFieldException("userId is empty");
        }

        Set<User> outgoingFriendRequestList = userService.getOutgoingFriendRequestSet(userId);
        respMessage.setData(outgoingFriendRequestList);
        if (outgoingFriendRequestList == null) return new ResponseEntity<>(respMessage, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @PostMapping(path = "/getUserWithUsername", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getUserWithUsername(
            @RequestBody GetUserWithUsernameRequest getUserWithUsernameRequest) throws EmptyFieldException, InValidFieldValueException {
        Message respMessage = new Message();
        String username = getUserWithUsernameRequest.getUsername();
        String password = getUserWithUsernameRequest.getPassword();

        if (username.equals("") || password.equals("")) {
            throw new EmptyFieldException("Empty Field");
        }

        User curUser = userService.getUserWithUsername(username, password);
        respMessage.setData(curUser);

        return new ResponseEntity<>(respMessage, HttpStatus.OK);
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
    public ResponseEntity<?> getUserBalance(@RequestParam(value = "userId", defaultValue = "") String userId) throws EmptyFieldException {
        Message respMessage = new Message();

        if (userId.equals("")) {
            throw new EmptyFieldException("userId is empty");
        }


        Long balance = userService.getUserBalance(userId);
        respMessage.setData(balance);
        if (balance == null) {
            return new ResponseEntity<>(respMessage, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @PostMapping(path = "/editProfile", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> editProfile(
            @RequestBody EditProfileRequest editProfileRequest
    ) throws EmptyFieldException, InValidFieldValueException {
        Message respMessage = new Message();
        String userId = editProfileRequest.getUserId();
        String newName = editProfileRequest.getNewName();
        String newUsername = editProfileRequest.getNewUsername();
        String newPassword = editProfileRequest.getNewPassword();
        String newEmail = editProfileRequest.getNewEmail();
        String newHandle = editProfileRequest.getNewHandle();


        if (userId.equals("")) {
            throw new EmptyFieldException("No User ID provided");
        } else if (newName.equals("") || newUsername.equals("") || newPassword.equals("") || newEmail.equals("") || newHandle.equals("")) {
            //This should actually be handled on the client side
            throw new EmptyFieldException("All fields were not filled out properly");
        } else {
            ArrayList<Integer> errors = userService.editProfile(userId, newName, newUsername, newPassword, newEmail, newHandle);
            if (errors.isEmpty()) {
                return new ResponseEntity<>(respMessage, HttpStatus.OK);
            } else {
                String[] posErrors = {"No userId found.", "Username already exists.", "Email already used by other user.",
                        "Handle already exists."};
                Message.Err error = new Message.Err("2", "Error editing profile");
                for (Integer s : errors) {
                    error.addError("Editing Profile", posErrors[-(s + 1)], posErrors[-(s + 1)]);
                }
                respMessage.setError(error);
                return new ResponseEntity<>(respMessage, HttpStatus.BAD_REQUEST);

            }
        }
    }

    @PostMapping(path = "/requestCashOut", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> requestCashOut(
            @RequestBody RequestCashOutRequest requestCashOutRequest
    ) throws EmptyFieldException, InValidFieldValueException {
        String userId = requestCashOutRequest.getUserId();
        String bankId = requestCashOutRequest.getBankId();
        long amount = requestCashOutRequest.getAmount();

        Message respMessage = new Message();

        if (userId.equals("") || bankId.equals("")) {
            throw new EmptyFieldException("userId or bankId is empty");
        }

        if (amount < 0) {
            throw new InValidFieldValueException("Can't request a negative value for cashOut");
        }

        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }


    @PostMapping(path = "/acceptIncomingRequest", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> acceptIncomingRequest(
            @RequestBody AcceptIncomingRequestRequest acceptIncomingRequestRequest) throws InValidFieldValueException, EmptyFieldException, BaseExceptionHandler.FriendRequestDoesNotExistException {

        String userId = acceptIncomingRequestRequest.getUserId();
        String friendId = acceptIncomingRequestRequest.getFriendId();
        System.out.println(userId);
        Message respMessage = new Message();

        if (userId.equals("") || friendId.equals("")) {
            throw new EmptyFieldException("userId or friendId is empty");
        } else {
            if (userService.acceptIncomingRequest(userId, friendId) == 0) {
                return new ResponseEntity<>(respMessage, HttpStatus.OK);
            } else {
                throw new InValidFieldValueException("No User with provided ID and/or Friend ID found in Incoming Requests");
            }
        }
    }

    @PostMapping(path = "/sendOutRequest", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> sendOutRequest(
            @RequestBody SendOutRequestRequest sendOutRequestRequest) throws InValidFieldValueException, EmptyFieldException, BaseExceptionHandler.DuplicateFriendRequestException, BaseExceptionHandler.NoUserFoundException {
        String userId = sendOutRequestRequest.getUserId();
        String friendId = sendOutRequestRequest.getFriendId();

        Message respMessage = new Message();

        if (userId.equals("") || friendId.equals("")) {
            throw new EmptyFieldException("userId or friendId is empty");
        } else {
            int ret_val = userService.sendOutRequest(userId, friendId);
            if (ret_val == 0) {
                return new ResponseEntity<>(respMessage, HttpStatus.OK);
            } else if (ret_val == -1) {
                throw new BaseExceptionHandler.NoUserFoundException("No User with provided ID and/or Friend ID found");
            } else {
                throw new BaseExceptionHandler.DuplicateFriendRequestException("Duplicate Friend Request");
            }
        }
    }


    @PostMapping(path = "/sendOutRequestWithUsername", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> sendOutRequestWithUsername(
            @RequestBody SendOutRequestWithUsernameRequest sendOutRequestWithUsernameRequest) throws InValidFieldValueException, EmptyFieldException, BaseExceptionHandler.AlreadyFriendsException {
        String username = sendOutRequestWithUsernameRequest.getUsername();
        String friendUsername = sendOutRequestWithUsernameRequest.getFriendUsername();

        Message respMessage = new Message();

        if (username.equals("") || friendUsername.equals("")) {
            throw new EmptyFieldException("No Username and/or Friend Username provided");
        } else {
            int ret_val = userService.sendOutRequestWithUsername(username, friendUsername);
            if (ret_val == 0) {
                return new ResponseEntity<>(respMessage, HttpStatus.OK);
            } else if (ret_val == -1) {
                throw new InValidFieldValueException("No User with provided ID and/or Friend ID found");
            } else {
                throw new BaseExceptionHandler.AlreadyFriendsException("Already Friends with given friend username");
            }
        }
    }

    @PostMapping(path = "/cancelOutgoingFriendRequest", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> cancelOutgoingFriendRequest(
            @RequestBody CancelOutgoingFriendRequestRequest cancelOutgoingFriendRequestRequest) throws InValidFieldValueException, EmptyFieldException, BaseExceptionHandler.NoUserFoundException {
        String userId = cancelOutgoingFriendRequestRequest.getUserId();
        String friendId = cancelOutgoingFriendRequestRequest.getFriendId();

        Message respMessage = new Message();

        if (userId.equals("") || friendId.equals("")) {
            throw new EmptyFieldException("userId or friendId is empty");
        } else {
            int ret_val = userService.cancelOutgoingFriendRequest(userId, friendId);
            if (ret_val == 0) {
                return new ResponseEntity<>(respMessage, HttpStatus.OK);
            } else if (ret_val == -1) {
                throw new BaseExceptionHandler.NoUserFoundException("No User with provided ID and/or Friend ID found");
            } else {
                throw new BaseExceptionHandler.NoUserFoundException("User not found in outgoing friends requests");
            }
        }
    }

    @PostMapping(path = "/declineFriendRequest", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> declineFriendRequest(
            @RequestBody DeclineFriendRequestRequest declineFriendRequestRequest) throws InValidFieldValueException, EmptyFieldException, BaseExceptionHandler.NoUserFoundException {
        String userId = declineFriendRequestRequest.getUserId();
        String friendId = declineFriendRequestRequest.getFriendId();


        Message respMessage = new Message();

        if (userId.equals("") || friendId.equals("")) {
            throw new EmptyFieldException("userId or friendId is empty");
        } else {
            int ret_val = userService.cancelOutgoingFriendRequest(friendId, userId);
            if (ret_val == 0) {
                return new ResponseEntity<>(respMessage, HttpStatus.OK);
            } else if (ret_val == -1) {
                throw new BaseExceptionHandler.NoUserFoundException("No User with provided ID and/or Friend ID found");
            } else {
                throw new BaseExceptionHandler.NoUserFoundException("User not found in incoming friend requests");
            }
        }
    }

    @PostMapping(path = "/deleteFriend", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteFriend(
            @RequestBody DeleteFriendRequest deleteFriendRequest) throws InValidFieldValueException, EmptyFieldException, BaseExceptionHandler.NoUserFoundException, BaseExceptionHandler.UsersAreNotFriendsException {
        String userId = deleteFriendRequest.getUserId();
        String friendId = deleteFriendRequest.getFriendId();

        Message respMessage = new Message();

        if (userId.equals("") || friendId.equals("")) {
            throw new EmptyFieldException("userId or friendId is empty");
        } else {
            int ret_val = userService.deleteFriend(userId, friendId);
            if (ret_val == 0) {
                return new ResponseEntity<>(respMessage, HttpStatus.OK);
            } else if (ret_val == -1) {
                throw new BaseExceptionHandler.NoUserFoundException("No User with provided ID and/or Friend ID found");
            } else {
                throw new BaseExceptionHandler.UsersAreNotFriendsException("The two user id's provided are not friends");
            }
        }
    }

    @PostMapping(path = "/findUsers", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> findUsers(@RequestBody FindUsersRequest request) throws EmptyFieldException {
        if (request.getMatch().equals(""))
            throw new EmptyFieldException("Empty Field");
        Message respMessage = new Message();
        Set<User> users = userService.findUsers(request);
        respMessage.setData(users);

        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    public static class CreateUserRequest {
        private User user;

        public CreateUserRequest(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public static class GetUserWithUsernameRequest {
        private String username;
        private String password;

        public GetUserWithUsernameRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class UserIdRequest {
        private String userId;

        public UserIdRequest(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class EditProfileRequest {
        private String userId;
        private String newName;
        private String newUsername;
        private String newPassword;
        private String newEmail;
        private String newHandle;

        public EditProfileRequest(String userId, String newName, String newUsername, String newPassword, String newEmail, String newHandle) {
            this.userId = userId;
            this.newName = newName;
            this.newUsername = newUsername;
            this.newPassword = newPassword;
            this.newEmail = newEmail;
            this.newHandle = newHandle;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNewName() {
            return newName;
        }

        public void setNewName(String newName) {
            this.newName = newName;
        }

        public String getNewUsername() {
            return newUsername;
        }

        public void setNewUsername(String newUsername) {
            this.newUsername = newUsername;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getNewEmail() {
            return newEmail;
        }

        public void setNewEmail(String newEmail) {
            this.newEmail = newEmail;
        }

        public String getNewHandle() {
            return newHandle;
        }

        public void setNewHandle(String newHandle) {
            this.newHandle = newHandle;
        }
    }

    public static class RequestCashOutRequest {
        private String userId;
        private String bankId;
        private long amount;

        public RequestCashOutRequest(String userId, String bankId, long amount) {
            this.userId = userId;
            this.bankId = bankId;
            this.amount = amount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getBankId() {
            return bankId;
        }

        public void setBankId(String bankId) {
            this.bankId = bankId;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }
    }

    public static class SendOutRequestWithUsernameRequest {
        private String username;
        private String friendUsername;

        public SendOutRequestWithUsernameRequest(String username, String friendUsername) {
            this.username = username;
            this.friendUsername = friendUsername;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFriendUsername() {
            return friendUsername;
        }

        public void setFriendUsername(String friendUsername) {
            this.friendUsername = friendUsername;
        }
    }

    public static class UserAndFriendRequest {
        private String userId;
        private String friendId;

        public UserAndFriendRequest(String userId, String friendId) {
            this.userId = userId;
            this.friendId = friendId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFriendId() {
            return friendId;
        }

        public void setFriendId(String friendId) {
            this.friendId = friendId;
        }
    }

    public static class AcceptIncomingRequestRequest extends UserAndFriendRequest {
        public AcceptIncomingRequestRequest(String userId, String friendId) {
            super(userId, friendId);
        }
    }

    public static class SendOutRequestRequest extends UserAndFriendRequest {
        public SendOutRequestRequest(String userId, String friendId) {
            super(userId, friendId);
        }
    }

    public static class CancelOutgoingFriendRequestRequest extends UserAndFriendRequest {
        public CancelOutgoingFriendRequestRequest(String userId, String friendId) {
            super(userId, friendId);
        }
    }

    public static class DeclineFriendRequestRequest extends UserAndFriendRequest {
        public DeclineFriendRequestRequest(String userId, String friendId) {
            super(userId, friendId);
        }
    }

    public static class DeleteFriendRequest extends UserAndFriendRequest {
        public DeleteFriendRequest(String userId, String friendId) {
            super(userId, friendId);
        }
    }

    public static class FindUsersRequest {
        private final String match;
        private final Type type;

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
