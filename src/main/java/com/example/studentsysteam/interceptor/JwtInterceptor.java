package com.example.studentsysteam.interceptor;

import com.example.studentsysteam.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    // 这个方法在每个请求到达 Controller 之前执行
    // 返回 true = 放行，返回 false = 拦住
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 第 1 步：从请求头里拿出 token
        String token = request.getHeader("Authorization");

        // 第 2 步：压根没带 token → 拦住
        if (token == null || token.isEmpty()) {
            return reject(response, "未登录，请先登录");
        }

        // 第 3 步：前端带的是 "Bearer xxxxx"，去掉前面的 "Bearer " 前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 第 4 步：验证 token（解析失败会抛异常，说明 token 是假的或过期了）
        try {
            jwtUtil.getUsername(token);
        } catch (Exception e) {
            return reject(response, "登录已过期，请重新登录");
        }

        // 第 5 步：全部通过 → 放行
        return true;
    }

    // 抽出来的小工具：返回 401 并写一句提示
    private boolean reject(HttpServletResponse response, String msg) throws Exception {
        response.setStatus(401);                                  // 401 = 未授权
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"msg\":\"" + msg + "\"}");
        return false;                                             // false = 拦住
    }
}
