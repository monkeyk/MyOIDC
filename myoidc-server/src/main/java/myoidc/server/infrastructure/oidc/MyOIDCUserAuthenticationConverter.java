package myoidc.server.infrastructure.oidc;

import myoidc.server.domain.security.OIDCUserDetails;
import myoidc.server.domain.shared.Application;
import org.jose4j.jwt.ReservedClaimNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * 2020/3/11
 * <p>
 * Ext. default
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class MyOIDCUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    private static final Logger LOG = LoggerFactory.getLogger(MyOIDCUserAuthenticationConverter.class);

    public MyOIDCUserAuthenticationConverter() {
    }

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> myOIDCMap = new HashMap<>();
        Map<String, ?> oldMap = super.convertUserAuthentication(authentication);
        myOIDCMap.putAll(oldMap);

        //按协议规范增加 required 属性
        // https://openid.net/specs/openid-connect-core-1_0.html#IDToken
        myOIDCMap.put(ReservedClaimNames.ISSUER, Application.host());
        myOIDCMap.put(ReservedClaimNames.ISSUED_AT, System.currentTimeMillis() / 1000);

        Object details = authentication.getDetails();
        if (details instanceof OIDCUserDetails) {
            OIDCUserDetails userDetails = (OIDCUserDetails) details;
            myOIDCMap.put(ReservedClaimNames.SUBJECT, userDetails.user().uuid());
        } else {
            myOIDCMap.put(ReservedClaimNames.SUBJECT, myOIDCMap.get(USERNAME));
        }
        return myOIDCMap;
    }
}
