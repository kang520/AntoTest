package com.kang.cases;

import com.kang.config.TestConfig;
import com.kang.model.GetUserListCase;
import com.kang.model.User;
import com.kang.utils.DataBaseUtil;
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

public class GetUserListTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男的用户信息")
    public void getUserListInfo() throws IOException, InterruptedException {
        SqlSession session = DataBaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
        //发送请求，获取结果
        JSONArray resultJson = getJsonResult(getUserListCase);

//        Thread.sleep(3000);
        //验证结果
        List<User> usreList = session.selectList(getUserListCase.getExpected(),getUserListCase);
        for (User users : usreList){
            System.out.println("获取的User是:"+users.toString());
        }
        JSONArray userListJson = new JSONArray(usreList);
        Assert.assertEquals(userListJson.length(),resultJson.length());
        for (int i =0;i<resultJson.length();i++){
            JSONObject expected= (JSONObject) resultJson.get(i);
            JSONObject actual= (JSONObject) userListJson.get(i);
            Assert.assertEquals(expected.toString(),actual.toString());
        }
    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject param = new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("sex",getUserListCase.getSex());
        param.put("age",getUserListCase.getAge());

        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        JSONArray resultJson = new JSONArray(result);
        return resultJson;
    }
}
