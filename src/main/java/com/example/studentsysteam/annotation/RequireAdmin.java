package com.example.studentsysteam.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)           // 只能贴在"方法"上
@Retention(RetentionPolicy.RUNTIME)   // 程序运行时也要能读到它
public @interface RequireAdmin {
}

