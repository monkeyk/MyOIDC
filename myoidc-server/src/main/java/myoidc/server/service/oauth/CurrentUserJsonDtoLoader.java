package myoidc.server.service.oauth;

import myoidc.server.domain.security.OIDCUserDetails;
import myoidc.server.domain.shared.BeanProvider;
import myoidc.server.domain.user.User;
import myoidc.server.domain.user.UserRepository;
import myoidc.server.service.dto.UserJsonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Collection;

/**
 * 2020/3/11
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class CurrentUserJsonDtoLoader {

    private static final Logger LOG = LoggerFactory.getLogger(CurrentUserJsonDtoLoader.class);


    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);

    public CurrentUserJsonDtoLoader() {
    }

    public UserJsonDto load() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();

        if (authentication instanceof OAuth2Authentication &&
                (principal instanceof String || principal instanceof org.springframework.security.core.userdetails.User)) {
            return loadOauthUserJsonDto((OAuth2Authentication) authentication);
        } else {
            final OIDCUserDetails userDetails = (OIDCUserDetails) principal;
            return new UserJsonDto(userRepository.findByUuid(User.class, userDetails.user().uuid()));
        }
    }


    private UserJsonDto loadOauthUserJsonDto(OAuth2Authentication oAuth2Authentication) {
        String name = oAuth2Authentication.getName();
        User user = userRepository.findLoginUserByUsername(name);
        UserJsonDto userJsonDto;
        if (user != null) {
            LOG.debug("Load OAuth user from User: {}", user);
            userJsonDto = new UserJsonDto(user);
        } else {
            userJsonDto = new UserJsonDto();
            userJsonDto.setUsername(name);

            final Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                userJsonDto.getPrivileges().add(authority.getAuthority());
            }
        }
        return userJsonDto;
    }
}
