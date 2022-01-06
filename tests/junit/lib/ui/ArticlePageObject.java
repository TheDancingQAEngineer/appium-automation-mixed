package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.*;

abstract public class ArticlePageObject extends MainPageObject{

    protected static String
            ADD_TO_READING_LIST_LOCATOR,
            ARTICLE_TITLE_LOCATOR,
            CLOSE_ARTICLE_BUTTON_LOCATOR,
            FOOTER_XPATH,
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

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    /** TEMPLATE METHODS BEGIN **/

    private String getReadingListXpathFromName(String list_name) {
        return ADD_TO_LIST_BY_NAME_XPATH_TPL.replace("{LIST_NAME}", list_name);
    }

    private String getArticleTitleXpathFromTitle(String title) {
        return ARTICLE_TITLE_XPATH_TPL.replace("{TITLE}", title);
    }

    /** TEMPLATE METHODS END **/

    public WebElement waitForTitleElement(String title_string)
    {
        String locator;
        if (Platform.getInstance().isIOS()) {
            locator = this.getArticleTitleXpathFromTitle(title_string);
        } else {
            locator = ARTICLE_TITLE_LOCATOR;
        }

        return this.waitForElementVisible(locator,
                "Cannot find article title by locator:" + locator,
                15);
    }

    public String getArticleTitle(String expected_title) {
        if (Platform.getInstance().isIOS()) {
            return getArticleTitleIOS(expected_title);
        } else {
            return getArticleTitleAndroid();
        }
    }

    // TODO: Bad logic! Doesn't actually check anything! Rework locators!
    private String getArticleTitleIOS(String expected_title) {
        // get an element locator from title
        String locator = this.getArticleTitleXpathFromTitle(expected_title);

        this.waitForWebViewElement();
        // Wait for element to appear
        WebElement title_element = this.waitForElementVisible(
                locator,
                "Cannot locate title by locator:" + locator,
                15);

        return title_element.getAttribute("name");
    }

    private String getArticleTitleAndroid() {
        WebElement title_element = waitForTitleElement(ARTICLE_TITLE_LOCATOR);
        return title_element.getAttribute("text");
    }

    public void swipeToFooter()
    {
        if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppears(FOOTER_XPATH,
                    "Cannot find footer.", 20);
        } else {
            this.swipeUpTillElement(FOOTER_XPATH,
                    "Cannot find footer.", 20);
        }
    }

    public void addArticleToReadingList(String list_name)
    {
        if (Platform.getInstance().isIOS()) {
            this.saveArticleForLaterIOS();
        } else {
            this.addArticleToReadingListAndroid(list_name);
        }
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
        waitForElementVisibleAndClick(
                CLOSE_ARTICLE_BUTTON_LOCATOR,
                "Cannot locate 'X' to close article.",
                5);
    }

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
}
