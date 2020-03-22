package myoidc.server.infrastructure;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 2020/3/22
 *
 * @author Shengzhao Li
 */
public class PasswordHandlerTest {


    @Test
    public void randomPassword() {

        String s = PasswordHandler.randomPassword();
        assertNotNull(s);
        assertEquals(s.length(), 12);
        System.out.println(s);

    }

}