package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.android.AndroidArticlePageObject;
import lib.ui.ios.IOSArticlePageObject;

public class ArticlePageObjectFactory {

    public static ArticlePageObject get(AppiumDriver driver)
    {
        if(Platform.getInstance().isIOS()) {
            return new IOSArticlePageObject(driver);
        } else {
            return new AndroidArticlePageObject(driver);
        }
    }
}
