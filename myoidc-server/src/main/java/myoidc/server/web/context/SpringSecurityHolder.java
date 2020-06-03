package myoidc.server.web.context;


import myoidc.server.domain.security.OIDCUserDetails;
import myoidc.server.domain.security.SecurityHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Shengzhao Li
 */
public class SpringSecurityHolder implements SecurityHolder {

    private static final Logger LOG = LoggerFactory.getLogger(SpringSecurityHolder.class);


    public SpringSecurityHolder() {
    }

    @Override
    public OIDCUserDetails userDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            LOG.warn("Null Authentication from SecurityContextHolder");
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof OIDCUserDetails) {
            return (OIDCUserDetails) principal;
        }
        LOG.warn("Unknown principal: {}, return null", principal);
        return null;
    }
}