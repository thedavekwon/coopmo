package edu.cooper.ee.ece366.coopmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class CoopmoApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(CoopmoApplication.class, args);
//        CoopmoTest.testPayment();
    }
}
