package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        SmokeTests.class,
        HerokuFormTests.class
})

public class SmokeSuite {
}
