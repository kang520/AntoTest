package com.kang.httpclient.cookies;

import com.sun.org.apache.regexp.internal.RE;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class PostCookies {

    private String url;
    private ResourceBundle bundle;
    private CookieStore store;
    @BeforeTest
    public void beforeTest(){
        //读取配置文件，设置编码格式
        bundle = ResourceBundle.getBundle("app", Locale.CHINA);
        //获取URL地址
        url = bundle.getString("test.url");
    }

    @Test
    public void getcookie() throws IOException {
        String result;
        String uri = bundle.getString("getcookie");
        //拼接请求头
        String testurl = this.url+uri;
        //声明clint对象，用来发送请求
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个get请求
        HttpGet get = new HttpGet(testurl);
        //发送请求，获取相应结果
        HttpResponse response = client.execute(get);
        //接收返回结果
        result = EntityUtils.toString(response.getEntity());
        System.out.println(result);
        //获取cookie仓库
        this.store = client.getCookieStore();
        //获取cookie集合
        List<Cookie> cookieList = store.getCookies();
        //遍历cookie集合，获取cookie信息
        for (Cookie cookie : cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("Cookie name = "+name+"; value = "+value);
        }
    }
    @Test(dependsOnMethods = "getcookie")
    public void postWithCookie() throws IOException {
        String uri = bundle.getString("postwithcookie");
        String testurl = this.url+uri;

        //声明client对象，用来发送请求，设置cookie信息
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个post请求
        HttpPost post = new HttpPost(testurl);
        //设置cookies信息
        client.setCookieStore(this.store);
        //设置post请求参数
        JSONObject param = new JSONObject();
        param.put("name","huhansan");
        param.put("age","18");
        //设置请求头信息
        post.setHeader("content-type","application/json");
        //将参数添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //发送post请求信息
        HttpResponse response = client.execute(post);
        //声明一个对象，接收返回结果
        String result;
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //判断结果是否跟预期结果一致
        //将返回结果转换成JSON对象
        JSONObject resultJson = new JSONObject(result);
        //具体判断结果值
        //获取结果值
        String success = resultJson.getString("huhansan");
        String status = (String) resultJson.get("status");
        Assert.assertEquals("success",success);
        Assert.assertEquals("1",status);
    }
}
