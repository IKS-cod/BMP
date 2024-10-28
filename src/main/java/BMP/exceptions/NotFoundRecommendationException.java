package BMP.exceptions;

public class NotFoundRecommendationException extends RuntimeException{
    public NotFoundRecommendationException() {
    }

    public NotFoundRecommendationException(String message) {
        super(message);
    }

    public NotFoundRecommendationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundRecommendationException(Throwable cause) {
        super(cause);
    }

    public NotFoundRecommendationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
