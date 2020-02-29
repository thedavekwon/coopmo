package edu.cooper.ee.ece366.coopmo.handler;

import org.springframework.http.ResponseEntity;

public class BaseController {
    protected ResponseEntity<String> checkEmpty(String input, String field) {
        if (input.equals("")) return ResponseEntity.badRequest().body("Empty " + field);
        return null;
    }

    protected ResponseEntity<String> checkPositive(Long input, String field) {
        if (input <= 0) return ResponseEntity.badRequest().body("Empty " + field);
        return null;
    }
}
