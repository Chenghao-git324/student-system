package com.example.studentsysteam.GlobalExceptionHandler;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 专门接住"参数校验失败"的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValid(MethodArgumentNotValidException e) {
        // 从异常里取出第一条校验错误信息（就是你在 @NotBlank 里写的 message）
        String msg = e.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        // 自己拼一个友好的 JSON 返回
        Map<String, String> map = new HashMap<>();
        map.put("msg", msg);
        return map;

    }
}