package ld.domain.user.exception;

public class InvalidBirthDateException extends RuntimeException {
    public InvalidBirthDateException(String message) {
        super(message);
    }
}
