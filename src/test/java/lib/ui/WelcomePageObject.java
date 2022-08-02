package lib.ui;

// import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
// import lib.ui.MainPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {

    private static final String
            LEARN_MORE_ABOUT_WIKIPEDIA_ID = "id:Learn more about Wikipedia",
            NEXT_BUTTON_ID = "id:Next",
            NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
            PREFERRED_LANGUAGES_TEXT = "id:Add or edit preferred languages",
            GET_STARTED_BUTTON_ID = "id:Get started",
            SKIP_BUTTON_ID = "id:Skip";

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Locate 'Learn more about wikipedia' link")
    public void waitForLearnMoreLink()
    {
        this.waitForElementVisible(
                LEARN_MORE_ABOUT_WIKIPEDIA_ID,
                "Cannot find \"Learn more about Wikipedia\" link.",
                10);
    }

    @Step("Click 'Next' button")
    public void clickNext()
    {
        this.waitForElementClickableAndClick(
                NEXT_BUTTON_ID,
                "Cannot find \"Next\" button.",
                10);
    }

    @Step("Locate 'New ways to explore' text")
    public void waitForNewWaysToExplore() {
        this.waitForElementVisible(
                NEW_WAYS_TO_EXPLORE_TEXT,
                "Cannot see \"New ways to explore\" text.",
                10
        );
    }

    @Step("Locate 'Add or edit preferred languages' text")
    public void waitForAddOrEditLanguages() {
        this.waitForElementVisible(
                PREFERRED_LANGUAGES_TEXT,
                "Cannot see \"Add or edit preferred languages\" text.",
                10
        );
    }

    @Step("Click 'Get started' button")
    public void clickGetStarted() {
        this.waitForElementClickableAndClick(
                GET_STARTED_BUTTON_ID,
                "Cannot find \"Get started\" button.",
                10);
    }

    @Step("Click 'Skip' button")
    public void clickSkip() {
        this.waitForElementClickableAndClick(
                SKIP_BUTTON_ID,
                "Cannot find \"Skip\" button.",
                10
        );
    }
}
