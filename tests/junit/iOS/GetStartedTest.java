package iOS;

import lib.ui.HomePageObject;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends lib.CoreTestCase {

    private lib.ui.WelcomePageObject WelcomePageObject;
    private lib.ui.HomePageObject HomePageObject;

    protected void setUp() throws Exception {
        super.setUp();
        this.WelcomePageObject = new WelcomePageObject(driver);
        this.HomePageObject = new HomePageObject(driver);
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

        HomePageObject.waitForLoginToSync();
        HomePageObject.clickDismiss();

        HomePageObject.waitForManagePreferences();
        HomePageObject.clickDismiss();
    }
}
