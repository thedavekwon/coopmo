package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.CoopmoApplication;
import edu.cooper.ee.ece366.coopmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    // TODO(error handling if something is missing)
    // @PostMapping("/createUser")
    @GetMapping("/createUser")
    public User createUser(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "handle", defaultValue = "") String handle) {
        User newUser = new User(name, username, password, email, handle);

//        userDB.put(newUser.id, newUser); //TODO(not working)
        return newUser;
    }
}
