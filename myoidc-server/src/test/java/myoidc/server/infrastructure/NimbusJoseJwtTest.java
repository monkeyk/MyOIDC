package myoidc.server.infrastructure;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


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
     * @throws Exception e
     * @since 1.1.0
     */
    @Test
    @Ignore
    public void testJWKSet() throws Exception {

        Resource resource = new ClassPathResource("classpath*:keystore.jwks");
        // read in the file
        String s = CharStreams.toString(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
        JWKSet jwkSet = JWKSet.parse(s);
        assertNotNull(jwkSet);
//        System.out.println(jwkSet);

        List<JWK> keys = jwkSet.getKeys();
        for (JWK key : keys) {
//            System.out.println(key);
//            System.out.println(key.getAlgorithm());
//            System.out.println(key.getKeyStore());
//            System.out.println(key.getKeyUse());
//            System.out.println(key.getKeyType());
//            System.out.println(key.getParsedX509CertChain());
            System.out.println(key.getKeyID());
            System.out.println(key.isPrivate());

//            JWK jwk = key.toPublicJWK();
//            System.out.println(jwk);
//            JSONObject jsonObject = key.toJSONObject();
//            System.out.println(jsonObject);

//            PublicJsonWebKey rsk = RsaJsonWebKey.Factory.newPublicJwk(key.toString());
//            PrivateKey privateKey = rsk.getPrivateKey();
//            PublicKey publicKey = rsk.getPublicKey();
//            System.out.println(publicKey + "\n" + privateKey);

//            RSAKey  rsaKey= new RSAKey();
//            rsaKey.
        }
    }


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
     * JWS
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
        keyGenerator.initialize(Curve.P_521.toECParameterSpec());
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


    /**
     * JWT
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
    public void jwtEC() throws Exception {

        //EC KeyPair
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("EC");
//        keyGenerator.initialize(ECKey.Curve.P_256.toECParameterSpec());
//        keyGenerator.initialize(ECKey.Curve.P_384.toECParameterSpec());
        keyGenerator.initialize(Curve.P_521.toECParameterSpec());
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
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("subject")
                .issuer("Issuer")
                .audience("Audience")
                .claim("payloadText", payloadText)
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        signedJWT.sign(signer);
        final String idToken = signedJWT.serialize();
        System.out.println(payloadText + " -> id_token: " + idToken);


        //校验 id_token
        final SignedJWT parseJWS = SignedJWT.parse(idToken);

        JWSVerifier verifier = new ECDSAVerifier(publicKey);
        final boolean verify = parseJWS.verify(verifier);

        assertTrue(verify);
        final JWTClaimsSet jwtClaimsSet = parseJWS.getJWTClaimsSet();
        assertEquals(jwtClaimsSet.getClaim("payloadText"), payloadText);

    }


    /**
     * 使用RSA 算法进行加密数据
     * 与解密数据
     * <p/>
     * 128
     * RSA_OAEP   - A128GCM
     * 256
     * RSA_OAEP_256 - A256GCM
     *
     * @throws Exception
     */
    @Test
    public void jwtRSAEncryption() throws Exception {

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


        //加密, 生成idToken
        //加密的数据放在 JWTClaimsSet 中
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer("https://myoidc.cc")
                .subject("Lims")
                .audience("https://one-app.com")
                .notBeforeTime(new Date())
                .issueTime(new Date())
                .expirationTime(new Date(new Date().getTime() + 1000 * 60 * 10))
                .jwtID(RandomStringUtils.random(16, true, true))
                .build();

//        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP, EncryptionMethod.A128GCM);
        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM);
        EncryptedJWT jwt = new EncryptedJWT(header, claimsSet);

        RSAEncrypter encrypter = new RSAEncrypter(publicKey);
        jwt.encrypt(encrypter);

        final String idToken = jwt.serialize();
        assertNotNull(idToken);

        //解密
        final EncryptedJWT parseJWT = EncryptedJWT.parse(idToken);
        RSADecrypter decrypter = new RSADecrypter(privateKey);
        parseJWT.decrypt(decrypter);

        final JWTClaimsSet jwtClaimsSet = parseJWT.getJWTClaimsSet();
        assertNotNull(jwtClaimsSet);
        assertNotNull(jwtClaimsSet.getAudience());

    }


    /**
     * AES 加密/解密
     * JWE
     *
     * @throws Exception
     */
    @Test
    public void jweAES() throws Exception {

        final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //位数
//        keyGenerator.init(128);
        keyGenerator.init(256);
        final SecretKey secretKey = keyGenerator.generateKey();

        //加密
//        JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128GCM);
        JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A256GCM);
        Payload payload = new Payload("I am MyOIDC");

        JWEObject jweObject = new JWEObject(jweHeader, payload);
        jweObject.encrypt(new DirectEncrypter(secretKey));

        final String idToken = jweObject.serialize();
        assertNotNull(idToken);

        //解密
        final JWEObject parseJWE = JWEObject.parse(idToken);
        parseJWE.decrypt(new DirectDecrypter(secretKey));

        final Payload payload1 = parseJWE.getPayload();
        assertNotNull(payload1);

    }


}
