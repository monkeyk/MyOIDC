package myoidc.server.service.oauth;


import myoidc.server.domain.oauth.OauthClientDetails;
import myoidc.server.service.OauthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;

/**
 * 处理OAuth 中用户授权确认的逻辑
 * 这在grant_type 为 authorization_code, implicit 会使用到
 * <p>
 * from <a href="https://gitee.com/shengzhao/spring-oauth-server">spring-oauth-server</a>
 *
 * @author Shengzhao Li
 */
public class OauthUserApprovalHandler extends TokenStoreUserApprovalHandler {

    private OauthService oauthService;

    public OauthUserApprovalHandler() {
        // Do nothing
    }


    /**
     * 这儿扩展了默认的逻辑, 若 OauthClientDetails 中的 trusted 字段为true, 将会自动跳过 授权流程
     *
     * @param authorizationRequest AuthorizationRequest
     * @param userAuthentication   Authentication
     * @return True is approved
     */
    public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
        if (super.isApproved(authorizationRequest, userAuthentication)) {
            return true;
        }
        if (!userAuthentication.isAuthenticated()) {
            return false;
        }

        OauthClientDetails clientDetails = oauthService.loadOauthClientDetails(authorizationRequest.getClientId());
        return clientDetails != null && clientDetails.trusted();

    }

    public void setOauthService(OauthService oauthService) {
        this.oauthService = oauthService;
    }
}
