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
        'platformName': 'Android',
        'deviceName': 'andro80',
        'platformVersion': '8.0',
        'automationName': 'Appium',
        'appPackage': 'org.wikipedia',
        'appActivity': '.main.MainActivity',
        'app': apk_dir + '/org.wikipedia.apk',
        'adbExecTimeout': 40000
    }
    return caps


@pytest.fixture()
def executor():
    executor_string = 'http://127.0.0.1:4723/wd/hub'
    return executor_string


@pytest.fixture()
def driver(request, capabilities, executor, selenium_is_4_or_newer):
    """Since v4.0.0, selenium deprecates passing capabilities as dictionaries.
    See: https://github.com/SeleniumHQ/selenium/blob/trunk/py/CHANGES

    "...
    Selenium 4.0 Beta 1

    * Deprecate all but `Options` and `Service` arguments in driver instantiation. (#9125,#9128)
    ..."

    Appium-Python-Client doesn't seem to provide a way to pass Options argument as of v2.0.0.
    Thus, for selenium 4 and newer, DeprecationWarning is suppressed by pytest.deprecated_call()
    (which is probably bad practice for production code O=)

    Possible alternatives include:
        - manually downgrading selenium to v3.14.1 (works OK);
        - writing a wrapper function that converts capabilities dict to valid Options object.
    """

    if selenium_is_4_or_newer:
        # see docstring right above
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
