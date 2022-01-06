package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
        NO_RESULTS_LABEL_XPATH = "xpath://*[@text='No results found']";
        SEARCH_INIT_ELEMENT_XPATH = "xpath://*[contains(@text, 'Search Wikipedia')]";
        SEARCH_TEXT_FIELD_XPATH = "xpath://*[@resource-id='org.wikipedia:id/search_src_text']";
        SEARCH_CANCEL_BUTTON_ID = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_XPATH = "xpath://*[@resource-id='org.wikipedia:id/search_results_container']" +
            "//*[@resource-id='org.wikipedia:id/page_list_item_container']";
        SEARCH_TEXT_FIELD_ID = "id:org.wikipedia:id/search_src_text";
    }

    /** STRING TEMPLATES BEGIN **/

    static {
        SEARCH_RESULT_BY_SUBSTRING_XPATH_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']"
            + "//*[contains(@text, '{SUBSTRING}')]";
        SEARCH_ITEM_TITLE_AND_DESCRIPTION_TPL =
            "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']"
            + "[.//@text=\"{TITLE}\" and .//@text=\"{DESCRIPTION}\"]";
    }

    /** STRING TEMPLATES END **/


    /** TEMPLATE METHODS BEGIN **/

    /** TEMPLATE METHODS END **/


    /** CONSTRUCTOR BEGINS **/
    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
