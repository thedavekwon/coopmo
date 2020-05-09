package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.SecurityConfig.MyUserDetails;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.EmptyFieldException;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.message.Message;
import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import edu.cooper.ee.ece366.coopmo.service.StorageService;
import edu.cooper.ee.ece366.coopmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
@RequestMapping(path = "/user", produces = "application/json")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final StorageService storageService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, StorageService storageService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.storageService = storageService;
    }

    private boolean validateEmail(String email) {
        // https://howtodoinjava.com/regex/java-regex-validate-email-address/
        String EMAIL_REGEX = "^(.+)@(.+)$";
        return Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }


    @PostMapping(path = "/createUser", consumes = "application/json")
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
    public ResponseEntity<?> getUserFriendList() throws InValidFieldValueException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;
        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }

        Message respMessage = new Message();

        Set<User> friendList = userService.getUserFriendSet(userId);
        respMessage.setData(friendList);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getUserBankAccountList")
    @ResponseBody
    public ResponseEntity<?> getUserBankAccountList() throws InValidFieldValueException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;
        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }

        Message respMessage = new Message();


        Set<BankAccount> bankAccountList = userService.getBankAccountSet(userId);
        respMessage.setData(bankAccountList);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getUserIncomingFriendRequest")
    @ResponseBody
    public ResponseEntity<?> getUserIncomingFriendRequest() throws InValidFieldValueException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;
        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }

        Message respMessage = new Message();

        Set<User> incomingFriendRequestList = userService.getIncomingFriendRequestSet(userId);
        respMessage.setData(incomingFriendRequestList);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getUserOutgoingFriendRequest")
    @ResponseBody
    public ResponseEntity<?> getUserOutgoingFriendRequest() throws InValidFieldValueException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;
        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }
        Message respMessage = new Message();

        Set<User> outgoingFriendRequestList = userService.getOutgoingFriendRequestSet(userId);
        respMessage.setData(outgoingFriendRequestList);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    // Debug Purpose
    @GetMapping(path = "/getUserSize")
    public long getUserSize() {
        return userRepository.count();
    }

    // Debug Purpose
    @GetMapping(path = "/getUserWithId")
    public User getUserWithId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;

        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }
        Optional<User> curUser = userRepository.findById(userId);
        return curUser.orElse(null);
    }

    @GetMapping(path = "/getUserBalance")
    @ResponseBody
    public ResponseEntity<?> getUserBalance() throws InValidFieldValueException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;

        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }

        Message respMessage = new Message();

        long balance = userService.getUserBalance(userId);
        respMessage.setData(balance);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @PostMapping(path = "/editProfile", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> editProfile(
            @RequestBody EditProfileRequest editProfileRequest
    ) throws EmptyFieldException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;

        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }

        Message respMessage = new Message();

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


    @PostMapping(path = "/acceptIncomingRequest", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> acceptIncomingRequest(
            @RequestBody UserAndFriendRequest acceptIncomingRequestRequest) throws InValidFieldValueException, EmptyFieldException, BaseExceptionHandler.FriendRequestDoesNotExistException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;

        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }

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
            @RequestBody UserAndFriendRequest sendOutRequestRequest) throws InValidFieldValueException, EmptyFieldException, BaseExceptionHandler.DuplicateFriendRequestException, BaseExceptionHandler.NoUserFoundException, BaseExceptionHandler.FriendRequestDoesNotExistException, BaseExceptionHandler.FriendRequestAlreadyExistException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;

        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }

        String friendId = sendOutRequestRequest.getFriendId();
        System.out.println(userId);
        System.out.println(friendId);
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

    @PostMapping(path = "/cancelOutgoingFriendRequest", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> cancelOutgoingFriendRequest(
            @RequestBody CancelOutgoingFriendRequestRequest cancelOutgoingFriendRequestRequest) throws InValidFieldValueException, EmptyFieldException, BaseExceptionHandler.NoUserFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;

        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }

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
            @RequestBody UserAndFriendRequest declineFriendRequestRequest) throws InValidFieldValueException, EmptyFieldException, BaseExceptionHandler.NoUserFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;

        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }

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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;

        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }
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

    @PostMapping(path = "/findUsers", consumes = "application/json")
    public ResponseEntity<?> findUsers(@RequestBody FindUsersRequest request) throws EmptyFieldException {
        if (request.getMatch().equals(""))
            throw new EmptyFieldException("Empty Field");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;
        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }
        Message respMessage = new Message();
        Set<User> users = userService.findUsers(request);
        users.removeIf(user -> user.getId().equals(userId));
        respMessage.setData(users);
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @PostMapping(path = "/uploadProfilePic")
    public ResponseEntity<?> uploadProfilePic(@RequestParam("file") MultipartFile file) throws InValidFieldValueException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;

        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }
        storageService.store(file, userId);
        userService.addProfilePic(userId);
        Message respMessage = new Message();
        respMessage.setData("ok");
        return new ResponseEntity<>(respMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/getProfilePic")
    public ResponseEntity<?> getProfilePic() throws InValidFieldValueException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;

        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getId();
        } else {
            userId = principal.toString();
        }
        if (!userService.getProfilePic(userId)) {
            Message respMessage = new Message();
            Message.Err err = new Message.Err("10", "file not exist");
            respMessage.setError(err);
            return new ResponseEntity<>(respMessage, HttpStatus.BAD_REQUEST);
        }
        Resource file = storageService.loadAsResource(userId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
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
        private final String newName;
        private final String newUsername;
        private final String newPassword;
        private final String newEmail;
        private final String newHandle;

        public EditProfileRequest(String newName, String newUsername, String newPassword, String newEmail, String newHandle) {
            this.newName = newName;
            this.newUsername = newUsername;
            this.newPassword = newPassword;
            this.newEmail = newEmail;
            this.newHandle = newHandle;
        }

        public String getNewName() {
            return newName;
        }

        public String getNewUsername() {
            return newUsername;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public String getNewEmail() {
            return newEmail;
        }

        public String getNewHandle() {
            return newHandle;
        }
    }


    public static class UserAndFriendRequest {
        private String friendId;

        public UserAndFriendRequest() {
            super();
        }

        public UserAndFriendRequest(String friendId) {
            this.friendId = friendId;
        }

        public String getFriendId() {
            return friendId;
        }

        public void setFriendId(String friendId) {
            this.friendId = friendId;
        }
    }

    public static class AcceptIncomingRequestRequest extends UserAndFriendRequest {
        public AcceptIncomingRequestRequest(String friendId) {
            super(friendId);
        }
    }

    public static class SendOutRequestRequest extends UserAndFriendRequest {
        public SendOutRequestRequest(String friendId) {
            super(friendId);
        }
    }

    public static class CancelOutgoingFriendRequestRequest extends UserAndFriendRequest {
        public CancelOutgoingFriendRequestRequest(String friendId) {
            super(friendId);
        }
    }

    public static class DeclineFriendRequestRequest extends UserAndFriendRequest {
        public DeclineFriendRequestRequest(String friendId) {
            super(friendId);
        }
    }

    public static class DeleteFriendRequest extends UserAndFriendRequest {
        public DeleteFriendRequest(String friendId) {
            super(friendId);
        }
    }

    public static class FindUsersRequest {
        private final String match;
        private final Type type;

        public FindUsersRequest(String match, Type type) {
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

        public enum Type {
            HANDLE, USERNAME, EMAIL
        }
    }
}


