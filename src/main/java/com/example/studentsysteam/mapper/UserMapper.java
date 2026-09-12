package com.example.studentsysteam.mapper;

import com.example.studentsysteam.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;



@Mapper
public interface UserMapper {

    // 只按用户名查（密码不参与查询，改由 BCrypt 比对）
    @Select("select id, username, password from users where username = #{username}")
    User findByUsername(@Param("username") String username);

    // 新增用户（注意：传进来的 password 已经是加密后的哈希了）
    @Insert("insert into users (username, password) values (#{username}, #{password})")
    void insert(User user);

}


