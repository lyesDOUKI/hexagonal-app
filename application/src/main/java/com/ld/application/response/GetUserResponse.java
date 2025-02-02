package com.ld.application.response;

public class GetUserResponse {
    private Long id;
    private String name;
    private String email;
    private String birthdate;

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
}
