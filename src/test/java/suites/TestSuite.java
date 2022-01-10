package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        SmokeTests.class,
        WelcomeTests.class,
        SearchTests.class,
        TestThreeArticles.class,
        ArticleTests.class,
        MyListsTests.class,
        TestAddTwoArticlesToReadingListAndRemoveOne.class
})

public class TestSuite {
}
