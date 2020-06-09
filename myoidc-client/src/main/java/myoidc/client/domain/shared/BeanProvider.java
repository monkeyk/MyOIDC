package myoidc.client.domain.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

/**
 * Get Bean form ApplicationContext
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public abstract class BeanProvider {

    private static final Logger LOG = LoggerFactory.getLogger(BeanProvider.class);

    /**
     * @since 1.1.2
     */
    protected static BeanFactory beanFactory;

    protected BeanProvider() {
    }

    /**
     * Initial from  beanFactory
     *
     * @param beanFactory beanFactory
     */
    public static void initialize(BeanFactory beanFactory) {
        BeanProvider.beanFactory = beanFactory;
    }

    /**
     * Get Bean by clazz.
     *
     * @param clazz Class
     * @param <T>   class type
     * @return Bean instance
     */
    public static <T> T getBean(Class<T> clazz) {
        if (beanFactory == null) {
            LOG.warn("Null beanFactory, clazz: {}", clazz);
            return null;
        }
        return beanFactory.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        if (beanFactory == null) {
            LOG.warn("Null beanFactory, beanId: {}", beanId);
            return null;
        }
        return (T) beanFactory.getBean(beanId);
    }

}