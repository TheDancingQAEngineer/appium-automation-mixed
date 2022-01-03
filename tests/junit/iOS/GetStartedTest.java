package iOS;

import lib.ui.ios.IOSHomePageObject;
import lib.ui.ios.IOSWelcomePageObject;
import org.junit.*;

public class GetStartedTest extends lib.CoreTestCase {

    private IOSWelcomePageObject IOSWelcomePageObject;
    private IOSHomePageObject IOSHomePageObject;

    protected void setUp() throws Exception {
        super.setUp();
        this.IOSWelcomePageObject = new IOSWelcomePageObject(driver);
        this.IOSHomePageObject = new IOSHomePageObject(driver);
    }

    @Test
    public void testPassThroughWelcome()
    {
        if (this.Platform.isAndroid()) {
            return;
        }

        IOSWelcomePageObject.waitForLearnMoreLink();
        IOSWelcomePageObject.clickNext();

        IOSWelcomePageObject.waitForNewWaysToExplore();
        IOSWelcomePageObject.clickNext();

        IOSWelcomePageObject.waitForAddOrEditLanguages();
        IOSWelcomePageObject.clickNext();

        IOSWelcomePageObject.clickGetStarted();

        IOSHomePageObject.waitForLoginToSync();
        IOSHomePageObject.clickDismiss();

        IOSHomePageObject.waitForManagePreferences();
        IOSHomePageObject.clickDismiss();
    }
}
