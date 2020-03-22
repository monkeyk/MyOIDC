package myoidc.server.web.controller.endpoint;

import myoidc.server.infrastructure.oidc.OIDCUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static myoidc.server.Constants.ID_TOKEN;
import static myoidc.server.Constants.OIDC_ALG;
import static myoidc.server.domain.shared.Application.host;
import static myoidc.server.infrastructure.oidc.OIDCUtils.SCOPE_OPENID;
import static myoidc.server.infrastructure.oidc.OIDCUtils.SCOPE_READ;
import static myoidc.server.infrastructure.oidc.OIDCUtils.SCOPE_WRITE;

/**
 * 2020/3/11
 * <p>
 * Discovery API
 * https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderMetadata
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@RestController
public class DiscoveryEndpoint {


    /**
     * https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderConfigurationRequest
     *
     * @return view
     * @throws Exception Exception
     */
    @GetMapping("/.well-known/openid-configuration")
    public Map<String, Object> configuration() throws Exception {
        Map<String, Object> model = new HashMap<>();
        String host = host();
        model.put("issuer", host);
        model.put("authorization_endpoint", OIDCUtils.authorizeEndpoint(host));
        model.put("token_endpoint", OIDCUtils.tokenEndpoint(host));
        model.put("userinfo_endpoint", OIDCUtils.userinfoEndpoint(host));

        model.put("jwks_uri", OIDCUtils.jwksURI(host));
        model.put("registration_endpoint", OIDCUtils.registrationEndpoint(host));

        model.put("scopes_supported", Arrays.asList(SCOPE_OPENID, SCOPE_READ, SCOPE_WRITE));
        model.put("grant_types_supported", OIDCUtils.GrantType.types());
        model.put("response_types_supported", Arrays.asList("token", "code", ID_TOKEN));
        //ALG:
        model.put("id_token_signing_alg_values_supported", Collections.singletonList(OIDC_ALG));
        // "pairwise",
        model.put("subject_types_supported", Arrays.asList("public"));
        model.put("claims_supported", Arrays.asList("sub", "aud", "scope", "iss", "exp", "iat", "client_id", "authorities", "user_name"));
        return model;
    }


}
