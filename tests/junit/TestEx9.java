import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.util.WikiArticle;
import org.junit.Test;

public class TestEx9 extends CoreTestCase {

    private SearchPageObject SearchPageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        SearchPageObject = new SearchPageObject(driver);
    }

    /** Задача:
     * 1. Подобрать локатор, который находит результат поиска одновременно по заголовку и описанию
     * (если заголовок или описание отличается - элемент не находится).
     *
     * 2. Добавить соответствующий метод в секцию TEMPLATES METHODS класса SearchPageObject.
     *
     * 3. В этот же класс добавить метод
     * waitForElementByTitleAndDescription(String title, String description).
     * Он должен дожидаться результата поиска по двум строкам - по заголовку и описанию.
     * Если такой элемент не появляется, тест должен упасть с читаемой и понятной ошибкой.
     *
     * 4. Написать тест, который будет делать поиск по любому запросу на ваш выбор
     * (поиск по этому слову должен возвращать как минимум 3 результата). Далее тест должен
     * убеждаться, что первых три результата присутствуют в результате поиска.
     **/

    @Test
    public void testArticleTitleAndDescription1()       // with custom class
    {
        String search_line = "Hungary";

        WikiArticle article_1 = new WikiArticle("Hungary","Country in Central Europe");
        WikiArticle article_2 = new WikiArticle("Hungary national football team",
                "Men's national association football team representing Hungary");
        WikiArticle article_3 = new WikiArticle("Hungary in World War II",
                "Involvement of Hungary in World War II");

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);

        SearchPageObject.waitForSearchResultByWikiArticleObject(article_1);
        SearchPageObject.waitForSearchResultByWikiArticleObject(article_2);
        SearchPageObject.waitForSearchResultByWikiArticleObject(article_3);
    }

    @Test
    public void testArticleTitleAndDescription2()       // with strings
    {
        String search_line = "Hungary";

        WikiArticle article_1 = new WikiArticle("Hungary","Country in Central Europe");
        WikiArticle article_2 = new WikiArticle("Hungary national football team",
                "Men's national association football team representing Hungary");
        WikiArticle article_3 = new WikiArticle("Hungary in World War II",
                "Involvement of Hungary in World War II");

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);

        SearchPageObject.waitForSearchResultByTitleAndDescription(article_1.getTitle(), article_1.getDescription());
        SearchPageObject.waitForSearchResultByTitleAndDescription(article_2.getTitle(), article_2.getDescription());
        SearchPageObject.waitForSearchResultByTitleAndDescription(article_3.getTitle(), article_3.getDescription());
    }

    @Test
    public void testBadArticleTitlesAndDescriptions1()       // checks that no mixed-up articles are found
    {
        String search_line = "Hungary";

        WikiArticle article_1 = new WikiArticle("Hungary","Involvement of Hungary in World War II");
        WikiArticle article_2 = new WikiArticle("Hungary national football team",
                "Country in Central Europe");
        WikiArticle article_3 = new WikiArticle("Hungary in World War II",
                "Men's national association football team representing Hungary");

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);

        SearchPageObject.assertNoSearchResultByTitleAndDescription(article_1.getTitle(), article_1.getDescription());
        SearchPageObject.assertNoSearchResultByTitleAndDescription(article_2.getTitle(), article_2.getDescription());
        SearchPageObject.assertNoSearchResultByTitleAndDescription(article_3.getTitle(), article_3.getDescription());
    }

    @Test
    public void testBadArticleTitlesAndDescriptions2()       // fails by design
    {
        String search_line = "Hungary";

        WikiArticle article_1 = new WikiArticle("Hungary","Involvement of Hungary in World War II");

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);

        SearchPageObject.waitForSearchResultByWikiArticleObject(article_1);
    }

    @Test
    public void testGoodArticleIsLabelledAsBad()       // fails by design
    {
        String search_line = "Hungary";

        WikiArticle article_1 = new WikiArticle("Hungary","Country in Central Europe");

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);

        SearchPageObject.assertNoSearchResultByTitleAndDescription(article_1.getTitle(), article_1.getDescription());
    }
}
