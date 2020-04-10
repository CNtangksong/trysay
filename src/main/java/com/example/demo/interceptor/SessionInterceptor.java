package com.example.demo.interceptor;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service    //没有service autowired不会工作，因为不是spring接管的
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {//在请求程序之前先检测cookie
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
        return true;//返回ture handler继续执行
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
