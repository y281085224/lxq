from selenium import webdriver
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support import expected_conditions as EC


# 该文件是二次封装的所有的函数

class Base:
    def __init__(self, driver_1: webdriver.Chrome):  # : webdriver.Chrome详情见t44
        self.driver = driver_1
        self.timeout = 10
        self.t = 0.5

    # 定位
    def findElement(self, locator):
        ele = WebDriverWait(self.driver, self.timeout, self.t).until(lambda x: x.find_element(*locator))
        return ele

    # 输入内容
    def sendKeys(self, locator, text):
        ele2 = self.findElement(locator)
        ele2.send_keys(text)

    # 点击
    def click(self, locator):
        ele3 = self.findElement(locator)
        ele3.click()

    # 光标移动悬停到某处
    def move(self, locator):
        ele = self.findElement(locator)
        ActionChains(self.driver).move_to_element(ele).perform()

    # 清空
    def clear(self, locator):
        ele = self.findElement(locator)
        ele.clear()

    # 判断元素是否被选中，返回bool值；一般用于：单选块，复选框，下拉框，是否选择到
    def isSelected(self, locator):
        ele = self.findElement(locator)
        r = ele.is_selected()
        return r

    # 判断某元素是否存在; 作用：和 findElement函数相比，这里不会抛异常，而是返回false
    def isElementExist(self, locator):
        try:
            self.findElement(locator)
            return True
        except:
            a = "False, 未找到该元素"
            return a

    # 判断title是否和期望的一样，返回bool值，因为title定位不到，通过其他方式对比，所以有一个参数
    # 演变过程在t39_2
    def is_title(self, _title):  # _title是需要比较的文本值
        try:
            result = WebDriverWait(self.driver, self.timeout, self.t).until(EC.title_is(_title))
            return result  # 如果一样返回True
        except:
            return False

    # 判断title是否包含该内容
    def is_title_contains(self, _title):
        try:
            result = WebDriverWait(self.driver, self.timeout, self.t).until(EC.title_contains(_title))
            return result
        except:
            return False

    # 判断该页面是否有这个文字，返回bool值,要做对比，所以有两个参数对比
    # 适用于：可点击和不可点击的文字；不适用于：按钮中的文字
    def is_text_in_element(self, locator, _text):
        try:
            result = WebDriverWait(self.driver, self.timeout, self.t).until(
                EC.text_to_be_present_in_element(locator, _text))
            return result
        except:  # 如果不写判断，错误的话会抛timeout的异常
            return False

    # 判断按钮中的是有某文字（包含也会返回true）；如果没有返回False
    # 仅限于元素里面有value=“***”的按钮才可使用该方法，不是所有的按钮都适用
    def is_value_in_element(self, locator, value):
        try:
            result = WebDriverWait(self.driver, self.timeout, self.t).until(
                EC.text_to_be_present_in_element_value(locator, value))
            return result
        except:
            return False

    # 判断alert弹窗的：是否存在系统的二次弹框，能定位到的二次弹框不算；
    # 可参考t16
    def is_alert(self):
        try:
            result = WebDriverWait(self.driver, self.timeout, self.t).until(EC.alert_is_present())
            # return result # 返回alert对象
            return True  # 这是手动让他返回True; 如果存在该弹框就返回True
        except:
            return False

    # 打印文本信息，比如：用户登录后，打印登录的用户名
    def get_text(self, locator):
        try:
            t = self.findElement(locator).text

            return t  # 直接调用这个函数不会打印出t的内容，除非这个函数被一个值接收，然后打印这个值才会有内容，但是实际是有值的，可以用来比较是否相等；和java不一样；java是调用带有return的方法就会返回内容；详情见t45
        except:
            print("获取text失败，请查找原因哈哈")
            return ""
