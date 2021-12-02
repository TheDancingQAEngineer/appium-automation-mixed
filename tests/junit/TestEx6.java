import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

public class TestEx6 {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        driver = AppiumSetup.setUp();

        // Reset screen rotation
        if (driver.getOrientation() != ScreenOrientation.PORTRAIT) {
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    @After
    public void tearDown()
    {
        AppiumSetup.tearDown(driver);
    }

    /**
     * Написать тест, который:
     * - открывает статью и убеждается, что у нее есть элемент title.
     * - Важно: тест не должен дожидаться появления title, проверка должна производиться сразу.
     * - Если title не найден - тест падает с ошибкой.
     *
     * Метод можно назвать assertElementPresent.
     */

    @Test
    public void testArticleHasTitleImmediately()
    // This test is supposed to be flaky.
    {
        String search_query = "Java";
        String expected_title = "Java (programming language)";
        String id_of_article_title = "org.wikipedia:id/view_page_title_text";

        // Go from start screen to an article
        goFromStartScreenThroughSearchToArticle(search_query, expected_title);

        // Check that a locator corresponding to article title is present
        assertElementPresent(By.id(id_of_article_title),
                "Cannot find article title.");
    }

    @Test
    public void testArticleHasTitleWithWait()
    // This test is supposed to be more robust than the previous.
    {
        String search_query = "Java";
        String expected_title = "Java (programming language)";
        String id_of_article_title = "org.wikipedia:id/view_page_title_text";

        // Go from start screen to an article
        goFromStartScreenThroughSearchToArticle(search_query, expected_title);

        // Wait for article title to become visible
        UiHelpers.waitForElementVisible(driver,
                By.id(id_of_article_title),
                "Article title not visible after timeout.",
                15);

        // Check that a locator corresponding to article title is present
        assertElementPresent(By.id(id_of_article_title),
                "Cannot find article title.");
    }

    private void assertElementPresent(By by, String error_message)
    {
        try {
            WebElement element = driver.findElement(by);
        } catch (NoSuchElementException e) {
            String default_message = String.format(
                    "An element %s is expected, but not found.", by.toString());
            throw new AssertionError(default_message + " " + error_message);
        }
    }


    private void goFromStartScreenThroughSearchToArticle(String search_query, String expected_header)
    {
        // Launch app, go to search screen
        UiHelpers.waitForElementPresentAndClick(driver,
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot locate search field.",
                5);

        // Send search query
        UiHelpers.waitForElementPresentAndSendKeys(driver,
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_query,
                String.format("Cannot type %s to search.", search_query),
                10);

        // Go to article
        UiHelpers.waitForElementPresentAndClick(driver,
                makeLocatorFromArticleTitle(expected_header),
                String.format("Cannot find '%s' in search results.", expected_header),
                10);
    }

    private By makeLocatorFromArticleTitle(String title)
    {
        return By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"
                + String.format("[@text='%s']", title));
    }
}
