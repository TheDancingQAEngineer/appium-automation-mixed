package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;

public class HomePageObject extends MainPageObject {

    private static final String
            DISMISS_BUTTON_ID = "id:Dismiss",
            LOGIN_BUTTON_ID = "id:Log in to sync your saved articles",
            MANAGE_PREFERENCES_BUTTON_ID = "id:Manage preferences";

    public HomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void clickDismiss() {
        this.waitForElementClickableAndClick(
                DISMISS_BUTTON_ID,
                "Cannot find \"Dismiss\" button.",
                10);
    }

    public void waitForLoginToSync() {
        this.waitForElementVisible(
                LOGIN_BUTTON_ID,
                "Cannot see \"Log in to sync your saved articles\" button.",
                10
        );
    }

    public void waitForManagePreferences() {
        this.waitForElementVisible(
                MANAGE_PREFERENCES_BUTTON_ID,
                "Cannot see \"Manage preferences\" button.",
                10
        );
    }
}
