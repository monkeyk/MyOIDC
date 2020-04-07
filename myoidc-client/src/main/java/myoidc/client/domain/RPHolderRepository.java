package myoidc.client.domain;

/**
 * 2020/4/7
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public interface RPHolderRepository {

    RPHolder findRPHolder();

    boolean saveRPHolder(RPHolder rpHolder);

}
