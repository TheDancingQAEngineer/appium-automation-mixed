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
        assertBrowserTitleEquals("HTML Form Elements");
        assertBrowserUrlEquals("https://testpages.herokuapp.com/styled/basic-html-form-test.html");
    }

    @Test
    public void testSubmittingEmptyForm() {
        FormPageObject.scrollToSubmitButton();
        FormPageObject.clickSubmitButton();
        assertBrowserTitleEquals("Processed Form Details");
        assertBrowserUrlEquals("https://testpages.herokuapp.com/styled/the_form_processor.php");
    }

    private void assertBrowserTitleEquals(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(
            String.format("Expected title to be '%s', but got '%s'", expectedTitle, actualTitle),
            expectedTitle, actualTitle);
    }
    
    private void assertBrowserUrlEquals(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(
            String.format("Expected URL to be '%s', but got '%s'", expectedUrl, actualUrl),
            expectedUrl, actualUrl);
    }
}