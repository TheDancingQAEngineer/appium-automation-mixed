import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class UiHelpers {

    public static WebElement waitForElementVisible(
            AppiumDriver driver,
            By by,
            String error_message,
            long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public static WebElement waitForElementAndClick(
            AppiumDriver driver,
            By by,
            String error_message,
            long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(driver, by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public static WebElement waitForElementAndSendKeys(
            AppiumDriver driver,
            By by,
            String keys,
            String error_message,
            long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(driver, by, error_message, timeoutInSeconds);
        element.sendKeys(keys);
        return element;
    }

    public static WebElement waitForElementAndClear(
            AppiumDriver driver, By by,
            String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(driver, by,
                error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected static void swipeUp(AppiumDriver driver, int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = (int) (size.width / 2);
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform();
    }

    protected static void swipeUpQuick(AppiumDriver driver)
    {
        swipeUp(driver,200);
    }

    protected static void swipeUpTillElement(AppiumDriver driver, By by, String error_message, int max_swipes)
    {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped >= max_swipes) {
                waitForElementVisible(driver,
                        by,
                        "Cannot find element by swiping up. \n" + error_message,
                        0);
                return;
            }

            swipeUpQuick(driver);
            already_swiped++;
        }
    }

    protected static void swipeElementToLeft(AppiumDriver driver,
                                             By by,
                                             String error_message)
    {
        WebElement element = waitForElementVisible(driver, by, error_message, 10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();

        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int mid_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);

        action
                .press(right_x, mid_y)
                .waitAction(500)
                .moveTo(left_x, mid_y)
                .release()
                .perform();
    }

    public static boolean waitForElementNotPresent(
            AppiumDriver driver,
            By by,
            String error_message,
            long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static int getNumberOfElements(AppiumDriver driver, By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public static void assertZeroElementsVisible(AppiumDriver driver,
                                                 By by, String error_message)
    {
        int number_of_elements = getNumberOfElements(driver, by);
        if (number_of_elements > 0) {
            String default_message = String.format(
                    "An element %s is expected to be absent", by.toString()
            );
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public static String waitForElementAndGetAttribute(AppiumDriver driver, By by,
                                                       String attribute_name, String error_message,
                                                       long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(driver, by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute_name);
    }
}
