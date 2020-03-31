package myoidc.server.config;


import myoidc.server.domain.shared.Application;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


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



    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("myoidc.server.domain");

        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("jdbc.use_scrollable_resultset", "false");
        properties.setProperty("hibernate.query.substitutions", "true=1,false=0");
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL57Dialect");
        properties.setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
        factoryBean.setHibernateProperties(properties);

        return factoryBean;
    }


    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

}
