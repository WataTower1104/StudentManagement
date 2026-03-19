package site.watatower.student.Service;

import java.util.List;

import site.watatower.student.Entity.Student;

/**
 * 系统需要实现的功能：添加、编辑、删除、查询学生信息，数据持久化
 * @author WataTower
 * @ProjectName: StudentManagement
 * @CreateTime: 2026/3/18  21:19
 * @Description: 业务逻辑层/学生管理服务接口
 */

public interface StudentService {
    /**
     * 添加学生信息
     * @param student 要添加的学生信息
     */
    void addStudent(Student student);

    /**
     * 编辑学生信息
     * @param student 要编辑的学生信息
     */
    void updateStudent(Student student);

    /**
     * 删除学生信息
     * @param student 要删除的学生信息
     */
    void deleteStudent(Student student);

    /**
     * 查询所有学生信息
     * @return 所有学生信息的列表
     */
    List<Student> getStudentLists();

    /**
     * 查询指定学号的学生信息
     * @param id 学生的学号
     * @return 学生信息
     */
    Student getStudentById(int id);


}
