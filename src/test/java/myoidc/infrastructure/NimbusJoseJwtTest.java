package myoidc.infrastructure;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;

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


    /**
     * 使用 RSA 算法 生成 id_token
     * 以及对其进行校验(verify)
     * 需要公私钥对
     *
     * @throws Exception
     */
    @Test
    public void jwsRSA() throws Exception {

        //keyPair
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        //长度 2048
        keyPairGenerator.initialize(2048);

        final KeyPair keyPair = keyPairGenerator.genKeyPair();
        //公钥
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        final PrivateKey privateKey = keyPair.getPrivate();

        //keyId
        String keyId = RandomUtils.randomNumber();

        //生成id_token
        JWSSigner jwsSigner = new RSASSASigner(privateKey);

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(keyId).build();
        final String payloadText = "I am MyOIDC [RSA]";
        Payload payload = new Payload(payloadText);
        JWSObject jwsObject = new JWSObject(header, payload);

        jwsObject.sign(jwsSigner);
        final String idToken = jwsObject.serialize();
        System.out.println(payloadText + " -> id_token: " + idToken);


        //校验 id_token
        final JWSObject parseJWS = JWSObject.parse(idToken);

        JWSVerifier verifier = new RSASSAVerifier(publicKey);
        final boolean verify = parseJWS.verify(verifier);
        assertTrue(verify);

        final String s = jwsObject.getPayload().toString();
        assertEquals(payloadText, s);

    }

}
