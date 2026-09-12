package com.example.studentsysteam.controller;

import com.example.studentsysteam.entity.Student;
import com.example.studentsysteam.mapper.StudentMapper;
import com.example.studentsysteam.service.StudentService;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.Map;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/student/list")
    public List<Student> list() {
        return studentService.list();
    }

    @PostMapping("/student")
    public String add(@Valid@RequestBody Student student){
        studentService.add(student);
        return "新增成功";
    }

    @GetMapping("/student/page")
    public Map<String, Object> page(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return studentService.page(page, size);
    }

    @DeleteMapping("/student/{id}")
    public String delete(@PathVariable Integer id){
        studentService.delete(id);
        return "删除成功";
    }

    @PutMapping("/student")
    public String update(@Valid @RequestBody Student student){
        studentService.update(student);
        return "修改成功";
    }

    @GetMapping("/student/{id}")
    public Student getById(@PathVariable Integer id) {
        return studentService.getById(id);
    }

    @GetMapping("/student/search")
    public List<Student> search(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String gender) {
        return studentService.findByCondition(name, gender);
    }

    @DeleteMapping("/student/batch")
    public String deleteBatch(@RequestBody List<Integer> ids) {
        studentService.deleteByIds(ids);
        return "批量删除成功";
    }

    @GetMapping("/student/withClass")
    public List<Student> findAllWithClass() {
        return studentService.findAllWithClass();
    }

    @GetMapping("/student/withClass/{id}")
    public Student findByIdWithClass(@PathVariable Integer id) {
        return studentService.findByIdWithClass(id);
    }

}