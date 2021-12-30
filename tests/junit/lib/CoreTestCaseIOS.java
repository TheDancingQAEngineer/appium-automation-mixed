package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCaseIOS extends TestCase {

    protected AppiumDriver driver;

    private static final String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName","iPhone 8");
        capabilities.setCapability("platformVersion","13.7");
        capabilities.setCapability("automationName","XCUITest");
        capabilities.setCapability("app","***REMOVED***/appium-automation-mixed/appium-automation-mixed/ios-apps/Wikipedia.app");

        driver = new IOSDriver(new URL(AppiumURL), capabilities);
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();

        super.tearDown();
    }
}
