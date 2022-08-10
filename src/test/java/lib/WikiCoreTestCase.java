package lib;

import io.qameta.allure.Step;


abstract public class WikiCoreTestCase extends CoreTestCase {
    
    // TODO: move to env or config
    protected static final String WIKI_HOME_PAGE = "https://en.m.wikipedia.org";

    @Override
    @Step("Launch test and get to starting page")
    public void setUp() throws Exception
    {
        this.sutMwHomepage = WIKI_HOME_PAGE;
        super.setUp();
    }
}
