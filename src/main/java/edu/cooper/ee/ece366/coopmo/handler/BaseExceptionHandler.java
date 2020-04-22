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
    @ExceptionHandler(value = {EmptyFieldException.class, InValidFieldValueException.class, InValidFieldTypeException.class, IllegalArgumentException.class,
            InvalidBalanceException.class, AlreadyFriendsException.class, NoUserFoundException.class,
            DuplicateFriendRequestException.class, UsersAreNotFriendsException.class, FriendRequestDoesNotExistException.class})
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
            super("Invalid Field Value: " + message);
        }
    }

    public static class EmptyFieldException extends Exception {
        public EmptyFieldException(String message) {
            super("Empty Field Value: " + message);
        }
    }

    public static class InValidFieldTypeException extends Exception {
        public InValidFieldTypeException(String message) {
            super("Invalid Field Type: " + message);
        }
    }

    public static class InvalidBalanceException extends Exception {
        public InvalidBalanceException(String message) {
            super("Invalid Balance: " + message);
        }
    }

    public static class AlreadyFriendsException extends Exception {
        public AlreadyFriendsException(String message) {
            super("Already Friends: " + message);
        }
    }

    public static class NoUserFoundException extends Exception {
        public NoUserFoundException(String message) {
            super("No User Found: " + message);
        }
    }

    public static class DuplicateFriendRequestException extends Exception {
        public DuplicateFriendRequestException(String message) {
            super("Duplicate Friend Request: " + message);
        }
    }

    public static class UsersAreNotFriendsException extends Exception {
        public UsersAreNotFriendsException(String message) {
            super("Users Are Not Friends: " + message);
        }
    }

    public static class FriendRequestDoesNotExistException extends Exception {
        public FriendRequestDoesNotExistException(String message) {
            super("Friend request does not exist: " + message);
        }
    }

    public static class FriendRequestAlreadyExistException extends Exception {
        public FriendRequestAlreadyExistException(String message) {
            super("Friend request already exists: " + message);
        }
    }
}
