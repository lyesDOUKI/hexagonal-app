package ld.domain.user.information;

import java.util.Objects;

public record Name(String value) {
    public Name{
        Objects.requireNonNull(value, "name value can't be null on Email value object");
    }
}