package myoidc.server.service.dto;


import myoidc.server.Constants;
import myoidc.server.domain.oauth.OauthClientDetails;
import myoidc.server.infrastructure.PasswordHandler;
import myoidc.server.infrastructure.oidc.OIDCUtils;
import myoidc.server.service.validation.ClientIdValidation;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static myoidc.server.infrastructure.oidc.OIDCUtils.ACCESS_TOKEN_VALIDITY;
import static myoidc.server.infrastructure.oidc.OIDCUtils.GrantType.*;
import static myoidc.server.infrastructure.oidc.OIDCUtils.REFRESH_TOKEN_VALIDITY;

/**
 * From spring-oauth-server
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class OauthClientDetailsDto implements Serializable {


    private static final long serialVersionUID = 4011292111995231569L;

    private String createTime;
    private boolean archived;

    @Length(min = 10, message = "client_id长度最少10位")
    @ClientIdValidation
    private String clientId = OIDCUtils.generateClientId();

    //固定值
    private String resourceIds = Constants.RESOURCE_ID;

    //加密存储
    @NotBlank(message = "client_secret不能为空")
    @Length(min = 10, message = "client_secret长度最少10位")
    private String clientSecret = OIDCUtils.generateClientSecret();

    //默认 openid
    @NotBlank(message = "scope不能为空")
    private String scope = OIDCUtils.SCOPE_OPENID;

    @NotBlank(message = "grant_type不能为空")
    private String authorizedGrantTypes;

    @NotBlank(message = "redirect_uri不能为空")
    @URL(message = "redirect_uri格式错误")
    private String webServerRedirectUri;


    private String authorities;

    @Positive(message = "access_token_validity必须大于0")
    private Integer accessTokenValidity;

    @Positive(message = "refresh_token_validity必须大于0")
    private Integer refreshTokenValidity;

    // optional
    private String additionalInformation;

    /**
     * trusted = true 将跳过 approval 流程
     */
    private boolean trusted;

    public OauthClientDetailsDto() {
    }

    public OauthClientDetailsDto(OauthClientDetails clientDetails) {
        this.clientId = clientDetails.clientId();
        //clientSecret 加密,不赋值
//        this.clientSecret = clientDetails.clientSecret();
        this.scope = clientDetails.scope();

        this.createTime = clientDetails.createTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.archived = clientDetails.archived();
        this.resourceIds = clientDetails.resourceIds();

        this.webServerRedirectUri = clientDetails.webServerRedirectUri();
        this.authorities = clientDetails.authorities();
        this.accessTokenValidity = clientDetails.accessTokenValidity();

        this.refreshTokenValidity = clientDetails.refreshTokenValidity();
        this.additionalInformation = clientDetails.additionalInformation();
        this.trusted = clientDetails.trusted();

        this.authorizedGrantTypes = clientDetails.authorizedGrantTypes();
    }


    /**
     * 做一些基础的初始化数据
     *
     * @return this
     * @since 1.1.0
     */
    public OauthClientDetailsDto initialized() {
        this.accessTokenValidity = ACCESS_TOKEN_VALIDITY;
        this.refreshTokenValidity = REFRESH_TOKEN_VALIDITY;
        this.authorizedGrantTypes = OIDCUtils.GrantType.AUTHORIZATION_CODE.getType();
        return this;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }


    public String getScopeWithBlank() {
        if (scope != null && scope.contains(",")) {
            return scope.replaceAll(",", " ");
        }
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    /**
     * 判断 是否空的 webServerRedirectUri
     *
     * @return true blank
     * @since 1.1.0
     */
    public boolean isBlankWebServerRedirectUri() {
        return StringUtils.isBlank(webServerRedirectUri);
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }

    public static List<OauthClientDetailsDto> toDtos(List<OauthClientDetails> clientDetailses) {
        List<OauthClientDetailsDto> dtos = new ArrayList<>(clientDetailses.size());
        for (OauthClientDetails clientDetailse : clientDetailses) {
            dtos.add(new OauthClientDetailsDto(clientDetailse));
        }
        return dtos;
    }


    public boolean isContainsAuthorizationCode() {
        return this.authorizedGrantTypes.contains(AUTHORIZATION_CODE.getType());
    }

    public boolean isContainsPassword() {
        return this.authorizedGrantTypes.contains(PASSWORD.getType());
    }

    public boolean isContainsImplicit() {
        return this.authorizedGrantTypes.contains(IMPLICIT.getType());
    }

    public boolean isContainsClientCredentials() {
        return this.authorizedGrantTypes.contains(CLIENT_CREDENTIALS.getType());
    }

    public boolean isContainsRefreshToken() {
        return this.authorizedGrantTypes.contains(REFRESH_TOKEN.getType());
    }


    //new instance without clientSecret
    public OauthClientDetails createDomain() {
        OauthClientDetails clientDetails = new OauthClientDetails()
                .clientId(clientId)
                // encrypted client secret
                .clientSecret(PasswordHandler.encode(clientSecret))
                .resourceIds(resourceIds)
                .authorizedGrantTypes(authorizedGrantTypes)
                .scope(scope);

        if (StringUtils.isNotEmpty(webServerRedirectUri)) {
            clientDetails.webServerRedirectUri(webServerRedirectUri);
        }

        if (StringUtils.isNotEmpty(authorities)) {
            clientDetails.authorities(authorities);
        }

        clientDetails.accessTokenValidity(accessTokenValidity)
                .refreshTokenValidity(refreshTokenValidity)
                .trusted(trusted);

        if (StringUtils.isNotEmpty(additionalInformation)) {
            clientDetails.additionalInformation(additionalInformation);
        }

        return clientDetails;
    }
}