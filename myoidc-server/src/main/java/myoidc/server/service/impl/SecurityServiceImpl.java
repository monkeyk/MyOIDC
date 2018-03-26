package myoidc.server.service.impl;

import myoidc.server.domain.security.OIDCUserDetails;
import myoidc.server.domain.user.User;
import myoidc.server.domain.user.UserRepository;
import myoidc.server.service.SecurityService;
import myoidc.server.service.dto.UserJsonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 2018/2/5
 *
 * @author Shengzhao Li
 */
@Service
public class SecurityServiceImpl implements SecurityService {


    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findLoginUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found by username: " + username);
        }
        return new OIDCUserDetails(user);
    }


    /**
     * 获取当前登录用户 的信息  , JSON
     */
    @Override
    public UserJsonDto loadCurrentUserJsonDto() {
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
        UserJsonDto userJsonDto = new UserJsonDto();
        userJsonDto.setUsername(oAuth2Authentication.getName());

        final Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            userJsonDto.getPrivileges().add(authority.getAuthority());
        }

        return userJsonDto;
    }


}
