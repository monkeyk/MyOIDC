package myoidc.server.infrastructure;

import myoidc.server.domain.shared.BeanProvider;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * 2016/3/25
 * <p>
 * From spring-oauth-server
 *
 * @author Shengzhao Li
 */
public abstract class PasswordHandler {


//    private PasswordEncoder passwordEncoder = SOSContextHolder.getBean(PasswordEncoder.class);


    private PasswordHandler() {
    }


    public static String encode(String password) {
        PasswordEncoder passwordEncoder = BeanProvider.getBean(PasswordEncoder.class);
        Assert.notNull(passwordEncoder, "passwordEncoder is null");
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


    /**
     * 生成新的随机密码, 长度: 12
     *
     * @return Random password
     * @since 1.1.0
     */
    public static String randomPassword() {
        return RandomStringUtils.random(12, true, true);
    }
}
