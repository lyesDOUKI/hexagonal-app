package com.ld.application.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class PutAddressRequest{

    @NotNull(message = "Le nom de l'adresse ne peut pas être null.")
    @NotBlank
    @Schema(description = "Le nom de l'adresse", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nomAdresse;

    @Schema(description = "Le complément d'adresse (facultatif)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String complementAdresse;

    @NotNull(message = "Le code postal ne peut pas être null.")
    @Pattern(regexp = "^[0-9]{5}$", message = "Le code postal doit être composé de 5 chiffres.")
    @Schema(description = "Le code postal de l'adresse sur 5 chiffre", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codePostal;

    @NotNull(message = "La ville ne peut pas être null.")
    @NotBlank
    @Schema(description = "La ville de l'adresse", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ville;

    @NotNull(message = "Le pays ne peut pas être null.")
    @NotBlank
    @Schema(description = "Le pays de l'adresse", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pays;

    public PutAddressRequest(){}
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

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}