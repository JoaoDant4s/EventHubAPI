package imd.eventhub.exception;

public class PasswordNotValidException extends RuntimeException {
    public PasswordNotValidException(String msg) {
        super(msg);
    }
    public PasswordNotValidException() {
        super("A senha não é válida");
    }
}
