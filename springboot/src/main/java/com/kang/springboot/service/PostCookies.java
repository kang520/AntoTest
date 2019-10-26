package com.kang.springboot.service;

import com.kang.springboot.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/",tags = "这是所有的POST请求")
public class PostCookies {

    private static Cookie cookie;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletResponse response,
                      @RequestParam(value = "userName",required = true) String name,
                      @RequestParam(value = "pwd",required = true) String pwd){
        cookie = new Cookie("login","true");
        if (name.equals("zhang") && pwd.equals("123")){
            response.addCookie(cookie);
            return "你成功能录，并且返回Cookies信息";
        }
        return "用户名密码错误!";
    }

    @RequestMapping(value = "/getuser",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户信息",httpMethod = "POST")
    public String getUser(HttpServletRequest request,
                          @RequestBody User u){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie :cookies){
            if (cookie.getName().equals("login")
                    && cookie.getValue().equals("true")
                    && u.getUserName().equals("zhang")
                    && u.getPassword().equals("123")){

                User user = new User();
                user.setName("张三");
                user.setAge("20");
                user.setSex("男");
                return user.toString();
            }
        }
        return "登录失败！";
    }
}
