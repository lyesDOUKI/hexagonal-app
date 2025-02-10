package com.ld.infrastructure.db.repository;

import com.ld.infrastructure.db.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

@Repository
public interface IUserAddressRepository extends JpaRepository<UserAddress, Long> {
    @Query("SELECT ua FROM UserAddress ua WHERE UPPER(ua.nomAdresse) = UPPER(:nomAdresse) AND " +
            "UPPER(ua.complementAdresse) = UPPER(:complementAdresse) AND UPPER(ua.codePostal) = UPPER(:codePostal)")
    Optional<UserAddress> findByNomAdresseAndComplementAdresseAndCodePostal(
            @Param("nomAdresse") String nomAdresse,
            @Param("complementAdresse") String complementAdresse,
            @Param("codePostal") String codePostal);

}
