import pytest
from selenium import webdriver
import os
import allure
import time

# 这个文件会自动被识别，里面的内容会自动执行；名字一定要是 conftest 不能改

_driver = None


# 下方这个函数是allure用例失败截图函数，截得图在allure报告中可看到
@pytest.hookimpl(tryfirst=True, hookwrapper=True)
def pytest_runtest_makereport(item, call):
    '''
    获取每个用例状态的钩子函数
    :param item:
    :param call:
    :return:
    '''
    # 获取钩子方法的调用结果
    outcome = yield
    rep = outcome.get_result()
    # 仅仅获取用例call 执行结果是失败的情况, 不包含 setup/teardown
    if rep.when == "call" and rep.failed:
        mode = "a" if os.path.exists("failures") else "w"
        with open("failures", mode) as f:
            # let's also access a fixture for the fun of it
            if "tmpdir" in item.fixturenames:
                extra = " (%s)" % item.funcargs["tmpdir"]
            else:
                extra = ""
            f.write(rep.nodeid + extra + "\\n")
        # 添加allure报告截图
        if hasattr(_driver, "get_screenshot_as_png"):
            with allure.step('添加失败截图...'):
                allure.attach(_driver.get_screenshot_as_png(), "失败截图", allure.attachment_type.PNG)


# 加上这个，Driver函数每次都会自动启动，和 @pytest.fixture(scope='function')  一个意思()不写内容，默认就是scope='function'
"""
@pytest.fixture(scope='session', autouse=True)  # 这Driver函数写不写autouse=True，在test_1/2用例中都能被使用，具体原因不明
def Driver():
    driver_1 = get_driver()
    yield driver_1
    driver_1.quit()
"""


@pytest.fixture(scope='session', autouse=True)
def Driver():
    global _driver
    _driver = webdriver.Chrome()
    time.sleep(2)

    # driver.get("http://sh.ganji.com")
    _driver.get("https://www.baidu.com")

    yield _driver
    _driver.quit()

    return _driver


# 下方几个函数是 初始化，消除代码（作用和setup/teardown一样）
# -session：是多个文件调用一次，可以跨.py文件调用
@pytest.fixture(scope='session', autouse=True)
def pySession():
    print("\n！！！！！！，session用例开始，！！！！！！")

    # 相当于teardown，最后执行，终结函数
    yield
    print("\n！！！！！！，session用例结束，！！！！！！")


# -module：每一个.py文件调用一次，
@pytest.fixture(scope='module', autouse=True)
def pyModule():
    print("\n！！！！！！，module用例开始，！！！！！！")

    yield
    print("\n！！！！！！，module用例结束，！！！！！！")


# -class：每一个类调用一次，一个类中可以有多个方法
@pytest.fixture(scope="class", autouse=True)
def pyClass():
    print("\n！！！！！！，class用例开始，！！！！！！")

    yield
    print("\n！！！！！！，class用例结束，！！！！！！")


# -function：每一个函数或方法都会调用，当然前提是得写上 autouse=True 才会生效
@pytest.fixture(scope='function')
def pyFunction():
    print("\n！！！！！！，function用例开始，！！！！！！")

    yield
    print("\n！！！！！！，function用例结束，！！！！！！")
