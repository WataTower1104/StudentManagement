# 学生管理系统

## 项目概述

学生管理系统是一个基于JavaFX开发的桌面应用程序，用于管理学生信息。该系统提供了完整的学生信息增删改查功能，采用MVC架构设计，数据持久化存储在文本文件中。

## 功能特性

### 1. 界面布局
- **搜索栏**：支持按学号、姓名、专业搜索学生
- **信息栏**：表格形式显示学生信息，支持列宽调整
- **功能栏**：提供添加、删除、修改和全部清空功能

### 2. 核心功能
- **添加学生**：自动生成学号，年龄使用下拉列表（10-100）
- **修改学生**：自动填充所选学生信息
- **删除学生**：需要确认后执行删除操作
- **全部清空**：需要输入确认信息防止误操作
- **搜索功能**：支持按学号、姓名、专业搜索，回车或点击按钮均可搜索
- **加载界面**：程序启动时显示加载动画，提升用户体验

### 3. 技术特点
- **MVC架构**：清晰的分层设计，易于维护和扩展
- **数据持久化**：学生信息存储在文本文件中，便于查看和备份
- **用户友好**：直观的界面设计，完善的错误处理和用户提示
- **响应式设计**：支持窗口大小调整，表格列宽可自由调整

## 技术栈

- **开发语言**：Java
- **UI框架**：JavaFX 21
- **构建工具**：Maven
- **数据存储**：文本文件（students.txt）

## 项目结构

```
StudentManagement/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── module-info.java
│   │   │   └── site/
│   │   │       └── watatower/
│   │   │           └── student/
│   │   │               ├── Dao/           # 数据访问层
│   │   │               ├── Entity/        # 实体类
│   │   │               ├── Service/       # 业务逻辑层
│   │   │               ├── Util/          # 工具类
│   │   │               │   ├── FileHandle/ # 文件操作工具
│   │   │               │   └── javafx/    # JavaFX控制器
│   │   │               └── Main.java      # 程序入口
│   │   └── resources/
│   │       └── site/
│   │           └── watatower/
│   │               └── student/
│   │                   ├── hello-view.fxml     # 示例界面
│   │                   ├── loading.fxml        # 加载界面
│   │                   ├── student-management.fxml  # 主界面
│   │                   └── students.txt        # 学生数据文件
├── .mvn/                # Maven包装器
├── pom.xml              # Maven配置文件
├── mvnw                 # Maven包装器脚本（Linux/Mac）
├── mvnw.cmd             # Maven包装器脚本（Windows）
└── README.md            # 项目说明文档
```

## 安装和运行

### 环境要求
- JDK 21 或更高版本
- Maven 3.6 或更高版本（可选，项目包含Maven包装器）

### 运行步骤

1. **克隆项目**
```bash
git clone <repository-url>
cd StudentManagement
```

2. **编译项目**
```bash
# 使用Maven包装器
./mvnw clean compile  # Linux/Mac
mvnw.cmd clean compile  # Windows
```

3. **运行程序**
```bash
./mvnw javafx:run  # Linux/Mac
mvnw.cmd javafx:run  # Windows
```

## 使用说明

### 基本操作流程

1. **添加学生**
   - 点击"添加"按钮
   - 填写学生信息（学号自动生成）
   - 选择年龄（10-100）
   - 点击"提交"按钮

2. **查找学生**
   - 在搜索类型下拉框中选择搜索条件（学号/姓名/专业）
   - 在输入框中输入搜索关键词
   - 点击"搜索"按钮或按回车键

3. **修改学生**
   - 在表格中选中要修改的学生
   - 点击"修改"按钮
   - 修改信息后点击"提交"按钮

4. **删除学生**
   - 在表格中选中要删除的学生
   - 点击"删除"按钮
   - 确认删除操作

5. **全部清空**
   - 点击"全部清空"按钮
   - 输入确认信息"我确定要删除所有学生信息"
   - 确认操作

### 数据格式

学生数据存储在 `src/main/resources/site/watatower/student/students.txt` 文件中，格式为：
```
学号,姓名,年龄,性别,专业
```

示例数据：
```
21,张三,24,男,土木
22,李四,23,女,计算机
23,王五,22,男,机械
```

## 核心技术实现

### 1. 文本文件数据存储

本项目使用文本文件（`students.txt`）作为数据存储介质，实现方式如下：

**数据格式设计**：
```
学号,姓名,年龄,性别,专业
```

**文件操作流程**：
- **读取数据**：通过 `FileUtil.java` 中的 `loadStudentList()` 方法从文件读取数据，解析每一行并转换为 `Student` 对象
- **写入数据**：通过 `FileUtil.java` 中的 `saveStudentList()` 方法将内存中的学生列表保存到文件
- **路径管理**：数据文件存储在 `src/main/resources/site/watatower/student/students.txt`

**代码实现**（FileUtil.java）：
```java
// 加载学生列表
public List<Student> loadStudentList() throws IOException {
    // 读取文件内容并解析为Student对象列表
}

// 保存学生列表
public void saveStudentList(List<Student> students) throws IOException {
    // 将Student对象列表转换为文本格式并写入文件
}
```

### 2. loadStudents()方法调用过多问题解决方案

**问题背景**：
在原始设计中，每次创建 `StudentDaoImpl` 实例都会调用 `loadStudents()` 方法，导致频繁读取文件，影响性能。

**解决方案**：使用单例模式（Singleton Pattern）

**实现步骤**：

1. **StudentDaoImpl单例化**：
   - 将构造函数设为私有（`private StudentDaoImpl()`）
   - 添加静态实例变量和 `getInstance()` 方法
   - 在构造函数中仅调用一次 `loadStudents()`

```java
public class StudentDaoImpl implements StudentDao {
    private static StudentDaoImpl instance;
    private List<Student> studentLists;
    
    private StudentDaoImpl() {
        studentLists = loadStudents();  // 仅在首次实例化时调用一次
    }
    
    public static StudentDaoImpl getInstance() {
        if (instance == null) {
            synchronized (StudentDaoImpl.class) {
                if (instance == null) {
                    instance = new StudentDaoImpl();
                }
            }
        }
        return instance;
    }
}
```

2. **StudentServiceImpl单例化**：
   - 同样使用单例模式确保全局只有一个实例
   - 通过 `StudentDaoImpl.getInstance()` 获取数据访问对象

```java
public class StudentServiceImpl implements StudentService {
    private static StudentServiceImpl instance;
    private StudentDao studentDao;
    
    private StudentServiceImpl() {
        studentDao = StudentDaoImpl.getInstance();  // 获取单例实例
    }
    
    public static StudentServiceImpl getInstance() {
        // 双重检查锁定实现单例
    }
}
```

**优化效果**：
- `loadStudents()` 调用次数从"每次操作调用"减少为"程序启动时调用一次"
- 内存中的数据在增删改操作后自动更新并保存到文件
- 大幅提升程序性能，减少文件I/O操作

### 3. JavaFX设计思路

本项目采用MVC（Model-View-Controller）架构设计，结合JavaFX实现图形界面：

**架构分层**：
- **Model（模型）**：`Student.java` 实体类，封装学生数据
- **View（视图）**：`.fxml` 文件定义界面布局
- **Controller（控制器）**：`MainController.java` 处理用户交互逻辑

**主要组件设计**：

1. **程序入口（Main.java）**：
   - 负责初始化应用程序
   - 加载启动界面（loading.fxml）
   - 在后台线程中加载数据，避免UI阻塞
   - 数据加载完成后切换到主界面

2. **启动界面（loading.fxml）**：
   - 显示加载动画和提示信息
   - 提升用户体验，避免程序启动时的空白窗口

3. **主界面（student-management.fxml）**：
   - 搜索栏：支持多种搜索条件
   - 表格视图：显示学生信息列表
   - 操作按钮：添加、修改、删除、清空
   - 表单区域：输入学生信息

4. **控制器（MainController.java）**：
   - 绑定UI组件与数据模型
   - 处理按钮点击事件
   - 调用Service层方法实现业务逻辑
   - 更新表格数据和UI状态

**线程管理**：
- 使用 `Platform.runLater()` 在JavaFX主线程更新UI
- 数据加载和文件操作在后台线程执行，避免UI卡顿

**数据绑定**：
- 使用JavaFX的ObservableList实现表格数据与内存数据的双向绑定
- 数据变更时自动更新UI显示

## 项目特点

1. **模块化设计**：采用分层架构，便于维护和扩展
2. **数据持久化**：学生信息自动保存到文件，重启程序后数据不丢失
3. **用户友好**：清晰的界面设计，完善的错误提示
4. **安全机制**：删除操作需要确认，防止误操作
5. **性能优化**：数据加载在后台线程进行，避免UI阻塞
6. **单例模式**：减少文件I/O操作，提升程序性能

## 开发说明

### 扩展功能

如果需要扩展功能，可以在以下位置添加代码：

- **添加新功能**：在 `Service` 层添加业务逻辑，在 `Dao` 层添加数据访问方法
- **修改界面**：编辑 `student-management.fxml` 文件和 `MainController.java` 文件
- **数据格式**：修改 `FileUtil.java` 中的文件读写逻辑

### 注意事项

- 不要修改现有的文件目录结构
- 添加新的查找方法或修改方法前请先确认需求
- 保持代码风格一致，遵循Java编码规范

## 许可证

本项目采用 MIT 许可证。

## 作者

WataTower

---

**注意**：本项目为教学演示项目，实际生产环境使用时请根据需要进行安全性和性能优化。
