package imd.eventhub.exception;

public class PasswordNotValidException extends RuntimeException {
    public PasswordNotValidException(String msg) {
        super(msg);
    }
}
