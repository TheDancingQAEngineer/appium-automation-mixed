import io.appium.java_client.AppiumDriver;
import org.junit.*;
import org.openqa.selenium.*;

public class TestEx5 {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        driver = AppiumSetup.setUp();

        // Reset screen rotation
        if (driver.getOrientation() != ScreenOrientation.PORTRAIT) {
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    @After
    public void tearDown()
    {
        AppiumSetup.tearDown(driver);
    }

    @Test
    public void testAddTwoArticlesToReadingListAndRemoveOne()
    {
        /* Написать тест, который:
        1. Сохраняет две статьи в одну папку
        2. Удаляет одну из статей
        3. Убеждается, что вторая осталась
        4. Переходит в неё и убеждается, что title совпадает */

        String search_query_1 = "Java";
        String expected_header_1 = "Java (programming language)";

        String search_query_2 = "Python";
        String expected_header_2 = "Python (programming language)";

        String reading_list_name = "Programming languages";


        String xpath_for_add_to_list = "//*[@resource-id='org.wikipedia:id/title']"
                + "[@text='Add to reading list']";

        String xpath_of_reading_list = "//*[@resource-id='org.wikipedia:id/list_of_lists']"
                + "//*[@resource-id='org.wikipedia:id/item_title']"
                + String.format("[@text='%s']", reading_list_name);

        // Launch app, go to search screen
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot locate search field.",
                5);

        // Send search query
        UiHelpers.waitForElementVisibleAndSendKeys(driver,
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_query_1,
                String.format("Cannot type %s to search.", search_query_1),
                10);

        // Go to article
        UiHelpers.waitForElementVisibleAndClick(driver,
                makeLocatorFromArticleTitle(expected_header_1),
                String.format("Cannot find '%s' in search results.", expected_header_1),
                10);

        // Tap "Three dots"
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath("//*[@resource-id='org.wikipedia:id/page_toolbar']" +
                        "//*[@content-desc='More options']"),
                "Cannot locate three dots. (1)",
                10);

        // Tap "Add to reading list"
        UiHelpers.waitForElementClickableAndClick(driver,
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' menu item (1).",
                10);

        // + Create new
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find onboarding button.",
                10);

        UiHelpers.waitForElementVisibleAndClear(driver,
                By.id("org.wikipedia:id/text_input"),
                "Cannot clear input in reading list name.",
                5);

        // Enter list name
        UiHelpers.waitForElementPresentAndSendKeys(driver,
                By.id("org.wikipedia:id/text_input"),
                reading_list_name,
                "Cannot send keys to text input.",
                10);

        // Tap 'OK'
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath("//*[@text='OK']"),
                "Cannot find OK button.",
                10);

        // Close article
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot locate 'X' to close article.",
                15);

        // This kicks us back to home screen, so we
        //  initiate new search
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot locate search field.",
                5);

        // Send search query
        UiHelpers.waitForElementVisibleAndSendKeys(driver,
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_query_2,
                String.format("Cannot type %s to search.", search_query_2),
                10);

        // Go to article
        UiHelpers.waitForElementVisibleAndClick(driver,
                makeLocatorFromArticleTitle(expected_header_2),
                String.format("Cannot find '%s' in search results.", expected_header_2),
                10);

        // Tap "Three dots"
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath("//*[@resource-id='org.wikipedia:id/page_toolbar']" +
                        "//*[@content-desc='More options']"),
                "Cannot locate three dots. (2)",
                10);

        // Tap "Add to reading list"
        UiHelpers.waitForElementClickableAndClick(driver,
                By.xpath(xpath_for_add_to_list),
                "Cannot find 'Add to reading list' menu item. (2)",
                10);

        // Get list by name
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath(xpath_of_reading_list),
                "Cannot find list to add second article.",
                10);

        // Check that list of lists is gone from screen
        UiHelpers.waitForElementNotPresent(driver,
                By.xpath(xpath_of_reading_list),
                "List of lists didn't disappear after tap.",
                5);

        // Close article 2
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot locate 'X' to close article.",
                15);

        // Tap 'My lists'
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot locate 'My Lists' button.",
                15);

        // Tap the list previously created
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath(
                        String.format("//*[@text='%s']", reading_list_name)),
                String.format("Cannot find '%s' in My lists.", reading_list_name),
                15);

        // Assert both articles are there
        UiHelpers.waitForElementVisible(driver,
                By.xpath(String.format("//*[@text='%s']", expected_header_1)),
                String.format("Text '%s' not visible after timeout.", expected_header_1),
                15);

        UiHelpers.waitForElementVisible(driver,
                By.xpath(String.format("//*[@text='%s']", expected_header_2)),
                String.format("Text '%s' not visible after timeout.", expected_header_2),
                5);

        // Remove one by swiping
        UiHelpers.swipeElementToLeft(driver,
                By.xpath(String.format("//*[@text='%s']", expected_header_1)),
                "Swipe to left failed.");

        // Assert one article is removed AND the other one is still there
        UiHelpers.waitForElementNotPresent(driver,
                By.xpath(String.format("//*[@text='%s']", expected_header_1)),
                "Swiped, but the article is still there.",
                5);

        WebElement remaining_article =
                UiHelpers.waitForElementPresent(driver,
                By.xpath(String.format("//*[@text='%s']", expected_header_2)),
                String.format("Text '%s' not visible after timeout.", expected_header_2),
                5);

        // Go to article from list
        remaining_article.click();

        // Check that we got to article
        WebElement article_header =
                UiHelpers.waitForElementVisible(driver,
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not visible after timeout.",
                15);

        String article_title_text = article_header.getText();

        // Assert title match
        Assert.assertEquals(
                String.format("Expected article title %s, got %s.",
                        expected_header_2, article_title_text),
                expected_header_2,
                article_title_text
        );
    }

    private By makeLocatorFromArticleTitle(String title)
    {
        return By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"
                + String.format("[@text='%s']", title));
    }
}
