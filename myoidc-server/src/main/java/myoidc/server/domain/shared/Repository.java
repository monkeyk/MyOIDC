package myoidc.server.domain.shared;


import myoidc.server.domain.AbstractDomain;

import java.util.Collection;
import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface Repository {

    <T extends AbstractDomain> T findById(Integer id, Class<T> clazz);

    <T extends AbstractDomain> T findByUuid(Class<T> clazz, String uuid);

    <T extends AbstractDomain> void saveOrUpdate(T domain);

    <T extends AbstractDomain> void saveOrUpdateAll(Collection<T> collection);

    <T extends AbstractDomain> void delete(T domain);

    <T extends AbstractDomain> void deleteByUuid(Class<T> clazz, String uuid);

    <T extends AbstractDomain> void deleteAll(Collection<T> elements);

    <T extends AbstractDomain> List<T> findAll(Class<T> clazz, boolean active);

    <T extends AbstractDomain> List<T> findByUuids(Class<T> clazz, List<String> uuids);

}