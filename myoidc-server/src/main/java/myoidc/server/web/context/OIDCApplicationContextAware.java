package myoidc.server.web.context;


import myoidc.server.domain.shared.Application;
import myoidc.server.domain.shared.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
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


    public OIDCApplicationContextAware() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanProvider.initialize(applicationContext);

        LOG.info("Initialed BeanProvider from ApplicationContext: {}", applicationContext);
        LOG.info("[myoidc-server] context initialized, Version: {}\n", Application.VERSION);
    }
}
