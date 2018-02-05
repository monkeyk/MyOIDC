package myoidc.server.web.context;


import myoidc.server.domain.security.OIDCUserDetails;
import myoidc.server.domain.security.SecurityHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Shengzhao Li
 */
public class SpringSecurityHolder implements SecurityHolder {

    @Override
    public OIDCUserDetails userDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof OIDCUserDetails) {
            return (OIDCUserDetails) principal;
        }
        return null;
    }
}