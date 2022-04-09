package com.tester.cases;

import com.tester.config.TestConfig;
import com.tester.model.GetUserInfoCase;
import com.tester.model.User;
import com.tester.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {

/*
    @BeforeTest(groups = "loginTrue")
    public void beforeTest(){//启用URL，调试用的，批量执行时是根据分组来的
        LoginTest b = new LoginTest();
        b.beforeTest();
    }

*/


//不建议看这个代码，GetUserInfoTest_2是优化后的
//这个文件代码的意思是：GetUserInfocase表中获取 userID中的内容，然后根据这个内容查看user表中的数据

    @Test(dependsOnGroups = "loginTrue",description = "获取userID为1的用户信息")
    public void getUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCose = session.selectOne("getUserInfoCase",1);
        System.out.println(getUserInfoCose.toString());
        System.out.println(TestConfig.getUserInfoUrl);


        JSONArray resultJson = getJsonResult(getUserInfoCose);




        User user = session.selectOne(getUserInfoCose.getExpected(),getUserInfoCose);//获取GetUserInfoCase表中getExpected的值，当做写死的“getUserInfo”，从而调用对应的sql语句



        List userList = new ArrayList();



        userList.add(user);//这个感觉可以优化，参考 GetUserInfoListTest文件
        JSONArray jsonArray = new JSONArray(userList);
        JSONArray resultJsonArray = new JSONArray(resultJson.getString(0));
        //Assert.assertEqualsNoOrder(jsonArray.toString(),resultJson);
                //assertEqualsNoOrder

        Assert.assertEquals(jsonArray.toString(),resultJsonArray.toString());//这个可能有问题，可参考GetUserInfoListTest 如果根据参考修改正确，以后就按照正确的思路来写
    }







    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCose) throws IOException {

        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        post.setHeader("content-type", "application/json");
        JSONObject param = new JSONObject();
        param.put("id", getUserInfoCose.getUserId());
        StringEntity entiy = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entiy);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        List resultList = Arrays.asList(result);
        JSONArray array = new JSONArray(resultList);

        return array;

    }

}
