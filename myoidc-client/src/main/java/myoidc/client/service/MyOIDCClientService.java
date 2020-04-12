package myoidc.client.service;


import myoidc.client.domain.RPHolder;
import myoidc.client.service.dto.AccessTokenDto;
import myoidc.client.service.dto.AuthAccessTokenDto;
import myoidc.client.service.dto.AuthCallbackDto;
import myoidc.client.service.dto.UserInfoDto;

import java.util.Map;

/**
 * 2020/4/7
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public interface MyOIDCClientService {

    RPHolder loadRPHolder();

    boolean saveRPHolder(RPHolder rpHolder);

    AuthAccessTokenDto createAuthAccessTokenDto(AuthCallbackDto callbackDto);

    AccessTokenDto retrieveAccessTokenDto(AuthAccessTokenDto tokenDto);

    UserInfoDto loadUserInfoDto(String access_token);

    Map<String,Object> verifyToken(String token);

    AccessTokenDto retrieveCredentialsAccessTokenDto(AuthAccessTokenDto tokenDto);
}
