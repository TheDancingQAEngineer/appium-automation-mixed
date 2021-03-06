package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String
            READING_LIST_CONTENTS_ID,
            SWIPE_DELETE_BUTTON_LOCATOR;

    /** STRING TEMPLATES BEGIN **/

    protected static String
            READING_LIST_BY_NAME_XPATH_TPL,
            ARTICLE_BY_TITLE_XPATH_TPL,
            UNWATCH_BUTTON_BY_TITLE_LOCATOR_TPL;

    /** STRING TEMPLATES END **/

    /** TEMPLATE METHODS BEGIN **/

    // Too specific for a @Step, no?
    private static String getListXpathByName(String list_name)
    {
        return READING_LIST_BY_NAME_XPATH_TPL
                .replace("{LIST_NAME}", list_name);
    }

    // Too specific for a @Step, no?
    private static String getSavedArticleXpathByTitle(String title)
    {
        return ARTICLE_BY_TITLE_XPATH_TPL
                .replace("{TITLE}", title);
    }

    // Too specific for a @Step, no?
    private static String getUnwatchButtonByTitle(String title)
    {
        return UNWATCH_BUTTON_BY_TITLE_LOCATOR_TPL
                .replace("{TITLE}", title);
    }

    /** TEMPLATE METHODS END **/

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("On Android, open named reading list '{list_name}'")
    public void openReadingListByName(String list_name)
    {
        String list_name_xpath = getListXpathByName(list_name);
        this.waitForElementClickableAndClick(
                list_name_xpath,
                String.format("Cannot find '%s' in My lists.", list_name),
                5);
        this.waitForElementVisible(READING_LIST_CONTENTS_ID,
                "Reading list contents didn't open after tap",
                5);
    }

    @Step("Swipe away/unwatch article '{article_title}'")
    public void swipeArticleToDelete(String article_title)
    {
        if (Platform.getInstance().isMW()) {
            this.unwatchArticleMW(article_title);
        } else {
            this.swipeArticleToDeleteNative(article_title);
            if (Platform.getInstance().isIOS()) {
                this.clickSwipeDeleteButton();
            }
        }
    }

    @Step("On mobile web, click 'Unwatch' button of article '{article_title}'")
    private void unwatchArticleMW(String article_title) {
        String unwatch_locator = getUnwatchButtonByTitle(article_title);
        this.waitForElementClickableAndClick(unwatch_locator,
                "Cannot locate 'Unwatch' button for article '" + article_title +
                "' by locator " + unwatch_locator, 5);
        this.waitForElementNotVisible(unwatch_locator,
                "Page Refresh may be incomplete. Element still visible by locator "
                        + unwatch_locator, 5);
        driver.navigate().refresh();

    }

    @Step("On mobile emulator, swipe article '{article_title}' to the left")
    private void swipeArticleToDeleteNative(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);

        String article_title_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(article_title_xpath,
                "Swipe to left failed.");
    }

    @Step("Wait till article '{article_title}' is visible")
    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementVisible(article_xpath,
                String.format("Article '%s' not visible after timeout.", article_title),
                10);
    }

    @Step("Wait till article '{article_title}' is no longer visible")
    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotVisible(article_xpath,
                String.format("Article '%s' still visible", article_title),
                10);
    }

    @Step("Click on article with title '{title}'")
    public void clickOnArticleByTitle(String title) {
        String article_xpath = getSavedArticleXpathByTitle(title);
        WebElement article_element = this.waitForElementVisible(
                article_xpath,
                String.format("Cannot find article by title '%s'", title),
                5);
        article_element.click();
    }

    @Step("On iOS, click 'Delete' button after swipe")
    public void clickSwipeDeleteButton() {
        this.waitForElementClickableAndClick(SWIPE_DELETE_BUTTON_LOCATOR,
                "Cannot locate 'Swipe action delete' button",
                10);
    }
}
