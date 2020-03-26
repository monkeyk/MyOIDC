package myoidc.server.service;

import myoidc.server.service.dto.UserFormDto;
import myoidc.server.service.dto.UserListDto;

/**
 * 2020/3/18
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public interface UserService {


    UserListDto loadUserListDto(UserListDto listDto);

    boolean isExistedUsername(String username);

    String saveUserForm(UserFormDto formDto);

    boolean archiveUserByUuid(String uuid);
}
