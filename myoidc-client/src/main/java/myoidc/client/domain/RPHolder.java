package myoidc.client.domain;

import myoidc.client.domain.shared.BeanProvider;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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

    private static final Logger LOG = LoggerFactory.getLogger(RPHolder.class);


    /**
     * Fixed,  resource-id, from myoidc-server Constants.java
     */
    public static final String RESOURCE_ID = "myoidc-resource";


    private static final String CLIENT_ID_KEY = "clientId";
    private static final String CLIENT_SECRET_KEY = "clientSecret";
    private static final String DISCOVERY_ENDPOINT_KEY = "discoveryEndpoint";


    @NotBlank(message = "clientId不能为空")
    private String clientId;

    @NotBlank(message = "clientSecret不能为空")
    private String clientSecret;

    @NotBlank(message = "discoveryEndpoint不能为空")
    private String discoveryEndpoint;


    /**
     * 临时变量，缓存
     */
    private DiscoveryEndpointInfo endpointInfo;


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

    /**
     * 发送请求获取 信息
     *
     * @return DiscoveryEndpointInfo or null(error)
     */
    public DiscoveryEndpointInfo getDiscoveryEndpointInfo() {
        if (this.endpointInfo == null) {
            RestTemplate restTemplate = BeanProvider.getBean(RestTemplate.class);
            try {
                this.endpointInfo = restTemplate.getForObject(this.discoveryEndpoint, DiscoveryEndpointInfo.class);
            } catch (RestClientException e) {
                LOG.error("Send request to: {} error: {}", this.discoveryEndpoint, e);
            }
        }
        return this.endpointInfo;
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
