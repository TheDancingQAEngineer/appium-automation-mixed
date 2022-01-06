package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IOSArticlePageObject extends ArticlePageObject {

    static {
        ADD_TO_READING_LIST_LOCATOR = "id:Save for later";
        ARTICLE_TITLE_LOCATOR = "xpath://XCUIElementTypeOther[@name=\"Appium\"]";
        CLOSE_ARTICLE_BUTTON_LOCATOR = "id:Back";
        FOOTER_XPATH = "xpath://XCUIElementTypeStaticText[@name=\"View article in browser\"]";
        DISMISS_LOGIN_BUTTON_LOCATOR = "id:places auth close";
        DUMMY = "";
    }

    /** STRING TEMPLATES BEGIN **/

    static {
        ADD_TO_LIST_BY_NAME_XPATH_TPL = "xpath://*[@resource-id='org.wikipedia:id/list_of_lists']"
                + "//*[@resource-id='org.wikipedia:id/item_title']"
                + "[@text='{LIST_NAME}']";
        ARTICLE_TITLE_XPATH_TPL = "xpath://XCUIElementTypeOther[@name=\"{TITLE}\"]";
    }

    /** STRING TEMPLATES END **/

    /** CONSTRUCTOR BEGINS **/
    public IOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/

    /** OVERRIDDEN METHODS **/

}
