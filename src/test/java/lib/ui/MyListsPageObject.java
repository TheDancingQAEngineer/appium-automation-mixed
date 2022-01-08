package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String
            READING_LIST_CONTENTS_ID;

    /** STRING TEMPLATES BEGIN **/

    protected static String
            READING_LIST_BY_NAME_XPATH_TPL,
            ARTICLE_BY_TITLE_XPATH_TPL,
            SWIPE_DELETE_BUTTON_LOCATOR;

    /** STRING TEMPLATES END **/

    /** TEMPLATE METHODS BEGIN **/

    private static String getListXpathByName(String list_name)
    {
        return READING_LIST_BY_NAME_XPATH_TPL
                .replace("{LIST_NAME}", list_name);
    }

    private static String getSavedArticleXpathByTitle(String title)
    {
        return ARTICLE_BY_TITLE_XPATH_TPL
                .replace("{TITLE}", title);
    }

    /** TEMPLATE METHODS END **/

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

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

    public void swipeArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);

        String article_title_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(article_title_xpath,
                "Swipe to left failed.");
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementVisible(article_xpath,
                String.format("Article '%s' not visible after timeout.", article_title),
                10);
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotVisible(article_xpath,
                String.format("Article '%s' still visible", article_title),
                10);
    }

        public void clickOnArticleByTitle(String title) {
        String article_xpath = getSavedArticleXpathByTitle(title);
        WebElement article_element = this.waitForElementVisible(
                article_xpath,
                String.format("Cannot find article by title '%s'", title),
                5);
        article_element.click();
    }

    public void clickSwipeDeleteButton() {
        this.waitForElementClickableAndClick(SWIPE_DELETE_BUTTON_LOCATOR,
                "Cannot locate 'Swipe action delete' button",
                10);
    }
}
