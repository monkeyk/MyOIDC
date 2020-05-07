package myoidc.server.domain.user;


import myoidc.server.domain.shared.Repository;

import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface UserRepository extends Repository {


    User findLoginUserByUsername(String username);

    List<Privilege> findUserPrivileges(String userUuid);

    List<User> findUsersByUsername(String username);

    /**
     * 无 archived 条件
     *
     * @param username username
     * @return User
     * @since 1.1.1
     */
    User findUserByUsernameNoArchived(String username);
}