package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class FormPageObject extends MainPageObject {
    
    protected static String
        SUBMIT_BUTTON_LOCATOR;

    public FormPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
