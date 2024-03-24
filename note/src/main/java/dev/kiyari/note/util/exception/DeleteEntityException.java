package dev.kiyari.note.util.exception;

public class DeleteEntityException extends RuntimeException {
    public DeleteEntityException() {
    }

    public DeleteEntityException(String message) {
        super(message);
    }

    public DeleteEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteEntityException(Throwable cause) {
        super(cause);
    }

    public DeleteEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
