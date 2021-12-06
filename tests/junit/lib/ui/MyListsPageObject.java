package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MyListsPageObject extends MainPageObject{

    private static final String
            READING_LIST_BY_NAME_XPATH_TPL = "//*[@resource-id='org.wikipedia:id/reading_list_list']//*[@text='{LIST_NAME}']",
            ARTICLE_BY_TITLE_XPATH_TPL = "//*[@resource-id='org.wikipedia:id/reading_list_contents']//*[@text='{TITLE}']",
            READING_LIST_CONTENTS_ID = "org.wikipedia:id/reading_list_contents";

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

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    protected void swipeElementToLeft(By by, String error_message)
    {
        WebElement element = this.waitForElementVisible(by, error_message, 10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();

        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int mid_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);

        action
                .press(right_x, mid_y)
                .waitAction(500)
                .moveTo(left_x, mid_y)
                .release()
                .perform();
    }

    public void openReadingListByName(String list_name)
    {
        String list_name_xpath = getListXpathByName(list_name);
        this.waitForElementClickableAndClick(
                By.xpath(list_name_xpath),
                String.format("Cannot find '%s' in My lists.", list_name),
                5);
        this.waitForElementVisible(By.id(READING_LIST_CONTENTS_ID),
                "Reading list contents didn't open after tap",
                5);
    }

    public void swipeArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);

        String article_title_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(By.xpath(article_title_xpath),
                "Swipe to left failed.");

        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementVisible(By.xpath(article_xpath),
                String.format("Article '%s' not visible after timeout.", article_title),
                10);
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotVisible(By.xpath(article_xpath),
                String.format("Article '%s' still visible", article_title),
                10);
    }

    // TODO: Implement clickOnArticleByTitle(String title);
    public void clickOnArticleByTitle(String title) {
        String article_xpath = getSavedArticleXpathByTitle(title);
        WebElement article_element = this.waitForElementVisible(
                By.xpath(article_xpath),
                String.format("Cannot find article by title '%s'", title),
                5);
        article_element.click();
    }

}
