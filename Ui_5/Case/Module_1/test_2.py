import pytest
from Page.y2 import YY2


class TestCase:

    # @pytest.mark.skip(reason="就是让这一条用例跳过")  #在需要跳过的用例上加这个装饰器，可直接跳过这一条用例
    def test_zufang(self, Driver):
        self.y2=YY2(Driver)
        self.y2.zufang2()
        ttt=Driver.title
        assert ttt == "【上海租房|上海租房网|上海租房信息】-上海赶集网1111111111111111"

    def test_zufangzi(self, Driver):
        self.y2 = YY2(Driver)
        self.y2.zufang2()
        ttt = Driver.title
        assert ttt == "【上海房产网】上海房产信息网 - 上海58同城"


if __name__ == '__main__':
    pytest.main()