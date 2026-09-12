package com.example.studentsysteam.controller;

import com.example.studentsysteam.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassController {

    @Autowired
    private ClassService classService;

    @DeleteMapping("/class/{id}")
    public String deleteClass(@PathVariable Integer id) {
        classService.deleteClass(id);
        return "删除班级成功";
    }
}

