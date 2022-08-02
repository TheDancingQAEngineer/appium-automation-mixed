package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class HomePageObject extends MainPageObject {

    protected static String
            DISMISS_BUTTON_ID,
            DISMISS_LOGIN_TO_SYNC_BUTTON_LOCATOR,
            LOGIN_BUTTON_ID,
            MANAGE_PREFERENCES_BUTTON_ID;

    public HomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Click 'Dismiss' button")
    public void clickDismiss() {
        this.waitForElementClickableAndClick(
                DISMISS_BUTTON_ID,
                "Cannot find \"Dismiss\" button.",
                10);
    }

    @Step("Locate 'Log in to sync your saved articles' overlay")
    public void waitForLoginToSync() {
        this.waitForElementVisible(
                LOGIN_BUTTON_ID,
                "Cannot see \"Log in to sync your saved articles\" button.",
                10
        );
    }

    @Step("Wait for 'Manage preferences' button")
    public void waitForManagePreferences() {
        this.waitForElementVisible(
                MANAGE_PREFERENCES_BUTTON_ID,
                "Cannot see \"Manage preferences\" button.",
                10
        );
    }

    @Step("Click 'dismiss' button on 'Log in to sync your saved articles' overlay")
    public void dismissLogInToSyncSavedArticles() {
        try {
            this.waitForLoginToSync();
            this.waitForElementClickableAndClick(
                    DISMISS_LOGIN_TO_SYNC_BUTTON_LOCATOR,
                    "Cannot locate 'X' button in \"Log in to sync\" pop-up.",
                    5
            );
        } catch (NoSuchElementException e) {
            // do nothing and return
        }
    }
}
