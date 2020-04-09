package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class QuestionDTO {//此传输层用来关联发布的帖子和用户的关系,比Question多一个User
    private  Integer id;
    private  String title;
    private  String description;
    private  String tag;
    private  Long gmtCreate;
    private  Long gmtModified;
    private Integer creator;
    private  Integer viewCount;
    private  Integer commentCount;
    private  Integer likeCount;
    private User user;
}
