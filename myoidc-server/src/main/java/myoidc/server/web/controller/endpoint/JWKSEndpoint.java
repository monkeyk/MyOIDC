package myoidc.server.web.controller.endpoint;

import myoidc.server.web.WebUtils;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2020/3/12
 * <p>
 * JWKS URL API
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@RestController
public class JWKSEndpoint {


    private static final Logger LOG = LoggerFactory.getLogger(JWKSEndpoint.class);

    @Autowired
    private JsonWebKeySet jsonWebKeySet;


    @GetMapping(value = "/public/jwks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String jwks() throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Call 'jwks' API from IP: {}", WebUtils.getIp());
        }
        return jsonWebKeySet.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
    }

}
