package com.example.demo.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {//获取accesstoken
    private  String client_id;
    private  String client_secret;
    private  String code;
    private  String redirect_uri;
    private  String state;
}
