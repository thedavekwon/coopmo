package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Jackson automatically convert object to string
// marks the class as controller where every method returns a domain object instead of a view
@RestController
@RequestMapping("/user")
public class UserController {

    // @GetMapping map HTTP GET requst with /user
    // @RequestParam binds the value of the query string
    @GetMapping("/getUser")
    public User user(@RequestParam(value = "id", defaultValue = "0") String id,
                     @RequestParam(value = "firstName", defaultValue = "first") String firstName,
                     @RequestParam(value = "lastName", defaultValue = "last") String lastName) {
        return new User(firstName, lastName);
    }
}
