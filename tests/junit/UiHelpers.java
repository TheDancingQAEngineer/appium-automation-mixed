import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UiHelpers {

    public static WebElement waitForElementPresent(
            AppiumDriver driver,
            By by,
            String error_message,
            long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public static WebElement waitForElementVisible(
            AppiumDriver driver,
            By by,
            String error_message,
            long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    public static WebElement waitForElementClickable(
            AppiumDriver driver,
            By by,
            String error_message,
            long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.elementToBeClickable(by)
        );
    }

    public static WebElement waitForElementVisibleAndClick(
            AppiumDriver driver,
            By by,
            String error_message,
            long timeoutInSeconds) {
        WebElement element = waitForElementVisible(driver, by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public static WebElement waitForElementClickableAndClick(
            AppiumDriver driver,
            By by,
            String error_message,
            long timeoutInSeconds) {
        WebElement element = waitForElementClickable(driver, by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public static WebElement waitForElementPresentAndSendKeys(
            AppiumDriver driver,
            By by,
            String keys,
            String error_message,
            long timeoutInSeconds) {
        WebElement element = waitForElementPresent(driver, by, error_message, timeoutInSeconds);
        element.sendKeys(keys);
        return element;
    }

    public static WebElement waitForElementVisibleAndSendKeys(
            AppiumDriver driver,
            By by,
            String keys,
            String error_message,
            long timeoutInSeconds) {
        WebElement element = waitForElementVisible(driver, by, error_message, timeoutInSeconds);
        element.sendKeys(keys);
        return element;
    }

    public static WebElement waitForElementVisibleAndClear(
            AppiumDriver driver, By by,
            String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementVisible(driver, by,
                error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected static void swipeElementToLeft(AppiumDriver driver,
                                             By by,
                                             String error_message) {
        WebElement element = waitForElementPresent(driver, by, error_message, 10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();

        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int mid_y = (upper_y + lower_y) / 2;

        PointOption startingPoint = PointOption.point(right_x, mid_y);
        PointOption endPoint = PointOption.point(left_x, mid_y);
        Duration swipeDuration = Duration.ofMillis(500);
        WaitOptions swipeWait = WaitOptions.waitOptions(swipeDuration);

        TouchAction action = new TouchAction(driver);

        action
                .press(startingPoint)
                .waitAction(swipeWait)
                .moveTo(endPoint)
                .release()
                .perform();
    }

    public static boolean waitForElementNotPresent(
            AppiumDriver driver,
            By by,
            String error_message,
            long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static int getNumberOfElements(AppiumDriver driver, By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public static void assertZeroElementsVisible(AppiumDriver driver,
                                                 By by, String error_message) {
        int number_of_elements = getNumberOfElements(driver, by);
        if (number_of_elements > 0) {
            String default_message = String.format(
                    "An element %s is expected to be absent", by.toString()
            );
            throw new AssertionError(default_message + " " + error_message);
        }
    }
}
