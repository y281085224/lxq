package com.tester.cases;

import com.tester.config.TestConfig;
import com.tester.model.UpdateUserInfoCase;
import com.tester.model.User;
import com.tester.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserInfoTest {



    @Test(dependsOnGroups = "loginTrue",description = "更改用户信息")
    public void updateUserInfo() throws IOException, InterruptedException {

        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        String result = getResult(updateUserInfoCase);

        Thread.sleep(3000);//因为修改后才能比对结果，要等修改完成后再获取新的结果 3s的等待时间等修改数据库完成

        User user = session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);
        System.out.println("7777777777777777777777:"+user.toString());
        Assert.assertNotNull(user);//判断这user是不是空,如果不是空就继续往下走
        Assert.assertNotNull(result);


    }




    @Test(dependsOnGroups = "loginTrue",description = "删除用户信息")
    public void deleteUser() throws IOException, InterruptedException {

        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        String result = getResult(updateUserInfoCase);

        Thread.sleep(3000);

        User user = session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);
        Assert.assertNotNull(user);//判断这user是不是空,如果不是空就继续往下走
        Assert.assertNotNull(result);
    }

    private String getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        post.setHeader("content-type", "application/json");
        JSONObject param = new JSONObject();
        param.put("id",updateUserInfoCase.getUserId());//这个决定了修改user表中的第几行
        param.put("userName",updateUserInfoCase.getUserName());
        param.put("sex",updateUserInfoCase.getSex());
        param.put("age",updateUserInfoCase.getAge());
        param.put("permission",updateUserInfoCase.getPermission());
        param.put("isDelete",updateUserInfoCase.getIsDelete());

        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        String resultInt;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        resultInt = EntityUtils.toString(response.getEntity(),"utf-8");

        //int resultInt = Integer.parseInt(result);//数据库里写得是Integer类型，这里转int
        return resultInt;



    }
}
