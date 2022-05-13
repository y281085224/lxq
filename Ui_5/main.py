import pytest
import os

# 第三方allure测试报告：推荐这种
pytest.main(['-vs', './Case/Module_1/test_1.py',  '--html=./report/allure_report --clean',
             '--alluredir=./report/allure-result', '--clean-alluredir'])

os.system('allure generate ./report/allure-result -o ./report/allure_report --clean')

"""
'-vs' 一般已启用，可加可不加
'./Case/Module_1/test_1.py' ：指定运行test_1.py文件中的用例
'--html=./report/allure_report --clean'：美化allure报告路径 --clean是情况原来的内容，一定要加不然文件被占用会报错
'--alluredir=./report/allure-result'：生成allure原始报告（未被美化，没有index.html，有很多看不懂的文件的那种）
'--clean-alluredir'：清空未美化前的路径历史内容；如果不加这个多次执行不同的用例后里面的内容越来越多，美化后显示的测试结果自然也多，会出现报告中显示执行多次的情况，即会带出历史报告内容
os.system：终端（cmd等）执行的意思
'allure generate ./report/allure-result -o ./report/allure_report --clean'：生成allure美化后的报告(有index.html的那种)，并放到美化报告路径中
"""