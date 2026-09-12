package com.example.studentsysteam.service;

import com.example.studentsysteam.mapper.ClassMapper;
import com.example.studentsysteam.mapper.StudentMapper;
import com.example.studentsysteam.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.studentsysteam.entity.Classes;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private StudentMapper studentMapper;

    // 关键！加 @Transactional，让下面两步"同生共死"
    @Transactional
    @Override
    public void deleteClass(Integer id) {
        // 第 1 步：先删这个班级里的所有学生
        studentMapper.deleteStudentByClassId(id);

        // 第 2 步：再删班级本身
        classMapper.deleteClass(id);
    }

    @Override
    public List<Classes> list() {
        return classMapper.findAll();
    }

}

