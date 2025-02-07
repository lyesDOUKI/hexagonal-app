package ld.domain.feature.putaddress;

import ld.domain.user.information.adresse.Adresse;

public interface VerifyAddressPort {
    boolean isValidAddress(Adresse adresse);
}
