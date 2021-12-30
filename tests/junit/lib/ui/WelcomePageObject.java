package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject {

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink()
    {
        this.waitForElementVisible(
                By.name("Learn more about Wikipedia"),
                "Cannot find \"Learn more about Wikipedia\" link.",
                10);
    }

    public void clickNext()
    {
        this.waitForElementClickableAndClick(
                By.id("Next"),
                "Cannot find \"Next\" button.",
                10);
    }

    public void waitForNewWaysToExplore() {
        this.waitForElementVisible(
                By.id("New ways to explore"),
                "Cannot see \"New ways to explore\" text.",
                10
        );
    }

    public void waitForAddOrEditLanguages() {
        this.waitForElementVisible(
                By.id("Add or edit preferred languages"),
                "Cannot see \"Add or edit preferred languages\" text.",
                10
        );
    }

    public void clickGetStarted() {
        this.waitForElementClickableAndClick(
                By.id("Get started"),
                "Cannot find \"Get started\" button.",
                10);
    }

    public void clickDismiss() {
        this.waitForElementClickableAndClick(
                By.id("Dismiss"),
                "Cannot find \"Dismiss\" button.",
                10);
    }

    public void waitForLoginToSync() {
        this.waitForElementVisible(
                By.id("Log in to sync your saved articles"),
                "Cannot see \"Log in to sync your saved articles\" button.",
                10
        );
    }

    public void waitForManagePreferences() {
        this.waitForElementVisible(
                By.id("Manage preferences"),
                "Cannot see \"Manage preferences\" button.",
                10
        );
    }
}
