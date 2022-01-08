package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TestSmoke.class,
        TestEx3.class,
        TestEx6.class,
        TestThreeArticles.class,
        TestAddToReadingListAndDelete.class,
        TestAddTwoArticlesToReadingListAndRemoveOne.class,
        TestTextInElements.class,
        TestSearch.class,
        TestSwipe.class,
        TestScreenFlip.class,
        GetStartedTest.class
})

public class TestSuite {
}
