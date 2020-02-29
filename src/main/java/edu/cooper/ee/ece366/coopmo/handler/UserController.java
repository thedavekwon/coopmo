package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import edu.cooper.ee.ece366.coopmo.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
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

    @GetMapping("/editProfile")
    public ResponseEntity<String> editProfile(
            @RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam(value = "newName", defaultValue = "") String newName,
            @RequestParam(value = "newUsername", defaultValue = "") String newUsername,
            @RequestParam(value = "newPassword", defaultValue = "") String newPassword,
            @RequestParam(value = "newEmail", defaultValue = "") String newEmail,
            @RequestParam(value = "newHandle", defaultValue = "") String newHandle
    ){
        if(id.equals("")){
            return ResponseEntity.badRequest().body("No id provided");
        }
        else if(newName.equals("") || newUsername.equals("") || newPassword.equals("") || newEmail.equals("") || newHandle.equals(""))
        {
            //This should actually be handled on the client side
            return ResponseEntity.badRequest().body("Please fill out all of the fields");
        }
        else{
            ArrayList<Integer> errors =  userService.editProfile(id, newName, newUsername,newPassword, newEmail,newHandle);
            if(errors.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.OK).body("Profile updated");
            }
            else{
                Iterator<Integer> error = errors.iterator();
                String error_msg = "";
                String[] pos_errors= {"No userId found.", "Username already exists.", "Email already used by other user.",
                "Handle already exists."};
                while(error.hasNext())
                {
                    error_msg += pos_errors[error.next()];
                }
                return ResponseEntity.badRequest().body(error_msg);
            }
        }
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



    @GetMapping("/acceptIncomingFriendRequest")
    public ResponseEntity<String> acceptIncomingRequest(
            @RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        if(id.equals("") || friendId.equals("")){
            return ResponseEntity.badRequest().body("No User ID and/or Friend ID found");
        }
        else
        {
            if(userService.acceptIncomingRequest(id,friendId)==0)
                return ResponseEntity.status(HttpStatus.OK).body("Accepted Incoming Friend Request");
            else
                return ResponseEntity.badRequest().body("No User with provided ID and/or Friend ID found in Incoming Requests");
        }
    }

    @GetMapping("/sendOutgoingFriendRequest")
    public ResponseEntity<String> sendOutgoingFriendRequest(
            @RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam(value = "friendId", defaultValue = "") String friendId) {
        if(id.equals("") || friendId.equals("")){
            return ResponseEntity.badRequest().body("No User ID and/or Friend ID provided");
        }
        else
        {
            int ret_val = userService.sendOutgoingFriendRequest(id, friendId);
            if(ret_val==0)
                return ResponseEntity.status(HttpStatus.OK).body("Sent Outgoing Friend Request");
            else if(ret_val == -1)
                return ResponseEntity.badRequest().body("No User with provided ID and/or Friend ID found");
            else
                return ResponseEntity.badRequest().body("Already Friends");
        }
    }
    @GetMapping("/cancelFriendRequest")
    public ResponseEntity<String> cancelOutgoingFriendRequest(
            @RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam(value = "friendId", defaultValue = "") String friendId){
        if(id.equals("") || friendId.equals("")){
            return ResponseEntity.badRequest().body("No User ID and/or Friend ID provided");
        }
        else{
            int ret_val = userService.cancelOutgoingFriendRequest(id,friendId);
            if(ret_val == 0){
                return ResponseEntity.status(HttpStatus.OK).body("Cancelled Outgoing Friend Request");
            }
            else if (ret_val == -1)
                return ResponseEntity.badRequest().body("No User with provided ID and/or Friend ID found");
            else
                return ResponseEntity.badRequest().body("User not found in outgoing friends requests");
        }
    }

    @GetMapping("/declineFriendRequest")
    public ResponseEntity<String> declineFriendRequest(
            @RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam(value = "friendId", defaultValue = "") String friendId){
        if(id.equals("") || friendId.equals("")){
            return ResponseEntity.badRequest().body("No User ID and/or Friend ID provided");
        }
        else{
            int ret_val = userService.cancelOutgoingFriendRequest(friendId,id);
            if(ret_val == 0){
                return ResponseEntity.status(HttpStatus.OK).body("Declined Incoming Friend Request");
            }
            else if(ret_val == -1)
                return ResponseEntity.badRequest().body("No User with provided ID and/or Friend ID found");
            else
                return ResponseEntity.badRequest().body("User not found in incoming friend requests");
        }
    }


}
