package BMP.exceptions;

public class IllegalNumberFormatException extends RuntimeException{
    public IllegalNumberFormatException() {
    }

    public IllegalNumberFormatException(String message) {
        super(message);
    }

    public IllegalNumberFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNumberFormatException(Throwable cause) {
        super(cause);
    }

    public IllegalNumberFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
