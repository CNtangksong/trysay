package com.example.demo.service;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.enums.NotificationStatusEnum;
import com.example.demo.enums.NotificationTypeEnum;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;


    public PaginationDTO list(Long userId, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        Integer totalPage;

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);//查userId
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);

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

        NotificationExample example=new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));//利用mybatis的一个插件实现分页

        if(notifications.size()==0){
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();//新建dtoList

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNRED.getStatus());
       return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {//消息空的话
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {//如果看的消息的人同用户的id不同
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);//更新状态

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
