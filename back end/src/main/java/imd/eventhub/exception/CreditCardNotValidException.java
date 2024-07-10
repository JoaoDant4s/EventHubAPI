package imd.eventhub.exception;

public class CreditCardNotValidException extends RuntimeException {
    public CreditCardNotValidException(String msg) {
        super(msg);
    }
}
