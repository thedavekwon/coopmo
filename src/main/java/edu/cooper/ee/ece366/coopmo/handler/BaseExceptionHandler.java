package edu.cooper.ee.ece366.coopmo.handler;

import edu.cooper.ee.ece366.coopmo.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// TODO (add more exceptions)
// TODO (make custom message for different exceptions)
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = {EmptyFieldException.class, InValidFieldValueException.class, InValidFieldTypeException.class, IllegalArgumentException.class})
    public final ResponseEntity<?> handleException(Exception ex) {
        return handleAllInValidException(ex);
    }

    private ResponseEntity<?> handleAllInValidException(Exception ex) {
        Message respMessage = new Message();
        Message.Err error = new Message.Err("1", ex.getMessage());
        respMessage.setError(error);
        return new ResponseEntity<>(respMessage, HttpStatus.BAD_REQUEST);
    }

    public static class InValidFieldValueException extends Exception {
        public InValidFieldValueException(String message) {
            super(message);
        }
    }

    public static class EmptyFieldException extends Exception {
        public EmptyFieldException(String message) {
            super(message);
        }
    }

    public static class InValidFieldTypeException extends Exception {
        public InValidFieldTypeException(String message) {
            super(message);
        }
    }
}
