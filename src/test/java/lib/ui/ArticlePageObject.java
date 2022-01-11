package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject{

    protected static String
            ADD_TO_READING_LIST_LOCATOR,
            REMOVE_FROM_READING_LIST_LOCATOR,
            ARTICLE_TITLE_LOCATOR,
            CLOSE_ARTICLE_BUTTON_LOCATOR,
            FOOTER_LOCATOR,
            OK_BUTTON_XPATH,
            ONBOARDING_BUTTON_ID,
            THREE_DOTS_XPATH,
            TEXT_INPUT_ID,
            WEBVIEW_LOCATOR,
            DUMMY;

    /** STRING TEMPLATES BEGIN **/

    protected static String
            ADD_TO_LIST_BY_NAME_XPATH_TPL,
            ARTICLE_TITLE_XPATH_TPL;

    /** STRING TEMPLATES END **/

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /** TEMPLATE METHODS BEGIN **/

    // Too specific for a @Step, no?
    private String getReadingListXpathFromName(String list_name) {
        return ADD_TO_LIST_BY_NAME_XPATH_TPL.replace("{LIST_NAME}", list_name);
    }

    // Too specific for a @Step, no?
    private String getArticleTitleXpathFromTitle(String title) {
        return ARTICLE_TITLE_XPATH_TPL.replace("{TITLE}", title);
    }

    /** TEMPLATE METHODS END **/

    @Step("Wait till article title is visible")
    public WebElement waitForTitleElement(String title_string)
    {
        String locator;
        if (Platform.getInstance().isIOS()) {
            locator = this.getArticleTitleXpathFromTitle(title_string);
            this.waitForWebViewElement();
        } else {
            locator = ARTICLE_TITLE_LOCATOR;
        }

        return this.waitForElementVisible(locator,
                "Cannot find article title by locator:" + locator,
                15);
    }

    @Step("Get article title")
    public String getArticleTitle(String expected_title) {
        WebElement title_element = this.waitForTitleElement(expected_title);
        screenshot(this.takeScreenshot("article_title"));

        if (Platform.getInstance().isIOS()) {
            return title_element.getAttribute("name");
        } else if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else {
            return title_element.getText();
        }
    }

    @Step("Swipe article till footer")
    public void swipeToFooter()
    {
        if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppears(FOOTER_LOCATOR,
                    "Cannot find footer by locator " + FOOTER_LOCATOR, 40);
        } else if (Platform.getInstance().isAndroid()) {
            this.swipeUpTillElement(FOOTER_LOCATOR,
                    "Cannot find footer by locator " + FOOTER_LOCATOR, 40);
        } else {
            this.scrollWebPageTillElementVisible(FOOTER_LOCATOR,
                    "Cannot find footer by locator " + FOOTER_LOCATOR, 40);
        }
    }

    @Step("Add current article to list '{list_name}', or 'Save for later', or 'Watch'")
    public void addArticleToReadingList(String list_name)
    {
        if (Platform.getInstance().isIOS()) {
            this.saveArticleForLaterIOS();
        } else if (Platform.getInstance().isAndroid()){
            this.addArticleToReadingListAndroid(list_name);
        } else {
            this.addArticleToWatchListMW();
        }
    }

    private void addArticleToWatchListMW() {
        this.removeArticleFromSavedIfAdded();
        this.tryClickElementWithFewAttempts(
            ADD_TO_READING_LIST_LOCATOR,
            "Cannot find 'Save for later' button.",
            10);
    }

    private void addArticleToReadingListAndroid(String list_name)
    {
                // Tap "Three dots"
        this.waitForElementVisibleAndClick(
                THREE_DOTS_XPATH,
                "Cannot locate three dots.",
                10);

        // Tap "Add to reading list"
        this.waitForElementClickableAndClick(
                ADD_TO_READING_LIST_LOCATOR,
                "Cannot find 'Add to reading list' menu item.",
                10);

        String reading_list_xpath = getReadingListXpathFromName(list_name);
        try {
            // This works when list 'list_name' already exists
            this.waitForElementVisibleAndClick(reading_list_xpath,
                    String.format("Cannot find reading list by name '%s'", list_name),
                    10);
        } catch (TimeoutException e) {
            // This should work on first run
            this.waitForElementVisibleAndClick(
                    ONBOARDING_BUTTON_ID,
                    "Cannot find onboarding button.",
                    10);

            this.waitForElementVisibleAndClear(
                    TEXT_INPUT_ID,
                    "Cannot clear input in reading list name.",
                    5);

            // Enter list name
            this.waitForElementVisibleAndSendKeys(
                    TEXT_INPUT_ID,
                    list_name,
                    "Cannot send keys to text input.",
                    10);

            // Tap 'OK'
            this.waitForElementVisibleAndClick(
                    OK_BUTTON_XPATH,
                    "Cannot find OK button.",
                    10);
        }
    }

    private void saveArticleForLaterIOS() {
        this.waitForElementClickableAndClick(
                ADD_TO_READING_LIST_LOCATOR,
                "Cannot find 'Save for later' button.",
                10);
    }

    public void closeArticle() {
        if (Platform.getInstance().isMW()) {
            System.out.println("No need to close article on platform: " + Platform.getInstance().getPlatformVar());
        } else {
            waitForElementVisibleAndClick(
                    CLOSE_ARTICLE_BUTTON_LOCATOR,
                    "Cannot locate 'X' to close article.",
                    5);
        }
    }

    @Step("Check that title element is on the page")
    public void assertTitleElementPresent(String expected_title)
    {
        String locator;
        if (Platform.getInstance().isIOS()) {
            locator = this.getArticleTitleXpathFromTitle(expected_title);
        } else {
            locator = ARTICLE_TITLE_LOCATOR;
        }

        try {
            By by = this.getLocatorByString(locator);
            WebElement element = driver.findElement(by);
        } catch (NoSuchElementException e) {
            String default_message = String.format("Article title not found by id: %s.", locator);
            throw new AssertionError(default_message);
        }
    }

    // TODO: Bad logic for iOS! Fix!
    @Step("Check that article is '{expected}'")
    public void assertTitleMatches(String expected) {
        String article_title = this.getArticleTitle(expected);

        Assert.assertEquals(
                String.format("Expected title '%s', got '%s'\n",
                        expected, article_title),
                expected,
                article_title);
    }

    private void waitForWebViewElement() {
        this.waitForElementVisible(WEBVIEW_LOCATOR,
                "Cannot locate webview element by locator:" + WEBVIEW_LOCATOR,
                10);
    }

    @Step("[mobileweb] If article already saved to 'watched', unwatch before proceeding")
    public void removeArticleFromSavedIfAdded()
    {
        if (this.isElementPresent(REMOVE_FROM_READING_LIST_LOCATOR)) {
            tryClickElementWithFewAttempts(REMOVE_FROM_READING_LIST_LOCATOR,
                    "Cannot click 'Unwatch' button by locator " + REMOVE_FROM_READING_LIST_LOCATOR,
                    5);
            waitForElementNotVisible(REMOVE_FROM_READING_LIST_LOCATOR,
                    "After unwatching, 'Unwatch' button is visible after timeout by locator " + ADD_TO_READING_LIST_LOCATOR,
                    5);
            waitForElementVisible(ADD_TO_READING_LIST_LOCATOR,
                    "After unwatching, 'Watch' button is not visible by locator " + ADD_TO_READING_LIST_LOCATOR,
                    5);
        }
    }
}
