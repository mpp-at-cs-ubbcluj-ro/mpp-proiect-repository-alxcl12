/*
 *  @author albua
 *  created on 28/02/2021
 */
package model.validators;
/**
 * Custom exception used to signal validation exception
 */
public class ValidationException extends RuntimeException {
    public ValidationException() { }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
