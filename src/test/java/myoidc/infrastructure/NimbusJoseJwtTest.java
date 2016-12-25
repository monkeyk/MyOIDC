package myoidc.infrastructure;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * 2016/12/25
 * <p/>
 * Testing
 * http://connect2id.com/products/nimbus-jose-jwt
 *
 * @author Shengzhao Li
 */
public class NimbusJoseJwtTest {


    /**
     * 使用HMAC SHA-256 进行加密 与 解密
     * 基于相同的 secret (对称算法)
     * <p/>
     * 算法     Secret长度
     * HS256   32
     * HS384   64
     * HS512   64
     *
     * @throws Exception
     */
    @Test
    public void jwsMAC() throws Exception {

        String sharedSecret = RandomStringUtils.random(64, true, true);
        JWSSigner jwsSigner = new MACSigner(sharedSecret);

        //加密
//        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
//        JWSHeader header = new JWSHeader(JWSAlgorithm.HS384);
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        final String payloadText = "I am MyOIDC";
        Payload payload = new Payload(payloadText);
        JWSObject jwsObject = new JWSObject(header, payload);

        jwsObject.sign(jwsSigner);
        //获取 idToken
        final String idToken = jwsObject.serialize();
        System.out.println(payloadText + " -> id_token: " + idToken);

        //解密
        JWSVerifier verifier = new MACVerifier(sharedSecret);
        final JWSObject parseJWS = JWSObject.parse(idToken);
        final boolean verify = parseJWS.verify(verifier);

        assertTrue(verify);
        final String decryptPayload = parseJWS.getPayload().toString();
        assertEquals(decryptPayload, payloadText);
    }




}
