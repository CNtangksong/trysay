package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questions=questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();//新建dtoList
        for (Question question:questions) {
            User user =userMapper.findbyId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//Spring 内置方法，把question值放到DTO中，questionDTO.setId(question.getId());
            questionDTO.setUser(user);//放入user
            questionDTOList.add(questionDTO);//把数据放进List
        }
        return questionDTOList;
    }   //做中间层，负责把mapper组装起来，不然不能关联（帖子和用户）起来使用
}
