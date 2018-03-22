package myoidc.server.infrastructure.jpa;

import com.google.common.collect.ImmutableMap;
import myoidc.server.domain.user.Privilege;
import myoidc.server.domain.user.User;
import myoidc.server.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
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

    @Override
    public List<Privilege> findUserPrivileges(String userUuid) {
        final String hql = " select up.privilege from UserPrivilege up where up.archived = false and up.user.uuid = :userUuid";
        final Query query = entityManager().createQuery(hql).setParameter("userUuid", userUuid);
        return query.getResultList();
    }

}
