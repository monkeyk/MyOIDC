package myoidc.infrastructure;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
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
     * JWS
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
     * JWT
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
    public void jwtMAC() throws Exception {

        String sharedSecret = RandomStringUtils.random(64, true, true);
        JWSSigner jwsSigner = new MACSigner(sharedSecret);

        //生成idToken
        final String payloadText = "I am MyOIDC";
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("subject")
                .issuer("https://andaily.com")
                .claim("payloadText", payloadText)
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .build();

//        final JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
//        final JWSHeader header = new JWSHeader(JWSAlgorithm.HS384);
        final JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        signedJWT.sign(jwsSigner);

        final String idToken = signedJWT.serialize();

        //校验idToken
        final SignedJWT parseJWT = SignedJWT.parse(idToken);
        JWSVerifier jwsVerifier = new MACVerifier(sharedSecret);
        final boolean verify = parseJWT.verify(jwsVerifier);

        assertTrue(verify);
//        final Payload payload = parseJWT.getPayload();
        final JWTClaimsSet jwtClaimsSet = parseJWT.getJWTClaimsSet();
        assertEquals(jwtClaimsSet.getSubject(), "subject");

    }


    /**
     * JWS
     * 使用 RSA 算法 生成 id_token
     * 以及对其进行校验(verify)
     * 需要公私钥对
     * <p/>
     * 支持算法
     * RS256
     * RS384
     * RS512
     *
     * @throws Exception
     */
    @Test
    public void jwsRSA() throws Exception {

        // RSA keyPair Generator
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        /**
         * 长度 至少 1024, 建议 2048
         */
        final int keySize = 2048;
        keyPairGenerator.initialize(keySize);

        final KeyPair keyPair = keyPairGenerator.genKeyPair();
        //公钥
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        final PrivateKey privateKey = keyPair.getPrivate();

        //keyId
        String keyId = RandomUtils.randomNumber();

        //生成id_token
        JWSSigner jwsSigner = new RSASSASigner(privateKey);

//        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(keyId).build();
//        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS384).keyID(keyId).build();
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS512).keyID(keyId).build();

        final String payloadText = "I am MyOIDC [RSA]";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Issuer", "Issuer");
        jsonObject.put("Audience", "Audience");
        jsonObject.put("payloadText", payloadText);

//        Payload payload = new Payload(payloadText);
        Payload payload = new Payload(jsonObject);
        JWSObject jwsObject = new JWSObject(header, payload);

        jwsObject.sign(jwsSigner);
        final String idToken = jwsObject.serialize();
        System.out.println(payloadText + " -> id_token: " + idToken);


        //校验 id_token
        final JWSObject parseJWS = JWSObject.parse(idToken);

        JWSVerifier verifier = new RSASSAVerifier(publicKey);
        final boolean verify = parseJWS.verify(verifier);
        assertTrue(verify);

        final Payload payload1 = parseJWS.getPayload();
        assertNotNull(payload1);
        final JSONObject jsonObject1 = payload1.toJSONObject();
        assertNotNull(jsonObject1);

        assertEquals(payloadText, jsonObject1.get("payloadText"));

    }


    /**
     * JWT
     * 使用 RSA 算法 生成 id_token
     * 以及对其进行校验(verify)
     * 需要公私钥对
     * <p/>
     * 支持算法
     * RS256
     * RS384
     * RS512
     *
     * @throws Exception
     */
    @Test
    public void jwtRSA() throws Exception {

        // RSA keyPair Generator
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        /**
         * 长度 至少 1024, 建议 2048
         */
        final int keySize = 2048;
        keyPairGenerator.initialize(keySize);

        final KeyPair keyPair = keyPairGenerator.genKeyPair();
        //公钥
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        final PrivateKey privateKey = keyPair.getPrivate();

        //keyId
        String keyId = RandomUtils.randomNumber();

        //生成id_token
        JWSSigner jwsSigner = new RSASSASigner(privateKey);

//        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(keyId).build();
//        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS384).keyID(keyId).build();
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS512).keyID(keyId).build();

        final String payloadText = "I am MyOIDC [RSA]";
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("subject")
                .issuer("Issuer")
                .audience("Audience")
                .claim("payloadText", payloadText)
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        signedJWT.sign(jwsSigner);
        final String idToken = signedJWT.serialize();
        System.out.println(payloadText + " -> id_token: " + idToken);


        //校验 id_token
        final SignedJWT parseJWT = SignedJWT.parse(idToken);

        JWSVerifier verifier = new RSASSAVerifier(publicKey);
        final boolean verify = parseJWT.verify(verifier);
        assertTrue(verify);

        final JWTClaimsSet jwtClaimsSet = parseJWT.getJWTClaimsSet();
        assertNotNull(jwtClaimsSet);
        assertEquals(payloadText, jwtClaimsSet.getStringClaim("payloadText"));


    }


    /**
     * 使用 EC 算法 生成 id_token
     * 以及对其进行校验(verify)
     * 需要公私钥对
     * <p/>
     * 支持算法
     * ES256
     * ES384
     * ES512
     *
     * @throws Exception
     */
    @Test
    public void jwsEC() throws Exception {

        //EC KeyPair
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("EC");
//        keyGenerator.initialize(ECKey.Curve.P_256.toECParameterSpec());
//        keyGenerator.initialize(ECKey.Curve.P_384.toECParameterSpec());
        keyGenerator.initialize(ECKey.Curve.P_521.toECParameterSpec());
        KeyPair keyPair = keyGenerator.generateKeyPair();

        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();

        //keyId
        String keyId = RandomUtils.randomNumber();

        //生成id_token
        JWSSigner signer = new ECDSASigner(privateKey);

//        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(keyId).build();
//        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES384).keyID(keyId).build();
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES512).keyID(keyId).build();

        final String payloadText = "I am MyOIDC [ECDSA]";
        Payload payload = new Payload(payloadText);
        JWSObject jwsObject = new JWSObject(header, payload);

        jwsObject.sign(signer);
        final String idToken = jwsObject.serialize();
        System.out.println(payloadText + " -> id_token: " + idToken);


        //校验 id_token
        final JWSObject parseJWS = JWSObject.parse(idToken);

        JWSVerifier verifier = new ECDSAVerifier(publicKey);
        final boolean verify = parseJWS.verify(verifier);

        assertTrue(verify);
        final String s = parseJWS.getPayload().toString();
        assertEquals(s, payloadText);

    }


}
