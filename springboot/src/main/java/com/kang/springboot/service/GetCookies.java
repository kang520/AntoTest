package com.kang.springboot.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/",tags = "这是我的所有get接口")
public class GetCookies {
    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "获取cookies的get方法",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "这是一个返回Cookis信息的接口";
    }
    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value = "携带cookies信息请求的get方法",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            return "你必须携带cookies来访问！";
        }
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")){
                return "请求带cookies信息，访问成功！";
            }
        }
        return "你必须携带cookies来访问！";
    }
    /**
     * get带参数两种方式
     */
    @RequestMapping(value = "/test1" ,method = RequestMethod.GET)
    @ApiOperation(value = "get请求带参数，键值对请求方法",httpMethod = "GET")
    public Map<String ,Integer> gettest1(@RequestParam Integer start,@RequestParam Integer end){
        Map<String,Integer> mylist = new HashMap<>();
        mylist.put("鞋子",300);
        mylist.put("衬衫",250);
        mylist.put("方便面",10);
        return mylist;
    }
    @RequestMapping(value = "/test2/{start}/{end}",method = RequestMethod.GET)
    @ApiOperation(value = "get请求带参数，/{}/{}方法",httpMethod = "GET")
    public Map gettest2(@PathVariable Integer start,@PathVariable Integer end){
        Map<String,Integer> mylist = new HashMap<>();
        mylist.put("鞋子",300);
        mylist.put("衬衫",250);
        mylist.put("方便面",10);
        return mylist;
    }
}
