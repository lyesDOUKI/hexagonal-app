package ld.domain.user.exception;

public class InvalidSurnameException extends RuntimeException {
    public InvalidSurnameException(String message){
        super(message);
    }
}
