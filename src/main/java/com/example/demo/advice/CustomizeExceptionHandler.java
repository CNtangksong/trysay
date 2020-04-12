package com.example.demo.advice;

import com.example.demo.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model){//ModelAndView是当错误之后返回一个显示错误的页面
        if(e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else {//捕捉不到异常的时候
            model.addAttribute("message","服务器在休息，等一会再试试吧~");
        }
        return  new ModelAndView("error");//跳转erroe页面
    }
}
