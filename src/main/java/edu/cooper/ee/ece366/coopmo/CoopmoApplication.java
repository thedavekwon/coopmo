package edu.cooper.ee.ece366.coopmo;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import edu.cooper.ee.ece366.coopmo.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class CoopmoApplication {
    public static ConcurrentHashMap<Long, Payment> paymentDB = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Long, User> userDB = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(CoopmoApplication.class, args);
    }
}
