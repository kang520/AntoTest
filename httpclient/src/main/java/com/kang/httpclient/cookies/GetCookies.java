package com.kang.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class GetCookies {
    private String url;
    private ResourceBundle bundle;
    private CookieStore store;
    @BeforeTest
    public void beforeTest(){
        //加载配置文件，设置编码格式
        bundle = ResourceBundle.getBundle("app", Locale.CHINA);
        url = bundle.getString("test.url");
    }
    @Test
    public void getCookies() throws IOException {
        String result;
        //拼接url
        String uri = bundle.getString("getcookie");
        String theUrl = this.url+uri;
        //请求逻辑
        HttpGet get = new HttpGet(theUrl);
        DefaultHttpClient client = new DefaultHttpClient();
//        HttpClient client = HttpClientBuilder.create().build();
//        CloseableHttpClient client = HttpClientBuilder.create().build();
//        CloseableHttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //获取cookies
        this.store = client.getCookieStore();
        List<Cookie> CookieList = store.getCookies();
        for (Cookie cookie : CookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("Cookie name = "+name+";  value ="+value);
        }
//        ((CloseableHttpClient)client).close();
    }
    @Test(dependsOnMethods = "getCookies")
    public void getwithCookie() throws IOException {
        String uri = bundle.getString("getwithcookie");
        String testurl = this.url+uri;
        HttpGet get = new HttpGet(testurl);
        DefaultHttpClient client = new DefaultHttpClient();
        //设置cookie
        client.setCookieStore(this.store);
        //获取状态码
        HttpResponse response = client.execute(get);
        int code = response.getStatusLine().getStatusCode();
        System.out.println("状态码 = " +code);
        if (code == 200){
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }
    }
}
