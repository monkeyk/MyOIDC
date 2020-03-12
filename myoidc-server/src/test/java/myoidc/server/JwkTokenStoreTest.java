package myoidc.server;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * 2020/3/13
 *
 * @author Shengzhao Li
 */
public class JwkTokenStoreTest {


    @Test
    @Ignore
    public void test() throws Exception {

        String jwkSetUrl = "http://localhost:8080/myoidc-server/public/jwks";
        JwkTokenStore tokenStore = new JwkTokenStore(jwkSetUrl);

        assertNotNull(tokenStore);

        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImF1ZCI6WyJteW9pZGMtcmVzb3VyY2UiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL215b2lkYy1zZXJ2ZXIvIiwiZXhwIjoxNTg0MDczNDc5LCJpYXQiOjE1ODQwMzAyNzksImF1dGhvcml0aWVzIjpbIlJPTEVfTU9CSUxFIiwiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiIsIlJPTEVfVU5JVFkiXSwianRpIjoiZmU3MGY3NjktZTIxYi00NzczLWJlN2EtYTQwMGE0MzY2YWYxIiwiY2xpZW50X2lkIjoibW9iaWxlLWNsaWVudCJ9.L7nETF-NDQcJV0YiZVbT25mSF-LPWq7oLoCPR1N5glCnB_wZO3i1wfIbVqo0HAtcTHwiLeRkyPBCDn2BTEdotQqyTxazAloV2dCej_9b2a341mAzqpsVHQygkYdMVNW5Gfo4QaBMab_PCNAeO5w0BRHhlCOVi-WMmvzoaZrPyFaLS0sURMqpCTjvrt7e_XkkCOoY-0WcRbXmWGmgB9Kil0NVA4j67pUVFMMP2sgzoCNmmYXK70vVLTzSRjodbEQqsyS9Q6sU7E28hT8Vu21GVyHIL0S0p1tK5JKXPerb-UGWBXSV90LEynTe0hU5IJxW-urN6YaoxcZfy6eGm8jI9w";
//        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
//        assertNotNull(oAuth2AccessToken);

        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
        assertNotNull(oAuth2Authentication);
    }
}
