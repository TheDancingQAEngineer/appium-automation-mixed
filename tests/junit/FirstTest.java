import io.appium.java_client.AppiumDriver;
import org.junit.*;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        driver = AppiumSetup.setUp();
    }

    @Test
    public void firstTest()
    {
        System.out.println("I am firstTest(). I ran!");
    }

    @After
    public void tearDown()
    {
        AppiumSetup.tearDown(driver);
    }
}