package edu.cooper.ee.ece366.coopmo.handler;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    protected ResponseEntity<?> checkEmpty(String input, String field, JsonObject respBody) {
        if (input.equals("")) {
            respBody.addProperty("message", "Empty " + field);
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    protected ResponseEntity<?> checkPositive(Long input, String field, JsonObject respBody) {
        if (input <= 0) {
            respBody.addProperty("message", "Empty " + field);
            return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
