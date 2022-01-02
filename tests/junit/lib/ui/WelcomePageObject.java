package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject {

    private static final String
            LEARN_MORE_ABOUT_WIKIPEDIA_ID = "Learn more about Wikipedia",
            NEXT_BUTTON_ID = "Next",
            NEW_WAYS_TO_EXPLORE_TEXT = "New ways to explore",
            PREFERRED_LANGUAGES_TEXT = "Add or edit preferred languages",
            GET_STARTED_BUTTON_ID = "Get started";

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink()
    {
        this.waitForElementVisible(
                By.name(LEARN_MORE_ABOUT_WIKIPEDIA_ID),
                "Cannot find \"Learn more about Wikipedia\" link.",
                10);
    }

    public void clickNext()
    {
        this.waitForElementClickableAndClick(
                By.id(NEXT_BUTTON_ID),
                "Cannot find \"Next\" button.",
                10);
    }

    public void waitForNewWaysToExplore() {
        this.waitForElementVisible(
                By.id(NEW_WAYS_TO_EXPLORE_TEXT),
                "Cannot see \"New ways to explore\" text.",
                10
        );
    }

    public void waitForAddOrEditLanguages() {
        this.waitForElementVisible(
                By.id(PREFERRED_LANGUAGES_TEXT),
                "Cannot see \"Add or edit preferred languages\" text.",
                10
        );
    }

    public void clickGetStarted() {
        this.waitForElementClickableAndClick(
                By.id(GET_STARTED_BUTTON_ID),
                "Cannot find \"Get started\" button.",
                10);
    }
}
