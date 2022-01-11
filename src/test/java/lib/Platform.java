package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {

    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";
    private static final String PATH_TO_CHROMEDRIVER = "***REMOVED***/chromedriver97/chromedriver";
    private static final String MW_USERAGENT = "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
    private static final String PLATFORM_ENV_VAR_NAME = "UI_TESTS_PLATFORM";
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_MOBILE_WEB = "mobileweb";

    private static Platform instance;
    private Platform() {}

    public static Platform getInstance()
    {
        if (instance == null) {
            instance = new Platform();
        }

        return instance;
    }

    @Step("Instantiate platform-specific driver")
    public RemoteWebDriver getDriver() throws Exception
    {
        URL url = new URL(APPIUM_URL);
        String error_message = String.format(
                "Cannot initialize Appium Driver for platform: \"%s\"",
                this.getPlatformVar());

        if (this.isAndroid()) {
            return new AndroidDriver(url, this.getAndroidDesiredCapabilities());
        } else if (this.isIOS()) {
            return new IOSDriver(url, this.getIOSDesiredCapabilities());
        } else if (this.isMW()) {
            return new ChromeDriver(this.getMWChromeOptions());
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

    public boolean isMW()
    {
        return isPlatform(PLATFORM_MOBILE_WEB);
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
        capabilities.setCapability("orientation", "PORTRAIT");

        return capabilities;
    }

    private ChromeOptions getMWChromeOptions()
    {
        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobile_emulation = new HashMap<String, Object>();
        mobile_emulation.put("deviceMetrics", deviceMetrics);
        mobile_emulation.put("userAgent", MW_USERAGENT);

        ChromeOptions chrome_options = new ChromeOptions();
        chrome_options.addArguments("window-size=360,640");
        chrome_options.setExperimentalOption("mobileEmulation", mobile_emulation);
        System.setProperty("webdriver.chrome.driver", PATH_TO_CHROMEDRIVER);

        return chrome_options;
    }

    public String getPlatformVar()
    {
        return System.getenv(PLATFORM_ENV_VAR_NAME);
    }

    private boolean isPlatform(String my_platform)
    {
        String env_platform = getPlatformVar();
        return my_platform.equals(env_platform);
    }
}
