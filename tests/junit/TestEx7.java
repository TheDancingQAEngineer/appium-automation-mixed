import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

public class TestEx7 {
    private static AppiumDriver driver;

    /**
     * A simple hack to reset screen rotation.
     * If necessary for all tests, this can be called in @Before method,
     * but NOT in @BeforeClass (;
     *
     * Run the whole class (NOT individual tests) to see in action.
     */
    private void setScreenOrientationPortrait() {
        if (driver.getOrientation() != ScreenOrientation.PORTRAIT) {
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("Initializing Appium Driver...");
        driver = AppiumSetup.setUp();
    }

    @AfterClass
    public static void tearDownClass()
    {
        System.out.println("Tearing down Appium Driver...");
        AppiumSetup.tearDown(driver);
        System.out.println("Done.");
    }

    @Test
    public void test01ThatGoesLandscapeAndDeliberatelyFails()
    {
        System.out.println("Running test 1...");
        // check that we're on start screen
        UiHelpers.waitForElementVisible(driver,
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot locate 'Search Wikipedia'. Maybe we're not on start screen",
                5);

        // flip screen
        driver.rotate(ScreenOrientation.LANDSCAPE);

        // fail by design
        Assert.assertTrue(false);
    }

    @Test
    public void test02ThatDependsOnPortraitModeAndFailsWithoutIt()
    {
        System.out.println("Running test 2...");

        // check that we're on start screen
        UiHelpers.waitForElementVisible(driver,
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot locate 'Search Wikipedia'. Maybe we're not on start screen",
                5);

        // try to find something that's only visible in portrait mode
        searchForSomethingThatIsOnlyVisibleInPortraitMode();
    }

    @Test
    public void test03ThatChecksScreenOrientationAndFlipsIfNecessary()
    {
        System.out.println("Running test 3...");

        // check if screen is in portrait and flip if necessary
        setScreenOrientationPortrait();

        // check that we're on start screen
        UiHelpers.waitForElementVisible(driver,
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot locate 'Search Wikipedia'. Maybe we're not on start screen",
                5);

        // search for the same thing as in test 2
        searchForSomethingThatIsOnlyVisibleInPortraitMode();

    }

    private void searchForSomethingThatIsOnlyVisibleInPortraitMode()
    {
        UiHelpers.waitForElementVisible(driver,
                By.id("org.wikipedia:id/view_featured_article_card_header"),
                "Featured article card not visible.",
                10);
    }
}
