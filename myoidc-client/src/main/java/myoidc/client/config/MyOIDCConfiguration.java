package myoidc.client.config;


import myoidc.client.domain.shared.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 2018/1/30
 * <p>
 *
 * @author Shengzhao Li
 */
@Configuration
public class MyOIDCConfiguration {


    @Value("${application.host}")
    private String host;


    // Application
    @Bean
    public Application application() {
        Application application = new Application();
        application.setHost(host);
        return application;
    }


}
