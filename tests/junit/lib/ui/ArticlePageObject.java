package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.*;

public class ArticlePageObject extends MainPageObject{

    private static final String
        ADD_TO_READING_LIST_XPATH = "xpath://*[@text='Add to reading list']",
        ARTICLE_TITLE_ID = "id:org.wikipedia:id/view_page_title_text",
        CLOSE_ARTICLE_BUTTON_XPATH = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
        FOOTER_XPATH = "xpath://*[@text='View page in browser']",
        OK_BUTTON_XPATH = "xpath://*[@text='OK']",
        ONBOARDING_BUTTON_ID = "id:org.wikipedia:id/onboarding_button",
        THREE_DOTS_XPATH = "xpath://*[@resource-id='org.wikipedia:id/page_toolbar']" +
                "//*[@content-desc='More options']",
        TEXT_INPUT_ID = "id:org.wikipedia:id/text_input",
        DUMMY = "";

    /** STRING TEMPLATES BEGIN **/

    private static final String
            ADD_TO_LIST_BY_NAME_XPATH_TPL = "xpath://*[@resource-id='org.wikipedia:id/list_of_lists']"
            + "//*[@resource-id='org.wikipedia:id/item_title']"
            + "[@text='{LIST_NAME}']";

    /** STRING TEMPLATES END **/

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    /** TEMPLATE METHODS BEGIN **/

    private String getReadingListXpathFromName(String list_name) {
        return ADD_TO_LIST_BY_NAME_XPATH_TPL.replace("{LIST_NAME}", list_name);
    }

    /** TEMPLATE METHODS END **/

    public WebElement waitForTitleElement()
    {
        return this.waitForElementVisible(ARTICLE_TITLE_ID,
                "Cannot find article title on page",
                15);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpTillElement(FOOTER_XPATH,
                "Cannot find footer.", 20);
    }

    private void swipeUpTillElement(String locator_with_type, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (driver.findElements(getLocatorByString(locator_with_type)).size() == 0) {

            if (already_swiped >= max_swipes) {
                waitForElementVisible(locator_with_type,
                        "Cannot find element by swiping up. \n" + error_message,
                        0);
                return;
            }

            swipeUpQuick();
            already_swiped++;
        }
    }

    private void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = (int) (size.width / 2);
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        this.swipeByCoordinates(x, start_y, x, end_y, timeOfSwipe);
    }

    public void addArticleToReadingList(String list_name)
    {
        // TODO: Adjust for different flow on first and subsequent additions
        // Tap "Three dots"
        this.waitForElementVisibleAndClick(
                THREE_DOTS_XPATH,
                "Cannot locate three dots.",
                10);

        // Tap "Add to reading list"
        this.waitForElementClickableAndClick(
                ADD_TO_READING_LIST_XPATH,
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

    public void closeArticle() {
        waitForElementVisibleAndClick(
                CLOSE_ARTICLE_BUTTON_XPATH,
                "Cannot locate 'X' to close article.",
                5);
    }

    public void assertTitleElementPresent()
    {
        try {
            // TODO: Refactor
            WebElement element = driver.findElement(By.id(ARTICLE_TITLE_ID));
        } catch (NoSuchElementException e) {
            String default_message = String.format("Article title not found by id: %s.", ARTICLE_TITLE_ID);
            throw new AssertionError(default_message);
        }
    }

    public void assertTitleMatches(String expected) {
        String article_title = this.getArticleTitle();

        Assert.assertEquals(
                String.format("Expected title '%s', got '%s'\n",
                        expected, article_title),
                expected,
                article_title);
    }
}
