package ssobocik.fp.exceptions;

/**
 * @author szymon.sobocik
 */
public class BadUserCredentialsException extends Exception {

    public BadUserCredentialsException() {
    }

    public BadUserCredentialsException(String message) {
        super(message);
    }

    public BadUserCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadUserCredentialsException(Throwable cause) {
        super(cause);
    }
}
