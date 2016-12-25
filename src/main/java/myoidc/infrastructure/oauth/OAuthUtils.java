package myoidc.infrastructure.oauth;


import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

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


    //Multi grant_type split by ','
    public static final String EMM_GRANT_TYPES = "client_credentials";


    //See  security.xml configuration
    public static final String EMM_RESOURCE_IDS = "emm-server-resource";

    //Available values:   read, read write
    public static final String EMM_CLIENT_SCOPE = "read";


    /*
    * Generate client_secret
    * */
    private static RandomValueStringGenerator randomValueStringGenerator = new RandomValueStringGenerator(42);


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
        return randomValueStringGenerator.generate();
    }


    /**
     * Generate a new clientSecret
     *
     * @return clientSecret
     */
    public static String generateClientSecret() {
        return randomValueStringGenerator.generate();
    }


}
