package myoidc.client.web.context;

import myoidc.client.domain.shared.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * 2020/5/7
 *
 * @author Shengzhao Li
 * @since 1.1.1
 */
@Component
public class OIDCServletContextAware implements ServletContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(OIDCServletContextAware.class);

    public OIDCServletContextAware() {
    }

    @Override
    public void setServletContext(ServletContext servletContext) {

        //主版本号
        servletContext.setAttribute("mainVersion", Application.VERSION);
        LOG.debug("Initialed: {}, mainVersion: {}", this, Application.VERSION);
    }
}
