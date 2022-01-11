package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject{

    protected static String
            MY_LISTS_LOCATOR,
            OPEN_NAVIGATION_PANEL_LOCATOR;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Click 'My Lists' (or 'Saved Articles' on iOS and MobileWeb)")
    public void clickMyLists()
    {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(MY_LISTS_LOCATOR,
                    "Cannot locate 'My Lists' button.",
                    5);
        } else {
            this.waitForElementVisibleAndClick(
                    MY_LISTS_LOCATOR,
                    "Cannot locate 'My Lists' button.",
                    15);
        }
    }

    @Step("Click navigation panel icon (does nothing on Android and iOS)")
    public void openNavigation()
    {
        if (Platform.getInstance().isMW()) {
            this.waitForElementClickableAndClick(
                    OPEN_NAVIGATION_PANEL_LOCATOR,
                    "Cannot open navigation panel by locator " + OPEN_NAVIGATION_PANEL_LOCATOR,
                    5);
        } else {
            System.out.println("openNavigation() does nothing on platform: " + Platform.getInstance().getPlatformVar());
        }
    }
}
