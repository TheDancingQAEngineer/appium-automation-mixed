"""This file was adapted from Appium sample code found at
https://github.com/appium/appium/tree/master/sample-code
as of November 15, 2021.

For fixtures and imports, see ./conftest.py"""


class TestFirst():

    def test_first(self, driver):
        """Simply checks test discovery and setup.
        Should be successfully collected and pass."""
        print("I am first test! I ran!")
        assert True
