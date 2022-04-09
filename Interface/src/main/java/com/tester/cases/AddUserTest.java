package com.tester.cases;

import com.tester.config.TestConfig;
import com.tester.model.AddUserCase;
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

//该代码不建议看，优化后的内容在AddUserTest_2
public class AddUserTest {

    //直接运行该代码会报错，是因为和依赖的方法 loginTrue方法不在一个class文件中；若想直接运行成功，可以把该代码复制到LoginTestclass文件中再运行
    //或者直接运行 testng.xml，也可全部成功


    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")//依赖于LoginTest里面的登录方法 longinTrue
    public void addUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();

        AddUserCase addUserCase = session.selectOne("addUserCase", 2);

        System.out.println("33333333333333333333333333333"+addUserCase);//打印出来的是该数据库中id为1的数据信息
        System.out.println(TestConfig.addUserUrl);


    //发送请求，获取结果
        String result = getResult(addUserCase);

        Thread.sleep(1000);//因为修改后才能比对结果，要等修改完成后再获取新的结果 3s的等待时间等修改数据库完成

        session.commit(true);//这是提交插入数据库的内容；如果修改数据库的事务没提交，那么数据库中还没有这个数据，下面的代码就查询不到该内容
        System.out.println("33333333333333333333333333333"+addUserCase);
        //验证返回结果 User 是model文件夹中的User
        User user = session.selectOne("addUser",addUserCase);//查询数据库中的内容
        System.out.println("101010101010101010100101101010"+user.toString());

        Assert.assertEquals(addUserCase.getExpected(),result);//addUserCase.getExpected()是预期结果，result是实际结果；这里两者进行比较



    }
    private String getResult(AddUserCase addUserCase) throws IOException {
        //詳細的註解見：HttpClient→cookies→Test_2_MyCookiesForPost
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        post.setHeader("content-type", "application/json");
        JSONObject param = new JSONObject();
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("age",addUserCase.getAge());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);

        //設置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        String reslut;

        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        reslut = EntityUtils.toString(response.getEntity());
        System.out.println("8888888888888888888888888888888: "+reslut);
        return reslut;
    }

}
