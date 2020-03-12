package myoidc.server.domain.shared;


import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Shengzhao Li
 */
public abstract class UUIDGenerator {


    protected UUIDGenerator() {
    }


    public static String generate() {
        return RandomStringUtils.random(42, true, true);
    }
}