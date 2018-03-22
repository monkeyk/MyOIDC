package myoidc.server.domain.user;


import myoidc.server.domain.shared.Repository;

import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface UserRepository extends Repository {


    User findLoginUserByUsername(String username);

    List<Privilege> findUserPrivileges(User user);
}