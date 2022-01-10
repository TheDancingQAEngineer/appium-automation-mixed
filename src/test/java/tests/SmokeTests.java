package tests;

import junit.framework.AssertionFailedError;
import lib.CoreTestCase;
import org.junit.*;

public class SmokeTests extends CoreTestCase {

    @Test
    public void testSmoke()
    {
        System.out.println("I am testSmoke(). I ran!");
    }

    @Test
    public void testExpectedFailure()
    {
        try {
            System.out.println("I am testExpectedFailure(). I only pass if I throw AssertionFailedError().");
            Assert.fail("shouldn't pass.");
        } catch (AssertionError e) {
            Assert.assertTrue(e.getMessage().contains("shouldn't pass"));
        }
    }
}