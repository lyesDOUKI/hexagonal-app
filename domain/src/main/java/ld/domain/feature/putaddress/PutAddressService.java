package ld.domain.feature.putaddress;

import ld.domain.feature.common.RetrieveUserPort;
import ld.domain.user.User;
import ld.domain.user.exception.UserDomainException;
import ld.domain.user.information.adresse.Adresse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PutAddressService implements PutAddressUseCase{

    private final VerifyAddressPort verifyAddressPort;
    private final RetrieveUserPort retrieveUserPort;
    private final PutAddressValidation putAddressValidation;
    private final PersistAddressPort persistAddressPort;

    public PutAddressService(VerifyAddressPort verifyAddressPort, RetrieveUserPort retrieveUserPort,
                             PersistAddressPort persistAddressPort) {
        this.verifyAddressPort = verifyAddressPort;
        this.retrieveUserPort = retrieveUserPort;
        this.persistAddressPort = persistAddressPort;
        this.putAddressValidation = new PutAddressValidation();
    }

    @Override
    public User execute(PutAddressToUserCommand putAddressToUserCommand) {
        List<RuntimeException> exceptions = new ArrayList<>();
        Optional<User> optionalUser = this.retrieveUserPort.getUserById(putAddressToUserCommand.uuid());
        User user = optionalUser.orElseThrow(() -> this.throwDomainException(exceptions));
        this.putAddressValidation.validateGivenAddress(putAddressToUserCommand.adresse(), verifyAddressPort, exceptions);
        return this.persistAddressPort.execute(putAddressToUserCommand.adresse(), user.userId());
    }

    private RuntimeException throwDomainException(List<RuntimeException> exceptions){
        UserDomainException userDomainException = new UserDomainException();
        userDomainException.setRuntimeExceptions(exceptions);
        return userDomainException;
    }
}
