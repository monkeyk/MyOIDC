package myoidc.client.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * 15-5-18
 * <p/>
 * {"access_token":"d580fbfe-da2c-4840-8b66-848168ad8d62","token_type":"bearer","refresh_token":"9406e12f-d62e-42bd-ad40-0206d94ae776","expires_in":43199,"scope":"read write"}
 *
 * @author Shengzhao Li
 */
public class AccessTokenDto extends AbstractOauthDto {

    private static final long serialVersionUID = 7994420675115475730L;

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("scope")
    private String scope;

    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("jti")
    private String jti;

    public AccessTokenDto() {
    }

    public AccessTokenDto(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }


    public boolean isIncludeIdToken() {
        return StringUtils.isNotBlank(this.idToken);
    }


    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{accessToken='").append(accessToken).append('\'');
        sb.append(", tokenType='").append(tokenType).append('\'');
        sb.append(", refreshToken='").append(refreshToken).append('\'');
        sb.append(", scope='").append(scope).append('\'');
        sb.append(", expiresIn=").append(expiresIn);
        sb.append(", errorDescription='").append(errorDescription).append('\'');
        sb.append(", error='").append(error).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
