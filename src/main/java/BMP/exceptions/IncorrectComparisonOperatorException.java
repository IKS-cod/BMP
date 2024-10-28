package BMP.exceptions;

public class IncorrectComparisonOperatorException extends RuntimeException{
    public IncorrectComparisonOperatorException() {
    }

    public IncorrectComparisonOperatorException(String message) {
        super(message);
    }

    public IncorrectComparisonOperatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectComparisonOperatorException(Throwable cause) {
        super(cause);
    }

    public IncorrectComparisonOperatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
