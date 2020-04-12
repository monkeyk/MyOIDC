package myoidc.client.service.business;


import myoidc.client.domain.shared.BeanProvider;
import myoidc.client.service.dto.AccessTokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Handle 'password'  type actions
 * <p/>
 * http://localhost:8080/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=password&scope=read,write&username=mobile&password=mobile
 * <p/>
 * How to use?
 * Please see unit-test <code>PasswordOAuth2HandlerTest</code>
 * From spring-oauth-client(https://gitee.com/mkk/spring-oauth-client)
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class PasswordOAuth2Handler {

    private static final Logger LOG = LoggerFactory.getLogger(PasswordOAuth2Handler.class);

    private transient RestTemplate restTemplate = BeanProvider.getBean(RestTemplate.class);

    public PasswordOAuth2Handler() {
    }

    /**
     * Step 1:
     * Get access token from Oauth server
     * <p/>
     * Response Data:
     * {"access_token":"9d0a2372-d039-4c1a-b0f9-762be10d531c","token_type":"bearer","refresh_token":"cfe0866d-d712-4ec2-9f23-ddb61443c9db","expires_in":38818,"scope":"read write"}
     * <p/>
     * Error Data:
     * <oauth>
     * <error_description>Bad client credentials</error_description>
     * <error>invalid_client</error>
     * </oauth>
     * <p/>
     * or
     * {"error":"invalid_grant","error_description":"Bad credentials"}
     *
     * @return AccessTokenDto
     */
    public AccessTokenDto getAccessToken(PasswordParams params) {
        final String fullUri = params.getFullUri();
        LOG.debug("Get 'access_token' uri: {}", fullUri);

        try {
            return restTemplate.postForObject(fullUri, null, AccessTokenDto.class);
        } catch (RestClientException e) {
            LOG.error("Send request to: {} error", fullUri, e);
            return new AccessTokenDto("request_error", e.getMessage());
        }
    }


}