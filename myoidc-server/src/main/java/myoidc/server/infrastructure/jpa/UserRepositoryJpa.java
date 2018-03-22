package myoidc.server.infrastructure.jpa;

import com.google.common.collect.ImmutableMap;
import myoidc.server.domain.user.User;
import myoidc.server.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 2018/3/22
 *
 * @author Shengzhao Li
 */
@Repository("userRepositoryJpa")
public class UserRepositoryJpa extends AbstractRepositoryJpa<User> implements UserRepository {


    @Override
    public User findLoginUserByUsername(String username) {
        final String hql = "from User u where u.username = :username and u.archived = false";
        final List<User> list = find(hql, ImmutableMap.of("username", username));
        return list.isEmpty() ? null : list.get(0);
    }
}
