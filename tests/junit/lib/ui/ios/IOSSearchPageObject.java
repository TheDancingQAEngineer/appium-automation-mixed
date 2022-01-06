package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class IOSSearchPageObject extends SearchPageObject {

    static {
        NO_RESULTS_LABEL_XPATH = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        SEARCH_INIT_ELEMENT_XPATH = "xpath://XCUIElementTypeSearchField[@label='Search Wikipedia']";
        SEARCH_TEXT_FIELD_XPATH = "xpath://XCUIElementTypeSearchField[@value='Search Wikipedia' and not(@label='Search Wikipedia')]";
        SEARCH_CANCEL_BUTTON_ID = "id:Close";
        SEARCH_RESULT_XPATH = "xpath://XCUIElementTypeCollectionView" +
            "//XCUIElementTypeCell";
        SEARCH_TEXT_FIELD_ID = "id:org.wikipedia:id/search_src_text";
    }

    /** STRING TEMPLATES BEGIN **/

    static {
        SEARCH_RESULT_BY_SUBSTRING_XPATH_TPL = "xpath://XCUIElementTypeCell"
            + "//*[contains(@value, '{SUBSTRING}')]";
        SEARCH_ITEM_TITLE_AND_DESCRIPTION_TPL =
            "xpath://XCUIElementTypeCell"
            + "[.//@value=\"{TITLE}\" and .//@value=\"{DESCRIPTION}\"]";
    }

    /** STRING TEMPLATES END **/


    /** TEMPLATE METHODS BEGIN **/

    /** TEMPLATE METHODS END **/


    /** CONSTRUCTOR BEGINS **/
    public IOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
