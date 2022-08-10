package lib.ui.factories;

import lib.Platform;
import lib.ui.FormPageObject;
import lib.ui.mobileweb.firefox.MWFirefoxFormPageObject;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FormPageObjectFactory {

    public static FormPageObject get(RemoteWebDriver driver)
    {
        if(Platform.getInstance().isMWFirefox()) {
            return new MWFirefoxFormPageObject(driver);
        } else {
            String error_message = String.format(
                "FormPageObject not implemented for platform: \"%s\"",
                Platform.getInstance().getPlatformVar());
                throw new NotImplementedException(error_message);
        }
    }
}
