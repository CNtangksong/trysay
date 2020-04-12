package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size()==0){//插入
            user.setGmtCreate(System.currentTimeMillis());//当前时间
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{//更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());//更新时间
            updateUser.setAvatarUrl(user.getAvatarUrl());//头像可能变化
            updateUser.setName(user.getName());//名字
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());//查符合id条件的
            userMapper.updateByExampleSelective(updateUser,example);//第一个更新的内容,第二个是要被替换的内容
        }
    }
}
