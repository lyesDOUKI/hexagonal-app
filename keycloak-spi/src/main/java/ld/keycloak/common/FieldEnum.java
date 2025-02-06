package ld.keycloak.common;

import ld.domain.user.exception.InvalidBirthDateException;
import ld.domain.user.exception.InvalidEmailException;
import ld.domain.user.exception.InvalidNameException;
import ld.domain.user.exception.InvalidSurnameException;

public enum FieldEnum {
    INVALID_BIRTHDATE(InvalidBirthDateException.class, "birthdate"),
    INVALID_NAME(InvalidNameException.class, "firstName"),
    INVALID_SURNAME(InvalidSurnameException.class, "lastName"),
    INVALID_EMAIL(InvalidEmailException.class, "email");

    private final Class<? extends RuntimeException> exceptionClass;
    private final String fieldName;

    FieldEnum(Class<? extends RuntimeException> exceptionClass, String fieldName) {
        this.exceptionClass = exceptionClass;
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static String getFieldFromException(RuntimeException ex) {
        for (FieldEnum fieldEnum : values()) {
            if (fieldEnum.exceptionClass.equals(ex.getClass())) {
                return fieldEnum.getFieldName();
            }
        }
        return null;
    }
}
