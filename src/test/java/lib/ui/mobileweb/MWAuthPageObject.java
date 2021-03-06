package lib.ui.mobileweb;

import lib.ui.AuthPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWAuthPageObject extends AuthPageObject {

    static {
        BAD_CREDENTIALS_MESSAGE_LOCATOR = "";
        LOGIN_BUTTON_LOCATOR = "xpath://body//div/a[text()='Log in']";
        PASSWORD_INPUT_LOCATOR = "css:input[name='wpPassword']";
        SUBMIT_BUTTON_LOCATOR = "css:button#wpLoginAttempt";
        USERNAME_INPUT_LOCATOR = "css:input[name='wpName']";
    }

    /** CONSTRUCTOR BEGINS **/
    public MWAuthPageObject(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
