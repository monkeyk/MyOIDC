package myoidc.server.infrastructure;


import org.junit.Test;
import org.primeframework.jwt.JWTUtils;
import org.primeframework.jwt.domain.JWT;
import org.primeframework.jwt.domain.RSAKeyPair;
import org.primeframework.jwt.rsa.RSASigner;
import org.primeframework.jwt.rsa.RSAVerifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 2018/6/2
 * <p>
 * Testing
 * https://github.com/inversoft/prime-jwt
 *
 * @author Shengzhao Li
 */
public class PrimeJwtTest {


    /**
     * Test RSA
     *
     * @throws Exception Exception
     */
    @Test
    public void jwt() throws Exception {


        // keypair
        final RSAKeyPair rsaKeyPair = JWTUtils.generate2048RSAKeyPair();

        System.out.println("PublicKey: " + rsaKeyPair.publicKey);
        System.out.println("PrivateKey: " + rsaKeyPair.privateKey);


//        final RSAPublicKey publicKey = RSAUtils.getPublicKeyFromPEM(rsaKeyPair.publicKey);
//        final RSAPrivateKey privateKey = RSAUtils.getPrivateKeyFromPEM(rsaKeyPair.privateKey);

        // generate
        final RSASigner rsaSigner = RSASigner.newSHA256Signer(rsaKeyPair.privateKey);
        final JWT jwt = new JWT().setSubject("subject").setAudience("audi").setUniqueId("uid-id");
        final String idToken = JWT.getEncoder().encode(jwt, rsaSigner);

        assertNotNull(idToken);
        System.out.println(idToken);

        //verify
        final RSAVerifier rsaVerifier = RSAVerifier.newVerifier(rsaKeyPair.publicKey);
        final JWT decode = JWT.getDecoder().decode(idToken, rsaVerifier);

        assertNotNull(decode);
        assertEquals(decode.audience, "audi");

    }


}
