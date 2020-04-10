package com.example.demo.service;

import com.example.demo.dto.PaginationDTO;
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

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();

        Integer totalPage;

        if(totalCount % size==0){//算总页数
            totalPage=totalCount / size ;
        }else {
            totalPage=totalCount / size +1;
        }

        if(page<1){//判断page的范围，以免有人手动修改地址出现错误
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset = size *(page-1);
        List<Question> questions=questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();//新建dtoList

        for (Question question:questions) {
            User user =userMapper.findbyId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//Spring 内置方法，把question值放到DTO中，questionDTO.setId(question.getId());
            questionDTO.setUser(user);//放入user
            questionDTOList.add(questionDTO);//把数据放进List
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }   //做中间层，负责把mapper组装起来，不然不能关联（帖子和用户）起来使用



    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);

        Integer totalPage;

        if(totalCount % size==0){//算总页数
            totalPage=totalCount / size ;
        }else {
            totalPage=totalCount / size +1;
        }

        if(page<1){//判断page的范围，以免有人手动修改地址出现错误
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset = size *(page-1);
        List<Question> questions=questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();//新建dtoList

        for (Question question:questions) {
            User user =userMapper.findbyId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//Spring 内置方法，把question值放到DTO中，questionDTO.setId(question.getId());
            questionDTO.setUser(user);//放入user
            questionDTOList.add(questionDTO);//把数据放进List
        }

        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
