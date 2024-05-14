package imd.eventhub.exception;

public class EmailNotValidException extends RuntimeException {
    public EmailNotValidException(String msg) {
        super(msg);
    }
}
