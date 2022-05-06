from selenium.webdriver.common.by import By
from common.base import Base
from selenium import webdriver
import time

url = "https://www.baidu.com"
loc1 = (By.CSS_SELECTOR, "div#u1>a.s-top-login-btn.c-btn.c-btn-primary.c-btn-mini.lb")
loc2 = (By.XPATH, "//p[@class='pass-form-item pass-form-item-userName']/input[@name='userName']")
loc3 = (By.XPATH, "//p[@class='pass-form-item pass-form-item-password']/input[2]")
loc4 = (By.CSS_SELECTOR, "p#TANGRAM__PSP_11__submitWrapper>input")
loc5 = (By.CSS_SELECTOR, "span.user-name.c-font-normal.c-color-t")

loc6 = (By.CSS_SELECTOR, "div#s-top-left>a")


class Mok1(Base):
    def login(self, userName="admin", password="123456"):  # 这里的admin是形参的默认值，如果没有传实参，admin就会生效
        self.click(loc1)
        self.sendKeys(loc2, userName)
        self.sendKeys(loc3, password)
        self.click(loc4)
        time.sleep(15)  # 给手动输入验证码留的时间
        if self.is_alert():  # 如果存在alert弹框，就关闭
            a = driver.switch_to.alert
            a.dismiss()

    # 用来写断言用的；这里是登录成功后获取登录的用户
    def user(self):
        u = self.get_text(loc5)
        return u

    # 百度新闻函数
    def xw(self):
        self.click(loc6)

    # 百度新闻用例验证函数
    def newHandle(self, _title):
        all_1 = self.driver.window_handles
        new_handle = all_1[1]
        self.driver.switch_to.window(new_handle)  # 切换到新的窗口上
        r = self.is_title_contains(_title)
        return r


if __name__ == "__main__":
    driver = webdriver.Chrome()
    driver.get("https://www.baidu.com")
    m = Mok1(driver)
    m.login("15150583810", "89933920liu")
