package myoidc.server.infrastructure.oidc;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 2016/12/25
 *
 * @author Shengzhao Li
 */
public abstract class OIDCUtils {


    /**
     * 默认的 refresh_token 有效时间(秒)
     * From DefaultTokenServices.java
     *
     * @since 1.1.0
     */
    public static int REFRESH_TOKEN_VALIDITY = 60 * 60 * 24 * 30; // default 30 days.

    /**
     * 默认的 access_token 有效时间(秒)
     * From DefaultTokenServices.java
     *
     * @since 1.1.0
     */
    public static int ACCESS_TOKEN_VALIDITY = 60 * 60 * 12; // default 12 hours.


    /**
     * 判断是否为 JWT 格式 token
     *
     * @param tokenValue token
     * @return true JWT, otherwise false
     * @since 1.1.0
     */
    public static boolean isJWT(String tokenValue) {
        return StringUtils.isNotBlank(tokenValue)
                && tokenValue.split("\\.").length == 3;
    }


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

        public static List<String> types() {
            GrantType[] values = values();
            List<String> list = new ArrayList<>(values.length);
            for (GrantType value : values) {
                list.add(value.getType());
            }
            return list;
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
