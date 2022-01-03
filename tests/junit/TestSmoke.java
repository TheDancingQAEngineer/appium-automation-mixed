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
            System.out.println("I am testExpectedFailure(). If I passed, something is off.");
            fail("shouldn't pass.");
        } catch (Exception e) {
            // expected
        }

    }
}