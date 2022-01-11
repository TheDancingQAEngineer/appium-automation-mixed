package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class AuthPageObject extends MainPageObject{

    protected static String
            LOGIN_BUTTON_LOCATOR,
            USERNAME_INPUT_LOCATOR,
            PASSWORD_INPUT_LOCATOR,
            SUBMIT_BUTTON_LOCATOR;

    public AuthPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Find and click login button")
    public void clickAuthButton() {
        this.waitForElementVisible(LOGIN_BUTTON_LOCATOR,
                "Cannot locate Login button by locator " + LOGIN_BUTTON_LOCATOR,
                5);
        this.waitForElementClickableAndClick(LOGIN_BUTTON_LOCATOR,
                "Cannot click Login button by locator " + LOGIN_BUTTON_LOCATOR,
                5);
    }

    @Step("Send login and password to respective fields")
    public void enterCredentials(String login, String password)
    {
        this.waitForElementVisibleAndSendKeys(USERNAME_INPUT_LOCATOR, login,
                "Cannot send keys to login field by locator " + USERNAME_INPUT_LOCATOR, 5);
        this.waitForElementVisibleAndSendKeys(PASSWORD_INPUT_LOCATOR, password,
                "Cannot send keys to password field by locator " + PASSWORD_INPUT_LOCATOR, 5);
    }

    @Step("Click submit button")
    public void submitLoginForm() {
        this.waitForElementClickableAndClick(SUBMIT_BUTTON_LOCATOR,
                "Cannot click Submit button by locator " + SUBMIT_BUTTON_LOCATOR,
                5);
    }
}
