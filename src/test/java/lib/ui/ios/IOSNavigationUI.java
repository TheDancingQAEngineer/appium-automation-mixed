package lib.ui.ios;

// import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSNavigationUI extends NavigationUI {

    static {
        MY_LISTS_LOCATOR = "id:Saved";
    }

    /** CONSTRUCTOR BEGINS **/
    public IOSNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
