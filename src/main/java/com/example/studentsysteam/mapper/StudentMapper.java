package com.example.studentsysteam.mapper;

import com.example.studentsysteam.entity.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public  interface StudentMapper {

    @Select("select s.id, s.name, s.age, s.gender, s.class_id as classId, c.class_name as className from student s left join classes c on s.class_id = c.id")
     List<Student> findAll();
    @Select("select count(*) from student")
    int count();
    @Select("select s.id, s.name, s.age, s.gender, s.class_id as classId, c.class_name as className from student s left join classes c on s.class_id = c.id limit #{offset},#{size}")
    List<Student> findByPage(@Param("offset") int offset, @Param("size") int size);

    @Insert("insert into student ( name, age, gender) values (#{name}, #{age}, #{gender})")
    void insert(Student student);

    @Delete("delete from student where id =#{id}")
    void deleteById(Integer id);

@Update("update student set name =#{name},age=#{age},gender=#{gender} where id=#{id}")
    void update(Student student);

   @Select ("select id,name ,age ,gender from student where id =#{id}")
Student  findById(@Param("id") Integer id);

    List<Student> findByCondition(@Param("name") String name, @Param("gender") String gender);

    void deleteByIds(@Param("ids") List<Integer> ids);

    List<Student> findAllWithClass();

    Student findByIdWithClass(@Param("id") Integer id);

    @Delete("delete from student where class_id = #{classId}")
    void deleteStudentByClassId(@Param("classId") Integer classId);

}
