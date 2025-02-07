package ld.domain.feature.putaddress;

import ld.domain.user.information.adresse.Adresse;

import java.util.Objects;
import java.util.UUID;

public record PutAddressToUserCommand (UUID uuid, Adresse adresse){
    public PutAddressToUserCommand{
        Objects.requireNonNull(uuid, "uuid must not be null to put an address for a user");
        Objects.requireNonNull(adresse,"address must not be null to put an address for a user");
    }
}
