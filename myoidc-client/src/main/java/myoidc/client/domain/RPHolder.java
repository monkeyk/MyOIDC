package myoidc.client.domain;

import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.JwtClaims;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * 2020/4/7
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class RPHolder implements Serializable {
    private static final long serialVersionUID = 4335285706375582063L;


    private static final String CLIENT_ID_KEY = "clientId";
    private static final String CLIENT_SECRET_KEY = "clientSecret";
    private static final String DISCOVERY_ENDPOINT_KEY = "discoveryEndpoint";


    @NotBlank(message = "clientId不能为空")
    private String clientId;

    @NotBlank(message = "clientSecret不能为空")
    private String clientSecret;

    @NotBlank(message = "discoveryEndpoint不能为空")
    private String discoveryEndpoint;


    public RPHolder() {
    }

    public RPHolder(Map<String, Object> jsonMap) {
        this.clientId = (String) jsonMap.get(CLIENT_ID_KEY);
        this.clientSecret = (String) jsonMap.get(CLIENT_SECRET_KEY);
        this.discoveryEndpoint = (String) jsonMap.get(DISCOVERY_ENDPOINT_KEY);
    }


    public String toJSON() {
        JwtClaims claims = new JwtClaims();
        claims.setStringClaim(CLIENT_ID_KEY, clientId);
        claims.setStringClaim(CLIENT_SECRET_KEY, clientSecret);
        claims.setStringClaim(DISCOVERY_ENDPOINT_KEY, discoveryEndpoint);
        return claims.toJson();
    }

    /**
     * 判断是否进行了配置
     *
     * @return true config
     */
    public boolean isConfigRP() {
        return StringUtils.isNoneBlank(clientId)
                && StringUtils.isNotBlank(clientSecret)
                && StringUtils.isNotBlank(discoveryEndpoint);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getDiscoveryEndpoint() {
        return discoveryEndpoint;
    }

    public void setDiscoveryEndpoint(String discoveryEndpoint) {
        this.discoveryEndpoint = discoveryEndpoint;
    }

}
