package myoidc.server.infrastructure.oidc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Collection;

import static myoidc.server.infrastructure.oidc.OIDCUtils.isJWT;

/**
 * 2020/3/22
 * <p>
 * 策略模式实现
 * 根据 token 不同类型采取不同的逻辑
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class MyOIDCTokenStore implements TokenStore {

    private static final Logger LOG = LoggerFactory.getLogger(MyOIDCTokenStore.class);


    private JdbcTokenStore jdbcTokenStore;


    private JwtTokenStore jwtTokenStore;


    /**
     * 判断是否将生成的 access_token, refresh_token 都用 JWT
     * 默认 false
     */
    private boolean tokenUseJwt = false;

    public MyOIDCTokenStore() {
    }


    public void setJdbcTokenStore(JdbcTokenStore jdbcTokenStore) {
        this.jdbcTokenStore = jdbcTokenStore;
    }

    public void setJwtTokenStore(JwtTokenStore jwtTokenStore) {
        this.jwtTokenStore = jwtTokenStore;
    }

    public void setTokenUseJwt(boolean tokenUseJwt) {
        this.tokenUseJwt = tokenUseJwt;
    }


    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        if (this.tokenUseJwt) {
            return this.jwtTokenStore.readAuthentication(token);
        }
        if (isJWT(token.getValue())) {
            return this.jdbcTokenStore.readAuthentication(token);
        } else {
            //从数据库读取
            return this.jdbcTokenStore.readAuthentication(token);
        }
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        if (this.tokenUseJwt) {
            return this.jwtTokenStore.readAuthentication(token);
        }
        if (isJWT(token)) {
            return this.jdbcTokenStore.readAuthentication(token);
        } else {
            //从数据库读取
            return this.jdbcTokenStore.readAuthentication(token);
        }
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        if (this.tokenUseJwt) {
            this.jwtTokenStore.storeAccessToken(token, authentication);
            return;
        }
        //存数据库
        this.jdbcTokenStore.storeAccessToken(token, authentication);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        if (this.tokenUseJwt) {
            return this.jwtTokenStore.readAccessToken(tokenValue);
        }
        //判断tokenValue 是JWT?
        if (isJWT(tokenValue)) {
            return this.jwtTokenStore.readAccessToken(tokenValue);
        } else {
            return this.jdbcTokenStore.readAccessToken(tokenValue);
        }
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        if (this.tokenUseJwt) {
            this.jwtTokenStore.removeAccessToken(token);
            return;
        }
        //判断tokenValue 是JWT?
        if (isJWT(token.getValue())) {
            this.jwtTokenStore.removeAccessToken(token);
        } else {
            this.jdbcTokenStore.removeAccessToken(token);
        }
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        if (this.tokenUseJwt) {
            this.jwtTokenStore.storeRefreshToken(refreshToken, authentication);
            return;
        }
        //判断tokenValue 是JWT?
        if (isJWT(refreshToken.getValue())) {
            this.jwtTokenStore.storeRefreshToken(refreshToken, authentication);
        } else {
            this.jdbcTokenStore.storeRefreshToken(refreshToken, authentication);
        }
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        if (this.tokenUseJwt) {
            return this.jwtTokenStore.readRefreshToken(tokenValue);
        }
        //先判断 是JWT?
        if (isJWT(tokenValue)) {
            return this.jwtTokenStore.readRefreshToken(tokenValue);
        } else {
            return this.jdbcTokenStore.readRefreshToken(tokenValue);
        }
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        if (this.tokenUseJwt) {
            return this.jwtTokenStore.readAuthenticationForRefreshToken(token);
        }
        //先判断 是JWT?
        if (isJWT(token.getValue())) {
            return this.jwtTokenStore.readAuthenticationForRefreshToken(token);
        } else {
            return this.jdbcTokenStore.readAuthenticationForRefreshToken(token);
        }
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        if (this.tokenUseJwt) {
            this.jwtTokenStore.removeRefreshToken(token);
            return;
        }
        //先判断 是JWT?
        if (isJWT(token.getValue())) {
            this.jwtTokenStore.removeRefreshToken(token);
        } else {
            this.jdbcTokenStore.removeRefreshToken(token);
        }
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        if (this.tokenUseJwt) {
            this.jwtTokenStore.removeAccessTokenUsingRefreshToken(refreshToken);
            return;
        }
        //先判断 是JWT?
        if (isJWT(refreshToken.getValue())) {
            this.jwtTokenStore.removeAccessTokenUsingRefreshToken(refreshToken);
        } else {
            this.jdbcTokenStore.removeAccessTokenUsingRefreshToken(refreshToken);
        }
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        if (this.tokenUseJwt) {
            return this.jwtTokenStore.getAccessToken(authentication);
        }
        //只有数据库场景
        return this.jdbcTokenStore.getAccessToken(authentication);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        if (this.tokenUseJwt) {
            return this.jwtTokenStore.findTokensByClientIdAndUserName(clientId, userName);
        }
        //只有数据库场景
        return this.jdbcTokenStore.findTokensByClientIdAndUserName(clientId, userName);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        if (this.tokenUseJwt) {
            return this.jwtTokenStore.findTokensByClientId(clientId);
        }
        //只有数据库场景
        return this.jdbcTokenStore.findTokensByClientId(clientId);
    }
}
