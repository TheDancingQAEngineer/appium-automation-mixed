import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class TestFindingTextInElements {

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
    public void testTextInTheNews()         // This test passes.
    {
        WebElement element =  waitForElementVisible(
                By.xpath("//*[contains(@text, 'In the news')]"),
                "Cannot locate 'In the news'",
                5
        );

        assertElementHasText(
                element,
                "news",
                "No 'news' in located element");
    }

    @Test
    public void testTextFeaturedArticle()       // This test fails by design.
    {
        WebElement element =  waitForElementVisible(
                By.xpath("//*[contains(@text, 'Featured')]"),
                "Cannot locate 'In the news'");

        assertElementHasText(
                element,
                "news",
                "No 'news' in located element");
    }

    @Test
    public void testTextInElementWithoutText()  // This test fails by design too.
    {
        WebElement element =  waitForElementVisible(
                By.id("org.wikipedia:id/single_fragment_toolbar_wordmark"),
                "Cannot locate id 'single_fragment_toolbar_wordmark'");

        assertElementHasText(
                element,
                "news",
                "No 'news' in located element");
    }

    private void assertElementHasText(WebElement element, String text, String error_message)
    {
        /* It appears that all WebElements have attribute 'text', even if it's empty.
         * Thus, we're not doing the try-catch here.
         */
        String element_text = element.getAttribute("text");

        Assert.assertTrue(error_message,
                element_text.contains(text));
    }

    private WebElement waitForElementVisible(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    // I like the concept of overloading :)
    private WebElement waitForElementVisible(By by, String error_message)
    {
        return waitForElementVisible(by, error_message, 5);
    }
}
