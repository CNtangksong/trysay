package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void  insert(User user);

    @Select("select * from user where token = #{token}") //#{}会把形参放进去，类会自动放，不是类要加Param注解
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findbyId(Integer id);
}
