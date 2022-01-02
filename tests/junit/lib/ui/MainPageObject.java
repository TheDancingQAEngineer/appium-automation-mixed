package lib.ui;

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
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }

    protected WebElement waitForElementInvisible(
            By by,
            String error_message,
            long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    protected WebElement waitForElementVisible(
            String locator_with_type,
            String error_message,
            long timeoutInSeconds)
    {
        By by = this.getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    protected WebElement waitForElementClickable(String locator_with_type,
            String error_message,
            long timeoutInSeconds)
    {
        By by = getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.elementToBeClickable(by)
        );
    }

    protected WebElement waitForElementVisibleByXpath(String xpath, String error_message, long timeoutInSeconds)
    {
        return waitForElementVisible(xpath, error_message, timeoutInSeconds);
    }

    protected WebElement waitForElementVisibleByXpath(String xpath, String error_message)
    {
        return waitForElementVisibleByXpath(xpath, error_message, 5);
    }

    protected void waitForElementVisibleAndClear(String locator_with_type, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(locator_with_type, error_message, timeoutInSeconds);
        element.clear();
    }

    protected WebElement waitForElementVisibleAndClick(String locator_with_type, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(locator_with_type, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public String waitForElementVisibleAndGetAttribute(String locator_with_type, String attribute_name,
                                                       String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(locator_with_type, error_message, timeoutInSeconds);
        return element.getAttribute(attribute_name);
    }

    /* Part 6: More refactoring. */
    protected WebElement waitForElementVisibleByXpathAndClick(String xpath, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisibleByXpath(xpath, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    protected WebElement waitForElementVisibleAndSendKeys(String locator_with_type, String keys,
            String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(locator_with_type, error_message, timeoutInSeconds);
        element.sendKeys(keys);
        return element;
    }

    /* Part 6: More refactoring. */
    protected WebElement waitForElementVisibleByXpathAndSendKeys(String xpath, String keys,
                                                               String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisibleByXpath(xpath, error_message, timeoutInSeconds);
        element.sendKeys(keys);
        return element;
    }

    protected boolean waitForElementNotVisible(String locator_with_type,
            String error_message, long timeoutInSeconds)
    {
        By by = getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    protected List<WebElement> waitForElementsVisible(By by, int count_more_than,
                                                      String error_message,
                                                      long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(by, count_more_than)
        );
    }

    protected void waitForElementClickableAndClick(String locator_with_type, String error_message,
                                                   long timeoutInSeconds)
    {
        WebElement element = waitForElementClickable(locator_with_type,
                error_message, timeoutInSeconds);
        element.click();
    }

    protected void swipeByCoordinates(
            int x_start, int y_start,
            int x_end, int y_end, int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);

        PointOption startingPoint = PointOption.point(x_start, y_start);
        PointOption endPoint = PointOption.point(x_end, y_end);

        Duration swipeDuration = Duration.ofMillis(timeOfSwipe);
        WaitOptions swipeWait = WaitOptions.waitOptions(swipeDuration);

        action
                .press(startingPoint)
                .waitAction(swipeWait)
                .moveTo(endPoint)
                .release()
                .perform();
    }

    protected By getLocatorByString(String locator_with_type) throws IllegalArgumentException
    {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"),2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];
        String error_message_format = "Cannot get type of locator: \"%s\"";

        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException(
                    String.format(error_message_format, locator_with_type));
        }
    }
}
