package com.example.demo.controller;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue="1") Integer page,//默认第一页是1，每页5个
                          @RequestParam(name = "size", defaultValue="2") Integer size,
                          Model model){

        User user =(User) request.getSession().getAttribute("user");//强转获取user
        if(user==null){
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");//section等于questions
            model.addAttribute("sectionName","我的帖子");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");//section等于questions
            model.addAttribute("sectionName","最新回复");
        }

        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }

}
