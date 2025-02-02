package com.ld.application.request;

import jakarta.validation.constraints.Pattern;

public record CreateUserRequest(String name, String surname, String email,
                                @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",
                                        message = "Date must be in format YYYY-MM-DD") String birthdate) {
}
