package myoidc.server.domain.security;


import myoidc.server.domain.user.User;

/**
 * @author Shengzhao Li
 */
public class SecurityUtils {

    private static SecurityHolder securityHolder;

    public void setSecurityHolder(SecurityHolder securityHolder) {
        SecurityUtils.securityHolder = securityHolder;
    }


    /**
     * 当前登录用户
     *
     * @return Current user
     * @since 1.1.0
     */
    public static User currentUser() {
        OIDCUserDetails userDetails = securityHolder.userDetails();
        return userDetails != null ? userDetails.user() : null;
    }


    /**
     * 当前登录用户名
     *
     * @return Current username
     * @since 1.1.0
     */
    public static String currentUsername() {
        final User user = currentUser();
        return user != null ? user.username() : "unknown";
    }
}