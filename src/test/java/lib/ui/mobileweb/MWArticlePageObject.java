package lib.ui.mobileweb;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        ADD_TO_READING_LIST_LOCATOR = "css:#page-actions #ca-watch[class*='page-actions-watch'][role=button]"; // OK
        REMOVE_FROM_READING_LIST_LOCATOR = "css:#page-actions #ca-watch[class*='page-actions-watch'][role=button][class*='watched']"; // OK
        ARTICLE_TITLE_LOCATOR = "css:#content h1"; // OK
        FOOTER_LOCATOR = "css:footer.mw-footer"; // OK
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
    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/

    /** OVERRIDDEN METHODS **/

}
