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

    # 登录百度用例
    def test_login(self):
        self.log.login("15150583810", "89933920liu")
        result = self.log.user()  # 如果不打印result就不会显示内容，但是实际上是有值的，可用来比较；可参考base文件
        print(result)

        # 断言：下面两个写哪个都可以
        assert result == "浣欌灂闊"
        self.assertTrue(result == "浣欌灂闊")

