package myoidc.server.infrastructure;

import org.jose4j.jwk.*;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.keys.EllipticCurves;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static myoidc.server.Constants.*;
import static org.junit.Assert.assertNotNull;

/**
 * 2020/6/3
 *
 * @author Shengzhao Li
 * @since 1.1.1
 */
public class JWKSTest {


    /**
     * RSA jwks 示例
     *
     * @throws Exception e
     */
    @Test
    public void rsaJWKS() throws Exception {

        RsaJsonWebKey jwk = RsaJwkGenerator.generateJwk(DEFAULT_KEY_SIZE);
        //sig or enc
        jwk.setUse(USE_SIG);
        jwk.setKeyId(DEFAULT_KEY_ID);
        jwk.setAlgorithm(OIDC_ALG);
//        jwk.setKeyOps();

        final String publicKeyString = jwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
        final String privateKeyString = jwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);

        assertNotNull(publicKeyString);
        assertNotNull(privateKeyString);
//        System.out.println("PublicKey:\n" + publicKeyString);
//        System.out.println("PrivateKey:\n" + privateKeyString);

        //生成 jwks
        JSONObject jsonObject = new JSONObject(jwk.toParams(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE));
        JSONArray jsonArray = new JSONArray();
        //加一个key
        jsonArray.put(jsonObject);
        //可以多加几个key
//        jsonArray.put(jsonObject);

        JSONObject jwksJSON = new JSONObject();
        jwksJSON.put("keys", jsonArray);
        assertNotNull(jwksJSON);
        //输出JSON格式
//        System.out.println(jwksJSON);


    }


    /**
     * EC jwks 示例
     *
     * @throws Exception e
     */
    @Test
    public void ecJWKS() throws Exception {

        EllipticCurveJsonWebKey jwk = EcJwkGenerator.generateJwk(EllipticCurves.P256);
        //sig or enc
        jwk.setUse(USE_SIG);
        jwk.setKeyId(DEFAULT_KEY_ID);
        jwk.setAlgorithm(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);
        //        jwk.setKeyOps();

        final String publicKeyString = jwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
        final String privateKeyString = jwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);

        assertNotNull(publicKeyString);
        assertNotNull(privateKeyString);
//        System.out.println("PublicKey:\n" + publicKeyString);
//        System.out.println("PrivateKey:\n" + privateKeyString);

        //生成 jwks
        JSONObject jsonObject = new JSONObject(jwk.toParams(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE));
        JSONArray jsonArray = new JSONArray();
        //加一个key
        jsonArray.put(jsonObject);
        //可以多加几个key
//        jsonArray.put(jsonObject);

        JSONObject jwksJSON = new JSONObject();
        jwksJSON.put("keys", jsonArray);
        assertNotNull(jwksJSON);
        //输出JSON格式
//        System.out.println(jwksJSON);


    }


    /**
     * Oct jwks 示例
     *
     * @throws Exception e
     */
    @Test
    public void octJWKS() throws Exception {

        OctetSequenceJsonWebKey jwk = OctJwkGenerator.generateJwk(DEFAULT_KEY_SIZE);
        //sig or enc
        jwk.setUse(USE_SIG);
        jwk.setKeyId(DEFAULT_KEY_ID);
        jwk.setAlgorithm(AlgorithmIdentifiers.HMAC_SHA256);
        //        jwk.setKeyOps();

//        final String publicKeyString = jwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
        final String privateKeyString = jwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);

//        assertNotNull(publicKeyString);
        assertNotNull(privateKeyString);
//        System.out.println("PublicKey:\n" + publicKeyString);
//        System.out.println("PrivateKey:\n" + privateKeyString);

        //生成 jwks
        JSONObject jsonObject = new JSONObject(jwk.toParams(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE));
        JSONArray jsonArray = new JSONArray();
        //加一个key
        jsonArray.put(jsonObject);
        //可以多加几个key
//        jsonArray.put(jsonObject);

        JSONObject jwksJSON = new JSONObject();
        jwksJSON.put("keys", jsonArray);
        assertNotNull(jwksJSON);
        //输出JSON格式
//        System.out.println(jwksJSON);


    }


}
