package myoidc.server.service.oauth;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;

import javax.sql.DataSource;


/**
 * 2016/7/23
 * <p>
 * from <a href="https://gitee.com/shengzhao/spring-oauth-server">spring-oauth-server</a>
 *
 * @author Shengzhao Li
 */
public class SOSAuthorizationCodeServices extends JdbcAuthorizationCodeServices {


    // 扩展长度 18
    private RandomValueStringGenerator generator = new RandomValueStringGenerator(18);


    public SOSAuthorizationCodeServices(DataSource dataSource) {
        super(dataSource);
    }


    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = generator.generate();
        store(code, authentication);
        return code;
    }

    @Override
//    @Cacheable(value = AUTHORIZATION_CODE_CACHE, key = "#code")
    protected void store(String code, OAuth2Authentication authentication) {
        super.store(code, authentication);
    }

    @Override
//    @CacheEvict(value = AUTHORIZATION_CODE_CACHE, key = "#code")
    public OAuth2Authentication remove(String code) {
        return super.remove(code);
    }
}
