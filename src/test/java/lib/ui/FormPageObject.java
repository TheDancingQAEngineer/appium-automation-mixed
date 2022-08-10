package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.Platform;

abstract public class FormPageObject extends MainPageObject {
    
    protected static String
        SUBMIT_BUTTON_LOCATOR;

    public FormPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void scrollToSubmitButton() {
        if (Platform.getInstance().isMW()) {
            this.scrollWebPageTillElementVisible(SUBMIT_BUTTON_LOCATOR,
                "Cannot find submit button by locator" + SUBMIT_BUTTON_LOCATOR, 
                40);
        }
    }

    public void clickSubmitButton() {
        this.tryClickElementWithFewAttempts(SUBMIT_BUTTON_LOCATOR, 
        "Cannot find submit button by locator" + SUBMIT_BUTTON_LOCATOR, 
        5);
    }
}
