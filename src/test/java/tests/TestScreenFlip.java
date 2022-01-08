package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.ScreenOrientation;

public class TestScreenFlip extends CoreTestCase {

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
}
