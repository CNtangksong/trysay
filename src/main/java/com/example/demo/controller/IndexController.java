package com.example.demo.controller;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller //运行类去接收前端请求,   地址/hello?name=xxx       页面返回到hello.html
public class IndexController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/")//  /后面没有则代表根
    public  String index(Model model,
                         @RequestParam(name = "page", defaultValue="1") Integer page,//默认第一页是1，每页5个
                         @RequestParam(name = "size", defaultValue="5") Integer size){
        PaginationDTO pagination =questionService.list(page,size);//获取用户发帖的数据，在跳转页面之前
        model.addAttribute("pagination",pagination);
        return "index";

    }
}
