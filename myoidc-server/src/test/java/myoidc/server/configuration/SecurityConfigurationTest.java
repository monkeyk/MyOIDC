package myoidc.server.configuration;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

/**
 * 2018/3/22
 *
 * @author Shengzhao Li
 */
public class SecurityConfigurationTest {
    @Test
    public void passwordEncoder() throws Exception {


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String admin = passwordEncoder.encode("admin");

        assertNotNull(admin);

//        System.out.println("admin --> " + admin);

    }

}