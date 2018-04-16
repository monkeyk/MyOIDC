package myoidc.server.configuration;

import myoidc.server.service.oauth.CustomJdbcClientDetailsService;
import myoidc.server.service.oauth.CustomJdbcTokenStore;
import myoidc.server.service.oauth.SOSAuthorizationCodeServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * 2018/3/24
 * <p>
 * OAuth2 resource server
 *
 * @author Shengzhao Li
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("unity-resource").stateless(false)
                .resourceId("mobile-resource").stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // Since we want the protected resources to be accessible in the UI as well we need
                // session creation to be allowed (it's disabled by default in 2.0.6)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().antMatchers("/unity/**", "/m/**")
                .and()
                .authorizeRequests()
                .antMatchers("/unity/**").access("#oauth2.hasScope('read') and hasRole('ROLE_UNITY')")
                .antMatchers("/m/**").access("#oauth2.hasScope('read') and hasRole('ROLE_MOBILE')");

    }


    @Bean
    public TokenStore tokenStore(DataSource dataSource) {
        return new CustomJdbcTokenStore(dataSource);
    }


    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        return new CustomJdbcClientDetailsService(dataSource);
    }


    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new SOSAuthorizationCodeServices(dataSource);
    }


//    public ResourceServerTokenServices tokenServices()


}
