package com.example.studentsysteam.service;
import java.util.List;
import com.example.studentsysteam.entity.Classes;

public interface ClassService {

    void deleteClass(Integer id);

    List<Classes> list();

}

