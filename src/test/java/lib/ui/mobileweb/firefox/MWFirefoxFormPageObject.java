package lib.ui.mobileweb.firefox;

import lib.ui.FormPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWFirefoxFormPageObject extends FormPageObject {

    static {
        SUBMIT_BUTTON_LOCATOR = "css:input[value='submit']";
    }

    /** CONSTRUCTOR BEGINS **/
    public MWFirefoxFormPageObject(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
