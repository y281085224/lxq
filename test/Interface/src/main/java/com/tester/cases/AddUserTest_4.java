package com.tester.cases;

import com.tester.config.OpenCsv;
import com.tester.config.TestConfig;
import com.tester.model.AddUserCase;
import com.tester.model.User;
import com.tester.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

//该用例实现了数据解耦；数据从 "C:/Users/lxq/Desktop/dd/test.csv" 中获取的
public class AddUserTest_4 {
    //下面的代码为调试用的；调试时要把addUser方法上的注解换成 @Test
    private OpenCsv oc = new OpenCsv();
    private String[] str = new String[]{"userName","password","sex","age","permission","isDelete"};
    @BeforeTest
    public void test00() throws IOException, InterruptedException {


        LoginTest t=new LoginTest();
        t.beforeTest();
        t.loginTrue();
    }




    //@Test
    @Test(dependsOnGroups = "loginTrue", description = "添加用户接口测试")
    public void addUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        OpenCsv oc = new OpenCsv();
        AddUserCase addUserCase = new AddUserCase();
        addUserCase.setUserName(oc.csv(1).get(0));
        addUserCase.setPassword(oc.csv(1).get(1));
        addUserCase.setAge(oc.csv(1).get(3));
        addUserCase.setSex(oc.csv(1).get(2));
        addUserCase.setPermission(oc.csv(1).get(4));
        addUserCase.setIsDelete(oc.csv(1).get(5));





        System.out.println(TestConfig.addUserUrl);

        List<User> expectedList = session.selectList("addUser", addUserCase);
        int expected = expectedList.size();//插入前现有的数量
        int expectLith = expected + 1;//期望的数量

        //获取插入数据接口的状态码,执行完了插入数据
        int result = getResult(addUserCase);

        Thread.sleep(1000);

        session.commit(true);

        //判断添加接口的状态码是不是200
        Assert.assertEquals(result, 200);


        //从数据库中获得的预期结果,插入数据后再查下一遍
        List<User> actualList = session.selectList("addUser", addUserCase);//把插入的内容当做查询条件，查询内容

        System.out.println("插入成功后查询符合条件的内容:" + actualList);
        System.out.println("符合条件的数量是：" + actualList.size());
        //Assert.assertEquals(actualList.size() , 1);//如果要求不存在重复；就这样判断，下面的代码不要；这里actualList.size()当做实际数量来使用

        //允许有重复的数据
        int actualLenth = actualList.size();

        boolean actual;
        if (actualLenth >= 1) {
            actual = true;
        } else {
            actual = false;
        }

        //判断列表是否存在内容(如果原本库中一条数据没有，执行后状态码是200，但实际没插入成功，那么这个断言就能判断出来；用于假返回的情况)
        Assert.assertTrue(actual);//assertTrue断言，如果内容是true，则通过

        //判断预期的数量和实际的数量是否相等
        Assert.assertEquals(actualLenth, expectLith);

    }


    private int getResult(AddUserCase addUserCase) throws IOException {
        //詳細的註解見：HttpClient→cookies→Test_2_MyCookiesForPost
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        post.setHeader("content-type", "application/json");
        JSONObject param = new JSONObject();
        param.put("userName", addUserCase.getUserName());
        param.put("password", addUserCase.getPassword());
        param.put("sex", addUserCase.getSex());
        param.put("age", addUserCase.getAge());
        param.put("permission", addUserCase.getPermission());
        param.put("isDelete", addUserCase.getIsDelete());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);

        //設置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        int reslutStatusCode;

        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //EntityUtils.toString(response.getEntity());
        reslutStatusCode = response.getStatusLine().getStatusCode();
        System.out.println("实际返回的状态码是: " + reslutStatusCode);
        post.abort();//终止post
        return reslutStatusCode;
    }

}
