package com.example.demo.controller;

import com.example.demo.dto.CommentCreateDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.service.CommentService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {//controller一般可以获得地址的一些信息

    @Autowired
    private QuestionService questionService;//service才拿的到，相当于把mapper组合起来了

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")//下面可以直接拿到地址栏后面的
    public String question(@PathVariable(name = "id")Long id, Model model){

        QuestionDTO questionDTO = questionService.getById(id);//questionDTO还有user信息

        List<CommentDTO> comments = commentService.listByQuestionId(id);

        //阅读数添加
        questionService.inView(id);
        model.addAttribute("question",questionDTO);//写到页面上去
        model.addAttribute("comments",comments);
        return "question";
    }
}
