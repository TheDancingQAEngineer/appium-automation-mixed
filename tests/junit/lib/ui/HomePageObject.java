package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.NoSuchElementException;

public class HomePageObject extends MainPageObject {

    protected static String
            DISMISS_BUTTON_ID,
            DISMISS_LOGIN_TO_SYNC_BUTTON_LOCATOR,
            LOGIN_BUTTON_ID,
            MANAGE_PREFERENCES_BUTTON_ID;

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

    public void dismissLogInToSyncSavedArticles() {
        try {
            this.waitForLoginToSync();
            this.waitForElementClickableAndClick(
                    DISMISS_LOGIN_TO_SYNC_BUTTON_LOCATOR,
                    "Cannot locate \"Log in to sync\" pop-up.",
                    5
            );
        } catch (NoSuchElementException e) {
            // do nothing and return
        }
    }
}
