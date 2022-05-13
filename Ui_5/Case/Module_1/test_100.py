import pytest
import os
import sys
from selenium import webdriver
import time

# os.path.abspath(__file__)  # 获取当前文件所在的路径，在cmd中执行该py文件时，要进入当前路径然后：python test_1
# os.path.dirname(__file__)  # 获取当前文件所在的路径,在cmd默认路径中，输入： python test_1所在路径\test_1
# os.path.dirname(os.path.dirname(__file__)) # 当前文件所在的路径的上一层路径
# 这里不可以写："../Case";原因：不明

# 由上面组合而来；回到了Ui_5的路径，并且在cmd中执行该py文件时，要进入当前路径然后输入：python test_1
path = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

"""
sys模块包含了与python解释器和它的环境有关的函数, 里面有个 sys.path属性。它是一个list.默然情况下python导入文件或者模块的话，
他会先在sys.path里找模块的路径。如果没有的话,程序就会报错。识别不了自己在其他模块中封装的函数
"""
# 把path路径添加到 sys.path中；这样就能识别自己在其他模块中封装的函数了，该添加必须写在导入自己定义在其他模块中的内容上面；具体图形讲解在有道云笔记
# 即：必须写在 from Page.y1 import YY1 上面
sys.path.append(path)

# 上述配置的目的：在cmd中运行带有在导入自己定义在其他模块中的内容得py文件时不会报错
from Page.y1 import YY1




#  @pytest.mark.skip(reason="就是要跳过这个类里面的所有用例")   #跳过该类的所有用例
class TestCase:
    def driver_1(self, Driver):
        self.y1 = YY1(Driver)
        print(Driver)

    def test_login(self, driver_1):
        self.y1.denglu()
        print("111111111111111")
        assert 1 == 1
        t = driver_1.title
        assert "注册" in t

    def test_login_close(self, driver_1):
        self.y1.login_close()
        t = driver_1.title
        assert "百度一下" in t


if __name__ == '__main__':
    pytest.main()
