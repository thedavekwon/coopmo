package edu.cooper.ee.ece366.coopmo.SecurityConfig;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler;
import edu.cooper.ee.ece366.coopmo.handler.UserController;
import edu.cooper.ee.ece366.coopmo.message.Message;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException {
        userRepository.findByUsernameStartsWith(username);
        Optional<User> user = getUserwithUsername(username, password);
        return new MyUserDetails(user);
    }

    public User getUserWithUsername(String username, String password) throws BaseExceptionHandler.InValidFieldValueException {
        String userId = userRepository.getIdfromUsername(username);
        if (userId == null) throw new BaseExceptionHandler.InValidFieldValueException("Invalid Username");

        User curUser = checkValidUserId(userId);
        if (!curUser.getPassword().equals(password)) {
            throw new BaseExceptionHandler.InValidFieldValueException("Invalid Password");
        }
        return curUser;
    }

}
