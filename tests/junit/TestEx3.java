import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.*;

public class TestEx3 extends CoreTestCase {

    private SearchPageObject SearchPageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        SearchPageObject = new SearchPageObject(driver);
    }

    @Test
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
    public void testOverlySpecificQueryThatFails()   // This test fails by design.
    {
        int minimum_expected_number_of_results = 2;
        String search_query = "Yo La Tengo discography";
        String expected_substring = "Yo La Tengo discography";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);
        SearchPageObject.waitForAtLeastNSearchResults(expected_substring, minimum_expected_number_of_results);
    }
}