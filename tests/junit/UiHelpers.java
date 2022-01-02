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

    public static WebElement waitForElementVisibleAndClick(
            AppiumDriver driver,
            By by,
            String error_message,
            long timeoutInSeconds) {
        WebElement element = waitForElementVisible(driver, by, error_message, timeoutInSeconds);
        element.click();
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
