package imd.eventhub.exception;

public class RatingOutOfRangeException extends RuntimeException {
    public RatingOutOfRangeException(String msg) {
        super(msg);
    }
}
