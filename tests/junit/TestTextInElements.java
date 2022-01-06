import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.*;

public class TestTextInElements extends CoreTestCase {

    private SearchPageObject SearchPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();
        SearchPageObject = SearchPageObjectFactory.get(driver);
    }

    @Test
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
    /* Part 5. Compound xPath */
    public void testSearchForJavaTheProgrammingLanguage() {
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
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

    /* Part 7: Search cancel. */
    @Test
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

    /* Part 9: Basic Assertions */
    @Test
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
        String article_title = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                String.format("Expected title '%s', got '%s'\n",
                        expected_title, article_title),
                expected_title,
                article_title
        );
    }

    /* Part 10: Search cancel. */
    @Test
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
