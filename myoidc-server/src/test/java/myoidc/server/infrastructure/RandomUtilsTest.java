package myoidc.server.infrastructure;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Shengzhao Li
 */
public class RandomUtilsTest {

    @Test
    public void testRandomText() throws Exception {

        final String random = RandomUtils.randomText();
        assertNotNull(random);
        assertEquals(random.length(), 32);
//        System.out.println(random);

    }

    @Test
    public void testRandomNumber() throws Exception {

        final String number = RandomUtils.randomNumber();
        assertNotNull(number);
        assertEquals(number.length(), 32);
//        System.out.println(number);

    }
}