package ld.domain.user.information;

import java.util.Objects;

public record Email(String value) {
    public Email{
        Objects.requireNonNull(value, "email value can't be null on Email value object");
    }
}
