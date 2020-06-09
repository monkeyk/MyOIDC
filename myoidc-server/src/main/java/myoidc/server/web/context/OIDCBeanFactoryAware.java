package myoidc.server.web.context;

import myoidc.server.domain.shared.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * 2020/6/9
 *
 * @author Shengzhao Li
 * @since 1.1.2
 */
@Component
public class OIDCBeanFactoryAware implements BeanFactoryAware {

    private static final Logger LOG = LoggerFactory.getLogger(OIDCBeanFactoryAware.class);


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BeanProvider.initialize(beanFactory);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Initialized BeanProvider from beanFactory: {}", beanFactory);
        }
    }
}
