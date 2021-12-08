package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.util.WikiArticle;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{

    /** CONSTANTS BEGIN **/
    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
        SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_TEXT_FIELD = "org.wikipedia:id/search_src_text";
    /** CONSTANTS END **/

    /** STRING TEMPLATES BEGIN **/
    private static final String
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']"
                + "//*[contains(@text, '{SUBSTRING}')]",
        SEARCH_ITEM_TITLE_AND_DESCRIPTION_TPL =
                    "//*[@resource-id='org.wikipedia:id/page_list_item_container']"
                            + "[.//@text=\"{TITLE}\" and .//@text=\"{DESCRIPTION}\"]";
    /** STRING TEMPLATES END **/

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /** TEMPLATE METHODS BEGIN **/
    private static String getSearchResultElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getSearchResultElementByWikiArticleObject(WikiArticle article)
    {
        return SEARCH_ITEM_TITLE_AND_DESCRIPTION_TPL
                .replace("{TITLE}", article.getTitle())
                .replace("{DESCRIPTION}", article.getDescription());
    }

    private static String getSearchResultElementByTitleAndDescription(
            String title, String description)
    {
        return SEARCH_ITEM_TITLE_AND_DESCRIPTION_TPL
                .replace("{TITLE}", title)
                .replace("{DESCRIPTION}", description);
    }
    /** TEMPLATE METHODS END **/


    public void initSearchInput()
    {
        this.waitForElementVisibleByXpath(SEARCH_INIT_ELEMENT,
                "Cannot find search field on start screen.",
                5);

        this.waitForElementVisibleByXpathAndClick(SEARCH_INIT_ELEMENT,
                "Cannot click in search field at home screen.",
                5);

        this.waitForElementVisibleByXpath(SEARCH_INPUT,
                "Cannot find search input after tap.",
                5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementVisibleAndSendKeys(
                By.xpath(SEARCH_INPUT),
                search_line,
                "Cannot type search line to search input",
                5
        );
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getSearchResultElement(substring);
        this.waitForElementVisibleByXpath(search_result_xpath,
                String.format("Cannot find search result by substring '%s'", substring));
    }

    public void waitForCancelButtonToAppear()
    {
        waitForElementVisible(By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find cancel button.",
                5);
    }

    public void waitForCancelButtonToDisappear()
    {
        waitForElementNotVisible(By.id(SEARCH_CANCEL_BUTTON),
                "Search cancel button visible after timeout.",
                5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementVisibleAndClick(By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find and click search cancel button",
                5);
    }

    public void clearSearchInput() {
        this.waitForElementVisibleAndClear(By.id(SEARCH_TEXT_FIELD),
                "Cannot locate search text element to clear.",
                5);
    }

    public void clickOnArticleWithSubstring(String substring)
    {
        String search_result_xpath = getSearchResultElement(substring);
        this.waitForElementVisibleByXpathAndClick(search_result_xpath,
                String.format("Cannot find and click search result by substring '%s'", substring),
                10);
    }

    public void waitForAtLeastNSearchResults(
            String expected_substring,
            int minimum_expected_number_of_results) {

        this.waitForElementsVisible(
                By.xpath(getSearchResultElement(expected_substring)),
                minimum_expected_number_of_results - 1,
                String.format("Less than %d elements found by substring '%s'",
                        minimum_expected_number_of_results, expected_substring),
                15);
    }

    public void waitForSearchResultsToDisappear(String substring) {
        this.waitForElementNotVisible(
                By.xpath(getSearchResultElement(substring)),
                "Search result still visible after click on cancel button",
                5);
    }

    public void waitForSearchResultByTitleAndDescription(String title, String description) {
        String article_xpath = this.getSearchResultElementByTitleAndDescription(title, description);
        String error_message = String.format("Cannot locate article with title \"%s\' and description \"%s\".",
                title, description);
        this.waitForElementVisibleByXpath(article_xpath,
                error_message,
                5);
    }

    public void waitForSearchResultByWikiArticleObject(WikiArticle article) {
        String article_xpath = getSearchResultElementByWikiArticleObject(article);
        String error_message = String.format("Cannot locate article with title \"%s\' and description \"%s\".",
                article.getTitle(), article.getDescription());
        this.waitForElementVisibleByXpath(article_xpath,
                error_message,
                5);
    }

    public void assertNoSearchResultByTitleAndDescription(String title, String description) {
        String article_xpath = getSearchResultElementByTitleAndDescription(title, description);
        String error_message = String.format("Unexpectedly found element with title \"%s\" and description \"%s\".",
                title, description);

        this.waitForElementNotVisible(By.xpath(article_xpath),
                    error_message,
                    5);
    }
}
