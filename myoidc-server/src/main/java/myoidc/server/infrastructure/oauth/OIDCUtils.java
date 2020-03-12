package myoidc.server.infrastructure.oauth;


import org.apache.commons.lang3.RandomStringUtils;

/**
 * 2016/12/25
 *
 * @author Shengzhao Li
 */
public abstract class OIDCUtils {


    public enum GrantType {
        PASSWORD("password"),
        AUTHORIZATION_CODE("authorization_code"),
        IMPLICIT("implicit"),
        REFRESH_TOKEN("refresh_token"),
        CLIENT_CREDENTIALS("client_credentials");

        private String type;

        GrantType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return this.type;
        }
    }


    // SCOPE
    public static final String SCOPE_READ = "read";
    public static final String SCOPE_WRITE = "write";
    public static final String SCOPE_OPENID = "openid";


    //singleton
    private OIDCUtils() {
    }


    public static String tokenEndpoint(String host) {
        if (host.endsWith("/")) {
            return host + "oauth/token";
        }
        return host + "/oauth/token";
    }


    /**
     * authorize URL
     *
     * @param host host
     * @return full url
     * @since 1.1.0
     */
    public static String authorizeEndpoint(String host) {
        if (host.endsWith("/")) {
            return host + "oauth/authorize";
        }
        return host + "/oauth/authorize";
    }


    /**
     * see UserInfoEndpoint.java
     *
     * @param host host
     * @return full url
     * @since 1.1.0
     */
    public static String userinfoEndpoint(String host) {
        if (host.endsWith("/")) {
            return host + "api/userinfo";
        }
        return host + "/api/userinfo";
    }


    /**
     * jwt url
     *
     * @param host host
     * @return full url
     * @since 1.1.0
     */
    public static String jwksURI(String host) {
        if (host.endsWith("/")) {
            return host + "public/jwks";
        }
        return host + "/public/jwks";
    }


    /**
     * register client url
     * see RegistrationEndpoint
     *
     * @param host host
     * @return full url
     * @since 1.1.0
     */
    public static String registrationEndpoint(String host) {
        if (host.endsWith("/")) {
            return host + "public/registration";
        }
        return host + "/public/registration";
    }

    /**
     * Generate a new clientId
     *
     * @return clientId
     */
    public static String generateClientId() {
        return RandomStringUtils.random(32, true, true);
    }


    /**
     * Generate a new clientSecret
     *
     * @return clientSecret
     */
    public static String generateClientSecret() {
        return RandomStringUtils.random(40, true, true);
    }


}
