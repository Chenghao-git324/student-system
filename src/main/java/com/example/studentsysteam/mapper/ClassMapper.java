package com.example.studentsysteam.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassMapper {

    // 删除班级
    @Delete("delete from classes where id = #{id}")
    void deleteClass(@Param("id") Integer id);
}

