package myoidc.server.domain.user;


/**
 * @author Shengzhao Li
 */

public enum Privilege {

    //Any user have the default privilege
    USER("User"),

    ADMIN("Admin"),    //管理 权限

    CLIENT("Client"),        //注册 client
    UNITY("Unity"),       //资源 权限
    MOBILE("Mobile");   //资源 权限


    private String label;


    Privilege(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


}