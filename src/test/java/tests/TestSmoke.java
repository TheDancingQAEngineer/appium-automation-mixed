package tests;

import junit.framework.AssertionFailedError;
import lib.CoreTestCase;
import org.junit.*;

public class TestSmoke extends CoreTestCase {

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
            fail("shouldn't pass.");
        } catch (AssertionFailedError e) {
            Assert.assertTrue(e.getMessage().contains("shouldn't pass"));
        }
    }
}