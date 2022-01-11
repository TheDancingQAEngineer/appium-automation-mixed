package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.*;

public class WelcomeTests extends lib.CoreTestCase {

    private WelcomePageObject WelcomePageObject;

    @Override
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
        this.WelcomePageObject = new WelcomePageObject(driver);
    }

    @Test
    @DisplayName("Walk through iOS app starter screen")
    @Description("Get from starter screen to app main view")
    @Severity(SeverityLevel.MINOR)
    @Features(value={@Feature("First Launch Screen")})
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
