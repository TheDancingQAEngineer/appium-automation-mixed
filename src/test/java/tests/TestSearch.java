package tests;

import lib.ui.SearchPageObject;
import lib.CoreTestCase;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.*;

public class TestSearch extends CoreTestCase {

    protected lib.ui.MainPageObject MainPageObject;
    protected SearchPageObject SearchPageObject;
    protected ArticlePageObject ArticlePageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.MainPageObject = new MainPageObject(driver);
        this.SearchPageObject = SearchPageObjectFactory.get(driver);
        this.ArticlePageObject = ArticlePageObjectFactory.get(driver);
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
        SearchPageObject.waitForAnySearchResult();

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

    /* Part 8. Background. */
    @Test
    public void testCheckSearchArticleInBackground()
    {
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
        this.sendAppToBackground(java.time.Duration.ofSeconds(2));

        // Check that our search result is still on the screen
        SearchPageObject.waitForSearchResult(article_title);
    }
}
