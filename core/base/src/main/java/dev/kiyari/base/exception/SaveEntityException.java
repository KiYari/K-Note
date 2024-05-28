package dev.kiyari.base.exception;
public class SaveEntityException extends RuntimeException{
    public SaveEntityException() {
    }

    public SaveEntityException(String message) {
        super(message);
    }

    public SaveEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveEntityException(Throwable cause) {
        super(cause);
    }

    public SaveEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
