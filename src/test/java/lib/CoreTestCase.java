package lib;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lib.ui.WelcomePageObject;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Properties;

abstract public class CoreTestCase {

    // TODO: move to env or config
    private static final String ALLURE_ENVIRONMENT_DOC_LINK = "https://docs.qameta.io/allure/#_environment";
    protected RemoteWebDriver driver;
    
    // TODO: move to env or config
    protected String sutMwHomepage;

    @Before
    @Step("Launch test and get to starting page")
    public void setUp() throws Exception
    {
        driver = Platform.getInstance().getDriver();
        this.createAllurePropertyFile();
        this.rotateScreenPortrait();
        this.skipIOSWelcomePage();
        this.openWebPageForMobileWeb();
    }

    @After
    @Step("Terminate session and quit driver")
    public void tearDown() {
        driver.quit();
    }

    @Step("Skip Welcome page on iOS (does nothing on Android and MobileWeb)")
    private void skipIOSWelcomePage()
    {
        if(Platform.getInstance().isIOS()) {
            WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
            WelcomePageObject.clickSkip();
        }
    }

    @Step("Rotate device to portrait (does nothing for MobileWeb)")
    protected void rotateScreenPortrait()
    {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            System.out.println("driver.rotate() not implemented on platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Rotate device to landscape (does nothing for MobileWeb)")
    protected void rotateScreenLandscape()
    {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        } else {
            System.out.println("driver.rotate() not implemented on platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Send app to background (does nothing for MobileWeb)")
    protected void sendAppToBackground(Duration duration) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(duration);
        } else {
            System.out.println("driver.runAppInBackground() not implemented on platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Open MobileWeb Wikipedia page (does nothing on Android and iOS)")
    protected void openWebPageForMobileWeb()
    {
        if (Platform.getInstance().isMW()) {
            driver.get(sutMwHomepage);
        } else {
            System.out.println("openWebPageForMobileWeb() does nothing on platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Create Allure 'properties' file")
    private void createAllurePropertyFile()
    {
        String path = System.getProperty("allure.results.directory");
        try {
            Properties props = new Properties();
            FileOutputStream fos = new FileOutputStream(path + "/environment.properties");
            props.setProperty("Environment", Platform.getInstance().getPlatformVar());
            props.store(fos, "See " + ALLURE_ENVIRONMENT_DOC_LINK);
            fos.close();
        } catch (Exception e) {
            System.err.println("IO problem when writing Allure properties file.");
            e.printStackTrace();
        }
    }

    @Step("Skip test if on MobileWeb")
    protected void skipTestIfMW()
    {
        Assume.assumeFalse(Platform.getInstance().isMW());
    }
}
