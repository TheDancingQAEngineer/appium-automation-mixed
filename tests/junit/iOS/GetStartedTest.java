package iOS;

import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends lib.CoreTestCaseIOS {

    private lib.ui.WelcomePageObject WelcomePageObject;

    protected void setUp() throws Exception {
        super.setUp();
        this.WelcomePageObject = new WelcomePageObject(driver);
    }

    @Test
    public void testPassThroughWelcome()
    {
        WelcomePageObject.waitForLearnMoreLink();
        WelcomePageObject.clickNext();
        WelcomePageObject.waitForNewWaysToExplore();
        WelcomePageObject.clickNext();
        WelcomePageObject.waitForAddOrEditLanguages();
        WelcomePageObject.clickNext();
        WelcomePageObject.clickGetStarted();
        WelcomePageObject.waitForLoginToSync();
        WelcomePageObject.clickDismiss();
        WelcomePageObject.waitForManagePreferences();
        WelcomePageObject.clickDismiss();
    }
}
