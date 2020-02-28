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

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean acceptIncomingRequest(String id, String friendId) {
        Optional<User> priUser = userRepository.findById(id);
        Optional<User> secUser = userRepository.findById(friendId);

        if(!priUser.isPresent() || !secUser.isPresent()) {
            return false;
        }
        else{
            //if compare to returns pos use that user first
            //else use other
            if(id.compareTo(friendId) > 0){
                synchronized (priUser.get()){
                    synchronized (secUser.get()){
                        if(!priUser.get().acceptIncomingFriendRequest(friendId))
                            return false;
                        secUser.get().acceptedOutgoingFriendRequest(id);
                    }
                }
            }
            else{
                synchronized (secUser.get()){
                    synchronized (priUser.get()){
                        if(!priUser.get().acceptIncomingFriendRequest(friendId))
                            return false;
                        secUser.get().acceptedOutgoingFriendRequest(id);
                    }
                }
            }
            return true;
        }
    }

    public boolean sendOutgoingFriendRequest(String id, String friendId) {
        Optional<User> priUser = userRepository.findById(id);
        Optional<User> secUser = userRepository.findById(friendId);

        if(!priUser.isPresent() || !secUser.isPresent()) {
            return false;
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
            return true;
        }
    }
}
