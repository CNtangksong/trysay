package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller //运行类去接收前端请求,   地址/hello?name=xxx       页面返回到hello.html
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")//  /后面没有则代表根
    public  String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null)
        {
            for(Cookie cookie:cookies)//循环找cookie
            {
                if(cookie.getName().equals("token")){//找key为token的value
                    String token =cookie.getValue();
                    User user =userMapper.findByToken(token);//数据库里找token的值
                    if(user !=null){
                        request.getSession().setAttribute("user",user);//找到把登录信息加入session中
                    }
                    break;
                }
            }
        }



        return "index";

    }
}