package site.watatower.student.Util.javafx;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import site.watatower.student.Entity.Student;
import site.watatower.student.Service.StudentService;
import site.watatower.student.Service.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

/**
 * MainController类 - 学生管理系统的主界面控制器
 * 
 * 该控制器负责处理用户与界面的交互，包括：
 * 1. 初始化界面组件和事件绑定
 * 2. 处理学生信息的增删改查操作
 * 3. 管理表格数据的显示和过滤
 * 4. 显示对话框进行用户输入和确认
 * 
 * JavaFX MVC架构中的Controller层，负责连接View（student-management.fxml）和Model（Student实体）
 */
public class MainController {

    // ==================== FXML界面组件 ====================
    // @FXML注解用于将Java代码与FXML文件中的UI组件绑定
    @FXML
    private HBox searchBox;              // 搜索栏容器
    @FXML
    private ComboBox<String> searchTypeComboBox;  // 搜索类型选择框（学号/姓名/专业）
    @FXML
    private TextField searchTextField;   // 搜索输入框
    @FXML
    private Button searchButton;         // 搜索按钮
    @FXML
    private Button clearButton;          // 清空搜索结果按钮
    @FXML
    private TableView<Student> studentTableView;  // 学生信息表格
    @FXML
    private TableColumn<Student, Integer> idColumn;  // 学号列
    @FXML
    private TableColumn<Student, String> nameColumn;  // 姓名列
    @FXML
    private TableColumn<Student, Integer> ageColumn;  // 年龄列
    @FXML
    private TableColumn<Student, String> genderColumn;  // 性别列
    @FXML
    private TableColumn<Student, String> majorColumn;  // 专业列
    @FXML
    private Button addButton;            // 添加学生按钮
    @FXML
    private Button deleteButton;         // 删除学生按钮
    @FXML
    private Button updateButton;         // 修改学生按钮
    @FXML
    private Button clearAllButton;       // 全部清空按钮

    // ==================== 成员变量 ====================
    private StudentService studentService = StudentServiceImpl.getInstance();  // 业务逻辑服务（单例模式）
    private ObservableList<Student> studentList = FXCollections.observableArrayList();  // 所有学生数据集合
    private ObservableList<Student> filteredList = FXCollections.observableArrayList();  // 搜索过滤后的学生数据集合
    private boolean isFiltered = false;  // 是否处于搜索过滤状态的标志

    /**
     * initialize方法 - 控制器初始化方法
     * 
     * 该方法在FXML文件加载完成后自动调用，用于：
     * 1. 初始化界面组件的属性和状态
     * 2. 设置表格列与数据模型的绑定关系
     * 3. 加载初始数据
     * 4. 绑定按钮的事件处理器
     */
    @FXML
    public void initialize() {
        // 初始化搜索类型下拉框选项
        searchTypeComboBox.getItems().addAll("学号", "姓名", "专业");
        searchTypeComboBox.setValue("学号");

        // 设置表格列与Student对象属性的绑定关系
        // PropertyValueFactory用于将Student对象的属性（如id, name等）绑定到表格列
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));

        // 设置表格列可调整宽度
        idColumn.setResizable(true);
        nameColumn.setResizable(true);
        ageColumn.setResizable(true);
        genderColumn.setResizable(true);
        majorColumn.setResizable(true);

        // 加载所有学生数据到表格
        loadAllStudents();

        // 绑定按钮事件处理器（使用Lambda表达式简化代码）
        searchButton.setOnAction(e -> searchStudents());  // 搜索按钮点击事件
        searchTextField.setOnAction(e -> searchStudents());  // 搜索框回车事件
        clearButton.setOnAction(e -> clearSearchResults());  // 清空搜索结果
        addButton.setOnAction(e -> showAddStudentDialog());  // 添加学生
        deleteButton.setOnAction(e -> deleteSelectedStudent());  // 删除学生
        updateButton.setOnAction(e -> showUpdateStudentDialog());  // 修改学生
        clearAllButton.setOnAction(e -> clearAllStudents());  // 全部清空
    }

    /**
     * loadAllStudents方法 - 加载所有学生数据到表格
     * 
     * 从StudentService获取所有学生数据，并更新到表格中显示
     */
    private void loadAllStudents() {
        // 从业务逻辑层获取所有学生数据
        List<Student> students = studentService.getStudentLists();
        
        if (students != null) {
            // 清空现有数据并添加新数据
            studentList.clear();
            studentList.addAll(students);
            
            // 更新表格显示的数据集合
            studentTableView.setItems(studentList);
            
            // 重置过滤状态
            isFiltered = false;
        }
    }

    /**
     * searchStudents方法 - 搜索学生信息
     * 
     * 根据用户选择的搜索类型（学号/姓名/专业）和输入的搜索文本，过滤学生数据
     */
    private void searchStudents() {
        // 获取搜索类型和搜索文本
        String searchType = searchTypeComboBox.getValue();
        String searchText = searchTextField.getText().trim();

        // 检查搜索文本是否为空
        if (searchText.isEmpty()) {
            // 显示提示对话框
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("学生不存在");
            alert.showAndWait();
            return;
        }

        // 清空过滤结果集合
        filteredList.clear();
        
        // 获取所有学生数据进行过滤
        List<Student> students = studentService.getStudentLists();

        if (students != null) {
            // 根据搜索类型进行过滤
            for (Student student : students) {
                if ("学号".equals(searchType)) {
                    // 按学号搜索（精确匹配）
                    try {
                        int id = Integer.parseInt(searchText);
                        if (student.getId() == id) {
                            filteredList.add(student);
                        }
                    } catch (NumberFormatException e) {
                        // 如果输入的不是数字，忽略异常
                    }
                } else if ("姓名".equals(searchType)) {
                    // 按姓名搜索（包含匹配）
                    if (student.getName().contains(searchText)) {
                        filteredList.add(student);
                    }
                } else if ("专业".equals(searchType)) {
                    // 按专业搜索（包含匹配）
                    if (student.getMajor().contains(searchText)) {
                        filteredList.add(student);
                    }
                }
            }
        }

        // 根据搜索结果更新界面
        if (filteredList.isEmpty()) {
            // 没有找到匹配的学生
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("学生不存在");
            alert.showAndWait();
        } else {
            // 显示过滤后的结果
            studentTableView.setItems(filteredList);
            isFiltered = true;
        }
    }

    /**
     * clearSearchResults方法 - 清空搜索结果
     * 
     * 恢复显示所有学生数据，并清空搜索输入框
     */
    private void clearSearchResults() {
        // 只有在搜索过滤状态下才执行清空操作
        if (isFiltered) {
            // 恢复显示所有学生数据
            studentTableView.setItems(studentList);
            
            // 重置过滤状态
            isFiltered = false;
            
            // 清空搜索输入框
            searchTextField.clear();
        }
    }

    /**
     * showAddStudentDialog方法 - 显示添加学生对话框
     * 
     * 创建一个模态对话框，用于输入新学生的信息
     * JavaFX Dialog类的使用示例：
     * 1. 创建对话框并设置标题和头部文本
     * 2. 构建对话框内容（表单组件）
     * 3. 设置按钮和结果转换器
     * 4. 显示对话框并处理用户输入
     */
    private void showAddStudentDialog() {
        // 创建对话框对象
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("添加学生");
        dialog.setHeaderText("请输入学生信息");
        dialog.initModality(Modality.APPLICATION_MODAL);  // 设置为模态对话框（阻塞其他窗口）

        // 创建垂直布局容器，用于放置表单组件
        VBox vbox = new VBox(10);  // 垂直布局，组件间距为10像素
        vbox.setPadding(new javafx.geometry.Insets(20));  // 设置内边距

        // ==================== 学号输入区域 ====================
        HBox idBox = new HBox(10);  // 水平布局，组件间距为10像素
        Label idLabel = new Label("学号：");
        TextField idField = new TextField();
        
        // 自动生成学号（当前学生数量 + 1）
        int nextId = studentService.getStudentLists() != null ? studentService.getStudentLists().size() + 1 : 1;
        idField.setText(String.valueOf(nextId));
        idField.setEditable(false);  // 学号不可编辑
        
        idBox.getChildren().addAll(idLabel, idField);

        // ==================== 姓名输入区域 ====================
        HBox nameBox = new HBox(10);
        Label nameLabel = new Label("姓名：");
        TextField nameField = new TextField();
        nameBox.getChildren().addAll(nameLabel, nameField);

        // ==================== 年龄选择区域 ====================
        HBox ageBox = new HBox(10);
        Label ageLabel = new Label("年龄：");
        ComboBox<Integer> ageComboBox = new ComboBox<>();
        
        // 添加10-100岁的选项
        for (int i = 10; i <= 100; i++) {
            ageComboBox.getItems().add(i);
        }
        ageComboBox.setValue(18);  // 默认值为18岁
        ageBox.getChildren().addAll(ageLabel, ageComboBox);

        // ==================== 性别输入区域 ====================
        HBox genderBox = new HBox(10);
        Label genderLabel = new Label("性别：");
        TextField genderField = new TextField();
        genderBox.getChildren().addAll(genderLabel, genderField);

        // ==================== 专业输入区域 ====================
        HBox majorBox = new HBox(10);
        Label majorLabel = new Label("专业：");
        TextField majorField = new TextField();
        majorBox.getChildren().addAll(majorLabel, majorField);

        // 将所有输入区域添加到垂直布局中
        vbox.getChildren().addAll(idBox, nameBox, ageBox, genderBox, majorBox);

        // 设置对话框按钮
        ButtonType addButtonType = new ButtonType("提交", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, cancelButtonType);

        // 设置对话框内容
        dialog.getDialogPane().setContent(vbox);

        // 设置结果转换器（将对话框按钮点击事件转换为Student对象）
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    // 获取用户输入
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText().trim();
                    int age = ageComboBox.getValue();
                    String gender = genderField.getText().trim();
                    String major = majorField.getText().trim();

                    // 验证输入
                    if (name.isEmpty() || gender.isEmpty() || major.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("警告");
                        alert.setHeaderText(null);
                        alert.setContentText("姓名、性别和专业不能为空");
                        alert.showAndWait();
                        return null;
                    }

                    // 创建并返回Student对象
                    return new Student(id, name, age, gender, major);
                } catch (Exception e) {
                    // 处理输入错误
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("警告");
                    alert.setHeaderText(null);
                    alert.setContentText("输入信息有误");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        // 显示对话框并等待用户响应
        Optional<Student> result = dialog.showAndWait();
        
        // 处理对话框结果
        result.ifPresent(student -> {
            // 添加学生到数据库
            studentService.addStudent(student);
            
            // 重新加载数据并更新表格
            loadAllStudents();
            
            // 显示成功提示
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("成功");
            alert.setHeaderText(null);
            alert.setContentText("学生添加成功");
            alert.showAndWait();
        });
    }

    /**
     * showUpdateStudentDialog方法 - 显示修改学生对话框
     * 
     * 创建一个模态对话框，用于修改选中学生的信息
     * 与添加对话框类似，但会预先填充选中学生的现有信息
     */
    private void showUpdateStudentDialog() {
        // 获取表格中选中的学生
        Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        
        // 检查是否有选中的学生
        if (selectedStudent == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("请先选择要修改的学生");
            alert.showAndWait();
            return;
        }

        // 创建对话框对象
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("修改学生");
        dialog.setHeaderText("请修改学生信息");
        dialog.initModality(Modality.APPLICATION_MODAL);

        // 创建垂直布局容器
        VBox vbox = new VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(20));

        // ==================== 学号显示区域 ====================
        HBox idBox = new HBox(10);
        Label idLabel = new Label("学号：");
        TextField idField = new TextField();
        idField.setText(String.valueOf(selectedStudent.getId()));
        idField.setEditable(false);  // 学号不可修改
        idBox.getChildren().addAll(idLabel, idField);

        // ==================== 姓名输入区域（预填充现有值） ====================
        HBox nameBox = new HBox(10);
        Label nameLabel = new Label("姓名：");
        TextField nameField = new TextField(selectedStudent.getName());
        nameBox.getChildren().addAll(nameLabel, nameField);

        // ==================== 年龄选择区域（预填充现有值） ====================
        HBox ageBox = new HBox(10);
        Label ageLabel = new Label("年龄：");
        ComboBox<Integer> ageComboBox = new ComboBox<>();
        for (int i = 10; i <= 100; i++) {
            ageComboBox.getItems().add(i);
        }
        ageComboBox.setValue(selectedStudent.getAge());
        ageBox.getChildren().addAll(ageLabel, ageComboBox);

        // ==================== 性别输入区域（预填充现有值） ====================
        HBox genderBox = new HBox(10);
        Label genderLabel = new Label("性别：");
        TextField genderField = new TextField(selectedStudent.getGender());
        genderBox.getChildren().addAll(genderLabel, genderField);

        // ==================== 专业输入区域（预填充现有值） ====================
        HBox majorBox = new HBox(10);
        Label majorLabel = new Label("专业：");
        TextField majorField = new TextField(selectedStudent.getMajor());
        majorBox.getChildren().addAll(majorLabel, majorField);

        // 将所有输入区域添加到垂直布局中
        vbox.getChildren().addAll(idBox, nameBox, ageBox, genderBox, majorBox);

        // 设置对话框按钮
        ButtonType updateButtonType = new ButtonType("提交", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, cancelButtonType);

        // 设置对话框内容
        dialog.getDialogPane().setContent(vbox);

        // 设置结果转换器
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                try {
                    // 获取用户输入
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText().trim();
                    int age = ageComboBox.getValue();
                    String gender = genderField.getText().trim();
                    String major = majorField.getText().trim();

                    // 验证输入
                    if (name.isEmpty() || gender.isEmpty() || major.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("警告");
                        alert.setHeaderText(null);
                        alert.setContentText("姓名、性别和专业不能为空");
                        alert.showAndWait();
                        return null;
                    }

                    // 创建并返回更新后的Student对象
                    return new Student(id, name, age, gender, major);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("警告");
                    alert.setHeaderText(null);
                    alert.setContentText("输入信息有误");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        // 显示对话框并等待用户响应
        Optional<Student> result = dialog.showAndWait();
        
        // 处理对话框结果
        result.ifPresent(student -> {
            // 更新学生信息
            studentService.updateStudent(student);
            
            // 重新加载数据并更新表格
            loadAllStudents();
            
            // 显示成功提示
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("成功");
            alert.setHeaderText(null);
            alert.setContentText("学生信息修改成功");
            alert.showAndWait();
        });
    }

    /**
     * deleteSelectedStudent方法 - 删除选中的学生
     * 
     * 删除表格中选中的学生，需要用户确认操作
     */
    private void deleteSelectedStudent() {
        // 获取表格中选中的学生
        Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        
        // 检查是否有选中的学生
        if (selectedStudent == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("请先选择要删除的学生");
            alert.showAndWait();
            return;
        }

        // 显示确认对话框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setHeaderText(null);
        alert.setContentText("确定要删除该学生吗？");

        // 等待用户确认
        Optional<ButtonType> result = alert.showAndWait();
        
        // 如果用户确认删除
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // 删除学生
            studentService.deleteStudent(selectedStudent);
            
            // 重新加载数据并更新表格
            loadAllStudents();
            
            // 显示成功提示
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("成功");
            successAlert.setHeaderText(null);
            successAlert.setContentText("学生删除成功");
            successAlert.showAndWait();
        }
    }

    /**
     * clearAllStudents方法 - 清空所有学生信息
     * 
     * 删除所有学生信息，需要用户输入特定的确认信息以防止误操作
     * 使用TextInputDialog获取用户输入
     */
    private void clearAllStudents() {
        // 创建文本输入对话框
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("确认清空");
        dialog.setHeaderText("请输入确认信息");
        dialog.setContentText("请输入：我确定要删除所有学生信息");

        // 显示对话框并等待用户输入
        Optional<String> result = dialog.showAndWait();
        
        // 处理用户输入
        result.ifPresent(input -> {
            // 验证输入是否正确
            if ("我确定要删除所有学生信息".equals(input)) {
                // 清空所有学生信息
                studentService.getStudentLists().clear();
                
                // 重新加载数据并更新表格
                loadAllStudents();
                
                // 显示成功提示
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("成功");
                alert.setHeaderText(null);
                alert.setContentText("所有学生信息已清空");
                alert.showAndWait();
            } else {
                // 输入不正确，取消操作
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("操作取消");
                alert.setHeaderText(null);
                alert.setContentText("输入信息不正确，操作已取消");
                alert.showAndWait();
            }
        });
    }
}
