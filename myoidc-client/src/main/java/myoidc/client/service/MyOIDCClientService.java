package myoidc.client.service;


import myoidc.client.domain.RPHolder;

/**
 * 2020/4/7
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public interface MyOIDCClientService {

    RPHolder loadRPHolder();

    boolean saveRPHolder(RPHolder rpHolder);
}
