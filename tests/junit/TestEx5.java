import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.*;

public class TestEx5 extends CoreTestCase {

    private SearchPageObject SearchPageObject;
    private ArticlePageObject ArticlePageObject;
    private NavigationUI NavigationUI;
    private MyListsPageObject MyListsPageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI = NavigationUIFactory.get(driver);
        this.MyListsPageObject = MyListsPageObjectFactory.get(driver);
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

        ArticlePageObject.addArticleToReadingListAndroid(reading_list_name);
        ArticlePageObject.closeArticle();

        // This kicks us back to home screen, so we
        //  initiate new search
        SearchPageObject.initSearchInput();

        // Send search query
        SearchPageObject.typeSearchLine(search_query_2);

        // Go to article
        SearchPageObject.clickOnArticleWithSubstring(expected_header_2);

        // Tap "Three dots"
        ArticlePageObject.waitForTitleElement(expected_header_2);
        ArticlePageObject.addArticleToReadingListAndroid(reading_list_name);
        ArticlePageObject.closeArticle();

        NavigationUI.clickMyLists();

        // Tap the list previously created
        MyListsPageObject.openReadingListByName(reading_list_name);

        MyListsPageObject.waitForArticleToAppearByTitle(expected_header_1);
        MyListsPageObject.waitForArticleToAppearByTitle(expected_header_2);
        MyListsPageObject.swipeArticleToDelete(expected_header_1);
        MyListsPageObject.waitForArticleToDisappearByTitle(expected_header_1);

        MyListsPageObject.clickOnArticleByTitle(expected_header_2);

        ArticlePageObject.waitForTitleElement(expected_header_2);
        ArticlePageObject.assertTitleMatches(expected_header_2);
    }
}
