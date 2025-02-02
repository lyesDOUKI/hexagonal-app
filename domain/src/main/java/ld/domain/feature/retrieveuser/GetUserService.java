package ld.domain.feature.retrieveuser;

import ld.domain.user.User;
import ld.domain.dependencies.UserRepositoryPort;

import java.util.Optional;

public class GetUserService implements GetUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final GetUserValidation getUserValidation;

    public GetUserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.getUserValidation = new GetUserValidation();

    }

    @Override
    public Optional<User> getUserById(GetUserByIdQuery userByIdQuery) {
        this.getUserValidation.validate(userByIdQuery);
        return this.userRepositoryPort.getUserById(userByIdQuery.userId());
    }
}
