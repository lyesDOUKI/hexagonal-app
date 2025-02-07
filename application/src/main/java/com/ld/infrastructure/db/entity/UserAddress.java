package com.ld.infrastructure.db.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_adresse")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adresse_id")
    private Long id;

    @Column(name = "nom_adresse", nullable = false)
    private String nomAdresse;

    @Column(name = "complement_adresse")
    private String complementAdresse;

    @Column(name = "code_postal", precision = 5)
    private String codePostal;

    @Column(name = "ville", nullable = false)
    private String ville;

    @Column(name = "pays", nullable = false)
    private String pays;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "adresse")
    List<UserEntity> usersWithThisAddress = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomAdresse() {
        return nomAdresse;
    }

    public void setNomAdresse(String nomAdresse) {
        this.nomAdresse = nomAdresse;
    }

    public String getComplementAdresse() {
        return complementAdresse;
    }

    public void setComplementAdresse(String complementAdresse) {
        this.complementAdresse = complementAdresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public List<UserEntity> getUsersWithThisAddress() {
        return usersWithThisAddress;
    }

    public void setUsersWithThisAddress(List<UserEntity> usersWithThisAddress) {
        this.usersWithThisAddress = usersWithThisAddress;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}