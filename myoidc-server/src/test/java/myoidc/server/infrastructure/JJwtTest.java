package myoidc.server.infrastructure;


import io.jsonwebtoken.*;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * 2018/5/30
 * <p>
 * <p>
 * Test JJWT  lib
 *
 * @author Shengzhao Li
 */
public class JJwtTest {


    @Test
    public void idToken() throws Exception {

        // RSA keyPair Generator
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        /*
         * 长度 至少 1024, 建议 2048
         */
        final int keySize = 2048;
        keyPairGenerator.initialize(keySize);

        final KeyPair keyPair = keyPairGenerator.genKeyPair();

        final PrivateKey privateKey = keyPair.getPrivate();

        // gen id_token
        final Date exp = DateUtils.addMinutes(new Date(), 5);
        final JwtBuilder jwtBuilder = Jwts.builder().setId("jti").setSubject("sub").setExpiration(exp).signWith(SignatureAlgorithm.RS512, privateKey);
        final String idToken = jwtBuilder.compact();


        assertNotNull(idToken);
        System.out.println(idToken);


        // verify

        final PublicKey publicKey = keyPair.getPublic();
//        final Jwt jwt = Jwts.parser().parse(idToken);
        final JwtParser parser = Jwts.parser();
        final Jwt jwt = parser.setSigningKey(publicKey).parse(idToken);

        assertNotNull(jwt);
        System.out.println(jwt.getHeader());
        System.out.println(jwt.getBody());


    }


}
