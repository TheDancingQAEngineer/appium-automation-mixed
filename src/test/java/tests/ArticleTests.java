package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.*;

public class ArticleTests extends CoreTestCase {

    private SearchPageObject SearchPageObject;
    private ArticlePageObject ArticlePageObject;

    public void setUp() throws Exception
    {
        super.setUp();
        SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject = ArticlePageObjectFactory.get(driver);
    }

    /* Part 9: Basic Assertions */
    @Test
    @DisplayName("Compare article title with expected.")
    @Description("GIVEN: Search by query 'Java'\n" +
            "WHEN: Article with substring 'Object-oriented programming langauge' is clicked\n" +
            "THEN: Article title is 'Java (programming language)'")
    @Step("Sterting test testCompareArticleTitle")
    public void testCompareArticleTitle()
        {
        String search_query = "Java";
        String expected_substring = "Object-oriented programming language";
        String expected_title = "Java (programming language)";

        // GIVEN:
        // - running emulator
        // - running Appium Server
        // - app is on search screen
        // - cursor in search field

        SearchPageObject.initSearchInput();

        // WHEN:
        // - we type "Java" into search field,
        // - click the result and wait for page to load
        SearchPageObject.typeSearchLine(search_query);
        SearchPageObject.clickOnArticleWithSubstring(expected_substring);

        // THEN: Page title matches our search
        // org.wikipedia:id/view_page_title_text

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = ArticlePageObject.getArticleTitle(expected_title);

        // ArticlePageObject.takeScreenshot("article_page");

        Assert.assertEquals(
                String.format("Expected title '%s', got '%s'\n",
                        expected_title, article_title),
                expected_title,
                article_title
        );
    }

    @Test
    @DisplayName("Swipe article 5 times.")
    @Description("A simple search article and swipe up test. No special checks.")
    public void testSwipeArticle()
    {

        String search_query = "Appium";
        String expected_substring = "Appium";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);

        SearchPageObject.clickOnArticleWithSubstring(expected_substring);
        ArticlePageObject.waitForTitleElement(expected_substring);

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

        String search_query = "Appium";
        String expected_substring = "Appium";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);
        SearchPageObject.clickOnArticleWithSubstring(expected_substring);

        ArticlePageObject.waitForTitleElement(expected_substring);
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testArticleHasTitleImmediately()
    // This test is supposed to be flaky.
    {
        String search_query = "Java";
        String expected_title = "Java (programming language)";

        goFromStartScreenThroughSearchToArticle(search_query, expected_title);
        try {
            ArticlePageObject.assertTitleElementPresent(expected_title);
        } catch (AssertionError e) {
            Assert.assertTrue(e.getMessage().contains("Article title not found"));
        }
    }

    @Test
    public void testArticleHasTitleWithWait()
    // This test is supposed to be more robust than the previous.
    {
        String search_query = "Java";
        String expected_title = "Java (programming language)";

        goFromStartScreenThroughSearchToArticle(search_query, expected_title);

        ArticlePageObject.waitForTitleElement(expected_title);
        ArticlePageObject.assertTitleElementPresent(expected_title);
    }

    /* Part 7. Rotation */
    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        // Tap search
        SearchPageObject.initSearchInput();

        // Send search query
        String search_query = "Java";
        String expected_substring = "Java (programming language)";
        SearchPageObject.typeSearchLine(search_query);

        // Wait for results
        SearchPageObject.clickOnArticleWithSubstring(expected_substring);

        // Get title
        String title_before_rotation = ArticlePageObject.getArticleTitle(expected_substring);

        // Rotate device
        this.rotateScreenLandscape();

        // Assert article title is still there
        String title_after_rotation = ArticlePageObject.getArticleTitle(expected_substring);

        Assert.assertEquals(
                "Titles before and after rotation don't match.",
                title_after_rotation,
                title_before_rotation
        );

        // Rotate again
        this.rotateScreenPortrait();
        this.rotateScreenLandscape();

        // Assert article title is still the same
        String title_after_second_rotation = ArticlePageObject.getArticleTitle(expected_substring);

        Assert.assertEquals(
                "Titles before and after two rotations don't match.",
                title_after_second_rotation,
                title_before_rotation
        );
    }

    private void goFromStartScreenThroughSearchToArticle(String search_query, String expected_header)
    {
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);
        SearchPageObject.clickOnArticleWithSubstring(expected_header);
    }
}
