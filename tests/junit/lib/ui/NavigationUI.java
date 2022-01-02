package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{

    private static final String
        MY_LISTS_XPATH = "xpath://android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyLists()
    {
        this.waitForElementVisibleAndClick(
                MY_LISTS_XPATH,
                "Cannot locate 'My Lists' button.",
                15);
    }
}
