package edu.cooper.ee.ece366.coopmo;

import edu.cooper.ee.ece366.coopmo.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class CoopmoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoopmoApplication.class, args);
    }
}
