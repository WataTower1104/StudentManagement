# 学生管理系统 - 开发计划

## 项目概述

学生管理系统是一个基于Java SE的控制台应用，用于管理学生信息，包括添加、编辑、删除、查询学生信息，以及数据持久化存储。

## 技术栈

- Java SE 8+
- 文件存储（用于数据持久化）

## 一、文件目录设计

### 基础目录结构

```
student-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── student/
│   │   │           ├── entity/          # 实体类
│   │   │           │   └── Student.java  # 学生类
│   │   │           ├── service/         # 业务逻辑层
│   │   │           │   ├── StudentService.java  # 学生管理服务接口
│   │   │           │   └── StudentServiceImpl.java  # 服务实现
│   │   │           ├── dao/             # 数据访问层
│   │   │           │   ├── StudentDao.java  # 数据访问接口
│   │   │           │   └── StudentDaoImpl.java  # 数据访问实现
│   │   │           ├── util/            # 工具类
│   │   │           │   └── FileUtil.java  # 文件读写工具
│   │   │           └── Main.java        # 主类（程序入口）
│   │   └── resources/
│   │       └── data/
│   │           └── students.txt  # 学生数据文件
├── pom.xml                     # Maven项目配置文件（可选）
└── README.md                   # 项目说明文档
```

## 二、开发进程与待做事项

### 阶段1：需求分析与设计

**目标**：明确系统功能和数据模型

#### 待做事项：

| 序号 | 任务 | 详细描述 | 完成标准 |
|------|------|----------|----------|
| 1.1 | 功能需求分析 | 明确系统需要实现的功能：添加、编辑、删除、查询学生信息，数据持久化 | 列出完整的功能清单 |
| 1.2 | 数据模型设计 | 设计Student类的属性：id、name、age、gender、major等 | 确定所有必要的属性及其类型 |
| 1.3 | 文件存储格式设计 | 设计学生数据的存储格式（如CSV或JSON） | 确定文件格式和存储结构 |

### 阶段2：核心功能实现

**目标**：实现系统的核心功能模块

#### 待做事项：

| 序号 | 任务 | 详细描述 | 完成标准 | 涉及API |
|------|------|----------|----------|---------|
| 2.1 | 实现Student实体类 | 创建Student类，定义属性和getter/setter方法 | Student类能够正确表示学生信息 | Java核心API |
| 2.2 | 实现FileUtil工具类 | 实现文件读写功能，用于保存和加载学生数据 | 能够正确读写学生数据文件 | `java.io`包（File、BufferedReader、BufferedWriter） |
| 2.3 | 实现StudentDao接口 | 定义数据访问方法：添加、更新、删除、查询学生 | 接口方法完整且符合需求 | Java核心API |
| 2.4 | 实现StudentDaoImpl | 实现数据访问接口，使用文件存储数据 | 能够正确操作学生数据文件 | `java.io`包 |
| 2.5 | 实现StudentService接口 | 定义业务逻辑方法：添加、更新、删除、查询学生 | 接口方法完整且符合需求 | Java核心API |
| 2.6 | 实现StudentServiceImpl | 实现业务逻辑接口，调用DAO层方法 | 能够正确处理业务逻辑 | Java核心API |

### 阶段3：用户界面与交互

**目标**：实现用户界面和交互逻辑

#### 待做事项：

| 序号 | 任务 | 详细描述 | 完成标准 | 涉及API |
|------|------|----------|----------|---------|
| 3.1 | 实现主菜单 | 设计控制台菜单，包含所有功能选项 | 菜单清晰易用 | Java核心API |
| 3.2 | 实现用户输入处理 | 处理用户输入，调用相应的服务方法 | 能够正确处理用户输入 | `java.util.Scanner` |
| 3.3 | 实现添加学生功能 | 实现添加学生的用户交互流程 | 能够成功添加学生 | Java核心API |
| 3.4 | 实现编辑学生功能 | 实现编辑学生的用户交互流程 | 能够成功编辑学生信息 | Java核心API |
| 3.5 | 实现删除学生功能 | 实现删除学生的用户交互流程 | 能够成功删除学生 | Java核心API |
| 3.6 | 实现查询学生功能 | 实现查询学生的用户交互流程 | 能够成功查询学生信息 | Java核心API |
| 3.7 | 实现显示所有学生功能 | 实现显示所有学生的用户交互流程 | 能够显示所有学生信息 | Java核心API |

### 阶段4：测试与优化

**目标**：测试系统功能，优化代码

#### 待做事项：

| 序号 | 任务 | 详细描述 | 完成标准 | 涉及API |
|------|------|----------|----------|---------|
| 4.1 | 功能测试 | 测试所有功能是否正常工作 | 所有功能能够正确执行 | Java核心API |
| 4.2 | 边界情况测试 | 测试空输入、无效输入等边界情况 | 系统能够正确处理边界情况 | Java核心API |
| 4.3 | 数据持久化测试 | 测试数据是否能够正确保存和加载 | 重启程序后数据仍然存在 | `java.io`包 |
| 4.4 | 代码优化 | 重构重复代码，提高代码可读性 | 代码结构清晰，易于维护 | Java核心API |
| 4.5 | 文档编写 | 编写README.md，说明项目结构和使用方法 | 文档完整清晰 | Markdown |

## 三、API说明

### 1. Student类 API

| 方法 | 描述 | 参数 | 返回值 |
|------|------|------|--------|
| `Student(String id, String name, int age, String gender, String major)` | 构造方法 | id: 学号<br>name: 姓名<br>age: 年龄<br>gender: 性别<br>major: 专业 | 无 |
| `String getId()` | 获取学号 | 无 | 学号字符串 |
| `void setId(String id)` | 设置学号 | id: 学号 | 无 |
| `String getName()` | 获取姓名 | 无 | 姓名字符串 |
| `void setName(String name)` | 设置姓名 | name: 姓名 | 无 |
| `int getAge()` | 获取年龄 | 无 | 年龄整数 |
| `void setAge(int age)` | 设置年龄 | age: 年龄 | 无 |
| `String getGender()` | 获取性别 | 无 | 性别字符串 |
| `void setGender(String gender)` | 设置性别 | gender: 性别 | 无 |
| `String getMajor()` | 获取专业 | 无 | 专业字符串 |
| `void setMajor(String major)` | 设置专业 | major: 专业 | 无 |
| `String toString()` | 转换为字符串 | 无 | 学生信息字符串 |

### 2. StudentDao接口 API

| 方法 | 描述 | 参数 | 返回值 |
|------|------|------|--------|
| `void addStudent(Student student)` | 添加学生 | student: 学生对象 | 无 |
| `void updateStudent(Student student)` | 更新学生 | student: 学生对象 | 无 |
| `void deleteStudent(String id)` | 删除学生 | id: 学号 | 无 |
| `Student getStudentById(String id)` | 根据学号获取学生 | id: 学号 | 学生对象，不存在返回null |
| `List<Student> getAllStudents()` | 获取所有学生 | 无 | 学生列表 |

### 3. StudentService接口 API

| 方法 | 描述 | 参数 | 返回值 |
|------|------|------|--------|
| `void addStudent(Student student)` | 添加学生 | student: 学生对象 | 无 |
| `void updateStudent(Student student)` | 更新学生 | student: 学生对象 | 无 |
| `void deleteStudent(String id)` | 删除学生 | id: 学号 | 无 |
| `Student getStudentById(String id)` | 根据学号获取学生 | id: 学号 | 学生对象，不存在返回null |
| `List<Student> getAllStudents()` | 获取所有学生 | 无 | 学生列表 |

### 4. FileUtil类 API

| 方法 | 描述 | 参数 | 返回值 |
|------|------|------|--------|
| `static List<Student> readStudents(String filePath)` | 从文件读取学生数据 | filePath: 文件路径 | 学生列表 |
| `static void writeStudents(String filePath, List<Student> students)` | 将学生数据写入文件 | filePath: 文件路径<br>students: 学生列表 | 无 |

## 四、数据存储格式

### CSV格式示例

```csv
id,name,age,gender,major
S001,张三,18,男,计算机科学与技术
S002,李四,19,女,软件工程
S003,王五,18,男,数据科学与大数据技术
```

### JSON格式示例

```json
[
  {
    "id": "S001",
    "name": "张三",
    "age": 18,
    "gender": "男",
    "major": "计算机科学与技术"
  },
  {
    "id": "S002",
    "name": "李四",
    "age": 19,
    "gender": "女",
    "major": "软件工程"
  }
]
```

## 五、开发建议

1. **循序渐进**：先实现核心功能，再逐步添加其他功能
2. **模块化开发**：每个模块独立实现，便于测试和调试
3. **代码规范**：遵循Java命名规范，添加适当的注释
4. **版本控制**：使用Git进行版本管理，记录开发过程
5. **文档记录**：在README.md中记录项目结构、功能说明和使用方法

## 六、扩展功能

1. **学生信息排序**：按学号、姓名、年龄等字段排序
2. **学生信息搜索**：支持按姓名、专业等字段搜索
3. **数据备份与恢复**：实现数据备份和恢复功能
4. **用户权限管理**：添加管理员和普通用户权限
5. **GUI界面**：使用Swing或JavaFX实现图形界面

---

通过以上开发计划，新手可以系统地学习Java项目的开发流程，从需求分析到代码实现，再到测试优化，逐步掌握Java编程技能。