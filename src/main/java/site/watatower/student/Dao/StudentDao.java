package site.watatower.student.Dao;

import java.util.List;

import site.watatower.student.Entity.Student;

/**
 * @author WataTower
 * @ProjectName: StudentManagement
 * @CreateTime: 2026/3/18  21:20
 * @Description: 数据访问层/数据访问接口
 */

public interface StudentDao {
    /**
     * 添加学生到数据存储
     * @param student 要添加的学生对象
     */
    void addStudent(Student student);

    /**
     * 更新学生信息
     * @param student 要更新的学生对象
     */
    void updateStudent(Student student);

    /**
     * 删除学生
     * @param id 要删除的学生ID
     */
    void deleteStudent(int id);

    /**
     * 根据ID获取学生信息
     * @param id 学生ID
     * @return 学生对象
     */
    Student getStudentById(int id);

    /**
     * 统计学生数量
     * @return 学生数量
     */
    int countStudents();

    /**
     * 清空所有学生信息
     */
    void clearStudents();

    /**
     * 保存所有学生信息到文件
     */
    void saveStudents();

    /**
     * 从文件加载所有学生信息
     * @return 加载到的学生列表
     */
    List<Student> loadStudents();

    /**
     * 获取所有学生信息
     * @return 所有学生信息的列表
     */
    List<Student> getStudentLists();

}
