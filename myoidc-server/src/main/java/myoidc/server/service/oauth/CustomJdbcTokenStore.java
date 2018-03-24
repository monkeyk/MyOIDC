package myoidc.server.service.oauth;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;



/**
 * 2016/7/26
 * <p/>
 * 扩展默认的 TokenStore, 增加对缓存的支持
 *
 *
 * from <a href="https://gitee.com/shengzhao/spring-oauth-server">spring-oauth-server</a>
 *
 * @author Shengzhao Li
 */
public class CustomJdbcTokenStore extends JdbcTokenStore {


    public CustomJdbcTokenStore(DataSource dataSource) {
        super(dataSource);
    }


//    @Cacheable(value = ACCESS_TOKEN_CACHE, key = "#tokenValue")
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return super.readAccessToken(tokenValue);
    }

//    @CacheEvict(value = ACCESS_TOKEN_CACHE, key = "#token.value")
    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        super.removeAccessToken(token);
    }

//    @Cacheable(value = REFRESH_TOKEN_CACHE, key = "#token")
    public OAuth2RefreshToken readRefreshToken(String token) {
        return super.readRefreshToken(token);
    }

//    @CacheEvict(value = REFRESH_TOKEN_CACHE, key = "#token.value")
    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        super.removeRefreshToken(token);
    }

}
