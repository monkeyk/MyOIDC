package myoidc.server.service.business;

import myoidc.server.domain.security.SecurityUtils;
import myoidc.server.domain.shared.BeanProvider;
import myoidc.server.domain.user.Privilege;
import myoidc.server.domain.user.User;
import myoidc.server.domain.user.UserPrivilege;
import myoidc.server.domain.user.UserRepository;
import myoidc.server.service.dto.UserFormDto;
import myoidc.server.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 2020/3/19
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class UserFormSaver {

    private static final Logger LOG = LoggerFactory.getLogger(UserFormSaver.class);


    private UserRepository userRepository = BeanProvider.getBean(UserRepository.class);

//    private PasswordEncoder passwordEncoder = BeanProvider.getBean(PasswordEncoder.class);


    private UserFormDto formDto;

    public UserFormSaver(UserFormDto formDto) {
        this.formDto = formDto;
    }

    public String save() {

        User user = formDto.newUser();
        user.creator(SecurityUtils.currentUser());
//        user.password(passwordEncoder.encode(formDto.getPassword()));
        userRepository.saveOrUpdate(user);

        List<Privilege> privileges = formDto.getPrivileges();
        for (Privilege privilege : privileges) {
            UserPrivilege userPrivilege = new UserPrivilege(user, privilege);
            userRepository.saveOrUpdate(userPrivilege);
        }

        LOG.debug("{}|Save User: {}", WebUtils.getIp(), user);
        return user.uuid();
    }
}
