package ld.domain.feature.retrieveuser;

import ld.domain.feature.common.RetrieveUserPort;
import ld.domain.user.User;

import java.util.Optional;

public class GetUserService implements GetUserUseCase {

    private final RetrieveUserPort userRepositoryPort;
    private final GetUserValidation getUserValidation;

    public GetUserService(RetrieveUserPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.getUserValidation = new GetUserValidation();

    }

    @Override
    public Optional<User> getUserById(GetUserByIdQuery userByIdQuery) {
        this.getUserValidation.validate(userByIdQuery);
        return this.userRepositoryPort.getUserById(userByIdQuery.userId());
    }
}
