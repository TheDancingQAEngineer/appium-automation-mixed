package iOS;

import lib.Platform;
import lib.ui.HomePageObject;
import lib.ui.WelcomePageObject;
import org.junit.*;

public class GetStartedTest extends lib.CoreTestCase {

    private WelcomePageObject WelcomePageObject;
    private HomePageObject HomePageObject;

    protected void setUp() throws Exception {
        super.setUp();
        this.WelcomePageObject = new WelcomePageObject(driver);
        this.HomePageObject = new HomePageObject(driver);
    }

    @Test
    public void testPassThroughWelcome()
    {
        if (Platform.getInstance().isAndroid()) {
            return;
        }

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
