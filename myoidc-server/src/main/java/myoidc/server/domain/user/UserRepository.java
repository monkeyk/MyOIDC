package myoidc.server.domain.user;


import myoidc.server.domain.shared.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Shengzhao Li
 */

public interface UserRepository extends Repository {


    User findByUsername(String username);

    List<Privilege> findUserPrivileges(User user);

    List<User> findListUsers(Map<String, Object> map);

    int totalListUsers(Map<String, Object> map);

    User findByUsernameIgnoreArchived(String username);

    int deleteUserPrivileges(User user);


}