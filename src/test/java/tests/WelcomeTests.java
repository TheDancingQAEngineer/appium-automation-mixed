package tests;

import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.*;

public class WelcomeTests extends lib.CoreTestCase {

    private WelcomePageObject WelcomePageObject;

    @Override
    protected void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
        this.WelcomePageObject = new WelcomePageObject(driver);
    }

    @Test
    public void testPassThroughWelcome()
    {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            return;
        }

        WelcomePageObject.waitForLearnMoreLink();
        WelcomePageObject.clickNext();

        WelcomePageObject.waitForNewWaysToExplore();
        WelcomePageObject.clickNext();

        WelcomePageObject.waitForAddOrEditLanguages();
        WelcomePageObject.clickNext();

        WelcomePageObject.clickGetStarted();
    }
}
