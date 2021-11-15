"""This file was adapted from Appium sample code found at
https://github.com/appium/appium/tree/master/sample-code
as of November 15, 2021."""

import os
import pytest

from appium import webdriver
from importlib.metadata import version
from packaging.version import parse as version_parse


@pytest.fixture(scope='session')
def selenium_is_4_or_newer():
    selenium_version = version_parse(version("selenium"))
    v_4_0 = version_parse('4.0')
    return selenium_version >= v_4_0


@pytest.fixture()
def apk_dir():
    """A quick and dirty hack to get a fixed path to apk,
    so that tests pass when run from PyCharm buttons or from
    terminal in project root."""
    apk_path = os.path.abspath(
        os.path.join(
            os.path.abspath(os.path.dirname(__file__)),
            '../../apks')
    )
    return apk_path


@pytest.fixture()
def capabilities(apk_dir):
    caps = {
        'app': apk_dir + '/org.wikipedia.apk',
        'automationName': 'Appium',
        'platformName': 'Android',
        'platformVersion': '8.0',
        'deviceName': 'andro80',
        'appActivity': '.main.MainActivity',
        'adbExecTimeout': 40000
    }
    return caps


@pytest.fixture()
def executor():
    executor_string = 'http://127.0.0.1:4723/wd/hub'
    return executor_string


@pytest.fixture()
def driver(request, capabilities, executor, selenium_is_4_or_newer):
    """With selenium v4.0.0, released on 2021-10-13, the code below
    emits the following warning:
    "DeprecationWarning: desired_capabilities has been deprecated,
    please pass in an Options object with options kwarg".
    This can be traced to the following change log entry at
    https://github.com/SeleniumHQ/selenium/blob/trunk/py/CHANGES


    "...
    Selenium 4.0 Beta 1

    * Deprecate all but `Options` and `Service` arguments in driver instantiation. (#9125,#9128)
    ..."

    Appium-Python-Client doesn't seem to provide a way to pass Options argument as of v2.0.0.
    Hence this hack, though it's probably not the best way to handle such situations.

    Possible alternatives include:
        - manually downgrading selenium to v3.14.1 (works OK);
        - writing a wrapper function that converts capabilities dict to valid Options object.
    """

    if selenium_is_4_or_newer:
        with pytest.deprecated_call():
            driver = webdriver.Remote(
                command_executor=executor,
                desired_capabilities=capabilities,
            )
    else:
        driver = webdriver.Remote(
            command_executor=executor,
            desired_capabilities=capabilities
        )

    def after():
        driver.quit()

    request.addfinalizer(after)

    return driver
