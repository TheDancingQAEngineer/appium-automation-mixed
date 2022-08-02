from helpers import *


class TestFour():

    def test_swipe_article(self, driver):
        wait_for_element_and_click(driver,
                                   By.XPATH("//*[contains(@text, 'Search Wikipedia')]"),
                                   "Cannot locate search field.", 5)

        wait_for_element_and_send_keys(driver,
                                       By.XPATH("//*[contains(@text, 'Search…')]"),
                                       "Java",
                                       "Cannot locate input field.", 5)

        xpath_to_search = "//*[@resource-id='org.wikipedia:id/page_list_item_container']" \
                          + "//*[@text='Object-oriented programming language']"

        wait_for_element_and_click(driver,
                                   By.XPATH(xpath_to_search),
                                   "Cannot locate article in search results.", 15)

        wait_for_element_visible(driver,
                                 By.ID("org.wikipedia:id/view_page_title_text"),
                                 "Cannot find article title.",
                                 15)

        swipe_up(driver, 2000)
