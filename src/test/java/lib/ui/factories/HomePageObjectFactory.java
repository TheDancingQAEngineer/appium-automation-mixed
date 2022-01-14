package lib.ui.factories;

import lib.Platform;
import lib.ui.HomePageObject;
import lib.ui.android.AndroidHomePageObject;
import lib.ui.ios.IOSHomePageObject;
import lib.ui.mobileweb.MWHomePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HomePageObjectFactory {

    public static HomePageObject get(RemoteWebDriver driver)
    {
        if(Platform.getInstance().isIOS()) {
            return new IOSHomePageObject(driver);
        } else if (Platform.getInstance().isAndroid()) {
            return new AndroidHomePageObject(driver);
        } else {
            return new MWHomePageObject(driver);
        }
    }
}
