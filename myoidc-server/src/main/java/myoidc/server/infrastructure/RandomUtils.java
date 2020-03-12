package myoidc.server.infrastructure;


import org.apache.commons.lang3.RandomStringUtils;

/**
 * 2016/12/25
 *
 * @author Shengzhao Li
 */
public abstract class RandomUtils {


    public static String randomText() {
        return RandomStringUtils.random(32, true, true);
    }


    private RandomUtils() {
    }

    //全数字的随机值
    public static String randomNumber() {
        return RandomStringUtils.random(32, false, true);
    }

}
