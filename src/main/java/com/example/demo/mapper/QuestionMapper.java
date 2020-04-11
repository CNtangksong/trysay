package com.example.demo.mapper;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_Create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void creat(Question question);

    @Select("select * from question limit #{offset},#{size}")//获取question表的值
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question")//查数据库的总数
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param("userId") Integer userId,@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id")Integer id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void update(Question question);
}
