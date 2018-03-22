package myoidc.server.domain.user;


import myoidc.server.domain.shared.Repository;

/**
 * @author Shengzhao Li
 */

public interface UserRepository extends Repository {


    User findLoginUserByUsername(String username);

}