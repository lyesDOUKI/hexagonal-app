package com.ld.infrastructure.db.entity;

import jakarta.persistence.*;
import ld.domain.user.information.adresse.Adresse;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_surname", nullable = false)
    private String surname;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_birthdate")
    private LocalDate birthdate;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false, unique = true, columnDefinition = "keycloak_id")
    private UUID keycloakId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_adresse_id")
    private UserAddress adresse;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UUID getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(UUID keycloakId) {
        this.keycloakId = keycloakId;
    }

    public UserAddress getAdresse() {
        return adresse;
    }

    public void setAdresse(UserAddress adresse) {
        this.adresse = adresse;
    }
}
