package com.example.studentsysteam.service;

import com.example.studentsysteam.entity.Student;
import com.example.studentsysteam.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
@Autowired
StudentMapper studentMapper;

@Override
    public List<Student>list(){
    return studentMapper.findAll();

}

@Override
    public void add(Student student){
        studentMapper.insert(student);
    }

    @Override
    public void delete(Integer id) {
        studentMapper.deleteById(id);
    }

    @Override
    public void update(Student student) {
        studentMapper.update(student);
    }

    @Override
    public Map<String, Object> page(int page, int size) {
        // 第 1 步：启动分页（这一句替代你之前手算 offset + limit + count）
        PageHelper.startPage(page, size);

        // 第 2 步：照常查（不用写 limit，PageHelper 会自动加）
        List<Student> list = studentMapper.findAll();

        // 第 3 步：用 PageInfo 拿总数（替代你之前的 count()）
        PageInfo<Student> pageInfo = new PageInfo<>(list);

        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());   // 总数，PageHelper 自动查好了
        map.put("list", list);
        return map;
    }


    @Override
    public Student getById(Integer id) {
        return studentMapper.findById(id);
    }

    @Override
    public List<Student> findByCondition(String name, String gender) {
        return studentMapper.findByCondition(name, gender);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        studentMapper.deleteByIds(ids);
    }

    @Override
    public List<Student> findAllWithClass() {
        return studentMapper.findAllWithClass();
    }
    @Override
    public Student findByIdWithClass(Integer id) {
        return studentMapper.findByIdWithClass(id);
    }

}
