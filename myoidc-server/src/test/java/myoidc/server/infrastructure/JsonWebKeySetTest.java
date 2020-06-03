package myoidc.server.infrastructure;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertNotNull;

/**
 * 2020/6/3
 *
 * @author Shengzhao Li
 * @since 1.1.1
 */
public class JsonWebKeySetTest {


    //RSA
    @Test
    public void keySet() throws Exception {

        JsonWebKeySet jwkSet;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("jwks_rsa.json")) {
            String keyJson = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
            jwkSet = new JsonWebKeySet(keyJson);
        }

        JsonWebKey jsonWebKey = jwkSet.getJsonWebKeys().get(0);
        assertNotNull(jsonWebKey);


    }


    //ECC
    @Test
    public void keyECCSet() throws Exception {

        JsonWebKeySet jwkSet;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("jwks_ec.json")) {
            String keyJson = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
            jwkSet = new JsonWebKeySet(keyJson);
        }

        JsonWebKey jsonWebKey = jwkSet.getJsonWebKeys().get(0);
        assertNotNull(jsonWebKey);


    }


    //OCT
    @Test
    public void keyOCTSet() throws Exception {

        JsonWebKeySet jwkSet;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("jwks_oct.json")) {
            String keyJson = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
            jwkSet = new JsonWebKeySet(keyJson);
        }

        JsonWebKey jsonWebKey = jwkSet.getJsonWebKeys().get(0);
        assertNotNull(jsonWebKey);


        JsonWebSignature jws = new JsonWebSignature();
        jws.setKey(jsonWebKey.getKey());

        JwtClaims claims = new JwtClaims();
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setSubject("subject");
        jws.setPayload(claims.toJson());

        jws.setKeyIdHeaderValue(jsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

        String idToken = jws.getCompactSerialization();
        assertNotNull(idToken);
//        System.out.println(idToken);

    }


}
