package myoidc.server.configuration;


import myoidc.server.domain.shared.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 2018/1/30
 * <p>
 *
 * @author Shengzhao Li
 */
@Configuration
@EnableTransactionManagement
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
