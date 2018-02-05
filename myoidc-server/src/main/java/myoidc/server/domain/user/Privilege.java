package myoidc.server.domain.user;


/**
 * @author Shengzhao Li
 */

public enum Privilege {

    //Any user have the default privilege
    USER("User");


    private String label;


    Privilege(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


}