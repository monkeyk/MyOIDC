package myoidc.server.infrastructure.jpa;

import com.google.common.collect.ImmutableMap;
import myoidc.server.domain.user.Privilege;
import myoidc.server.domain.user.User;
import myoidc.server.domain.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 2018/3/22
 *
 * @author Shengzhao Li
 */
@Repository()
public class UserRepositoryHibernate extends AbstractRepositoryHibernate<User> implements UserRepository {


    @Override
    public User findLoginUserByUsername(String username) {
        final String hql = "from User u where u.username = :username and u.archived = false";
        final List<User> list = find(hql, ImmutableMap.of("username", username));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Privilege> findUserPrivileges(String userUuid) {
        final String hql = " select up.privilege from UserPrivilege up where up.archived = false and up.user.uuid = :userUuid";
        final Query query = session().createQuery(hql).setParameter("userUuid", userUuid);
        return query.getResultList();
    }

    @Override
    public List<User> findUsersByUsername(String username) {
        if (StringUtils.isNoneBlank(username)) {
            final String hql = "from User u where u.username like :username and u.archived = false order by u.id desc ";
            // 右半 %,  使用索引
            return find(hql, ImmutableMap.of("username", username + "%"));
        } else {
            return find("from User u where u.archived = false order by u.id desc ");
        }
    }

    @Override
    public User findUserByUsernameNoArchived(String username) {
        final String hql = "from User u where u.username = :username ";
        final List<User> list = find(hql, ImmutableMap.of("username", username));
        return list.isEmpty() ? null : list.get(0);
    }

}
