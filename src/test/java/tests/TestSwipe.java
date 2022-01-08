package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class TestSwipe extends CoreTestCase {

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

    @Test
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
}
