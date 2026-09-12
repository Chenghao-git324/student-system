package com.example.studentsysteam.service;

import com.example.studentsysteam.entity.Student;
import java.util.List;
import java.util.Map;

public interface StudentService {
    List<Student>list();

    void add(Student student);   // 新增学生

    void delete(Integer id);   // 删除学生

void update(Student student);

Map<String,Object> page(int page,int size);

    Student getById(Integer id);

    List<Student> findByCondition(String name, String gender);

    void deleteByIds(List<Integer> ids);

    List<Student> findAllWithClass();

    Student findByIdWithClass(Integer id);


}
