import unittest
from selenium import webdriver
from page.login import Mok1, url
import time


class mok1Case(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.driver = webdriver.Chrome()
        cls.driver.get(url)
        cls.log = Mok1(cls.driver)

    @classmethod
    def tearDownClass(cls):
        time.sleep(3)
        cls.driver.quit()  # 这里有黄色的警告，不用管

    def setUp(self):
        print("打开浏览器")
        print("用例执行前只执行一次")

    def tearDown(self):
        print("关闭浏览器")

    # 打开百度新闻用例
    def test_xw(self):
        self.log.xw()
        result = self.log.newHandle("百度新闻")

        # 断言
        assert result == True
