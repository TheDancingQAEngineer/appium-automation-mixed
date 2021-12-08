package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

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

    public WebElement waitForElementVisible(
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

    protected WebElement waitForElementClickable(
            By by,
            String error_message,
            long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.elementToBeClickable(by)
        );
    }

    protected WebElement waitForElementVisibleByXpath(String xpath, String error_message, long timeoutInSeconds)
    {
        By by = By.xpath(xpath);
        return waitForElementVisible(by, error_message, timeoutInSeconds);
    }

    protected WebElement waitForElementVisibleByXpath(String xpath, String error_message)
    {
        return waitForElementVisibleByXpath(xpath, error_message, 5);
    }

    protected void waitForElementVisibleAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(by, error_message, timeoutInSeconds);
        element.clear();
    }

    protected WebElement waitForElementVisibleAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public String waitForElementVisibleAndGetAttribute(By by, String attribute_name,
                                                       String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute_name);
    }

    /* Part 6: More refactoring. */
    protected WebElement waitForElementVisibleByXpathAndClick(String xpath, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisibleByXpath(xpath, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    protected WebElement waitForElementVisibleAndSendKeys(By by, String keys,
            String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementVisible(by, error_message, timeoutInSeconds);
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

    protected boolean waitForElementNotVisible(By by,
            String error_message, long timeoutInSeconds)
    {
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

    protected void waitForElementClickableAndClick(By by, String error_message,
                                                   long timeoutInSeconds)
    {
        WebElement element = waitForElementClickable(by,
                error_message, timeoutInSeconds);
        element.click();
    }
}
