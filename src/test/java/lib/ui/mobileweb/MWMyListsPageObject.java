package lib.ui.mobileweb;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {

    private static final String MW_WIKI_TEN_MINUTE_MAIL = "hereha9135@unigeol.com";

    static {
        READING_LIST_CONTENTS_ID = "id:org.wikipedia:id/reading_list_contents";
    }

    /** STRING TEMPLATES BEGIN **/

    static {
        ARTICLE_BY_TITLE_XPATH_TPL = "xpath://ul[contains(@class, 'watchlist')]//h3[contains(text(), '{TITLE}')]";
        UNWATCH_BUTTON_BY_TITLE_LOCATOR_TPL = "xpath://ul[contains(@class, 'watchlist')]//h3[contains(text(), '{TITLE}')]/../../*[contains(@class, 'watched')]";
    }

    /** STRING TEMPLATES END **/

    /** CONSTRUCTOR BEGINS **/
    public MWMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
