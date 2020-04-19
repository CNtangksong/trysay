package com.example.demo.controller;

import com.example.demo.cache.TagCache;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(
            Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public  String edit(@PathVariable(name = "id")Long id,
                        Model model){//拿路径后面的id
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());//回些到页面
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());//用来区分是否是修改的
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public  String doPublish(
            @RequestParam(value = "title",required = false) String title,//可以空
            @RequestParam(value ="description",required = false) String description,
            @RequestParam(value ="tag",required = false) String tag,
            @RequestParam(value ="id",required = false)Long id,
            HttpServletRequest request,
            Model model){       //服务端错误写道model里面

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());
        if(title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null||description==""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if(tag==null||tag==""){
            model.addAttribute("error","主题不能为空");
            return "publish";
        }

        User user =(User) request.getSession().getAttribute("user");//强转获取user
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","检测到非法标签"+invalid);
            return "publish";
        }


        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);

        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
