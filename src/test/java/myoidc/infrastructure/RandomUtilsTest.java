package myoidc.infrastructure;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

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