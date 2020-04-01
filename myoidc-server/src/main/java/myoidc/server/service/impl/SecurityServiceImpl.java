package myoidc.server.service.impl;

import myoidc.server.domain.security.OIDCUserDetails;
import myoidc.server.domain.user.User;
import myoidc.server.domain.user.UserRepository;
import myoidc.server.service.SecurityService;
import myoidc.server.service.business.ClientRegistrationFormSaver;
import myoidc.server.service.dto.ClientRegistrationFormDto;
import myoidc.server.service.dto.OauthClientDetailsDto;
import myoidc.server.service.dto.UserJsonDto;
import myoidc.server.service.oauth.CurrentUserJsonDtoLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 2018/2/5
 *
 * @author Shengzhao Li
 */
@Service()
public class SecurityServiceImpl implements SecurityService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.debug("Try load User by username: {}", username);
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
    @Transactional(readOnly = true)
    public UserJsonDto loadCurrentUserJsonDto() {
        CurrentUserJsonDtoLoader loader = new CurrentUserJsonDtoLoader();
        return loader.load();
    }

    @Override
    @Transactional()
    public OauthClientDetailsDto saveClientRegistrationForm(ClientRegistrationFormDto formDto) {
        ClientRegistrationFormSaver formSaver = new ClientRegistrationFormSaver(formDto);
        return formSaver.save();
    }


}
