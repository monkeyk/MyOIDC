package myoidc.server.infrastructure;


import io.vertx.core.json.JsonObject;
import io.vertx.ext.jwt.JWK;
import io.vertx.ext.jwt.JWT;
import io.vertx.ext.jwt.JWTOptions;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 2018/6/2
 * <p>
 * Test
 * https://github.com/vert-x3/vertx-auth
 *
 * @author Shengzhao Li
 */
public class VertxAuthJwtTest {


    /**
     * Generate/ Verify
     *
     * @throws Exception Exception
     */
    @Test
    public void jwt() throws Exception {


        // RSA keyPair Generator
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        /*
         * 长度 至少 1024, 建议 2048
         */
        final int keySize = 2048;
        keyPairGenerator.initialize(keySize);

        final KeyPair keyPair = keyPairGenerator.genKeyPair();
        //公钥
        final PublicKey publicKey = keyPair.getPublic();
        //私钥
        final PrivateKey privateKey = keyPair.getPrivate();


        final String pemPub = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        final String pemSec = Base64.getEncoder().encodeToString(privateKey.getEncoded());


        //generate
        final String algorithm = "RS256";
        JWK jwk = new JWK(algorithm, pemPub, pemSec);
        final JWT jwt = new JWT().addJWK(jwk);


        JsonObject payload = new JsonObject();
        payload.put("appid", "appid");

        JWTOptions options = new JWTOptions();
        options.setAlgorithm(algorithm);
        options.setSubject("subject");

        String idToken = jwt.sign(payload, options);

        assertNotNull(idToken);
        System.out.println(idToken);


        //verify
        JWK jwk2 = new JWK(algorithm, pemPub, pemSec);
        final JWT jwtVerify = new JWT().addJWK(jwk2);

        final JsonObject decode = jwtVerify.decode(idToken);
        assertNotNull(decode);
        assertEquals(decode.getString("appid"), "appid");

    }


}
