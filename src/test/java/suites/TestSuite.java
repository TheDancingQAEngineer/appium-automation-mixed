package suites;

import org.apache.tools.ant.taskdefs.Get;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TestSmoke.class,
        TestEx3.class,
        TestEx6.class,
        TestEx9.class,
        TestAddToReadingListAndDelete.class,
        TestAddTwoArticlesToReadingListAndRemoveOne.class,
        TestTextInElements.class,
        TestSearchSwipeAndScreenFlip.class,
        GetStartedTest.class
})

public class TestSuite {
}
