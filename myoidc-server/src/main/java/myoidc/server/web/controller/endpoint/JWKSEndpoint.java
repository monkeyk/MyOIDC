package myoidc.server.web.controller.endpoint;

import myoidc.server.web.WebUtils;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.PublicJsonWebKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

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
    private PublicJsonWebKey publicJsonWebKey;


    @GetMapping("/public/jwks")
    public Map<String, Object> jwks(Model model) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Call 'jwks' API from IP: {}", WebUtils.getIp());
        }
        Map<String, Object> params = publicJsonWebKey.toParams(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
        //only one key
        model.addAttribute(JsonWebKeySet.JWK_SET_MEMBER_NAME, Collections.singletonList(params));
//        JsonWebKeySet jsonWebKeySet = new JsonWebKeySet(publicJsonWebKey);
//        jsonWebKeySet.toJson();
        return model.asMap();
    }

}
