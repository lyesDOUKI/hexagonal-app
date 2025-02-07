package com.ld.infrastructure.db.repository;

import com.ld.infrastructure.db.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface IUserAddressRepository extends JpaRepository<UserAddress, Long> {
    Optional<UserAddress> findByNomAdresseAndComplementAdresseAndCodePostal(String nomAdresse, String ComplementAdresse, String codePostal);
}
