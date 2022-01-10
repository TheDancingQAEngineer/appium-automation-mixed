package lib.ui.mobileweb;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        NO_RESULTS_LABEL_XPATH = "css:div.caption>p.without-results"; // OK
        SEARCH_INIT_ELEMENT_XPATH = "css:button#searchIcon"; // OK
        SEARCH_TEXT_FIELD_LOCATOR = "css:form>input[type='search']"; // OK
        SEARCH_CANCEL_BUTTON_LOCATOR = "css:div>button.cancel"; // OK
        SEARCH_RESULT_LOCATOR = "css:ul.page-list>li.page-summary"; // OK
    }

    /** STRING TEMPLATES BEGIN **/

    static {
        // Should work if either title or description contains {SUBSTRING}
        SEARCH_RESULT_BY_SUBSTRING_XPATH_TPL =
                "xpath://li[contains(@class, 'page-summary')][contains(@title, '{SUBSTRING}') or .//*[contains(@class, 'description')][contains(text(), '{SUBSTRING}')]]";

        // Should work if title contains {TITLE} AND description contains {DESCRIPTION}
        SEARCH_ITEM_TITLE_AND_DESCRIPTION_TPL =
                "xpath://li[contains(@class, 'page-summary')][@title='{TITLE}' and .//*[contains(@class, 'description')][contains(text(), '{DESCRIPTION}')]]";
    }

    /** STRING TEMPLATES END **/

    /** CONSTRUCTOR BEGINS **/
        public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
