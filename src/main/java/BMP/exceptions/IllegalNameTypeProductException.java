package BMP.exceptions;

public class IllegalNameTypeProductException extends RuntimeException{
    public IllegalNameTypeProductException() {
    }

    public IllegalNameTypeProductException(String s) {
        super(s);
    }

    public IllegalNameTypeProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNameTypeProductException(Throwable cause) {
        super(cause);
    }
}
