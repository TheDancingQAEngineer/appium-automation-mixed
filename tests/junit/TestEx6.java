import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class TestEx6 extends CoreTestCase {

    /**
     * Написать тест, который:
     * - открывает статью и убеждается, что у нее есть элемент title.
     * - Важно: тест не должен дожидаться появления title, проверка должна производиться сразу.
     * - Если title не найден - тест падает с ошибкой.
     *
     * Метод можно назвать assertElementPresent.
     */
    private SearchPageObject SearchPageObject;
    private ArticlePageObject ArticlePageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject = new ArticlePageObject(driver);
    }

    @Test
    public void testArticleHasTitleImmediately()
    // This test is supposed to be flaky.
    {
        String search_query = "Java";
        String expected_title = "Java (programming language)";

        goFromStartScreenThroughSearchToArticle(search_query, expected_title);
        ArticlePageObject.assertTitleElementPresent();
    }

    @Test
    public void testArticleHasTitleWithWait()
    // This test is supposed to be more robust than the previous.
    {
        String search_query = "Java";
        String expected_title = "Java (programming language)";

        goFromStartScreenThroughSearchToArticle(search_query, expected_title);

        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.assertTitleElementPresent();
    }

    private void goFromStartScreenThroughSearchToArticle(String search_query, String expected_header)
    {
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);
        SearchPageObject.clickOnArticleWithSubstring(expected_header);
    }
}
