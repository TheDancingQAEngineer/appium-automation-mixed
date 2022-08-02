package lib.ui.ios;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSSearchPageObject extends SearchPageObject {

    static {
        NO_RESULTS_LABEL_XPATH = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        SEARCH_INIT_ELEMENT_XPATH = "xpath://XCUIElementTypeSearchField[@label='Search Wikipedia']";
        SEARCH_TEXT_FIELD_LOCATOR = "xpath://XCUIElementTypeSearchField";
        SEARCH_CANCEL_BUTTON_LOCATOR = "xpath://XCUIElementTypeButton[@name=\"Cancel\"]";
        SEARCH_RESULT_LOCATOR = "xpath://XCUIElementTypeCollectionView" +
            "//XCUIElementTypeCell";
    }

    /** STRING TEMPLATES BEGIN **/

    static {
        SEARCH_RESULT_BY_SUBSTRING_XPATH_TPL = "xpath://XCUIElementTypeCell"
            + "//*[contains(@value, '{SUBSTRING}')]";
        SEARCH_ITEM_TITLE_AND_DESCRIPTION_TPL =
            "xpath://XCUIElementTypeCell"
            + "[.//*[@value=\"{TITLE}\"] and .//*[contains(@value, \"{DESCRIPTION}\")]]";
    }

    /** STRING TEMPLATES END **/


    /** TEMPLATE METHODS BEGIN **/

    /** TEMPLATE METHODS END **/


    /** CONSTRUCTOR BEGINS **/
    public IOSSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
