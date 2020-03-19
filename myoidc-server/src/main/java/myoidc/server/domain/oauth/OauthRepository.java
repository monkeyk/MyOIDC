package myoidc.server.domain.oauth;


import java.util.List;

/**
 * 处理 OAuth 相关业务的 Repository
 *
 * @author Shengzhao Li
 */
public interface OauthRepository {

    OauthClientDetails findOauthClientDetails(String clientId);

    List<OauthClientDetails> findAllOauthClientDetails(String clientId);

    int updateOauthClientDetailsArchive(String clientId, boolean archive);

    void saveOauthClientDetails(OauthClientDetails clientDetails);
}