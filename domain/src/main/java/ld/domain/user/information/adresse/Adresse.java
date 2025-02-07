package ld.domain.user.information.adresse;

import ld.domain.user.information.UserId;
import ld.domain.user.information.adresse.information.*;

public record Adresse(UserId userId, NomAdresse nomAdresse, ComplementAdresse complementAdresse, CodePostal codePostal,
                      Ville ville, Pays pays) {
}
