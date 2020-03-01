package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.BankAccountRepository;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;




@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(String name, String username, String password, String email, String handle) {
        User new_user = new User(name, username, password, email, handle);
        if(!userRepository.insertEmail(email, new_user.getId()) || !userRepository.insertUname(username, new_user.getId()) || !userRepository.insertHandle(handle, new_user.getId()))
            return null;
        else
            userRepository.save(new_user);
        return new_user;
    }

    // empty if no errors
    // -2 is Username exists already
    // -3 Email already exists
    // -4 Handle already exists
    public ArrayList<Integer> check_if_taken(String uname, String email, String handle){
        ArrayList<Integer> errors = new ArrayList<>();

        if(userRepository.containsUname(uname))
            errors.add(-2);
        if(userRepository.containsEmail(email))
            errors.add(-3);
        if(userRepository.containsHandle(handle))
            errors.add(-4);
        return errors;

    }

    private boolean editUsername(String id, String newUname){
        Optional<User> user = userRepository.findById(id);
        if (userRepository.changeUname(user.get().getUsername(), newUname, id))
        {
            user.get().setUsername(newUname);
            return true;
        }
        else
            return false;
    }

    private boolean editEmail(String id, String newEmail){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            return false;
        else {
            return userRepository.changeEmail(user.get().getEmail(), newEmail, id);
        }
    }

    private boolean editHandle(String id, String newHandle){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            return false;
        else {
            return userRepository.changeHandle(user.get().getHandle(), newHandle, id);
        }
    }

    // empty if no errors
    // -1 is no user with id exists
    // -2 is Username exists already
    // -3 Email already exists
    // -4 Handle already exists
    public ArrayList<Integer> editProfile(String id, String newName, String newUname, String newPassword,
                               String newEmail, String newHandle){
        ArrayList<Integer> errors = new ArrayList<>();
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            errors.add(-1);
            return errors;
        }
        else{
            user.get().setName(newName);
            user.get().setPassword(newPassword);
            if(!editUsername(id, newUname))
                errors.add(-2);
            if(!editEmail(id, newEmail))
                errors.add(-3);
            if(!editHandle(id, newHandle))
                errors.add(-4);
            return errors;
        }
    }

    // This is also used to decline Friend Requests
    // -1 is user not found
    // -2 if friend not found in incoming friend request
    // 0 if success
    public int cancelOutgoingFriendRequest(String id, String friendId){
        Optional<User> priUser = userRepository.findById(id);
        Optional<User> secUser = userRepository.findById(friendId);

        if(!priUser.isPresent() || !secUser.isPresent()) {
            return -1;
        }
        else {
            //if compare to returns pos use that user first
            //else use other
            if (id.compareTo(friendId) > 0) {
                synchronized (priUser.get()) {
                    synchronized (secUser.get()) {
                        priUser.get().deleteOutgoingFriendRequest(friendId);
                        secUser.get().deleteIncomingFriendRequest(id);
                    }
                }
            }
            else{
                synchronized (secUser.get()) {
                    synchronized (priUser.get()) {
                        priUser.get().deleteOutgoingFriendRequest(friendId);
                        secUser.get().deleteIncomingFriendRequest(id);
                    }
                }
            }
            return 0;
        }
    }

    // -1 if user not found
    public int acceptIncomingRequest(String id, String friendId) {
        Optional<User> priUser = userRepository.findById(id);
        Optional<User> secUser = userRepository.findById(friendId);

        if(!priUser.isPresent() || !secUser.isPresent()) {
            return -1;
        }
        else{
            //if compare to returns pos use that user first
            //else use other
            if(id.compareTo(friendId) > 0){
                synchronized (priUser.get()){
                    synchronized (secUser.get()){
                        if(!priUser.get().acceptIncomingFriendRequest(friendId))
                            return -2;
                        secUser.get().acceptedOutgoingFriendRequest(id);
                    }
                }
            }
            else{
                synchronized (secUser.get()){
                    synchronized (priUser.get()){
                        if(!priUser.get().acceptIncomingFriendRequest(friendId))
                            return -2;
                        secUser.get().acceptedOutgoingFriendRequest(id);
                    }
                }
            }
            return 0;
        }
    }

    // -1 if users not found
    // -2 if already friends
    public int sendOutgoingFriendRequest(String id, String friendId) {
        Optional<User> priUser = userRepository.findById(id);
        Optional<User> secUser = userRepository.findById(friendId);

        if(!priUser.isPresent() || !secUser.isPresent()) {
            return -1;
        }
        else{
            //if compare to returns pos use that user first
            //else use other
            if(id.compareTo(friendId) > 0){
                synchronized (priUser.get()){
                    synchronized (secUser.get()){
                        priUser.get().sendOutgoingFriendRequest(friendId);
                        secUser.get().receivedIncomingFriendRequest(id);
                    }
                }
            }
            else{
                synchronized (secUser.get()){
                    synchronized (priUser.get()){
                        priUser.get().sendOutgoingFriendRequest(friendId);
                        secUser.get().receivedIncomingFriendRequest(id);
                    }
                }
            }
            return 0;
        }
    }
}
