package myoidc.server.service;


import myoidc.server.domain.oauth.OauthClientDetails;
import myoidc.server.service.dto.OauthClientDetailsDto;

import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface OauthService {

    OauthClientDetails loadOauthClientDetails(String clientId);

    void archiveOauthClientDetails(String clientId);

    List<OauthClientDetailsDto> loadOauthClientDetailsDtos(String clientId);

    String saveOAuthClientDetails(OauthClientDetailsDto formDto);

    OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

}