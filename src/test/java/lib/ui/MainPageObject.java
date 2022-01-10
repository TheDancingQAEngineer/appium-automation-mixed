package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Attachment;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver)
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

    protected WebElement waitForElementPresent(
            String locator_with_type,
            String error_message,
            long timeoutInSeconds)
    {
        By by = this.getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
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

    protected boolean isElementStale(String locator_with_type,
                                                 String error_message,
                                                 long timeoutInSeconds)
    {
        By by = getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.stalenessOf(driver.findElement(by))
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

    protected List<WebElement> waitForElementsVisible(String locator_with_type, int count_more_than,
                                                      String error_message,
                                                      long timeoutInSeconds)
    {
        By by = getLocatorByString(locator_with_type);
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
        if (driver instanceof AppiumDriver) {

            TouchAction action = new TouchAction((AppiumDriver) driver);

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
        } else {
            System.out.println("TouchAction() not implemented on platform " +
                    Platform.getInstance().getPlatformVar());
        }
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
        } else if (by_type.equals("css")) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException(
                    String.format(error_message_format, locator_with_type));
        }
    }

    protected int getNumberOfElements(String locator_with_type) {
        By by = getLocatorByString(locator_with_type);
        List elements = driver.findElements(by);
        return elements.size();
    }

    protected void assertZeroElementsVisible(String locator_with_type,
                                             String error_message)
    {
        int number_of_elements = this.getNumberOfElements(locator_with_type);
        if (number_of_elements > 0) {
            String default_message = String.format(
                    "An element %s is expected to be absent.", locator_with_type
            );
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public boolean isElementWithinScreenBounds(String locator)
    {
        int element_y_pos = this.waitForElementPresent(locator,
                "Cannot locate element by locator:" + locator,
                15)
                .getLocation()
                .getY();
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExec = (JavascriptExecutor) driver;
            Object js_result = JSExec.executeScript("return window.pageYOffset");
            element_y_pos -= Integer.parseInt(js_result.toString());
        }
        int screen_height = driver
                .manage()
                .window()
                .getSize()
                .getHeight();

        return element_y_pos < screen_height;
    }

    protected void swipeElementToLeft(String locator_with_type, String error_message)
    {
        WebElement element = this.waitForElementVisible(locator_with_type, error_message, 10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();

        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int mid_y = (upper_y + lower_y) / 2;

        this.swipeByCoordinates(right_x, mid_y,
                left_x, mid_y, 500);
    }

    protected void swipeUpTillElement(String locator_with_type, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (driver.findElements(getLocatorByString(locator_with_type)).size() == 0) {

            if (already_swiped >= max_swipes) {
                waitForElementVisible(locator_with_type,
                        "Cannot find element by swiping up. \n" + error_message,
                        0);
                return;
            }

            swipeUpQuick();
            already_swiped++;
        }
    }

    protected void swipeUpTillElementAppears(String locator, String error_message, int max_swipes) {
        int already_swiped = 0;

        while (!this.isElementWithinScreenBounds(locator)) {
            if (already_swiped >= max_swipes) {
                Assert.assertTrue(error_message, this.isElementWithinScreenBounds(locator));
            }

            swipeUpQuick();
            already_swiped++;
        }
    }

    private void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUp(int timeOfSwipe) {
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();
            int x = (int) (size.width / 2);
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);

            this.swipeByCoordinates(x, start_y, x, end_y, timeOfSwipe);
        } else {
            this.scrollWebPageUp(timeOfSwipe);
        }
    }

    public void scrollWebPageUp(int time_of_scroll_in_millis)
    {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExec = (JavascriptExecutor) driver;
            JSExec.executeScript("window.scrollBy(0, 250)");
            try {
                Thread.sleep(time_of_scroll_in_millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("scrollWebPageUp not implemented on platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    public void scrollWebPageTillElementVisible(String locator, String error_message, int max_swipes)
    {
        int already_swiped = 0;

        WebElement element = this.waitForElementVisible(locator, error_message, 5);

        while(!this.isElementWithinScreenBounds(locator)) {
            this.scrollWebPageUp(200);
            already_swiped++;
            if (already_swiped >= max_swipes) {
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
    }

    public boolean isElementPresent(String locator) {
        return this.getNumberOfElements(locator) > 0;
    }

    public void tryClickElementWithFewAttempts(String locator, String error_message, int max_attempts)
    {
        int current_attempt = 0;
        boolean need_more_attempts = true;

        while (need_more_attempts) {
            try {
                this.waitForElementClickableAndClick(locator, error_message, 1);
                need_more_attempts = false;
            } catch (Exception e) {
                if (current_attempt > max_attempts) {
                    this.waitForElementClickableAndClick(locator, error_message, 1);
                }
            }
            current_attempt++;
        }
    }

    public String takeScreenshot(String name)
    {
        TakesScreenshot ts = (TakesScreenshot) this.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + name + "_screenshot.png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("Saved screenshot to " + path);
        } catch (Exception e) {
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }

    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];

        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (Exception e) {
            System.out.println("Cannot get bytes from screenshot. Error: " + e.getMessage());
        }
        return bytes;
    }
}
