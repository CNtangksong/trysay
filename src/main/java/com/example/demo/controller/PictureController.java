package com.example.demo.controller;

import com.example.demo.dto.PictureDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//需要返回session,message,url   对象
public class PictureController {
    @RequestMapping("/picture/upload")
    @ResponseBody//返回json
    public PictureDTO upload(){
        PictureDTO pictureDTO = new PictureDTO();
        pictureDTO.setSuccess(1);
        pictureDTO.setUrl("/img/123.png");
        return pictureDTO;
    }
}
