package site.watatower.student.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.LogManager;
import site.watatower.student.Dao.StudentDao;
import site.watatower.student.Dao.StudentDaoImpl;
import site.watatower.student.Entity.Student;

/**
 * @author WataTower
 * @ProjectName: StudentManagement
 * @CreateTime: 2026/3/18  21:19
 * @Description: 业务逻辑层/服务时限
 */

public class StudentServiceImpl implements StudentService {

    private static final Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());
    private static StudentServiceImpl instance;
    private StudentDao studentDao;

    private StudentServiceImpl() {
        studentDao = StudentDaoImpl.getInstance();
    }

    public static StudentServiceImpl getInstance() {
        if (instance == null) {
            synchronized (StudentServiceImpl.class) {
                if (instance == null) {
                    instance = new StudentServiceImpl();
                }
            }
        }
        return instance;
    }

    /**
     * 添加学生信息
     * @param student 要添加的学生信息
     */
    @Override
    public void addStudent(Student student) {
        // 验证学生信息是否符合要求
        validateStudent(student);

        // 检查学生ID是否已存在
        if (studentDao.getStudentById(student.getId()) != null) {
            logger.warning("学生ID已存在: " + student.getId());
            return;
        }

        // 添加student信息
        studentDao.addStudent(student);
    }

    /**
     * 更新学生信息
     * @param student 要更新的学生信息
     */
    @Override
    public void updateStudent(Student student) {
        // 验证学生信息是否符合要求
        validateStudent(student);
        // 检查学生信息是否与数据库中已存在的信息相同
        if (isSameInfo(student)) {
            logger.info("学生信息与数据库中已存在的信息相同: " + student.getId());
            return;
        }

        // 检查学生ID是否存在
        if (studentDao.getStudentById(student.getId()) == null) {
            logger.warning("学生ID不存在: " + student.getId());
            return;
        }

        // 更新student信息
        studentDao.updateStudent(student);
    }

    /**
     * 删除学生信息
     * @param student 要删除的学生信息
     */
    @Override
    public void deleteStudent(Student student) {

        // 检查学生ID是否存在
        if (studentDao.getStudentById(student.getId()) == null) {
            logger.warning("学生ID不存在: " + student.getId());
            return;
        }

        // 删除student信息
        studentDao.deleteStudent(student.getId());
    }

    /**
     * 查询所有学生信息
     * @return 所有学生信息的列表
     */
    @Override
    public List<Student> getStudentLists() {
        // 获取所有学生信息
        return studentDao.getStudentLists();
    }

    @Override
    public Student getStudentById(int id) {
        // 根据ID查询学生信息
        return studentDao.getStudentById(id);   
    }

    /**
     * 验证学生信息是否符合要求
     * @param student 要验证的学生信息
     */
    private void validateStudent(Student student) {
        // 验证学生信息是否符合要求
        if (student.getId() == 0) {
            logger.warning("学生ID不能为空");
            return;
        }
        if (student.getName() == null || student.getName().isEmpty()) {
            logger.warning("学生姓名不能为空");
            return;
        }
        if (student.getAge() <= 0) {
            logger.warning("学生年龄必须大于0");
            return;
        }
        if (student.getGender() == null) {
            logger.warning("学生性别不能为空");
            return;
        }
           }

    private boolean isSameInfo(Student student) {
        // 检查学生信息是否与数据库中已存在的信息相同
        Student existingStudent = studentDao.getStudentById(student.getId());
        return existingStudent != null && existingStudent.getName().equals(student.getName()) && existingStudent.getAge() == student.getAge() && existingStudent.getGender().equals(student.getGender());
    }
}
