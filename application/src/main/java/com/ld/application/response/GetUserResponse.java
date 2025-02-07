package com.ld.application.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserResponse {
    private Long id;
    private String name;
    private String email;
    private String birthdate;
    private MonAdresse adresse;

    public GetUserResponse(Long id, String name, String email, String birthdate){
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public MonAdresse getAdresse() {
        return adresse;
    }

    public void setAdresse(MonAdresse adresse) {
        this.adresse = adresse;
    }

    public static class MonAdresse{
        private String nomAdresse;
        private String complementAdrese;
        private String codePostal;
        private String ville;
        private String pays;

        public MonAdresse(String nomAdresse, String complementAdrese, String codePostal, String ville, String pays) {
            this.nomAdresse = nomAdresse;
            this.complementAdrese = complementAdrese;
            this.codePostal = codePostal;
            this.ville = ville;
            this.pays = pays;
        }


        public String getNomAdresse() {
            return nomAdresse;
        }

        public void setNomAdresse(String nomAdresse) {
            this.nomAdresse = nomAdresse;
        }

        public String getComplementAdrese() {
            return complementAdrese;
        }

        public void setComplementAdrese(String complementAdrese) {
            this.complementAdrese = complementAdrese;
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
}
