package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject {


    static {
        ADD_TO_READING_LIST_LOCATOR = "xpath://*[@text='Add to reading list']";
        ARTICLE_TITLE_LOCATOR = "id:org.wikipedia:id/view_page_title_text";
        CLOSE_ARTICLE_BUTTON_LOCATOR = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
        FOOTER_XPATH = "xpath://*[@text='View page in browser']";
        OK_BUTTON_XPATH = "xpath://*[@text='OK']";
        ONBOARDING_BUTTON_ID = "id:org.wikipedia:id/onboarding_button";
        THREE_DOTS_XPATH = "xpath://*[@resource-id='org.wikipedia:id/page_toolbar']" +
            "//*[@content-desc='More options']";
        TEXT_INPUT_ID = "id:org.wikipedia:id/text_input";
        DUMMY = "";
    }

    /** STRING TEMPLATES BEGIN **/

    static {
        ADD_TO_LIST_BY_NAME_XPATH_TPL = "xpath://*[@resource-id='org.wikipedia:id/list_of_lists']"
                + "//*[@resource-id='org.wikipedia:id/item_title']"
                + "[@text='{LIST_NAME}']";
    }

    /** STRING TEMPLATES END **/

    /** CONSTRUCTOR BEGINS **/
    public AndroidArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
