package dev.kiyari.note.util.exception;

public class EntityAlreadyPresentException extends RuntimeException {
    public EntityAlreadyPresentException() {
    }

    public EntityAlreadyPresentException(String message) {
        super(message);
    }

    public EntityAlreadyPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyPresentException(Throwable cause) {
        super(cause);
    }

    public EntityAlreadyPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
