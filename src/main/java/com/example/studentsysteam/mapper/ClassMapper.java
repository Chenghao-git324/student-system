package com.example.studentsysteam.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.studentsysteam.entity.Classes;
import org.apache.ibatis.annotations.Select;
import java.util.List;


@Mapper
public interface ClassMapper {

    // 删除班级
    @Delete("delete from classes where id = #{id}")
    void deleteClass(@Param("id") Integer id);

    // 查所有班级（给前端下拉框用）
    @Select("select id, class_name as className from classes")
    List<Classes> findAll();



}

