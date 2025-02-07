package ld.domain.feature.putaddress;

import ld.domain.user.User;

public interface PutAddressUseCase {
    User execute(PutAddressToUserCommand putAddressToUserCommand);
}
