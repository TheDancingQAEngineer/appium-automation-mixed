package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {

    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_IOS = "ios";
    protected AppiumDriver driver;


    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformFromEnv();
        driver = this.getDriverByPlatformFromEnv(APPIUM_URL, capabilities);
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();

        super.tearDown();
    }

    private DesiredCapabilities getCapabilitiesByPlatformFromEnv() throws Exception
    {
        String platform = System.getenv("UI_TESTS_PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String error_message = String.format(
                "Wrong platform name or tests not implemented for platform: \"%s\"",
                platform);

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName","Android");
            capabilities.setCapability("deviceName","andro80");
            capabilities.setCapability("platformVersion","8.0");
            capabilities.setCapability("automationName","UiAutomator2");
            capabilities.setCapability("appPackage","org.wikipedia");
            capabilities.setCapability("appActivity",".main.MainActivity");
            capabilities.setCapability("app","/Users/user2/codebase/github/appium-automation-mixed/appium-automation-mixed/apks/org.wikipedia.apk");
            capabilities.setCapability("appWaitDuration", 40000);
            capabilities.setCapability("orientation", "PORTRAIT");
        } else if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformName","iOS");
            capabilities.setCapability("deviceName","iPhone 8");
            capabilities.setCapability("platformVersion","13.7");
            capabilities.setCapability("automationName","XCUITest");
            capabilities.setCapability("app","/Users/user2/codebase/github/appium-automation-mixed/appium-automation-mixed/ios-apps/Wikipedia.app");
        } else {
            throw new Exception(error_message);
        }

        return capabilities;
    }

    private AppiumDriver getDriverByPlatformFromEnv(
            String appium_url,
            DesiredCapabilities capabilities
    ) throws Exception
    {
        String platform = System.getenv("UI_TESTS_PLATFORM");
        String error_message = String.format(
                "Cannot initialize Appium Driver for platform: \"%s\"",
                platform);
        AppiumDriver driver;

        if (platform.equals(PLATFORM_ANDROID)) {
            driver = new AndroidDriver(new URL(appium_url), capabilities);
        } else if (platform.equals(PLATFORM_IOS)) {
            driver = new IOSDriver(new URL(appium_url), capabilities);
        } else {
            throw new Exception(error_message);
        }

        return driver;
    }
}
