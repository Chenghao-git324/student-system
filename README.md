# 学生管理系统

一个基于 **Spring Boot + MyBatis + MySQL** 的学生信息管理系统，包含完整的三层架构、用户认证和权限控制。

前后端一体（前端页面直接放在 Spring Boot 的 `static` 目录下），打开浏览器即可使用。

---

## 功能特性

### 学生管理
- ✅ 学生信息的**增删改查**
- ✅ **分页查询**（PageHelper 插件）
- ✅ **条件搜索**（按姓名模糊查、按性别筛选，动态 SQL 拼接）
- ✅ **批量删除**（一次删除多个学生）
- ✅ **联表查询**（学生列表带出所属班级名）

### 班级管理
- ✅ 班级列表查询
- ✅ 学生分配班级（新增/编辑学生时选择）
- ✅ 删除班级（**事务保护**，连带删除该班所有学生）

### 用户与安全
- ✅ 用户**注册**（密码 BCrypt 加密存储）
- ✅ 用户**登录**（JWT 令牌认证）
- ✅ **登录拦截器**（未登录无法访问任何业务接口）
- ✅ **角色权限**（管理员可增删改，普通用户只能查看）
- ✅ **参数校验**（姓名非空、年龄 1~120、性别只能男/女）
- ✅ **统一异常处理**（把报错转成友好的中文提示）

### 前端页面
- ✅ 登录页 / 注册页 / 管理主页
- ✅ 主页含：新增、编辑、删除、批量删除、分页、搜索
- ✅ 按角色自动隐藏无权限的操作按钮

---

## 技术栈

| 分类 | 技术 | 版本 |
|------|------|------|
| 语言 | Java | 17 |
| 框架 | Spring Boot | 3.5.10 |
| 持久层 | MyBatis | 3.0.5 |
| 数据库 | MySQL | 8.x |
| 分页插件 | PageHelper | 2.1.1 |
| 认证 | JJWT | 0.12.6 |
| 密码加密 | Spring Security Crypto (BCrypt) | 6.5.7 |
| 参数校验 | Spring Boot Validation | — |
| 简化代码 | Lombok | 1.18.42 |
| 构建工具 | Maven | 3.9.x |
| 前端 | 原生 HTML + CSS + JavaScript | — |

---

## 快速开始

### 环境要求

- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 1. 创建数据库并导入表结构

在 MySQL 中执行：

```sql
-- 创建数据库
create database student_db default charset utf8mb4;

use student_db;

-- 班级表
create table classes (
    id int primary key auto_increment,
    class_name varchar(50) not null
);

-- 学生表
create table student (
    id int primary key auto_increment,
    name varchar(50) not null,
    age int,
    gender varchar(10),
    class_id int
);

-- 用户表
create table users (
    id int primary key auto_increment,
    username varchar(50) not null,
    password varchar(100) not null,
    role varchar(20) not null default 'user'
);

-- 初始化班级数据
insert into classes (class_name) values ('计算机一班'), ('计算机二班'), ('软件一班');

-- 初始化学生数据
insert into student (name, age, gender, class_id) values
('张三', 25, '男', 1),
('李四', 21, '女', 1),
('王五', 22, '男', 1);

-- 初始化管理员账号（密码是 123456 经过 BCrypt 加密后的结果）
insert into users (username, password, role) values
('admin', '$2a$10$kaVtrxSElrfrt0MELBCJf.nonI0rHVjt3H8kPBZqYAKAjMKQcozUy', 'admin');
```

### 2. 修改数据库配置

打开 `src/main/resources/application.yml`，把数据库账号密码改成你自己的：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/student_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: 你的密码
```

### 3. 启动项目

方式一（IDEA）：直接运行 `StudentSystemApplication.java`

方式二（命令行）：

```bash
mvn spring-boot:run
```

### 4. 打开浏览器

访问 <http://localhost:8080>

系统会自动跳转到登录页。

---

## 测试账号

| 用户名 | 密码 | 角色 | 权限 |
|--------|------|------|------|
| `admin` | `123456` | 管理员 | 增删改查全部功能 |
| 自行注册 | — | 普通用户 | 只能查看、搜索、分页 |

> 也可以点击登录页的「去注册」自己注册一个账号，新注册的账号默认是**普通用户**。

---

## 接口列表

所有接口除登录、注册外，都需要在请求头中携带 token：

```
Authorization: Bearer <你的token>
```

### 用户模块

| 方法 | 路径 | 说明 | 需要管理员 |
|------|------|------|-----------|
| POST | `/register` | 用户注册 | 否 |
| POST | `/login` | 用户登录，返回 token | 否 |

### 学生模块

| 方法 | 路径 | 说明 | 需要管理员 |
|------|------|------|-----------|
| GET | `/student/list` | 查询全部学生（带班级名） | 否 |
| GET | `/student/page?page=1&size=5` | 分页查询 | 否 |
| GET | `/student/{id}` | 查询单个学生 | 否 |
| GET | `/student/search?name=张&gender=男` | 条件搜索 | 否 |
| GET | `/student/withClass` | 查询全部（联表带班级） | 否 |
| GET | `/student/withClass/{id}` | 查询单个（联表带班级） | 否 |
| POST | `/student` | 新增学生 | ✅ 是 |
| PUT | `/student` | 修改学生 | ✅ 是 |
| DELETE | `/student/{id}` | 删除学生 | ✅ 是 |
| DELETE | `/student/batch` | 批量删除（Body 传 id 数组） | ✅ 是 |

### 班级模块

| 方法 | 路径 | 说明 | 需要管理员 |
|------|------|------|-----------|
| GET | `/class/list` | 查询班级列表 | 否 |
| DELETE | `/class/{id}` | 删除班级（连带删学生） | ✅ 是 |

---

## 项目结构

```
src/main/java/com/example/studentsysteam
├── StudentSystemApplication.java     启动类
│
├── entity/                           实体类（对应数据库表）
│   ├── Student.java                  学生（含校验注解）
│   ├── Classes.java                  班级
│   └── User.java                     用户
│
├── mapper/                           数据层（执行 SQL）
│   ├── StudentMapper.java            注解 SQL + XML 动态 SQL
│   ├── ClassMapper.java
│   └── UserMapper.java
│
├── service/                          业务层（业务逻辑）
│   ├── StudentService / Impl
│   ├── ClassService / Impl           含 @Transactional 事务
│   └── UserService / Impl            含 BCrypt 加密
│
├── controller/                       控制层（接收请求）
│   ├── StudentController.java
│   ├── ClassController.java
│   └── LoginController.java
│
├── interceptor/                      拦截器
│   └── JwtInterceptor.java           校验 token + 角色
│
├── annotation/                       自定义注解
│   └── RequireAdmin.java             标记"仅管理员可用"的接口
│
├── config/                           配置
│   └── WebConfig.java                注册拦截器 + 白名单
│
├── util/                             工具类
│   └── JwtUtil.java                  生成/解析 token
│
└── GlobalExceptionHandler/           全局异常处理
    └── GlobalExceptionHandler.java

src/main/resources
├── application.yml                   配置文件
├── mapper/StudentMapper.xml          MyBatis 动态 SQL
└── static/                           前端页面
    ├── login.html                    登录页
    ├── register.html                 注册页
    └── index.html                    管理主页
```

---

## 核心实现说明

### 三层架构

```
请求 → 拦截器（验 token 和角色）→ Controller（收参数）→ Service（业务逻辑）→ Mapper（SQL）→ MySQL
```

### 登录认证流程

```
1. 用户提交账号密码 → POST /login
2. Service 按用户名查出用户，用 BCrypt 的 matches() 比对密码
3. 比对通过 → JwtUtil 生成 token（内含用户名、角色、过期时间）
4. 前端把 token 存进 localStorage
5. 之后每次请求，前端自动在请求头带上 Authorization: Bearer <token>
6. 拦截器校验 token 的签名和有效期，通过则放行
```

### 角色权限

在需要管理员权限的接口方法上贴 `@RequireAdmin` 注解：

```java
@RequireAdmin
@DeleteMapping("/student/{id}")
public String delete(@PathVariable Integer id) { ... }
```

拦截器会读取这个注解，检查 token 中的 `role` 字段，非管理员返回 403。

### 动态 SQL（搜索功能）

使用 MyBatis 的 `<if>` 和 `<where>` 标签，根据传入参数动态拼接查询条件：

```xml
<select id="findByCondition" resultType="...Student">
    select s.id, s.name, s.age, s.gender, s.class_id as classId, c.class_name as className
    from student s
    left join classes c on s.class_id = c.id
    <where>
        <if test="name != null and name != ''">
            and s.name like concat('%', #{name}, '%')
        </if>
        <if test="gender != null and gender != ''">
            and s.gender = #{gender}
        </if>
    </where>
</select>
```

### 事务保护

删除班级时，需要同时删除该班级下的所有学生，用 `@Transactional` 保证两个操作"要么全成功，要么全回滚"：

```java
@Transactional
public void deleteClass(Integer id) {
    studentMapper.deleteStudentByClassId(id);   // 第 1 步：删该班学生
    classMapper.deleteClass(id);                // 第 2 步：删班级
}
```

---

## 安全说明

- 用户密码使用 **BCrypt** 加密存储，数据库中看不到明文
- BCrypt 每次加密结果不同（自动加盐），但都能正确验证
- 认证使用 **JWT**，token 有签名防篡改、有 7 天有效期
- 敏感接口有**双重保护**：前端隐藏按钮（体验）+ 后端角色校验（安全）
- 参数有**后端校验**，非法数据无法入库

> ⚠️ **注意**：`application.yml` 中的数据库密码为明文。如果要把项目上传到公开仓库，建议改用环境变量引用。

---

## 开发环境

- 操作系统：Windows 11
- IDE：IntelliJ IDEA
- 数据库工具：DataGrip
- 接口调试：Apifox
