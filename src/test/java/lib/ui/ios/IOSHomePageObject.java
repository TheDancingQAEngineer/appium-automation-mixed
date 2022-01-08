package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.HomePageObject;
import lib.ui.MainPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSHomePageObject extends HomePageObject {

    static {
        DISMISS_BUTTON_ID = "id:Dismiss";
        DISMISS_LOGIN_TO_SYNC_BUTTON_LOCATOR = "id:Close";
        LOGIN_BUTTON_ID = "id:Log in to sync your saved articles";
        MANAGE_PREFERENCES_BUTTON_ID = "id:Manage preferences";
    }

    public IOSHomePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
