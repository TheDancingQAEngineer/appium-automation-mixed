import lib.ui.SearchPageObject;
import lib.CoreTestCase;
import lib.ui.*;
import org.junit.*;
import org.openqa.selenium.*;

public class TestSearchSwipeAndScreenFlip extends CoreTestCase {

    protected lib.ui.MainPageObject MainPageObject;
    protected SearchPageObject SearchPageObject;
    protected ArticlePageObject ArticlePageObject;
    protected NavigationUI NavigationUI;
    protected MyListsPageObject MyListsPageObject;

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

        this.NavigationUI = new NavigationUI(driver);
        this.MyListsPageObject = new MyListsPageObject(driver);

        // Launch app, enter search mode
        SearchPageObject.initSearchInput();

        // Search "Java"
        SearchPageObject.typeSearchLine(search_query);

        // Go to article
        SearchPageObject.clickOnArticleWithSubstring(article_title);

        ArticlePageObject.waitForTitleElement();

        ArticlePageObject.addArticleToReadingList(reading_list_title);

        // Close article
        ArticlePageObject.closeArticle();

        // Tap 'My lists'
        NavigationUI.clickMyLists();

        // Tap the list previously created
        MyListsPageObject.openReadingListByName(reading_list_title);

        // Assert the article is there
        MyListsPageObject.waitForArticleToAppearByTitle(article_title);

        // Remove by swiping
        MyListsPageObject.swipeArticleToDelete(article_title);

        // Assert article is removed
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title);
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
        SearchPageObject.typeSearchLine(search_line);

        int number_of_search_elements = SearchPageObject.getNumberOfSearchResults();

        // Check that search results has more than 0 items, if true, pass
        Assert.assertTrue("Too few results", number_of_search_elements > 0);
    }

    /* Lesson 4, Part 6. Empty Search */
    @Test
    public void testAmountOfEmptySearch()
    {
        String search_line = "VCXZasdfrewq";

        // Open app
        // Tap 'Search'
        SearchPageObject.initSearchInput();

        // Send random query with unlikely matches
        SearchPageObject.typeSearchLine(search_line);

        // Check that search results has 0 items, if false, throw exception
        SearchPageObject.assertZeroSearchResults(search_line);

        // Check that "No results found" label is visible
        SearchPageObject.waitForNoResultsLabel();
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
        String expected_substring = "Object-oriented programming language";
        SearchPageObject.typeSearchLine(search_query);

        // Wait for results
        SearchPageObject.clickOnArticleWithSubstring(expected_substring);

        // Get title
        String title_before_rotation = ArticlePageObject.getArticleTitle();

        // Rotate device
        driver.rotate(ScreenOrientation.LANDSCAPE);

        // Assert article title is still there
        String title_after_rotation = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Titles before and after rotation don't match.",
                title_after_rotation,
                title_before_rotation
        );

        // Rotate again
        driver.rotate(ScreenOrientation.LANDSCAPE);

        // Assert article title is still the same
        String title_after_second_rotation = ArticlePageObject.getArticleTitle();

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

        SearchPageObject.typeSearchLine(search_query);

        // Check that 'Java (programming language)' is found
        SearchPageObject.waitForSearchResult(article_title);

        // Send app to background by clicking 'O'
        // Launch again
        driver.runAppInBackground(java.time.Duration.ofSeconds(2));

        // Check that our search result is still on the screen
        SearchPageObject.waitForSearchResult(article_title);
    }
}
