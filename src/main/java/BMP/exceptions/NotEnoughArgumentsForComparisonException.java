package BMP.exceptions;

public class NotEnoughArgumentsForComparisonException extends RuntimeException{
    public NotEnoughArgumentsForComparisonException() {
    }

    public NotEnoughArgumentsForComparisonException(String message) {
        super(message);
    }

    public NotEnoughArgumentsForComparisonException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughArgumentsForComparisonException(Throwable cause) {
        super(cause);
    }

    public NotEnoughArgumentsForComparisonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
