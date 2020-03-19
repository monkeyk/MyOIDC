package myoidc.server.service;



import myoidc.server.domain.oauth.OauthClientDetails;
import myoidc.server.service.dto.OauthClientDetailsDto;

import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface OauthService {

    OauthClientDetails loadOauthClientDetails(String clientId);

//    List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos();

    void archiveOauthClientDetails(String clientId);

    List<OauthClientDetailsDto> loadOauthClientDetailsDtos(String clientId);

//    OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

//    void registerClientDetails(OauthClientDetailsDto formDto);
}