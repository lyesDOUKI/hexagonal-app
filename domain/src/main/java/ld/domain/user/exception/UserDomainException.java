package ld.domain.user.exception;

import java.util.List;

public class UserDomainException extends RuntimeException{
    private List<RuntimeException> runtimeExceptions;

    public UserDomainException() {
        super("User Domain Exception - go to runtime Exception list to see all the exceptions");
    }

    public void setRuntimeExceptions(List<RuntimeException> runtimeExceptions) {
        this.runtimeExceptions = runtimeExceptions;
    }

    public List<RuntimeException> getRuntimeExceptions() {
        return runtimeExceptions;
    }
}
