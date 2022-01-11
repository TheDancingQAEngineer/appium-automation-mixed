package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Features;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import junit.framework.AssertionFailedError;
import lib.CoreTestCase;
import org.junit.*;

public class SmokeTests extends CoreTestCase {

    @Test
    @DisplayName("Smoke test of test tool stack")
    @Description("Launches an app under test/opens mobile web page.\n" +
        "If it fails, execution of ALL tests is likely to fail.")
    @Severity(SeverityLevel.BLOCKER) // Or CRITICAL?
    public void testSmoke()
    {
        System.out.println("I am testSmoke(). I ran!");
    }

    @Test
    @DisplayName("Smoke test of test failure logic")
    @Description("Launches an app, throws a failure and catches it.\n" +
        "If it fails.")
    @Severity(SeverityLevel.CRITICAL)
    public void testExpectedFailure()
    {
        try {
            System.out.println("I am testExpectedFailure(). I only pass if I throw AssertionFailedError().");
            Assert.fail("shouldn't pass.");
        } catch (AssertionError e) {
            Assert.assertTrue(e.getMessage().contains("shouldn't pass"));
        }
    }
}