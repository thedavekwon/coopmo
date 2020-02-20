package edu.cooper.ee.ece366.coopmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// SpringBootApplication includes:
// 1. @Configuration
// 2. @EnableAutoConfiguration
// 3. @ComponentScan
@SpringBootApplication
public class CoopmoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoopmoApplication.class, args);
    }
}
