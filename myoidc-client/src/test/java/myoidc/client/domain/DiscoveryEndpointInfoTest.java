package myoidc.client.domain;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * 2020/4/7
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class DiscoveryEndpointInfoTest {


    @Test
    @Ignore
    public void test() {

        RestTemplate restTemplate = new RestTemplate();
        DiscoveryEndpointInfo info = restTemplate.getForObject("http://localhost:8080/myoidc-server/.well-known/openid-configuration", DiscoveryEndpointInfo.class);

        assertNotNull(info);

    }

}