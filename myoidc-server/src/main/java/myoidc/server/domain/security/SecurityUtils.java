package myoidc.server.domain.security;


/**
 * @author Shengzhao Li
 */
public class SecurityUtils {

    private static SecurityHolder securityHolder;

    public void setSecurityHolder(SecurityHolder securityHolder) {
        SecurityUtils.securityHolder = securityHolder;
    }

//    public static User currentUser() {
//        AndailyUserDetails userDetails = securityHolder.userDetails();
//        return userDetails != null ? userDetails.user() : null;
//    }
//
//    public static String currentUsername() {
//        final User user = currentUser();
//        return user != null ? user.username() : "unknown";
//    }
}