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
        Assert.assertEquals("HTML Form Elements", driver.getTitle());
    }

    @Test
    public void testSubmittingEmptyForm() {
        FormPageObject.scrollToSubmitButton();
        FormPageObject.clickSubmitButton();
        Assert.assertEquals("Processed Form Details", driver.getTitle());
        Assert.assertEquals("https://testpages.herokuapp.com/styled/the_form_processor.php", driver.getCurrentUrl());
    }
}