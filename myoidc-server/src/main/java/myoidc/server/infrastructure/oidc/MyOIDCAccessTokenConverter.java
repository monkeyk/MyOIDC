package myoidc.server.infrastructure.oidc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

/**
 * 2020/3/11
 * <p>
 * Ext. default
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class MyOIDCAccessTokenConverter extends DefaultAccessTokenConverter {


    private static final Logger LOG = LoggerFactory.getLogger(MyOIDCAccessTokenConverter.class);


    public MyOIDCAccessTokenConverter() {
    }

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        return super.convertAccessToken(token, authentication);
    }
}
