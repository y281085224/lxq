import pytest

# pytest 默认的测试报告
if __name__ == '__main__':
    pytest.main([
        './Module_1/test_1.py',  # 执行Case路径下的用例
        "--html=baogao/report.html"  #报告存储路径
    ])

"""
    这是pytest自带的写法，和自带的测试报告；要想生成pytest自带的报告，需要在cmd默认路径中 输入：pip install pytest-html
    # 以下内容仅限在终端(如：cmd等)执行该py文件，或main.py文件中执行才会生效；
    # 原因：在pycharm中运行用例py文件虽然if __name__ == '__main__':下写了pytest.main()，但这句话其实是不生效的。只会执行当前py的内容

    pytest.main()  # 运行当前文件夹下 test_开头和_test开头的用例
    pytest.main(['./'])  # 运行当前文件夹下 test_开头和_test开头的用例
    pytest.main(['./test_1.py'])  # 运行当前文件夹下 test_1.py的文件用例
    pytest.main(['./test_1.py::test_m1_1'])  # 运行模块中的指定用例
    pytest.main(['./test_1.py::TestCase::test_m1_1'])  # 运行类中的指定用例
    pytest.main(['-k', 'pp'])  # 匹配包含pp的用例(匹配目录名、模块名、类名、用例名)
    pytest.main(['-k', 'spec', './test_1.py'])  # 匹配test_1.py文件下包含spec的用例
    pytest.main(['-k', 'pp', './test_1.py::TestCase'])  # 匹配TestCase类中包含pp的用例

    参数详解： 
    -s 表示输出信息,包括print打印的信息 
    -v 表示更相信的信息 
    -vs 一般一起用 
    -n 支持分布式运行测试用例 
    -k 根据用例的部分字符串指定测试用例 
    --html 路径 生成测试报告
    """
