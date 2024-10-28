package BMP.exceptions;

public class IllegalNameTypeTransactionException extends RuntimeException{
    public IllegalNameTypeTransactionException() {
    }

    public IllegalNameTypeTransactionException(String message) {
        super(message);
    }

    public IllegalNameTypeTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNameTypeTransactionException(Throwable cause) {
        super(cause);
    }

    public IllegalNameTypeTransactionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
