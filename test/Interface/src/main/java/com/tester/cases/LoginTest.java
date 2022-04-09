package com.tester.cases;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import com.tester.model.InterfaceName;


import com.tester.config.TestConfig;
import com.tester.model.LoginCase;
import com.tester.utils.ConfigFile;
import com.tester.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;


public class LoginTest {


       //@BeforeTest(groups = "loginTrue", description = "测试准备工作,获取HttpClient对象")
    @BeforeTest
    public void beforeTest() {
        //TestConfig类里面的属性是静态的，所以是类名.属性名
        TestConfig.defaultHttpClient = new DefaultHttpClient();

        //getUrl是静态方法，所以这里使用的是类名.方法名
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);//这里写的是给name传的实参，如果ConfigFile文件中的getUrl方法中写的与这个填写的相等，就会执行相等中的代码
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
    }




    @Test(groups = "loginTrue",description = "用户成功登陆接口")
    public void loginTrue() throws IOException, InterruptedException {

        SqlSession session = DatabaseUtil.getSqlSession();

        //执行这个代码能执行数据库的原因：com.tester.utils中DatabaseUtil文件的配置
        //LoginCase是 model文件下的文件名
        LoginCase loginCase = session.selectOne("loginCase",1);//第一个参数是传的配置文件中的要返回的内容，那里面的ID；第二个参数是取数据库表中的id为2的数据
        //这里写的 1 而不是"1",是因为
        System.out.println("1111111111111111111111111111111111111111111111"+loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //发送请求
        Boolean result = getResult(loginCase);
        Assert.assertTrue(result);


        //验证结果
       // Assert.assertEquals(loginCase.getExpected(), result);//addUserCase.getExpected()是预期结果，result是实际结果；这里两者进行比较


    }




    @Test(description = "用户登陆失败接口")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        Boolean result = getResult(loginCase);


        Assert.assertFalse(result);//如果返回的内容是false，就算通过



    }

    private Boolean getResult(LoginCase loginCase) throws IOException {

        HttpPost post = new HttpPost(TestConfig.loginUrl);
        post.setHeader("content-type", "application/json");
        JSONObject param = new JSONObject();
        param.put("userName",loginCase.getUserName());
        param.put("password",loginCase.getPassword());

        StringEntity entity = new StringEntity(param.toString());
        post.setEntity(entity);

        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");


       
        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
        


        return Boolean.valueOf(result);


    }




}
