package com.example.demo.service;

import com.example.demo.dto.CommentDTO;
import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.enums.NotificationStatusEnum;
import com.example.demo.enums.NotificationTypeEnum;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.*;
import com.example.demo.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;


    @Transactional//spring自带的注解，把方法变成一个事务，只要里面出现失败，全部回滚
    public void insert(Comment comment, User commentator) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }else {

                Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());//拿到问题的title，顺便验证一下问题是不是存在
                if(dbComment==null){
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }

                //增加评论数，，    dbcomment也可以？
                commentMapper.insert(comment);
                Comment parentComment = new Comment();
                parentComment.setId(comment.getParentId());
                parentComment.setCommentCount(1);
                commentExtMapper.incCommentCount(parentComment);
                //    创建通知
                createNotify(comment,dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT,question.getId());
            }
        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);//插入评论
            question.setCommentCount(1);//给评论数赋值1，再调用extmapper来实现+1
            questionExtMapper.incCommentCount(question);
//    创建通知
            createNotify(comment,question.getCreator(), commentator.getName(),question.getTitle(),NotificationTypeEnum.REPLY_QUESTION,question.getId());
        }
    }

//    创建通知
    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType,Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNRED.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()//通过example查id和类型
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());//传一个type
        commentExample.setOrderByClause("gmt_create desc");//按gmtcreate 倒叙排序
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size()==0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> comentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());//拿到评论者的id,集合特点不重复
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(comentators);//转成userids

        //获取评论人转为map,map可以一次获取到，降低时间复杂度
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换 comment 为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
