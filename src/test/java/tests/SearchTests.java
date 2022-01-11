package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Severity;
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
    @DisplayName(0)
    @Description(0)
    @Severity()
    @Features()
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
    @DisplayName(0)
    @Description(0)
    @Severity()
    @Features()
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
    @DisplayName(0)
    @Description(0)
    @Severity()
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
    @DisplayName(0)
    @Description(0)
    @Severity()
    @Features()
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
    @DisplayName(0)
    @Description(0)
    @Severity()
    @Features()
    /* Part 5. Compound xPath */
    public void testSearchForJavaTheProgrammingLanguage() {
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    @DisplayName(0)
    @Description(0)
    @Severity()
    @Features()
    public void testSearchForJavaRefactored() {
        String
                search_query = "Java",
                expected_substring = "Object-oriented programming language";

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
    @DisplayName(0)
    @Description(0)
    @Severity()
    @Features()
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
    @DisplayName(0)
    @Description(0)
    @Severity()
    @Features()
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
    @DisplayName(0)
    @Description(0)
    @Severity()
    @Features()
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
    @DisplayName(0)
    @Description(0)
    @Severity()
    @Features()
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

    /* Part 7: Search cancel. */
    @Test
    @DisplayName(0)
    @Description(0)
    @Severity()
    @Features()
    public void testSearchCancel()
    {
        // GIVEN:
        // - running emulator
        // - running Appium Server
        // - app is on search screen


        SearchPageObject.initSearchInput();

        // WHEN: we click X to cancel search...
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();

        // THEN: the X is no longer visible (though it says nothing of functionality)
        SearchPageObject.waitForCancelButtonToDisappear();
    }
}
