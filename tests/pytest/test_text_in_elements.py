"""[WARNING] In Selenium v4.0.0 for some reason find_element*() returns a dictionary
instead of webelement.WebElement. Thus, we can't call typical WebElement methods on it.
These tests are most likely to fail when run with Selenium newer than v3.141.0."""

from appium.webdriver import webelement


class TestTextInElements:

    def test_send_text_to_search_field(self, driver):
        element_to_init_search = driver.find_element_by_xpath("//*[contains(@text, 'Search Wikipedia')]")
        assert isinstance(element_to_init_search, webelement.WebElement)
        element_to_init_search.click()
