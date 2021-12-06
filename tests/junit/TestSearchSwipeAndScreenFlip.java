import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.*;
import org.openqa.selenium.*;

public class TestSearchSwipeAndScreenFlip extends CoreTestCase {

    protected lib.ui.MainPageObject MainPageObject;
    protected SearchPageObject SearchPageObject;
    protected ArticlePageObject ArticlePageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.MainPageObject = new MainPageObject(driver);
        this.SearchPageObject = new SearchPageObject(driver);
        this.ArticlePageObject = new ArticlePageObject(driver);
    }

    @Test
    public void testSwipeArticle()
    {
        // DONE: TODO: Refactor during section 5

        String search_query = "Appium";
        String expected_substring = "Appium";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);

        SearchPageObject.clickOnArticleWithSubstring(expected_substring);
        ArticlePageObject.waitForTitleElement();

        ArticlePageObject.swipeUp(2000);
        ArticlePageObject.swipeUp(2000);
        ArticlePageObject.swipeUp(2000);
        ArticlePageObject.swipeUp(2000);
        ArticlePageObject.swipeUp(2000);
    }

    /* Lesson 4, Parts 1-2. Swipes */
    @Test
    public void testSwipeTillElementFound()
    {
        // DONE: TODO: Refactor during section 5

        String search_query = "Appium";
        String expected_substring = "Appium";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);
        SearchPageObject.clickOnArticleWithSubstring(expected_substring);

        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    /* Lesson 4, Parts 3-4. Long test */
    @Test
    public void testAddToReadingListAndDelete()
    {
        String reading_list_title = "Learning programming";
        String search_query = "Java";
        String article_title = "Java (programming language)";

        // Launch app, enter search mode
        SearchPageObject.initSearchInput();

        // Search "Java"
        SearchPageObject.typeSearchLine(search_query);

        // Go to article
        SearchPageObject.clickOnArticleWithSubstring(article_title);

        // Hit "three dots"
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot locate three dots.",
                20);

        // Add to reading list
        UiHelpers.waitForElementClickableAndClick(driver,
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' menu item.",
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
        UiHelpers.waitForElementVisibleAndSendKeys(driver,
                By.id("org.wikipedia:id/text_input"),
                reading_list_title,
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

        // Tap 'My lists'
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot locate 'My Lists' button.",
                15);

        // Tap the list previously created
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath(
                        String.format("//*[@text='%s']", reading_list_title)),
                String.format("Cannot find '%s' in My lists.", reading_list_title),
                15);

        // Assert the article is there
        // Remove by swiping
        UiHelpers.swipeElementToLeft(driver,
                By.xpath(
                        String.format("//*[@text='%s']", article_title)),
                String.format(
                        "Swipe to left failed. Cannot find '%s' in reading list.",
                        article_title));

        // Assert article is removed
        UiHelpers.waitForElementNotPresent(driver,
                By.xpath(String.format("//*[@text='%s']", article_title)),
                "Swiped, but article still present",
                5);
    }

    /* Lesson 4, Part 5. Asserts. */
    @Test
    public void testAmountOfNotEmptySearch()
    {
        String search_line = "Yo La Tengo discography";
        // Open app
        // Tap 'Search'
        SearchPageObject.initSearchInput();

        // Send query with at least one likely match
        UiHelpers.waitForElementVisibleAndSendKeys(driver,
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                String.format("Cannot type '%s' to search.", search_line),
                10);


        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_container']" +
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']";

        UiHelpers.waitForElementVisible(driver,
                By.xpath(search_result_locator),
                String.format("Failed to locate element by xpath: \n%s.", search_result_locator),
                10);

        int number_of_search_elements = UiHelpers.getNumberOfElements(driver,
                By.xpath(search_result_locator));

        // Check that search results has more than 0 items, if true, pass
        Assert.assertTrue("Too few results", number_of_search_elements > 0);
    }

    /* Lesson 4, Part 6. Empty Search */
    @Test
    public void testAmountOfEmptySearch()
    {
        String search_line = "VCXZasdfrewq";
        // String search_line = "zxcvfdsaqwer";

        // String no_results_label_locator = "//*[@resource-id='org.wikipedia:id/search_empty_view']";
        String no_results_label_locator = "//*[@text='No results found']";
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_container']" +
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']";

        // Open app
        // Tap 'Search'
        SearchPageObject.initSearchInput();

        // Send random query with unlikely matches
        UiHelpers.waitForElementVisibleAndSendKeys(driver,
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                String.format("Cannot type '%s' to search.", search_line),
                10);

        // Check that search results has 0 items, if false, throw exception
        UiHelpers.assertZeroElementsVisible(driver,
                By.xpath(search_result_locator),
                String.format("Found results by query: '%s'", search_line));

        // Check that "No results found" label is visible
        UiHelpers.waitForElementVisible(driver,
                By.xpath(no_results_label_locator),
                String.format("Cannot locate item by xpath: \n%s", no_results_label_locator),
                10);
    }

    /* Part 7. Rotation */
    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        // Reset screen rotation
        if (driver.getOrientation() != ScreenOrientation.PORTRAIT) {
            driver.rotate(ScreenOrientation.PORTRAIT);
        }

        // Tap search
        SearchPageObject.initSearchInput();

        // Send search query
        String search_query = "Java";
        UiHelpers.waitForElementVisibleAndSendKeys(driver,
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_query,
                "Cannot locate search input field.",
                5);

        // Wait for results
        String xpath_to_search =
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']"
                        + "//*[@text='Object-oriented programming language']";

        // Open article
        UiHelpers.waitForElementVisibleAndClick(driver,
                By.xpath(xpath_to_search),
                String.format("Cannot locate element by xpath: \n%s", xpath_to_search),
                15);

        // Get title
        String title_before_rotation = MainPageObject.waitForElementVisibleAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title.",
                15);

        // Rotate device
        driver.rotate(ScreenOrientation.LANDSCAPE);

        // Assert article title is still there
        String title_after_rotation = MainPageObject.waitForElementVisibleAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title.",
                15);

        Assert.assertEquals(
                "Titles before and after rotation don't match.",
                title_after_rotation,
                title_before_rotation
        );

        // Rotate again
        driver.rotate(ScreenOrientation.LANDSCAPE);

        // Assert article title is still the same
        String title_after_second_rotation = MainPageObject.waitForElementVisibleAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title.",
                15);

        Assert.assertEquals(
                "Titles before and after two rotations don't match.",
                title_after_second_rotation,
                title_before_rotation
        );
    }

    /* Part 8. Background. */
    @Test
    public void testCheckSearchArticleInBackground()
    {
        // Reset screen rotation
        if (driver.getOrientation() != ScreenOrientation.PORTRAIT) {
            driver.rotate(ScreenOrientation.PORTRAIT);
        }

        // Launch app
        // Go to search screen
        SearchPageObject.initSearchInput();

        // Send 'Java'
        String search_query = "Java";
        String article_title = "Java (programming language)";

        UiHelpers.waitForElementVisibleAndSendKeys(driver,
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_query,
                "Cannot locate search input field.",
                5);

        // Check that 'Java (programming language)' is found
        String xpath_to_search =
                "//*[@resource-id='org.wikipedia:id/page_list_item_title']"
                        + String.format("[@text='%s']", article_title);

        UiHelpers.waitForElementVisible(driver,
                By.xpath(xpath_to_search),
                String.format("Cannot find '%s' in search results.", article_title),
                10);

        // Send app to background by clicking 'O'
        // Launch again
        driver.runAppInBackground(2);

        // Check that our search result is still on the screen
        UiHelpers.waitForElementVisible(driver,
                By.xpath(xpath_to_search),
                String.format("Cannot find '%s' in search results.", article_title),
                10);
    }
}
