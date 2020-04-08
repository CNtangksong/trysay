package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //运行类去接收前端请求,   地址/hello?name=xxx       页面返回到hello.html
public class IndexController {

    @GetMapping("/")//  /后面没有则代表根
    public  String index(){
        return "index";

    }
}
