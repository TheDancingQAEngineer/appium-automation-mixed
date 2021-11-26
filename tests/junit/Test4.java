import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test4 {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        driver = AppiumSetup.setUp();
    }

    @After
    public void tearDown()
    {
        AppiumSetup.tearDown(driver);
    }

    @Test
    public void testSwipeArticle()
    {
        // GIVEN:
        // - running emulator
        // - running Appium Server
        // - app is on search screen
        // - cursor in search field
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot locate requested element",
                5);

        // WHEN:
        // - we type "Java" into search field,
        // - click the result and wait for page to load
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot locate requested element",
                5);

        String xpath_to_search =
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']"
                        + "//*[@text='Object-oriented programming language']";

        waitForElementAndClick(
                By.xpath(xpath_to_search),
                "Cannot locate requested element",
                15);

        // THEN: Page title matches our search
        // org.wikipedia:id/view_page_title_text
        WebElement title_element =  waitForElementVisible(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title.",
                15);

        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);
    }

    @Test
    public void testSwipeTillElementFound()
    {
        // GIVEN:
        // - running emulator
        // - running Appium Server
        // - app is on search screen
        // - cursor in search field
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot locate requested element",
                5);

        // WHEN:
        // - we type "Java" into search field,
        // - click the result and wait for page to load
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot locate requested element",
                10);

        String xpath_to_search =
                "//*[@resource-id='org.wikipedia:id/page_list_item_title']"
                        + "[@text='Appium']";

        waitForElementAndClick(
                By.xpath(xpath_to_search),
                "Cannot locate requested element",
                10);

        // THEN: Page title matches our search
        // org.wikipedia:id/view_page_title_text
        WebElement title_element =  waitForElementVisible(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title.",
                15);

        swipeUpTillElement(
                By.xpath("//*[@text='View page in browser']"),
                "Can't find text 'View page in browser'",
                20);
    }

    private WebElement waitForElementVisible(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String keys,
                                                 String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(by, error_message, timeoutInSeconds);
        element.sendKeys(keys);
        return element;
    }

    protected void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = (int) (size.width / 2);
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform();
    }

    protected void swipeUpQuick()
    {
        swipeUp(200);
    }

    protected void swipeUpTillElement(By by, String error_message, int max_swipes)
    {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped >= max_swipes) {
                waitForElementVisible(by,
                        "Cannot find element by swiping up. \n" + error_message,
                        0);
                return;
            }

            swipeUpQuick();
            already_swiped++;
        }
    }
}