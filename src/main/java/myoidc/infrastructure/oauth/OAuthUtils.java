package myoidc.infrastructure.oauth;


import org.apache.commons.lang.RandomStringUtils;

/**
 * 2016/12/25
 *
 * @author Shengzhao Li
 */
public abstract class OAuthUtils {

    public static enum GrantType {
        PASSWORD("password"),
        REFRESH_TOKEN("refresh_token"),
        CLIENT_CREDENTIALS("client_credentials");

        private String type;

        GrantType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public String getValue() {
            return name();
        }


    }


    //Available values:   read, read write
    public static final String READ_SCOPE = "read";


    //singleton
    private OAuthUtils() {
    }


    public static String tokenURI(String host) {
        if (host.endsWith("/")) {
            return host + "oauth/token";
        }
        return host + "/oauth/token";
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
