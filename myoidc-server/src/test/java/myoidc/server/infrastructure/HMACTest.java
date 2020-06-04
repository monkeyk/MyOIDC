package myoidc.server.infrastructure;

import myoidc.server.Constants;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.junit.Test;

import static myoidc.server.Constants.DEFAULT_KEY_ID;
import static org.junit.Assert.assertNotNull;

/**
 * 2020/6/4
 *
 * @author Shengzhao Li
 * @since 1.1.2
 */
public class HMACTest {


    //HMAC KEY
//    private String secret = new RandomValueStringGenerator(32).generate();
    private String secret = "Bl0depAULLDRPZnR0DJThK9a9KSJFXXr";

    /**
     * HMAC sign
     * 生成 idToken
     *
     * @throws Exception e
     */
    @Test
    public void sign() throws Exception {

        HmacKey key = new HmacKey(secret.getBytes(Constants.ENCODING));

        JsonWebSignature jws = new JsonWebSignature();
        jws.setKeyIdHeaderValue(DEFAULT_KEY_ID);
        jws.setKey(key);
        jws.setAlgorithmConstraints(AlgorithmConstraints.NO_CONSTRAINTS);
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

        JwtClaims claims = new JwtClaims();
        claims.setSubject("zhangsan");
        claims.setIssuer("https://myoidc.com");
        claims.setIssuedAtToNow();
        claims.setGeneratedJwtId();
        claims.setExpirationTimeMinutesInTheFuture(10);
        claims.setAudience("MyOIDC");
        jws.setPayload(claims.toJson());

        String idToken = jws.getCompactSerialization();
        assertNotNull(idToken);
//        System.out.println(secret);
//        System.out.println(idToken);

    }


    /**
     * HMAC verify
     * 校验idToken
     *
     * @throws Exception e
     */
    @Test
    public void verify() throws Exception {

        String idToken = "eyJraWQiOiJteW9pZGMta2V5aWQiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6aGFuZ3NhbiIsImlzcyI6Imh0dHBzOi8vbXlvaWRjLmNvbSIsImlhdCI6MTU5MTI3OTMzMywianRpIjoiVzlDSFdMNTFSZG9ZMVNaQWJQbTB3ZyIsImV4cCI6MTU5MTI3OTkzMywiYXVkIjoiTXlPSURDIn0.7038DF0Md3V5tFtYzsh4iiwx0x9TxwdKePpxEZYfb7I";


        HmacKey key = new HmacKey(secret.getBytes(Constants.ENCODING));
        JwtConsumer consumer = new JwtConsumerBuilder().setVerificationKey(key)
                .setRequireSubject()
                .setRequireIssuedAt()
                .setRequireJwtId()
                .setExpectedAudience("MyOIDC")
                .build();
        JwtClaims claims = consumer.processToClaims(idToken);
        assertNotNull(claims);
//        System.out.println(claims);

    }

}
