package ssobocik.fp.exceptions;

/**
 * @author szymon.sobocik
 */
public class ObjectNotValidException extends Exception {

    public ObjectNotValidException() {
    }

    public ObjectNotValidException(String message) {
        super(message);
    }

    public ObjectNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotValidException(Throwable cause) {
        super(cause);
    }
}
