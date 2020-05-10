package edu.cooper.ee.ece366.coopmo.error;
// https://github.com/spring-guides/gs-uploading-files/blob/master/complete/src/main/java/com/example/uploadingfiles/storage/StorageException.java

public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}