package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class AndroidNavigationUI extends NavigationUI {

    static {
        MY_LISTS_LOCATOR = "xpath://android.widget.FrameLayout[@content-desc='My lists']";
    }

    /** CONSTRUCTOR BEGINS **/
    public AndroidNavigationUI(AppiumDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
