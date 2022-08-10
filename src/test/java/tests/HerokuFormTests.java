package tests;

import org.junit.Test;

import org.junit.Assert;
import lib.CoreTestCase;
import lib.ui.FormPageObject;
import lib.ui.factories.FormPageObjectFactory;


public class HerokuFormTests extends CoreTestCase {
    
    protected static final String TEST_PAGE_URL = "https://testpages.herokuapp.com/styled/basic-html-form-test.html";
    
    protected FormPageObject FormPageObject;

    @Override
    public void setUp() throws Exception {
        this.sutMwHomepage = TEST_PAGE_URL;
        super.setUp();
        this.FormPageObject = FormPageObjectFactory.get(driver);
    }

    @Test
    public void testFormPageAccess() {

        String expected = "HTML Form Elements";
        String actual = driver.getTitle();
        Assert.assertEquals(
            String.format("Expected page title to be \"%s\", but got \"%s\"", expected, actual),
            expected, actual);
    }

    @Test
    public void testSubmittingEmptyForm() {
        FormPageObject.scrollToSubmitButton();
        FormPageObject.clickSubmitButton();
    }
}