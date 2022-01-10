package lib.ui.mobileweb;

import lib.ui.AuthPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWAuthPageObject extends AuthPageObject {

    static {
        LOGIN_BUTTON_LOCATOR = "xpath://body//div/a[text()='Log in']";
        USERNAME_INPUT_LOCATOR = "css:input[name='wpName']";
        PASSWORD_INPUT_LOCATOR = "css:input[name='wpPassword']";
        SUBMIT_BUTTON_LOCATOR = "css:button#wpLoginAttempt";
    }

    /** CONSTRUCTOR BEGINS **/
    public MWAuthPageObject(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
