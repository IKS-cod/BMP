package BMP.exceptions;

public class IllegalArgumentForSumAndCountException extends RuntimeException{
    public IllegalArgumentForSumAndCountException() {
    }

    public IllegalArgumentForSumAndCountException(String message) {
        super(message);
    }

    public IllegalArgumentForSumAndCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentForSumAndCountException(Throwable cause) {
        super(cause);
    }

    public IllegalArgumentForSumAndCountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
