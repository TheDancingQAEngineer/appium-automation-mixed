package lib.ui;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class AuthPageObject extends MainPageObject{

    protected static String
            BAD_CREDENTIALS_MESSAGE_LOCATOR,
            LOGIN_BUTTON_LOCATOR,
            PASSWORD_INPUT_LOCATOR,
            SUBMIT_BUTTON_LOCATOR,
            USERNAME_INPUT_LOCATOR;

    protected static final String
            wiki_username_env_var = "WIKI_USERNAME",
            wiki_password_env_var = "WIKI_PASSWORD";

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

    @Step("Send login and password to respective fields")
    public void enterCredentials()
    {
        String login = getLoginFromEnv();
        String password = getPasswordFromEnv();
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

    protected static String getLoginFromEnv() throws RuntimeException {
        String login = System.getenv(wiki_username_env_var);
        if (login == null) {
            String error_message = new StringBuilder()
                    .append(String.format("Getting environment variable %s returned null. ", wiki_username_env_var))
                    .append(String.format("Please set %s to a valid username.", wiki_username_env_var))
                    .toString();
            throw new RuntimeException(error_message);
        } else {
            return login;
        }
    }

    protected static String getPasswordFromEnv() throws RuntimeException {
        String password = System.getenv(wiki_password_env_var);
        if (password == null) {
            String error_message = new StringBuilder()
                    .append(String.format("Getting environment variable %s returned null. ", wiki_password_env_var))
                    .append(String.format("Please set %s to a valid password.", wiki_password_env_var))
                    .toString();
            throw new RuntimeException(error_message);
        } else {
            return password;
        }
    }

    public boolean gotToLoginProblemsScreen() {
        try {
            this.waitForElementVisible(BAD_CREDENTIALS_MESSAGE_LOCATOR,
                    "Can't locate 'Incorrect username or password' message",
                    5);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void authorize() {
        this.clickAuthButton();
        this.enterCredentials();
        this.submitLoginForm();
        if (this.gotToLoginProblemsScreen())
        {
            Assert.fail("Invalid username and/or password.");
        }
    }
}
