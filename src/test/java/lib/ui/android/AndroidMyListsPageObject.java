package lib.ui.android;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidMyListsPageObject extends MyListsPageObject {

    static {
        READING_LIST_CONTENTS_ID = "id:org.wikipedia:id/reading_list_contents";
    }

    /** STRING TEMPLATES BEGIN **/

    static {
        READING_LIST_BY_NAME_XPATH_TPL = "xpath://*[@resource-id='org.wikipedia:id/reading_list_list']//*[@text='{LIST_NAME}']";
        ARTICLE_BY_TITLE_XPATH_TPL = "xpath://*[@resource-id='org.wikipedia:id/reading_list_contents']//*[@text=\"{TITLE}\"]";
    }

    /** STRING TEMPLATES END **/

    /** CONSTRUCTOR BEGINS **/
    public AndroidMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
