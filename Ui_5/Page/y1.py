from Base.Base import Base
from selenium import webdriver
import time
from selenium.webdriver.common.by import By


class Y1():
    url = "https://www.baidu.com"
    denglu_link = (By.LINK_TEXT, "登录")
    zhuce_class = (By.LINK_TEXT, "立即注册")
    login_close1 = (By.XPATH, "//div[@class='buttons']/a")


class YY1(Base, Y1):

    # 这里写的是封装好的函数，在写测试用例时直接调用就好
    def denglu(self):
        self.click(self.denglu_link)
        self.click(self.zhuce_class)
        self.new_handle()
        time.sleep(2)

    def login_close(self):
        self.old_handle()
        self.click(self.login_close1)


if __name__ == '__main__':
    driver = webdriver.Chrome()
    y1 = YY1(driver)
    driver.get(y1.url)
    y1.denglu()
    y1.login_close()
    time.sleep(355)
    driver.quit()
