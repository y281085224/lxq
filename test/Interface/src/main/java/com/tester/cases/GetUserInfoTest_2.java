package com.tester.cases;

import com.tester.config.TestConfig;
import com.tester.model.AddData;
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
import java.util.List;

public class GetUserInfoTest_2 {


    //优化了GetUserInfoTest中的代码
    //这个文件代码的意思是：GetUserInfocase表中获取 userID中的内容，然后根据这个内容查看user表中的数据
    @Test(dependsOnGroups = "loginTrue",description = "获取userID为1的用户信息")
    public void getUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCose = session.selectOne("getUserInfoCase",1);
        System.out.println(getUserInfoCose.toString());
        System.out.println(TestConfig.getUserInfoUrl);


        JSONArray resultJson = getJsonResult(getUserInfoCose);



        //从数据库中获取的预期结果
        List<User> user = session.selectList(getUserInfoCose.getExpected(),getUserInfoCose);

        JSONArray userJson = new JSONArray(user);

        //判断长度
        Assert.assertEquals(resultJson.length(), userJson.length());

        //判断内容
        for (int i=0;i<resultJson.length(); i++){
            JSONObject actual = (JSONObject) resultJson.get(i);

            Object expect =  userJson.get(i);

            System.out.println("actual中的内容"+actual.toString());
            Assert.assertEquals(actual.toString(),expect.toString());
        }
  }







    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCose) throws IOException {

        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        post.setHeader("content-type", "application/json");
        JSONObject param = new JSONObject();
        param.put("id",getUserInfoCose.getUserId());
        StringEntity entiy = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entiy);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        //List resultList = Arrays.asList(result);
        JSONArray array = new JSONArray(result);
        return array;

    }

}
