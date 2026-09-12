package com.example.studentsysteam.controller;

import com.example.studentsysteam.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.studentsysteam.annotation.RequireAdmin;
import com.example.studentsysteam.entity.Classes;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


@RestController
public class ClassController {

    @Autowired
    private ClassService classService;

    @RequireAdmin
    @DeleteMapping("/class/{id}")
    public String deleteClass(@PathVariable Integer id) {
        classService.deleteClass(id);
        return "删除班级成功";
    }

    @GetMapping("/class/list")     // ← 注意：这个不加 @RequireAdmin（只读操作）
    public List<Classes> list() {
        return classService.list();
    }

}

