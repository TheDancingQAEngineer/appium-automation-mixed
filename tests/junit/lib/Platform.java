package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {

    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";
    private static final String PLATFORM_ENV_VAR_NAME = "UI_TESTS_PLATFORM";
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_IOS = "ios";

    private static Platform instance;
    private Platform() {}

    public static Platform getInstance()
    {
        if (instance == null) {
            instance = new Platform();
        }

        return instance;
    }

    public AppiumDriver getDriver() throws Exception
    {
        URL url = new URL(APPIUM_URL);
        String error_message = String.format(
                "Cannot initialize Appium Driver for platform: \"%s\"",
                this.getPlatformVar());

        if (this.isAndroid()) {
            return new AndroidDriver(url, this.getAndroidDesiredCapabilities());
        } else if (this.isIOS()) {
            return new IOSDriver(url, this.getIOSDesiredCapabilities());
        } else {
            throw new Exception(error_message);
        }
    }

    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()
    {
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities()
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

        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName","iPhone 8");
        capabilities.setCapability("platformVersion","13.7");
        capabilities.setCapability("automationName","XCUITest");
        capabilities.setCapability("app","***REMOVED***/appium-automation-mixed/appium-automation-mixed/ios-apps/Wikipedia.app");

        return capabilities;
    }

    private String getPlatformVar()
    {
        return System.getenv(PLATFORM_ENV_VAR_NAME);
    }

    private boolean isPlatform(String my_platform)
    {
        String env_platform = getPlatformVar();
        return my_platform.equals(env_platform);
    }
}
