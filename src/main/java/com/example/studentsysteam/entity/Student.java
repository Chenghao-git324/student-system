package com.example.studentsysteam.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;


@Data
public class Student {
    private Integer id;

    @NotBlank(message = "姓名不能为空")
    private String name ;

    @Pattern(regexp = "男|女", message = "性别只能是男或女")
    private String gender;

    @Min(value = 1, message = "年龄不能小于1")
    @Max(value = 120, message = "年龄不能大于120")
    private Integer age;


    private Integer classId;   // 所属班级 id（存进表里的）
    private String className;  // 班级名（联表查出来的，表里没有）
}
