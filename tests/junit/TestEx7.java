import io.appium.java_client.AppiumDriver;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

public class TestEx7 {

    private static AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = AppiumSetup.setUp();
        System.out.println("ScreenOrientation after setUp(): "
                + driver.getOrientation().toString());
    }

    @After
    public void tearDown()
    {
        System.out.println("ScreenOrientation before tearDown(): "
                + driver.getOrientation().toString());
        AppiumSetup.tearDown(driver);
    }

    @Test
    public void testThatFlipsScreenAndDoesntCleanUpAfterItself()
    {
        // check that we're on start screen
        UiHelpers.waitForElementVisible(driver,
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot locate 'Search Wikipedia'. Maybe we're not on start screen",
                5);

        // try to find something that's only visible in portrait mode
        WebElement featured_article_card = searchForSomethingThatIsOnlyVisibleInPortraitMode();

        // flip screen
        driver.rotate(ScreenOrientation.LANDSCAPE);

        // Check that featured article card disappears, so that we know
        // that the test would fail if we started it with device in LANDSCAPE mode.
        Assert.assertTrue(
                UiHelpers.waitForElementInvisible(driver, featured_article_card,
                        "Featured article card visible after screen flip.", 15)
        );
    }

    private WebElement searchForSomethingThatIsOnlyVisibleInPortraitMode()
    {
        return UiHelpers.waitForElementVisible(driver,
                By.id("org.wikipedia:id/view_featured_article_card_header"),
                "Featured article card not visible.",
                10);
    }
}
