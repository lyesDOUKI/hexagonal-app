package ld.domain.feature.retrieveuser;

import ld.domain.user.information.UserId;

import java.util.UUID;

public record GetUserByIdQuery(UUID userId) {
}
