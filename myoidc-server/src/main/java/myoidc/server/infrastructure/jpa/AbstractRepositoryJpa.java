package myoidc.server.infrastructure.jpa;


import com.google.common.collect.ImmutableMap;
import myoidc.server.domain.AbstractDomain;
import myoidc.server.domain.shared.Repository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public abstract class AbstractRepositoryJpa<T> implements Repository, InitializingBean {


    @Autowired
    private EntityManagerFactory entityManagerFactory;


    protected EntityManager entityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(entityManagerFactory, "EntityManagerFactory is required!");
    }


    @SuppressWarnings("unchecked")
    protected <T extends AbstractDomain> List<T> find(final String queryString, final ImmutableMap<String, ?> paramsMap) throws DataAccessException {
        final javax.persistence.Query query = entityManager().createQuery(queryString);
        if (paramsMap != null) {
            for (String key : paramsMap.keySet()) {
                query.setParameter(key, paramsMap.get(key));
            }
        }
        return query.getResultList();
    }


    protected <T extends AbstractDomain> List<T> find(final String queryString) throws DataAccessException {
        return find(queryString, null);
    }

    @Override
    public <T extends AbstractDomain> T findById(Integer id, Class<T> clazz) {
        List<T> list = find("from " + clazz.getSimpleName() + " do where do.id = :id", ImmutableMap.of("id", id));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public <T extends AbstractDomain> T findByUuid(Class<T> clazz, String uuid) {
        List<T> list = find("from " + clazz.getSimpleName() + " do where do.uuid = :uuid", ImmutableMap.of("uuid", uuid));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public <T extends AbstractDomain> void saveOrUpdate(T domain) {
        this.entityManager().persist(domain);
    }

    @Override
    public <T extends AbstractDomain> void saveOrUpdateAll(Collection<T> collection) {
        for (T t : collection) {
            saveOrUpdate(t);
        }
    }

    @Override
    public <T extends AbstractDomain> void delete(T domain) {
        this.entityManager().remove(domain);
    }

    @Override
    public <T extends AbstractDomain> void deleteByUuid(Class<T> clazz, String uuid) {
        final T t = findByUuid(clazz, uuid);
        delete(t);
    }

    @Override
    public <T extends AbstractDomain> void deleteAll(Collection<T> elements) {
        for (T element : elements) {
            delete(element);
        }
    }

    @Override
    public <T extends AbstractDomain> List<T> findAll(Class<T> clazz, boolean active) {
        return find("from " + clazz.getName() + " c where c.archived = :archived", ImmutableMap.of("archived", !active));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends AbstractDomain> List<T> findByUuids(Class<T> clazz, List<String> uuids) {
        if (uuids == null || uuids.isEmpty()) {
            return Collections.emptyList();
        }
        final javax.persistence.Query query = this.entityManager().createQuery("from " + clazz.getName() + " c where c.uuid in (:uuids)");
//        query.setParameterList("uuids", uuids);
        query.setParameter("uuids", uuids);
        return query.getResultList();
    }
}