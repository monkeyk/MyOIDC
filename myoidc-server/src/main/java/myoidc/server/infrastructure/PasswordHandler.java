package myoidc.server.infrastructure;

import myoidc.server.domain.shared.BeanProvider;
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
}
