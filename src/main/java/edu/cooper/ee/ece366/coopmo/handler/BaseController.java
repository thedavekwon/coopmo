package edu.cooper.ee.ece366.coopmo.handler;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    protected ResponseEntity<?> checkEmpty(String input, String field, JSONObject respBody) {
        if (input.equals("")) {
            respBody.put("message", "Empty " + field);
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    protected ResponseEntity<?> checkPositive(Long input, String field, JSONObject respBody) {
        if (input <= 0) {
            respBody.put("message", "Empty " + field);
            return new ResponseEntity<>(respBody, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
