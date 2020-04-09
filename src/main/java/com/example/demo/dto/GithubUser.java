package com.example.demo.dto;

import lombok.Data;

@Data
public class GithubUser {//用户返回值
    private  String name;
    private  Long id;
    private  String bid;
    private  String avatar_url;
}
