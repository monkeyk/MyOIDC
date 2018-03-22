package myoidc.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * 2018/2/8
 *
 * @author Shengzhao Li
 */

@Configuration
@EnableAuthorizationServer
public class OIDCConfiguration extends AuthorizationServerConfigurerAdapter {


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {



    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

//        clients.withClientDetails()

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {



    }


}
