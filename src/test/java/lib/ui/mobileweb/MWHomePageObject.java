package lib.ui.mobileweb;

import lib.ui.HomePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWHomePageObject extends HomePageObject {

    static {
        DISMISS_BUTTON_ID = "";
        DISMISS_LOGIN_TO_SYNC_BUTTON_LOCATOR = "";
        LOGIN_BUTTON_ID = "";
        MANAGE_PREFERENCES_BUTTON_ID = "";
    }

    public MWHomePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
