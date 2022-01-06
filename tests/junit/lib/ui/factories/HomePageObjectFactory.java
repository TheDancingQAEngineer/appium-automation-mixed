package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.HomePageObject;
import lib.ui.android.AndroidHomePageObject;
import lib.ui.ios.IOSHomePageObject;

public class HomePageObjectFactory {

    public static HomePageObject get(AppiumDriver driver)
    {
        if(Platform.getInstance().isIOS()) {
            return new IOSHomePageObject(driver);
        } else {
            return new AndroidHomePageObject(driver);
        }
    }
}
