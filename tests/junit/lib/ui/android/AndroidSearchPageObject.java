package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;
import lib.util.WikiArticle;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
        NO_RESULTS_LABEL_XPATH = "xpath://*[@text='No results found']";
        SEARCH_INIT_ELEMENT_XPATH = "xpath://*[contains(@text, 'Search Wikipedia')]";
        SEARCH_TEXT_FIELD_XPATH = "xpath://*[@resource-id='org.wikipedia:id/search_src_text']";
        SEARCH_CANCEL_BUTTON_ID = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_XPATH = "xpath://*[@resource-id='org.wikipedia:id/search_results_container']" +
            "//*[@resource-id='org.wikipedia:id/page_list_item_container']";
        SEARCH_TEXT_FIELD_ID = "id:org.wikipedia:id/search_src_text";
    }

    /** STRING TEMPLATES BEGIN **/

    static {
        SEARCH_RESULT_BY_SUBSTRING_XPATH_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']"
            + "//*[contains(@text, '{SUBSTRING}')]";
        SEARCH_ITEM_TITLE_AND_DESCRIPTION_TPL =
            "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']"
            + "[.//@text=\"{TITLE}\" and .//@text=\"{DESCRIPTION}\"]";
    }

    /** STRING TEMPLATES END **/


    /** TEMPLATE METHODS BEGIN **/

    private static String getSearchResultElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_XPATH_TPL.replace("{SUBSTRING}", substring);
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


    /** CONSTRUCTOR BEGINS **/
    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/

    public void initSearchInput()
    {
        this.waitForElementVisibleByXpath(SEARCH_INIT_ELEMENT_XPATH,
                "Cannot find search field on start screen.",
                5);

        this.waitForElementVisibleByXpathAndClick(SEARCH_INIT_ELEMENT_XPATH,
                "Cannot click in search field at home screen.",
                5);

        this.waitForElementVisibleByXpath(SEARCH_TEXT_FIELD_XPATH,
                "Cannot find search input after tap.",
                5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementVisibleAndSendKeys(
                SEARCH_TEXT_FIELD_XPATH,
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
        waitForElementVisible(SEARCH_CANCEL_BUTTON_ID,
                "Cannot find cancel button.",
                5);
    }

    public void waitForCancelButtonToDisappear()
    {
        waitForElementNotVisible(SEARCH_CANCEL_BUTTON_ID,
                "Search cancel button visible after timeout.",
                5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementVisibleAndClick(SEARCH_CANCEL_BUTTON_ID,
                "Cannot find and click search cancel button",
                5);
    }

    public void clearSearchInput() {
        this.waitForElementVisibleAndClear(SEARCH_TEXT_FIELD_ID,
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
                getSearchResultElement(expected_substring),
                minimum_expected_number_of_results - 1,
                String.format("Less than %d elements found by substring '%s'",
                        minimum_expected_number_of_results, expected_substring),
                15);
    }

    public void waitForSearchResultsToDisappear(String substring) {
        this.waitForElementNotVisible(
                getSearchResultElement(substring),
                "Search result still visible after click on cancel button",
                5);
    }

    public int getNumberOfSearchResults() {
        return this.getNumberOfElements(SEARCH_RESULT_XPATH);
    }

    public void waitForNoResultsLabel() {
        this.waitForElementVisible(
                NO_RESULTS_LABEL_XPATH,
                String.format("Cannot locate item by xpath: \n%s", NO_RESULTS_LABEL_XPATH),
                10);
    }

    public void assertZeroSearchResults(String search_line) {
        this.assertZeroElementsVisible(SEARCH_RESULT_XPATH,
                String.format("Found results by query: '%s'", search_line));
    }

    public void waitForSearchResultByTitleAndDescription(String title, String description) {
        String article_xpath = this.getSearchResultElementByTitleAndDescription(title, description);
        String error_message = String.format("Cannot locate article with title \"%s\' and description \"%s\".",
                title, description);
        this.waitForElementVisible(article_xpath,
                error_message,
                5);
    }

    public void waitForSearchResultByWikiArticleObject(WikiArticle article) {
        String article_xpath = getSearchResultElementByWikiArticleObject(article);
        String error_message = String.format("Cannot locate article with title \"%s\' and description \"%s\".",
                article.getTitle(), article.getDescription());
        this.waitForElementVisible(article_xpath,
                error_message,
                5);
    }

    public void assertNoSearchResultByTitleAndDescription(String title, String description) {
        String article_xpath = getSearchResultElementByTitleAndDescription(title, description);
        String error_message = String.format("Unexpectedly found element with title \"%s\" and description \"%s\".",
                title, description);

        this.waitForElementNotVisible(article_xpath,
                error_message,
                5);
    }

    public void waitForAnySearchResult() {
        try {
            this.waitForNoResultsLabel();
        } catch (Exception e) {
            this.waitForElementClickable(SEARCH_RESULT_XPATH,
                    "Failed to load search results.",
                    10);
        }
    }
}
