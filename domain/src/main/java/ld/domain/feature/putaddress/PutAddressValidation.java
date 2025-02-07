package ld.domain.feature.putaddress;

import ld.domain.user.exception.InvalidAdresseException;
import ld.domain.user.exception.UserDomainException;
import ld.domain.user.information.adresse.Adresse;

import java.util.List;

public class PutAddressValidation {
    public void validateGivenAddress(Adresse adresse, VerifyAddressPort verifyAddressPort,
                                     List<RuntimeException> exceptions){
        if(!verifyAddressPort.isValidAddress(adresse)){
            exceptions.add(new InvalidAdresseException("user.address.invalid"));
        }
        if(!exceptions.isEmpty()){
            UserDomainException userDomainException = new UserDomainException();
            userDomainException.setRuntimeExceptions(exceptions);
            throw userDomainException;
        }
    }
}
