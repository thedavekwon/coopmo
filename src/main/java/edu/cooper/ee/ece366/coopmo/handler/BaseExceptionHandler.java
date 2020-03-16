package edu.cooper.ee.ece366.coopmo.handler;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = {EmptyFieldException.class, InValidFieldException.class})
    public final ResponseEntity<?> handleException(Exception ex) {
        // TODO (add more exceptions)
        return handleAllInValidException(ex);
    }

    private ResponseEntity<?> handleAllInValidException(Exception ex) {
        JsonObject respBody = new JsonObject();
        respBody.addProperty("message", ex.getMessage());
        return new ResponseEntity<>(respBody.toString(), HttpStatus.BAD_REQUEST);
    }

    public static class InValidFieldException extends Exception {
        public InValidFieldException(String message) {
            super(message);
        }
    }

    public static class EmptyFieldException extends Exception {
        public EmptyFieldException(String message) {
            super(message);
        }
    }
}
