import io.appium.java_client.AppiumDriver;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

public class TestEx6 extends CoreTestCase {

    /**
     * Написать тест, который:
     * - открывает статью и убеждается, что у нее есть элемент title.
     * - Важно: тест не должен дожидаться появления title, проверка должна производиться сразу.
     * - Если title не найден - тест падает с ошибкой.
     *
     * Метод можно назвать assertElementPresent.
     */
    private lib.ui.SearchPageObject SearchPageObject;
    private ArticlePageObject ArticlePageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        SearchPageObject = new SearchPageObject(driver);
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