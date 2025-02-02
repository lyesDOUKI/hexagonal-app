package ld.domain.feature.updateuser;

import ld.domain.user.information.BirthDate;
import ld.domain.user.information.Email;
import ld.domain.user.information.UserId;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public record UpdateUserCommand(UUID userId, Optional<Email> email, Optional<BirthDate> birthDate) {
    public UpdateUserCommand{
        Objects.requireNonNull(userId, "UserId can't be null on UpdateUserCommand");
    }
    private static <T> void validateNotNull(T value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " can't be null on UpdateUserCommand");
    }
}
