package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller //运行类去接收前端请求,   地址/hello?name=xxx       页面返回到hello.html
public class HelloController {

    @GetMapping("/hello")//
    public  String hello(@RequestParam(name ="name") String name , Model model){
        model.addAttribute("name",name);
        return  "hello";

    }
}
