package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;

public class CoreTestCase extends TestCase {

    protected Platform Platform;
    protected AppiumDriver driver;


    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        this.Platform = new Platform();
        driver = this.Platform.getDriver();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();

        super.tearDown();
    }
}
