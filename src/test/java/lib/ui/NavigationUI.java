package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject{

    protected static String
            MY_LISTS_LOCATOR;

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyLists()
    {
        this.waitForElementVisibleAndClick(
                MY_LISTS_LOCATOR,
                "Cannot locate 'My Lists' button.",
                15);
    }
}
