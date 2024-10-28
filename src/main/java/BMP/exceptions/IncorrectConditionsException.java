package BMP.exceptions;

public class IncorrectConditionsException extends RuntimeException{
    public IncorrectConditionsException() {
    }

    public IncorrectConditionsException(String message) {
        super(message);
    }

    public IncorrectConditionsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectConditionsException(Throwable cause) {
        super(cause);
    }

    public IncorrectConditionsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
