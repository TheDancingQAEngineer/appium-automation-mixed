package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.*;
import lib.ui.mobileweb.MWAuthPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    protected HomePageObject HomePageObject;
    protected SearchPageObject SearchPageObject;
    protected ArticlePageObject ArticlePageObject;
    protected NavigationUI NavigationUI;
    protected MyListsPageObject MyListsPageObject;

    private static final String
        USERNAME = "TheDancingQAEngineer",
        PASSWORD = "***REMOVED***";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.HomePageObject = HomePageObjectFactory.get(driver);
        this.SearchPageObject = SearchPageObjectFactory.get(driver);
        this.ArticlePageObject = ArticlePageObjectFactory.get(driver);
    }

    @Test
    public void testAddToReadingListAndDelete()
    {
        String reading_list_title = "Learning programming";
        String search_query = "Java";
        String article_title = "Java (programming language)";

        this.NavigationUI = NavigationUIFactory.get(driver);
        this.MyListsPageObject = MyListsPageObjectFactory.get(driver);

        // Launch app, enter search mode
        SearchPageObject.initSearchInput();

        // Search "Java"
        SearchPageObject.typeSearchLine(search_query);

        // Go to article
        SearchPageObject.clickOnArticleWithSubstring(article_title);

        ArticlePageObject.waitForTitleElement(article_title);

        ArticlePageObject.addArticleToReadingList(reading_list_title);

        if (Platform.getInstance().isMW()) {
            // Log in
            AuthPageObject Auth = new MWAuthPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterCredentials(USERNAME, PASSWORD);
            Auth.submitLoginForm();

            ArticlePageObject.waitForTitleElement(article_title);
            ArticlePageObject.assertTitleMatches(article_title);
        }

        // Close article
        ArticlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()) {
            SearchPageObject.clickCancelSearch();
        }

        // Tap 'My lists'
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        if (Platform.getInstance().isIOS()) {
            HomePageObject.dismissLogInToSyncSavedArticles();
        }

        // Tap the list previously created
        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openReadingListByName(reading_list_title);
        }

        // Assert the article is there
        MyListsPageObject.waitForArticleToAppearByTitle(article_title);

        // Remove by swiping
        MyListsPageObject.swipeArticleToDelete(article_title);

        // Assert article is removed
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title);
    }
}
