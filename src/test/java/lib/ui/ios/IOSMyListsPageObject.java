package lib.ui.ios;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSMyListsPageObject extends MyListsPageObject {

    static {
        READING_LIST_CONTENTS_ID = "id:org.wikipedia:id/reading_list_contents";
        SWIPE_DELETE_BUTTON_LOCATOR = "id:swipe action delete";
    }

    /** STRING TEMPLATES BEGIN **/

    static {
        ARTICLE_BY_TITLE_XPATH_TPL = "xpath://XCUIElementTypeCell"
                + "//*[contains(@value, '{TITLE}')]";
    }

    /** STRING TEMPLATES END **/

    /** CONSTRUCTOR BEGINS **/
    public IOSMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
