package myoidc.client.service.business;

import myoidc.client.MyOIDCClientApplicationTest;
import myoidc.client.domain.RPHolder;
import myoidc.client.service.dto.AccessTokenDto;
import myoidc.client.service.dto.UserInfoDto;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * 2020/4/12
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class PasswordOAuth2HandlerTest extends MyOIDCClientApplicationTest {


    @Autowired
    private RestTemplate restTemplate;

    private PasswordOAuth2Handler passwordOauthHandler;


    /**
     * 填写从 myoidc-server 获取的 clientId, clientSecret
     * <p>
     * grant_type必须有 password
     */
    private String clientId = "Jc0S89O9N7Q8KZtiZ6U2IFisVGWw5GTZ";
    private String clientSecret = "8OUqjvT38UX8TqpG2fsmRcdbjrPGSXbJEXLsyLdA";

    /**
     * 填写从 myoidc-server 的 账号与密码
     */
    private String username = "mobile";
    private String password = "MyOIDC-2017";


    @Before
    public void before() {
        this.passwordOauthHandler = new PasswordOAuth2Handler();
    }

    /**
     * Test variables from  'spring-oauth-client.properties'
     *
     * @throws Exception e
     */
    @Test
    @Ignore
    public void getAccessToken() throws Exception {

        RPHolder rpHolder = new RPHolder();
        rpHolder.setClientId(this.clientId);
        rpHolder.setClientSecret(clientSecret);
//        rpHolder.getDiscoveryEndpointInfo();
        final String accessTokenUri = "http://localhost:8080/myoidc-server/oauth/token";
//        final String accessTokenUri = rpHolder.getDiscoveryEndpointInfo().getToken_endpoint();

        /*
        *  Test case 1:  All variable are right
        * */
        PasswordParams params = new PasswordParams(accessTokenUri)
                .setClientId(rpHolder.getClientId())
                .setClientSecret(rpHolder.getClientSecret())
                .setUsername(username)
                .setPassword(password);

        final AccessTokenDto accessToken = passwordOauthHandler.getAccessToken(params);
        assertNotNull(accessToken);
        assertNull(accessToken.getError());
        assertNotNull(accessToken.getAccessToken());

        assertTrue(accessToken.getExpiresIn() > 0);
        assertFalse(accessToken.error());
        System.out.println("Test Case 1: " + accessToken);

        /*
        *  Test case 2:  'client_id' is wrong
        * */

        PasswordParams params2 = new PasswordParams(accessTokenUri)
                .setClientId("mobile-client-wrong")
                .setClientSecret(rpHolder.getClientSecret())
                .setUsername(username)
                .setPassword(password);

        final AccessTokenDto accessToken2 = passwordOauthHandler.getAccessToken(params2);
        assertNotNull(accessToken2);
        assertTrue(accessToken2.error());
        System.out.println("Test Case 2: " + accessToken2);

        /*
        *  Test case 3:  'client_secret' is wrong
        * */

        PasswordParams params3 = new PasswordParams(accessTokenUri)
                .setClientId(rpHolder.getClientId())
                .setClientSecret("mobile-wrong")
                .setUsername(username)
                .setPassword(password);

        final AccessTokenDto accessToken3 = passwordOauthHandler.getAccessToken(params3);
        assertNotNull(accessToken3);
        assertTrue(accessToken3.error());
        System.out.println("Test Case 3: " + accessToken3);

        /*
        *  Test case 4:  'username' is wrong
        * */

        PasswordParams params4 = new PasswordParams(accessTokenUri)
                .setClientId(rpHolder.getClientId())
                .setClientSecret(rpHolder.getClientSecret())
                .setUsername("mobile-wrong")
                .setPassword(password);

        final AccessTokenDto accessToken4 = passwordOauthHandler.getAccessToken(params4);
        assertNotNull(accessToken4);
        assertTrue(accessToken4.error());
        System.out.println("Test Case 4: " + accessToken4);

        /*
        *  Test case 5:  'password' is wrong
        * */

        PasswordParams params5 = new PasswordParams(accessTokenUri)
                .setClientId(rpHolder.getClientId())
                .setClientSecret(rpHolder.getClientSecret())
                .setUsername(username)
                .setPassword("mobile-wrong");

        final AccessTokenDto accessToken5 = passwordOauthHandler.getAccessToken(params5);
        assertNotNull(accessToken5);
        assertTrue(accessToken5.error());
        System.out.println("Test Case 5: " + accessToken5);


    }


    @Test
    @Ignore
    public void getUserInfoDto() throws Exception {
        RPHolder rpHolder = new RPHolder();
        rpHolder.setClientId(this.clientId);
        rpHolder.setClientSecret(clientSecret);

        final String accessTokenUri = "http://localhost:8080/myoidc-server/oauth/token";
        final String userInfoUri = "http://localhost:8080/myoidc-server/api/userinfo";
//        final String accessTokenUri = rpHolder.getDiscoveryEndpointInfo().getToken_endpoint();


        PasswordParams params = new PasswordParams(accessTokenUri)
                .setClientId(rpHolder.getClientId())
                .setClientSecret(rpHolder.getClientSecret())
                .setUsername(this.username)
                .setPassword(this.password);

        final AccessTokenDto accessToken = passwordOauthHandler.getAccessToken(params);
        assertNotNull(accessToken);

        //user info url
//        this.clientService.loadRPHolder();
//        String mobileUserInfoUri = "http://localhost:8080/myoidc-server/api/userinfo";

        /*
        * Test case 1:  normally
        * */
        final UserInfoDto userDto = restTemplate.getForObject(userInfoUri + "?access_token=" + accessToken.getAccessToken(), UserInfoDto.class);
//        final UserInfoDto userDto = this.clientService.loadUserInfoDto(accessToken.getAccessToken());
        assertNotNull(userDto);
        assertFalse(userDto.error());

        assertNotNull(userDto.getUsername());
        assertNotNull(userDto.getOpenid());

        /*
       * Test case 2:  illegal access_token
       * */
        UserInfoDto userDto2;
        try {
            userDto2 = restTemplate.getForObject(userInfoUri + "?access_token=" + accessToken.getAccessToken() + "xxxx", UserInfoDto.class);
        } catch (RestClientException e) {
            userDto2 = new UserInfoDto("request_error", e.getMessage());
        }
        assertNotNull(userDto2);
        assertTrue(userDto2.error());
        System.out.println(userDto2);


    }


}