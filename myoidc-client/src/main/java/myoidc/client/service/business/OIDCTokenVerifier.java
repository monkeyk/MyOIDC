package myoidc.client.service.business;

import com.google.common.collect.ImmutableMap;
import myoidc.client.domain.RPHolder;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import org.jose4j.keys.resolvers.VerificationKeyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static myoidc.client.domain.RPHolder.RESOURCE_ID;

/**
 * 2020/4/12
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class OIDCTokenVerifier {


    private static final Logger LOG = LoggerFactory.getLogger(OIDCTokenVerifier.class);

    private final RPHolder rpHolder;
    private final String token;

    public OIDCTokenVerifier(RPHolder rpHolder, String token) {
        this.rpHolder = rpHolder;
        this.token = token;
    }

    public Map<String, Object> verify() {

        VerificationKeyResolver verificationKeyResolver = new HttpsJwksVerificationKeyResolver(new HttpsJwks(rpHolder.getDiscoveryEndpointInfo().getJwks_uri()));
        JwtConsumer consumer = new JwtConsumerBuilder()
                .setVerificationKeyResolver(verificationKeyResolver)
                //此处有许可项可配置进行校验，请根据实际需要配置
                //更多帮助可访问 https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples
                .setRequireExpirationTime()
                .setRequireSubject()
                .setRequireIssuedAt()
                .setExpectedAudience(RESOURCE_ID)
//                .setRequireNotBefore()
//                .setRequireJwtId()
                .build();
        try {
            JwtClaims claims = consumer.processToClaims(token);
            return claims.getClaimsMap();
        } catch (InvalidJwtException e) {
            LOG.warn("Verify token failed", e);
            return ImmutableMap.of("error", "verify_failed", "error_details", e.getErrorDetails());
        }

    }
}
