import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class TestTextInElements {

    private AppiumDriver driver;

    public WebElement waitForWebElementVisibleByXpath(String xpath, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        By by = By.xpath(xpath);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","andro80");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","***REMOVED***/appium-automation-mixed/appium-automation-mixed/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void testFindSearchFieldAndClick()
    {
        // GIVEN:
        // - running emulator
        // - running Appium Server

        // WHEN: On app home screen

        // THEN:
        // - there's an element with text 'Search Wikipedia';
        // - the element is clickable
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
        element_to_init_search.click();
    }

    @Test
    public void testSendTextToSearchField()
    {
        // GIVEN:
        // - running emulator
        // - running Appium Server

        // WHEN: On search screen
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
        element_to_init_search.click();

        // THEN: Search field accepts text
        WebElement element_to_type_search_query = waitForWebElementVisibleByXpath(
                "//*[contains(@text, 'Search…')]",
                "Cannot locate requested element",
                5);
        // WebElement element_to_type_search_query = driver.findElementByXPath("//*[contains(@text, 'Search…')]");
        element_to_type_search_query.sendKeys("Yo La Tengo Discography");
    }
}
