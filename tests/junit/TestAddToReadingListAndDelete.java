import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class TestAddToReadingListAndDelete extends CoreTestCase {

    protected lib.ui.MainPageObject MainPageObject;
    protected SearchPageObject SearchPageObject;
    protected ArticlePageObject ArticlePageObject;
    protected NavigationUI NavigationUI;
    protected MyListsPageObject MyListsPageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.MainPageObject = new MainPageObject(driver);
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

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToReadingListAndroid(reading_list_title);
        } else {
            ArticlePageObject.saveArticleForLaterIOS();
            ArticlePageObject.dismissLogInToSyncSavedArticles();
        }

        // Close article
        ArticlePageObject.closeArticle();

        // Tap 'My lists'
        NavigationUI.clickMyLists();

        // Tap the list previously created
        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openReadingListByName(reading_list_title);
        }

        // Assert the article is there
        MyListsPageObject.waitForArticleToAppearByTitle(article_title);

        // Remove by swiping
        MyListsPageObject.swipeArticleToDelete(article_title);
        if (Platform.getInstance().isIOS())
        {
            MyListsPageObject.clickSwipeDeleteButton();
        }

        // Assert article is removed
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title);
    }
}
