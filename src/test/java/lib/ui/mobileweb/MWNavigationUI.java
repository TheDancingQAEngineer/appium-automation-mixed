package lib.ui.mobileweb;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {

    static {
        MY_LISTS_LOCATOR = "css:li a[data-event-name='menu.unStar']";
        OPEN_NAVIGATION_PANEL_LOCATOR = "css:nav label[title='Open main menu']";
    }

    /** CONSTRUCTOR BEGINS **/
    public MWNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
    /** CONSTRUCTOR ENDS **/
}
