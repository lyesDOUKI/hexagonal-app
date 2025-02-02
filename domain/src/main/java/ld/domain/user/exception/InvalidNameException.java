package ld.domain.user.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException(String message) {
        super(message);
    }
}
