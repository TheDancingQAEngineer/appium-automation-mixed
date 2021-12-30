import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestTextInElements extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();
        
        MainPageObject = new MainPageObject(driver);
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
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
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

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();

        // THEN: Search field accepts text

        // This is a custom method with wait().
        // Note that we're using an overloaded method.
        SearchPageObject.typeSearchLine(search_query);
    }

    @Test
    /* Part 5. Compound xPath */
    public void testSearchForJavaTheProgrammingLanguage() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

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
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
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
        // DONE: TODO: Refactor this test by the end of section 4
        // GIVEN:
        // - running emulator
        // - running Appium Server
        // - app is on search screen
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

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
    // DONE: TODO: Refactor during section 5
    {
        String search_query = "Java";
        String expected_substring = "Object-oriented programming language";
        String expected_title = "Java (programming language)";

        // GIVEN:
        // - running emulator
        // - running Appium Server
        // - app is on search screen
        // - cursor in search field
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();

        // WHEN:
        // - we type "Java" into search field,
        // - click the result and wait for page to load
        SearchPageObject.typeSearchLine(search_query);
        SearchPageObject.clickOnArticleWithSubstring(expected_substring);

        // THEN: Page title matches our search
        // org.wikipedia:id/view_page_title_text

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
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
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

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

    private WebElement waitForWebElementVisibleById(String id, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        By by = By.id(id);
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementByIdAndClick(String id, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForWebElementVisibleById(id, error_message, timeoutInSeconds);
        element.click();
        return element;
    }
}