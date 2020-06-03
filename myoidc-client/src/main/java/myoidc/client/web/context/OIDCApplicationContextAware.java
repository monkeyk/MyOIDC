package myoidc.client.web.context;


import myoidc.client.domain.shared.Application;
import myoidc.client.domain.shared.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 2018/03/22
 * <p>
 *
 * @author Shengzhao Li
 */
@Component
public class OIDCApplicationContextAware implements ApplicationContextAware {


    private static final Logger LOG = LoggerFactory.getLogger(OIDCApplicationContextAware.class);


    @Value("${spring.application.name}")
    private String applicationName;


    @Value("${application.host}")
    private String applicationHost;


    public OIDCApplicationContextAware() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanProvider.initialize(applicationContext);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Initialed BeanProvider from ApplicationContext: {}", applicationContext);
        }
        LOG.info("{} context initialized, Version: {}, applicationHost: {}\n", this.applicationName, Application.VERSION, this.applicationHost);
    }
}
