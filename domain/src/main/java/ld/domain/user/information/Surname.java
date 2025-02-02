package ld.domain.user.information;

import java.util.Objects;

public record Surname(String value) {
    public Surname{
        Objects.requireNonNull(value, "Surname value can't be null on Email value object");
    }
}
