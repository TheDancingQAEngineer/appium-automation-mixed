package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

abstract public class CoreTestCase extends TestCase {

    protected RemoteWebDriver driver;

    protected static final String WIKIPEDIA_MW_HOMEPAGE = "https://en.m.wikipedia.org";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        this.skipIOSWelcomePage();
        this.openWikiWebPageForMobileWeb();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();

        super.tearDown();
    }

    private void skipIOSWelcomePage()
    {
        if(Platform.getInstance().isIOS()) {
            WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
            WelcomePageObject.clickSkip();
        }
    }

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

    protected void sendAppToBackground(Duration duration) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(duration);
        } else {
            System.out.println("driver.runAppInBackground() not implemented on platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    protected void openWikiWebPageForMobileWeb()
    {
        if (Platform.getInstance().isMW()) {
            driver.get(WIKIPEDIA_MW_HOMEPAGE);
        } else {
            System.out.println("openWikiWebPageForMobileWeb() does nothing on platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }
}
