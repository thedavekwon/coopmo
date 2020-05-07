package edu.cooper.ee.ece366.coopmo.error;
// https://github.com/spring-guides/gs-uploading-files/blob/master/complete/src/main/java/com/example/uploadingfiles/storage/StorageFileNotFoundException.javas

public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}