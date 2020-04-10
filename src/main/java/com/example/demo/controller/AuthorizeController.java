package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired//自动加载spring容器里面的实例
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;//报错无影响

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,  //接收参数
                           @RequestParam(name="state")String state,
                           HttpServletResponse response){         //session是在这里拿到的
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();//选中new ~,ctrl alt v自动添加写入对象
        accessTokenDTO.setClient_id(clientId);//编号
        accessTokenDTO.setClient_secret(clientSecret);//机密
        accessTokenDTO.setCode(code);//登录的code
        accessTokenDTO.setRedirect_uri(redirectUri);//返回地址
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);//写入对象
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser !=null && githubUser.getId()!=null){  //解析回来的信息可能不空，但没有id，需要判断
            //登录成功写cookie session
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());//当前时间
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));//把生成的token放在（数据库，前面放了）和cookie中
            return "redirect:/";    //重定向
        }else{
            //失败
            return "redirect:/";    //重定向
        }
    }
}
