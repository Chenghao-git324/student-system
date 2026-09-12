package com.example.studentsysteam.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // 密钥（相当于"印章"），HS256 算法要求至少 32 个字符
    private static final String SECRET = "studentSystemSecretKeyForJwt1234567890";

    // 把密钥字符串变成密钥对象
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 有效期：7 天（单位毫秒）
    private static final long EXPIRE = 7 * 24 * 60 * 60 * 1000L;

    // 生成 token（登录成功时调用）
    public String createToken(String username, String role) {
        return Jwts.builder()
                .subject(username)                                          // 装谁的信息
                .claim("role", role)                                        // 装角色（自定义字段）
                .issuedAt(new Date())                                       // 签发时间
                .expiration(new Date(System.currentTimeMillis() + EXPIRE))   // 过期时间
                .signWith(KEY)                                              // 盖章
                .compact();                                                 // 生成字符串
    }

    // 解析 token，取出用户名（解析失败会抛异常，说明 token 无效）
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(KEY)            // 用密钥验签（检查防伪标记）
                .build()
                .parseSignedClaims(token)   // 解析
                .getPayload();
        return claims.getSubject();         // 取出用户名（sub 字段）
    }

    // 解析 token，取出角色
    public String getRole(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("role", String.class);   // 取出角色（自定义字段）
    }
}
