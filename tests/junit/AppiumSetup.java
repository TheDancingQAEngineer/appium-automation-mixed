import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AppiumSetup {

    public static AppiumDriver setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","andro80");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","UiAutomator2");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","***REMOVED***/appium-automation-mixed/appium-automation-mixed/apks/org.wikipedia.apk");
        capabilities.setCapability("appWaitDuration", 40000);
        capabilities.setCapability("orientation", "PORTRAIT");

        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        return driver;
    }

    public static void tearDown(AppiumDriver driver)
    {
        driver.quit();
    }
}