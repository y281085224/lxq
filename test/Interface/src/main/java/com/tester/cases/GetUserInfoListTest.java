package com.tester.cases;


import com.tester.config.TestConfig;
import com.tester.model.*;
import com.tester.utils.ConfigFile;
import com.tester.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;


public class GetUserInfoListTest {


//这个文件代码的意思是：获取表“getuserlistcase”表中的数据，当做筛选条件查询user表中符合条件的数据 （“getuserlistcase”表中有用的数据是：sex=0）

    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男的用户信息")
    public void  GetUserListInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
        
        //获取结果
        JSONArray resultJson = getJsonResult(getUserListCase);

        //从数据库中获取实际结果
        List<User> userList = session.selectList(getUserListCase.getExpected(),getUserListCase);//获取getExpected()的值，当做写死的“getUserList”，从而调用对应的sql语句

        //验证步骤写法和AddUserTest中的不一样；这个是打印看下里面的内容，不涉及验证，可删除
        for (User u :
                userList) {
            System.out.println("222222222222222222222222获取的user：  "+u.toString());
        }

        //把userList内容转成 JSONArray类型的
        JSONArray userListJson = new JSONArray(userList); //userList是List类型的；只有List类型的才能转成JSONArray类型
        //判断长度一不一样
        Assert.assertEquals(resultJson.length() , userListJson.length());

        for (int i=0;i<resultJson.length(); i++){
            Object actual = resultJson.get(i);
            Object expect = userListJson.get(i);

            System.out.println("actual中的内容"+actual.toString());

            //判断内容一不一样
            Assert.assertEquals(actual.toString(), expect.toString());
        }

    }


    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {

        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        post.setHeader("content-type", "application/json");
        JSONObject param = new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("sex",getUserListCase.getSex());
        param.put("age",getUserListCase.getAge());

        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);

        result = EntityUtils.toString(response.getEntity(),"utf-8");

        //把result转换成json格式
        JSONArray jsonArray = new JSONArray(result);
        System.out.println("打印jsonArray："+jsonArray);

        return jsonArray;

    }

}
