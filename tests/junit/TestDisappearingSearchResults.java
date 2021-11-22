import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class TestDisappearingSearchResults {

    private AppiumDriver driver;

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
    public void testSearchResultsAppearAndDisappear()   // This test passes.
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot locate 'Search Wikipedia'");

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Hungary",
                "Cannot locate search input.",
                15);

        String xpath_to_search =
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']"
                        + "//*[contains(@text, 'Hungary')]";

        // This only passes if more than 1 item is on the screen.
        // Compare with testOverlySpecificQueryThatFails()
        waitForElementsVisible(
                By.xpath(xpath_to_search),
                "Cannot locate requested item",
                1,
                15);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot locate 'X' button to cancel search.",
                5);

        waitForElementNotPresent(
                By.xpath(xpath_to_search),
                "Search elements still visible.",
                10
        );
    }

    @Test
    public void testOverlySpecificQueryThatFails()   // This test fails by design.
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot locate 'Search Wikipedia'");

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Yo La Tengo discography",
                "Cannot locate search input.",
                15);

        String xpath_to_search =
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']"
                + "//*[contains(@text, 'Yo La Tengo discography')]";

        waitForElementsVisible(
                By.xpath(xpath_to_search),
                "Cannot locate requested item",
                1,
                15);
    }

    private WebElement waitForElementVisible(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private List<WebElement> waitForElementsVisible(By by, String error_message,
                                                    int count_more_than,
                                                    long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(by, count_more_than)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndClick(By by, String error_message)
    {
        return waitForElementAndClick(by, error_message, 5);
    }

    private WebElement waitForElementAndSendKeys(By by, String keys,
                                                 String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(by, error_message, timeoutInSeconds);
        element.sendKeys(keys);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
}
