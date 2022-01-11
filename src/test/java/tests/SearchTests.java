package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.ui.SearchPageObject;
import lib.CoreTestCase;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.*;

public class SearchTests extends CoreTestCase {

    protected SearchPageObject SearchPageObject;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.SearchPageObject = SearchPageObjectFactory.get(driver);
    }

    @Test
    @DisplayName("Find search field and click")
    @Description("Test that home screen has a clickable 'Search' element")
    @Severity(SeverityLevel.CRITICAL)
    @Features(value = {@Feature("Search")})
    public void testFindSearchFieldAndClick()
    {
        // GIVEN:
        // - running emulator
        // - running Appium Server

        // WHEN: On app home screen

        // THEN:
        // - there's an element with text 'Search Wikipedia';
        // - the element is clickable
        SearchPageObject.initSearchInput();
    }

    @Test
    @DisplayName("Test sending text to search input")
    @Description("Check that framework does send text to search field")
    @Severity(SeverityLevel.CRITICAL)
    @Features(value = {@Feature("Search")})
    public void testSendTextToSearchField()
    {
        String search_query = "Yo La Tengo Discography";
        // GIVEN:
        // - running emulator
        // - running Appium Server

        // WHEN: On search screen

        SearchPageObject.initSearchInput();

        // THEN: Search field accepts text

        // This is a custom method with wait().
        // Note that we're using an overloaded method.
        SearchPageObject.typeSearchLine(search_query);
    }

    @Test
    @DisplayName("Test that search results appear and disappear")
    @Description("Verify that search results react to user input")
    @Severity(SeverityLevel.NORMAL)
    @Features(value={@Feature("Search")})
    public void testSearchResultsAppearAndDisappear()   // This test passes.
    {
        int minimum_expected_number_of_results = 2;
        String search_query = "Hungary";
        String expected_substring = "Hungary";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);

        SearchPageObject.waitForAtLeastNSearchResults(expected_substring,
                minimum_expected_number_of_results);

        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForSearchResultsToDisappear(expected_substring);
    }

    @Test
    @DisplayName("Test that overly specific queries return at most 1 result")
    @Description("This is a demo test for checking that our framework counts results correctly")
    @Severity(SeverityLevel.MINOR)
    @Features(value = {@Feature("Search")})
    public void testOverlySpecificQueryThatFails()   // This test fails by design.
    {
        int minimum_expected_number_of_results = 2;
        String search_query = "Yo La Tengo discography";
        String expected_substring = "Yo La Tengo discography";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);

        try {
            SearchPageObject.waitForAtLeastNSearchResults(expected_substring, minimum_expected_number_of_results);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Less than 2"));
        }
    }

    @Test
    @DisplayName("Test search by query")
    @Description("GIVEN: initiated search\n" +
            "// WHEN: we type 'Java' into search field\n" +
            "// THEN: at least one search result contains text 'rogramming language'")
    @Severity(SeverityLevel.NORMAL)
    @Features(value = {@Feature("Search")})
    public void testSearchForJavaRefactored() {
        String
                search_query = "Java",
                expected_substring = "rogramming language";

        // GIVEN:
        // - running emulator
        // - running Appium Server
        // - app is on search screen
        // - cursor in search field

        SearchPageObject.initSearchInput();

        // WHEN: we type "Java" into search field
        SearchPageObject.typeSearchLine(search_query);

        // THEN: at least one search result contains text "Programming Language"
        SearchPageObject.waitForSearchResult(expected_substring);
    }

    /* Lesson 4, Part 5. Asserts. */
    @Test
    @DisplayName("Test presence of search results by a reasonable query")
    @Description("Check that searching by reasonable query returns at least one result")
    @Severity(SeverityLevel.BLOCKER)
    @Features(value = {@Feature("Search")})
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
    @DisplayName("Test that garbage input doesn't return results")
    @Description("Send a nonsense input and check that no search results were found.")
    @Severity(SeverityLevel.NORMAL)
    @Features(value = {@Feature("Search")})
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
    @DisplayName("Check search results after sending app to background")
    @Description("0")
    @Severity(SeverityLevel.NORMAL)
    @Features(value = {@Feature("Search")})
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

    /* Part 10: Search cancel. */
    @Test
    @DisplayName("Test search cancel")
    @Description("Test that cancelling search brings user out of search screen")
    @Severity(SeverityLevel.NORMAL)
    @Features(value = {@Feature("Search")})
    public void testSearchCancelAdvanced()
    {
        String search_query = "Java";

        // GIVEN: search query typed in
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);

        // WHEN: we have cleared text from search input
        //      AND we click X to cancel search...
        SearchPageObject.clearSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();

        // THEN: the X is no longer visible (though it says nothing of functionality)
        SearchPageObject.waitForCancelButtonToDisappear();
    }
}
