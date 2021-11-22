import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class TestTextInElements {

    private AppiumDriver driver;

    /* Part 8: Still more refactoring. */
    private WebElement waitForWebElementVisible(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    /* Part 8: Still more refactoring. */
    private WebElement waitForWebElementVisibleByXpathRefactored(String xpath, String error_message, long timeoutInSeconds)
    {
        By by = By.xpath(xpath);
        return waitForWebElementVisible(by, error_message, timeoutInSeconds);
    }

    private WebElement waitForWebElementVisibleByXpath(String xpath, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        By by = By.xpath(xpath);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    /* Part 4: An example of method overloading. */
    private WebElement waitForWebElementVisibleByXpath(String xpath, String error_message)
    {
        return waitForWebElementVisibleByXpath(xpath, error_message, 5);
    }

    private WebElement waitForWebElementVisibleById(String id, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        By by = By.id(id);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForWebElementVisibleById(String id, String error_message)
    {
        return waitForWebElementVisibleById(id, error_message, 5);
    }

    /* Part 6: More refactoring. */
    private WebElement waitForElementByXpathAndClick(String xpath, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForWebElementVisibleByXpath(xpath, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    /* Part 6: More refactoring. */
    private WebElement waitForElementByXpathAndSendKeys(String xpath, String keys,
                                                        String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForWebElementVisibleByXpath(xpath, error_message, timeoutInSeconds);
        element.sendKeys(keys);
        return element;
    }

    private WebElement waitForElementByIdAndClick(String id, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForWebElementVisibleById(id, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    /* Part 6: More refactoring. */
    private WebElement waitForElementByIdAndSendKeys(String id, String keys,
                                                        String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForWebElementVisibleById(id, error_message, timeoutInSeconds);
        element.sendKeys(keys);
        return element;
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
        capabilities.setCapability("app","***REMOVED***/codebase/github/appium-automation-mixed/appium-automation-mixed/apks/org.wikipedia.apk");

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

        // This is explicit reference to findElement*().
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
        element_to_init_search.click();

        // THEN: Search field accepts text

        // This is a custom method with wait().
        // Note that we're using an overloaded method.
        WebElement element_to_type_search_query = waitForWebElementVisibleByXpath(
                "//*[contains(@text, 'Search…')]",
                "Cannot locate requested element");

        element_to_type_search_query.sendKeys("Yo La Tengo Discography");
    }

    @Test
    /* Part 5. Compound xPath */
    public void testSearchForJavaTheProgrammingLanguage() {
        // GIVEN:
        // - running emulator
        // - running Appium Server
        // - app is on search screen
        // - cursor in search field
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
        element_to_init_search.click();
        WebElement element_to_type_search_query = waitForWebElementVisibleByXpath(
                "//*[contains(@text, 'Search…')]",
                "Cannot locate requested element");

        // WHEN: we type "Java" into search field
        element_to_type_search_query.sendKeys("Java");

        // THEN: at least one search result contains text "Programming Language"
        String xpath_to_search =
                "//*[@resource_id='org.wikipedia:id/page_list_item_container']"
                        + "//*[@text='Object-Oriented Programming Language']";

        WebElement element_expected = waitForWebElementVisibleByXpath(
                xpath_to_search,
                "Cannot locate requested element.",
                15
        );
    }

    @Test
    public void testSearchForJavaRefactored() {
        // GIVEN:
        // - running emulator
        // - running Appium Server
        // - app is on search screen
        // - cursor in search field
        waitForElementByXpathAndClick("//*[contains(@text, 'Search Wikipedia')]",
                "Cannot locate requested element",
                5);

        // WHEN: we type "Java" into search field
        waitForElementByXpathAndSendKeys(
                "//*[contains(@text, 'Search…')]",
                "Java",
                "Cannot locate requested element",
                5);

        // THEN: at least one search result contains text "Programming Language"
        String xpath_to_search =
                "//*[@resource_id='org.wikipedia:id/page_list_item_container']"
                        + "//*[@text='Object-Oriented Programming Language']";

        waitForWebElementVisibleByXpath(
                xpath_to_search,
                "Cannot locate requested element.",
                15
        );

    }

    /* Part 7: Search cancel. */
    @Test
    public void testSearchCancel()
    {
        // GIVEN:
        // - running emulator
        // - running Appium Server
        // - app is on search screen
        // - cursor in search field
        // - search query typed in

        waitForElementByIdAndClick("org.wikipedia:id/search-container",
                "Cannot locate search container.",
                5);

        // WHEN: we click X to cancel search...
        waitForElementByIdAndClick("org.wikipedia:id/search_close_btn",
                "Cannot locate 'X' button to cancel search.",
                5);

        // THEN: the X is no longer visible (though it says nothing of functionality)
        waitForElementNotPresentById("org.wikipedia:id/search_close_btn",
                "'X' button did not disappear.",
                5);

    }

    private boolean waitForElementNotPresentById(String id, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        By by = By.id(id);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
}
