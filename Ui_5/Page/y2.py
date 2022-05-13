from Base.Base import Base
from selenium import webdriver
import time
from selenium.webdriver.common.by import By


class Y2:
    zufang = (By.LINK_TEXT, "租房")


class YY2(Base, Y2):
    def zufang2(self):
        self.click(self.zufang)
        all = self.driver.window_handles
        new_handle = all[1]
        self.driver.switch_to.window(new_handle)


if __name__ == '__main__':
    driver = webdriver.Chrome()
    y2 = YY2(driver)
    driver.get("http://sh.ganji.com")
    y2.zufang2()
    print(driver.title)
    time.sleep(3)
    driver.quit()
