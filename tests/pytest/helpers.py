import appium.webdriver
from appium import webdriver
from appium.webdriver.common.touch_action import TouchAction
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as ec
from selenium.webdriver.common.by import By


def wait_for_element_visible(driver: webdriver,
                             by: By,
                             error_message: str,
                             timeout_in_seconds: int) -> webdriver.WebElement:
    wait = WebDriverWait(driver, timeout_in_seconds)
    return wait.until(ec.presence_of_element_located(by), error_message + "\n")


def wait_for_element_and_click(driver: webdriver,
                               by: By,
                               error_message: str,
                               timeout_in_seconds: int) -> webdriver.WebElement:
    element = wait_for_element_visible(driver, by, error_message, timeout_in_seconds)
    element.click()
    return element


def wait_for_element_and_send_keys(driver: webdriver,
                                   by: By,
                                   keys: str,
                                   error_message: str,
                                   timeout_in_seconds: int) -> webdriver.WebElement:
    element = wait_for_element_visible(driver, by, error_message, timeout_in_seconds)
    element.send_keys(keys)
    return element


def swipe_up(driver: webdriver, time_of_swipe: int) -> None:
    action = TouchAction(driver)
    size = appium.webdriver.Remote.get_window_size