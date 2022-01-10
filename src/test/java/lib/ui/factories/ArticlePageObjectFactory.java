package lib.ui.factories;

import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.android.AndroidArticlePageObject;
import lib.ui.ios.IOSArticlePageObject;
import lib.ui.mobileweb.MWArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ArticlePageObjectFactory {

    public static ArticlePageObject get(RemoteWebDriver driver)
    {
        if (Platform.getInstance().isIOS()) {
            return new IOSArticlePageObject(driver);
        } else if (Platform.getInstance().isAndroid()){
            return new AndroidArticlePageObject(driver);
        } else {
            return new MWArticlePageObject(driver);
        }
    }
}
