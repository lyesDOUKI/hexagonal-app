package ld.keycloak.common;

public enum AvailableKeycloakMessageEnum {
    TOO_YOUNG("user.birthdate.validation"),
    SAME_NAMES("user.validation.sameNameUsername");

    private String code;

     AvailableKeycloakMessageEnum(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean isMessageManagedByKeycloak(String code){
         for (AvailableKeycloakMessageEnum availableKeycloakMessageEnum :values()){
             if(availableKeycloakMessageEnum.code.equals(code)){
                 return true;
             }
         }
         return false;
    }
}
