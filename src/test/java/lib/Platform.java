package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Platform {

    // TODO: move to env or config
    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";
    private static final String CHROMEDRIVER_ENV_VAR_NAME = "PATH_TO_CHROMEDRIVER";
    private static final String GECKODRIVER_ENV_VAR_NAME = "PATH_TO_GECKODRIVER";
    // TODO: move to env or config
    private static final String MW_USERAGENT = "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
    private static final String PLATFORM_ENV_VAR_NAME = "UI_TESTS_PLATFORM";
    // TODO: how about converting to enum?
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_MOBILE_WEB_CHROME = "mw-chrome";
    private static final String PLATFORM_MOBILE_WEB_FIREFOX = "mw-firefox";

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
        } else if (this.isMWChrome()) {
            return new ChromeDriver(this.getMWChromeOptions());
        } else if (this.isMWFirefox()) {
            return new FirefoxDriver(this.getMWFirefoxOptions());
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
        return (isMWChrome() || isMWFirefox());
    }

    public boolean isMWChrome()
    {
        return isPlatform(PLATFORM_MOBILE_WEB_CHROME);
    }

    public boolean isMWFirefox()
    {
        return isPlatform(PLATFORM_MOBILE_WEB_FIREFOX);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // TODO: Convert ALL capabilities to external config
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
        
        // TODO: Convert ALL capabilities to external config
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
        System.setProperty("webdriver.chrome.driver", this.getChromedriverPathFromEnv());

        return chrome_options;
    }

    private FirefoxOptions getMWFirefoxOptions() {

        List<String> width = Arrays.asList("-width", "360");
        List<String> height = Arrays.asList("-height", "640");

        ArrayList<String> deviceMetrics = new ArrayList<String>();
        deviceMetrics.addAll(width);
        deviceMetrics.addAll(height);

        String preferenceToEnableChromeDevtools = "devtools.chrome.enabled";

        FirefoxOptions firefox_options = new FirefoxOptions();
        firefox_options.addArguments(deviceMetrics);
        firefox_options.addPreference(preferenceToEnableChromeDevtools, true);
        System.setProperty("webdriver.gecko.driver", this.getGeckodriverPathFromEnv());        
        return firefox_options;
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

    private String getChromedriverPathFromEnv()
    {
        return System.getenv(CHROMEDRIVER_ENV_VAR_NAME);
    }
    
    private String getGeckodriverPathFromEnv() {
        return System.getenv(GECKODRIVER_ENV_VAR_NAME);
    }
}
