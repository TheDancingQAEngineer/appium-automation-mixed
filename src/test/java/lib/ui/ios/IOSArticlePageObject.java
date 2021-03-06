package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSArticlePageObject extends ArticlePageObject {

    static {
        ADD_TO_READING_LIST_LOCATOR = "id:Save for later";
        ARTICLE_TITLE_LOCATOR = "xpath://XCUIElementTypeOther[@name=\"Appium\"]";
        CLOSE_ARTICLE_BUTTON_LOCATOR = "id:Back";
        FOOTER_LOCATOR = "xpath://XCUIElementTypeStaticText[@name=\"View article in browser\"]";
        WEBVIEW_LOCATOR = "xpath://XCUIElementTypeWebView";
        DUMMY = "";
    }

    /** STRING TEMPLATES BEGIN **/

    static {
        ADD_TO_LIST_BY_NAME_XPATH_TPL = "xpath://*[@resource-id='org.wikipedia:id/list_of_lists']"
                + "//*[@resource-id='org.wikipedia:id/item_title']"
                + "[@text='{LIST_NAME}']";
        ARTICLE_TITLE_XPATH_TPL = "xpath://*[@name=\"{TITLE}\"]";
    }

    /** STRING TEMPLATES END **/

    /** CONSTRUCTOR BEGINS **/
    public IOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/

    /** OVERRIDDEN METHODS **/

}
