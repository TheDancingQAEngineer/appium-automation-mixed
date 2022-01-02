package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class HomePageObject extends MainPageObject {

    private static final String
            DISMISS_BUTTON_ID = "Dismiss",
            LOGIN_BUTTON_ID = "Log in to sync your saved articles",
            MANAGE_PREFERENCES_BUTTON_ID = "Manage preferences";

    public HomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void clickDismiss() {
        this.waitForElementClickableAndClick(
                By.id(DISMISS_BUTTON_ID),
                "Cannot find \"Dismiss\" button.",
                10);
    }

    public void waitForLoginToSync() {
        this.waitForElementVisible(
                By.id(LOGIN_BUTTON_ID),
                "Cannot see \"Log in to sync your saved articles\" button.",
                10
        );
    }

    public void waitForManagePreferences() {
        this.waitForElementVisible(
                By.id(MANAGE_PREFERENCES_BUTTON_ID),
                "Cannot see \"Manage preferences\" button.",
                10
        );
    }
}
