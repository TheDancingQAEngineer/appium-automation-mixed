package lib.ui.factories;

import lib.Platform;
import lib.ui.MyListsPageObject;
import lib.ui.android.AndroidMyListsPageObject;
import lib.ui.ios.IOSMyListsPageObject;
import lib.ui.mobileweb.MWMyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListsPageObjectFactory {

    public static MyListsPageObject get(RemoteWebDriver driver)
    {
        if(Platform.getInstance().isIOS()) {
            return new IOSMyListsPageObject(driver);
        } else if (Platform.getInstance().isAndroid()) {
            return new AndroidMyListsPageObject(driver);
        } else {
            return new MWMyListsPageObject(driver);
        }
    }
}
