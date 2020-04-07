package myoidc.client.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 2020/4/7
 * <p>
 * DiscoveryEndpoint 信息
 * <p>
 * <pre>
 *     {
 * "response_types_supported": [
 * "token",
 * "code",
 * "id_token"
 * ],
 * "claims_supported": [
 * "sub",
 * "aud",
 * "scope",
 * "iss",
 * "exp",
 * "iat",
 * "client_id",
 * "authorities",
 * "user_name"
 * ],
 * "jwks_uri": "http://localhost:8080/myoidc-server/public/jwks",
 * "grant_types_supported": [
 * "password",
 * "authorization_code",
 * "implicit",
 * "refresh_token",
 * "client_credentials"
 * ],
 * "subject_types_supported": [
 * "public"
 * ],
 * "id_token_signing_alg_values_supported": [
 * "RS256"
 * ],
 * "registration_endpoint": "http://localhost:8080/myoidc-server/public/registration",
 * "scopes_supported": [
 * "openid",
 * "read",
 * "write"
 * ],
 * "issuer": "http://localhost:8080/myoidc-server/",
 * "authorization_endpoint": "http://localhost:8080/myoidc-server/oauth/authorize",
 * "token_endpoint": "http://localhost:8080/myoidc-server/oauth/token",
 * "userinfo_endpoint": "http://localhost:8080/myoidc-server/api/userinfo"
 * }
 * </pre>
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class DiscoveryEndpointInfo implements Serializable {
    private static final long serialVersionUID = 549004975910871665L;


    private String userinfo_endpoint;
    private String token_endpoint;
    private String authorization_endpoint;
    private String issuer;
    private String registration_endpoint;

    private String jwks_uri;

    private List<String> scopes_supported = new ArrayList<>();
    private List<String> id_token_signing_alg_values_supported = new ArrayList<>();
    private List<String> subject_types_supported = new ArrayList<>();

    private List<String> grant_types_supported = new ArrayList<>();
    private List<String> claims_supported = new ArrayList<>();
    private List<String> response_types_supported = new ArrayList<>();


    public DiscoveryEndpointInfo() {
    }


    public String getUserinfo_endpoint() {
        return userinfo_endpoint;
    }

    public void setUserinfo_endpoint(String userinfo_endpoint) {
        this.userinfo_endpoint = userinfo_endpoint;
    }

    public String getToken_endpoint() {
        return token_endpoint;
    }

    public void setToken_endpoint(String token_endpoint) {
        this.token_endpoint = token_endpoint;
    }

    public String getAuthorization_endpoint() {
        return authorization_endpoint;
    }

    public void setAuthorization_endpoint(String authorization_endpoint) {
        this.authorization_endpoint = authorization_endpoint;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getRegistration_endpoint() {
        return registration_endpoint;
    }

    public void setRegistration_endpoint(String registration_endpoint) {
        this.registration_endpoint = registration_endpoint;
    }

    public String getJwks_uri() {
        return jwks_uri;
    }

    public void setJwks_uri(String jwks_uri) {
        this.jwks_uri = jwks_uri;
    }

    public List<String> getScopes_supported() {
        return scopes_supported;
    }

    public void setScopes_supported(List<String> scopes_supported) {
        this.scopes_supported = scopes_supported;
    }

    public List<String> getId_token_signing_alg_values_supported() {
        return id_token_signing_alg_values_supported;
    }

    public void setId_token_signing_alg_values_supported(List<String> id_token_signing_alg_values_supported) {
        this.id_token_signing_alg_values_supported = id_token_signing_alg_values_supported;
    }

    public List<String> getSubject_types_supported() {
        return subject_types_supported;
    }

    public void setSubject_types_supported(List<String> subject_types_supported) {
        this.subject_types_supported = subject_types_supported;
    }

    public List<String> getGrant_types_supported() {
        return grant_types_supported;
    }

    public void setGrant_types_supported(List<String> grant_types_supported) {
        this.grant_types_supported = grant_types_supported;
    }

    public List<String> getClaims_supported() {
        return claims_supported;
    }

    public void setClaims_supported(List<String> claims_supported) {
        this.claims_supported = claims_supported;
    }

    public List<String> getResponse_types_supported() {
        return response_types_supported;
    }

    public void setResponse_types_supported(List<String> response_types_supported) {
        this.response_types_supported = response_types_supported;
    }

    @Override
    public String toString() {
        return "DiscoveryEndpointInfo{" +
                "userinfo_endpoint='" + userinfo_endpoint + '\'' +
                ", token_endpoint='" + token_endpoint + '\'' +
                ", authorization_endpoint='" + authorization_endpoint + '\'' +
                ", issuer='" + issuer + '\'' +
                ", registration_endpoint='" + registration_endpoint + '\'' +
                ", jwks_uri='" + jwks_uri + '\'' +
                ", scopes_supported=" + scopes_supported +
                ", id_token_signing_alg_values_supported=" + id_token_signing_alg_values_supported +
                ", subject_types_supported=" + subject_types_supported +
                ", grant_types_supported=" + grant_types_supported +
                ", claims_supported=" + claims_supported +
                ", response_types_supported=" + response_types_supported +
                '}';
    }
}
