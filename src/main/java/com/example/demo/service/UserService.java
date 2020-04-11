package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(dbUser==null){//插入
            user.setGmtCreate(System.currentTimeMillis());//当前时间
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{//更新
            dbUser.setGmtModified(System.currentTimeMillis());//更新时间
            dbUser.setAvatarUrl(user.getAvatarUrl());//头像可能变化
            dbUser.setName(user.getName());//名字
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }
}
