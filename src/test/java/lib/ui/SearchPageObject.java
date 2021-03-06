package lib.ui;

import io.qameta.allure.Step;
import lib.util.WikiArticle;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
        NO_RESULTS_LABEL_XPATH,
        SEARCH_INIT_ELEMENT_XPATH,
        SEARCH_TEXT_FIELD_LOCATOR,
        SEARCH_CANCEL_BUTTON_LOCATOR,
        SEARCH_RESULT_LOCATOR,
        SEARCH_TEXT_FIELD_ID;

    /** STRING TEMPLATES BEGIN **/

    protected static String
            SEARCH_RESULT_BY_SUBSTRING_XPATH_TPL,
            SEARCH_ITEM_TITLE_AND_DESCRIPTION_TPL;

    /** STRING TEMPLATES END **/


    /** TEMPLATE METHODS BEGIN **/

    // Too specific for a @Step, no?
    private static String getSearchResultElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_XPATH_TPL.replace("{SUBSTRING}", substring);
    }

    // Too specific for a @Step, no?
    private static String getSearchResultElementByWikiArticleObject(WikiArticle article)
    {
        return SEARCH_ITEM_TITLE_AND_DESCRIPTION_TPL
                .replace("{TITLE}", article.getTitle())
                .replace("{DESCRIPTION}", article.getDescription());
    }

    // Too specific for a @Step, no?
    private static String getSearchResultElementByTitleAndDescription(
            String title, String description)
    {
        return SEARCH_ITEM_TITLE_AND_DESCRIPTION_TPL
                .replace("{TITLE}", title)
                .replace("{DESCRIPTION}", description);
    }

    /** TEMPLATE METHODS END **/


    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void initSearchInput()
    {
        this.waitForElementClickableAndClick(SEARCH_INIT_ELEMENT_XPATH,
                "Cannot click in search field at home screen.",
                5);
    }

    @Step("Type '{search_line}' into search input field")
    public void typeSearchLine(String search_line)
    {
        this.waitForElementVisibleAndSendKeys(
                SEARCH_TEXT_FIELD_LOCATOR,
                search_line,
                "Cannot type search line to search input",
                5
        );
    }

    @Step("Look for search result containing '{substring}' substring")
    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getSearchResultElement(substring);
        this.waitForElementVisibleByXpath(search_result_xpath,
                String.format("Cannot find search result by substring '%s'", substring));
    }

    @Step("Look for search cancel button")
    public void waitForCancelButtonToAppear()
    {
        waitForElementVisible(SEARCH_CANCEL_BUTTON_LOCATOR,
                "Cannot find cancel button.",
                5);
    }

    @Step("Wait for cancel button to disappear")
    public void waitForCancelButtonToDisappear()
    {
        waitForElementNotVisible(SEARCH_CANCEL_BUTTON_LOCATOR,
                "Search cancel button visible after timeout.",
                5);
    }

    @Step("Click search cancel button")
    public void clickCancelSearch()
    {
        this.waitForElementClickableAndClick(SEARCH_CANCEL_BUTTON_LOCATOR,
                "Cannot find and click search cancel button by locator: " + SEARCH_CANCEL_BUTTON_LOCATOR,
                10);
    }

    @Step("Clear search input")
    public void clearSearchInput() {
        this.waitForElementVisibleAndClear(SEARCH_TEXT_FIELD_LOCATOR,
                "Cannot locate search text element to clear.",
                5);
    }

    @Step("Click on search result containing '{substring}' substring'")
    public void clickOnArticleWithSubstring(String substring)
    {
        String search_result_xpath = getSearchResultElement(substring);
        this.waitForElementVisibleByXpathAndClick(search_result_xpath,
                String.format("Cannot find and click search result by substring '%s'", substring),
                10);
    }

    @Step("Wait for at least {minimum_expected_number_of_results} search results" +
            "with substring '{expected_substring}'")
    public void waitForAtLeastNSearchResults(
            String expected_substring,
            int minimum_expected_number_of_results) {

        String locator = getSearchResultElement(expected_substring);
        this.waitForElementsVisible(
                locator,
                minimum_expected_number_of_results - 1,
                String.format("Less than %d elements found by locator '%s'",
                        minimum_expected_number_of_results, locator),
                15);
    }

    @Step("Wait till search results disappear")
    public void waitForSearchResultsToDisappear(String substring) {
        this.waitForElementNotVisible(
                getSearchResultElement(substring),
                "Search result still visible after click on cancel button",
                5);
    }

    @Step("Get number of search results")
    public int getNumberOfSearchResults() {
        return this.getNumberOfElements(SEARCH_RESULT_LOCATOR);
    }

    @Step("Wait till 'No results' label appears")
    public void waitForNoResultsLabel() {
        this.waitForElementVisible(
                NO_RESULTS_LABEL_XPATH,
                String.format("Cannot locate item by xpath: \n%s", NO_RESULTS_LABEL_XPATH),
                5);
    }

    @Step("Assert that no results were found by query '{search_line}'")
    public void assertZeroSearchResults(String search_line) {
        this.assertZeroElementsVisible(SEARCH_RESULT_LOCATOR,
                String.format("Found results by query: '%s'", search_line));
    }

    @Step("Look for search result with title '{title}'" +
            "and description containing '{description}'")
    public void waitForSearchResultByTitleAndDescription(String title, String description) {
        String article_xpath = getSearchResultElementByTitleAndDescription(title, description);
        String error_message = String.format("Cannot locate article with title \"%s\' and description \"%s\".",
                title, description);
        this.waitForElementVisible(article_xpath,
                error_message,
                5);
    }

    @Step("Look for search result with title" +
            "and description from WikiArticle object")
    public void waitForSearchResultByWikiArticleObject(WikiArticle article) {
        String article_xpath = getSearchResultElementByWikiArticleObject(article);
        String error_message = String.format("Cannot locate article with title \"%s\' and description \"%s\".",
                article.getTitle(), article.getDescription());
        this.waitForElementVisible(article_xpath,
                error_message,
                5);
    }

    @Step("Assert that no item with title '{title}'" +
            "and description containing '{description}' was found")
    public void assertNoSearchResultByTitleAndDescription(String title, String description) {
        String article_xpath = getSearchResultElementByTitleAndDescription(title, description);
        String error_message = String.format("Unexpectedly found element with title \"%s\" and description \"%s\".",
                title, description);

        this.waitForElementNotVisible(article_xpath,
                error_message,
                5);
    }

    @Step("Wait for any search result to appear")
    public void waitForAnySearchResult() {
        try {
            this.waitForNoResultsLabel();
        } catch (Exception e) {
            this.waitForElementClickable(SEARCH_RESULT_LOCATOR,
                    "Failed to load search results.",
                    10);
        }
    }
}
