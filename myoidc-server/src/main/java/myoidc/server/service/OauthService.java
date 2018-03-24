package myoidc.server.service;



import myoidc.server.domain.oauth.OauthClientDetails;

/**
 * @author Shengzhao Li
 */

public interface OauthService {

    OauthClientDetails loadOauthClientDetails(String clientId);

//    List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos();

    void archiveOauthClientDetails(String clientId);

//    OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

//    void registerClientDetails(OauthClientDetailsDto formDto);
}