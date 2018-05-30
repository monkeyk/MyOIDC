package myoidc.server.infrastructure;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.Assert.assertNotNull;

/**
 * 2018/5/30
 * <p>
 * OAuth0  jwt
 *
 * @author Shengzhao Li
 */
public class Auth0JwtTest {


    /**
     * Test JWT
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


        final PublicKey publicKey = keyPair.getPublic();
        final PrivateKey privateKey = keyPair.getPrivate();


        // gen id_token
        final Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);

        final String idToken = JWT.create().withJWTId("jwt-id").withAudience("audience").withSubject("subject").sign(algorithm);

        assertNotNull(idToken);
        System.out.println(idToken);


        //verify
//        final DecodedJWT decodedJWT = JWT.decode(idToken);
//        System.out.println("id_token -> header: " + decodedJWT.getHeader());
//        System.out.println("id_token -> payload: " + decodedJWT.getPayload());
//        System.out.println("id_token -> token: " + decodedJWT.getToken());
//        System.out.println("id_token -> signature: " + decodedJWT.getSignature());


        final JWTVerifier verifier = JWT.require(algorithm).build();
        final DecodedJWT verify = verifier.verify(idToken);

        assertNotNull(verify);
        System.out.println(verify);


//        final Algorithm none = Algorithm.none();

    }


}
