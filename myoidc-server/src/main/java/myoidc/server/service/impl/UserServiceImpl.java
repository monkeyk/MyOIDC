package myoidc.server.service.impl;

import myoidc.server.domain.user.User;
import myoidc.server.domain.user.UserRepository;
import myoidc.server.service.UserService;
import myoidc.server.service.business.UserFormSaver;
import myoidc.server.service.dto.UserDto;
import myoidc.server.service.dto.UserFormDto;
import myoidc.server.service.dto.UserListDto;
import myoidc.server.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 2020/3/18
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public UserListDto loadUserListDto(UserListDto listDto) {
        //暂不支持分页
        List<User> users = userRepository.findUsersByUsername(listDto.getUsername());
        listDto.setUserDtos(UserDto.toDtos(users));
        return listDto;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistedUsername(String username) {
        final User user = userRepository.findUserByUsernameNoArchived(username);
        return user != null;
    }

    @Override
    @Transactional()
    public String saveUserForm(UserFormDto formDto) {
        UserFormSaver saver = new UserFormSaver(formDto);
        return saver.save();
    }

    @Override
    @Transactional()
    public boolean archiveUserByUuid(String uuid) {
        User user = userRepository.findByUuid(User.class, uuid);
        if (user == null || user.defaultUser()) {
            LOG.debug("{}|Null or defaultUser: {}", WebUtils.getIp(), user);
            return false;
        }
        user.archiveMe();
        return true;
    }
}
