import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.*;
import org.junit.*;

public class TestAddTwoArticlesToReadingListAndRemoveOne extends CoreTestCase {

    private HomePageObject HomePageObject;
    private SearchPageObject SearchPageObject;
    private ArticlePageObject ArticlePageObject;
    private NavigationUI NavigationUI;
    private MyListsPageObject MyListsPageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        HomePageObject = HomePageObjectFactory.get(driver);
        SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject = MyListsPageObjectFactory.get(driver);
    }

    @Test
    public void testAddTwoArticlesToReadingListAndRemoveOne()
    {
        /* Написать тест, который:
        1. Сохраняет две статьи в одну папку
        2. Удаляет одну из статей
        3. Убеждается, что вторая осталась
        4. Переходит в неё и убеждается, что title совпадает */


        String
                search_query_1 = "Java",
                expected_header_1 = "Java (programming language)",
                search_query_2 = "Python",
                expected_header_2 = "Python (programming language)",
                reading_list_name = "Programming languages";

        // Launch app, go to search screen
        SearchPageObject.initSearchInput();

        // Send search query
        SearchPageObject.typeSearchLine(search_query_1);

        // Go to article
        SearchPageObject.clickOnArticleWithSubstring(expected_header_1);

        ArticlePageObject.waitForTitleElement(expected_header_1);

        ArticlePageObject.addArticleToReadingList(reading_list_name);
        ArticlePageObject.closeArticle();

        if (Platform.getInstance().isAndroid()) {
            // closing article kicks us back to home screen, so we simply
            // initiate new search
            SearchPageObject.initSearchInput();
        } else {
            // on iOS, we get to search results instead, so we need to
            // clear search input
            SearchPageObject.clearSearchInput();
        }

        // Send search query
        SearchPageObject.typeSearchLine(search_query_2);

        // Go to article
        SearchPageObject.clickOnArticleWithSubstring(expected_header_2);

        ArticlePageObject.waitForTitleElement(expected_header_2);
        ArticlePageObject.addArticleToReadingList(reading_list_name);
        ArticlePageObject.closeArticle();

        if (Platform.getInstance().isIOS()) {
            SearchPageObject.clickCancelSearch();
        }

        NavigationUI.clickMyLists();

        if (Platform.getInstance().isIOS()) {
            HomePageObject.dismissLogInToSyncSavedArticles();
        }

        // Tap the list previously created
        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openReadingListByName(reading_list_name);
        }

        MyListsPageObject.waitForArticleToAppearByTitle(expected_header_1);
        MyListsPageObject.waitForArticleToAppearByTitle(expected_header_2);
        MyListsPageObject.swipeArticleToDelete(expected_header_1);
        if (Platform.getInstance().isIOS())
        {
            MyListsPageObject.clickSwipeDeleteButton();
        }

        MyListsPageObject.waitForArticleToDisappearByTitle(expected_header_1);

        MyListsPageObject.clickOnArticleByTitle(expected_header_2);

        ArticlePageObject.waitForTitleElement(expected_header_2);
        ArticlePageObject.assertTitleMatches(expected_header_2);
    }
}
