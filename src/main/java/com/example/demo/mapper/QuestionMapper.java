package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_Create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void creat(Question question);

    @Select("select * from question limit #{offset},#{size}")//获取question表的值
    List<Question> list(@Param(value = "offset") Integer offset, Integer size);

    @Select("select count(1) from question")//查数据库的总数
    Integer count();
}
