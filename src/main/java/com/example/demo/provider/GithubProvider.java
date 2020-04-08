package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component      //调用不需要实例化对象,ioc-spring强大之处
public class GithubProvider {
    public  String getAccessToken(AccessTokenDTO accessTokenDTO) {//提交，参考github代码注解
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
                    String string= response.body().string();    //拿到返回的信息
            String token = string.split("&")[0].split("=")[1];//从信息获取accessToken
            return token ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public GithubUser getUser(String accessToken)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
           //     .url("https://api.github.com/user?access_token="+ accessToken)   // https://blog.csdn.net/warmHug/article/details/105284048
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string= response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//把string自动转为GithubUser对象
            return  githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
