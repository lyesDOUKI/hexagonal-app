package ld.domain.feature.putaddress;

import ld.domain.user.User;
import ld.domain.user.information.UserId;
import ld.domain.user.information.adresse.Adresse;

public interface PersistAddressPort {
    User execute(Adresse adresse, UserId userId);
}
