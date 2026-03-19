package site.watatower.student.Dao;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import site.watatower.student.Entity.Student;
import site.watatower.student.Util.FileHandle.FileUtil;

/**
 * @author WataTower
 * @ProjectName: StudentManagement
 * @CreateTime: 2026/3/18  21:20
 * @Description: 数据访问层/数据访问实现
 */

public class StudentDaoImpl implements StudentDao {

    private static final Logger logger = Logger.getLogger(StudentDaoImpl.class.getName());
    private List<Student> studentLists;
    private static StudentDaoImpl instance;

    private StudentDaoImpl() {
        studentLists = loadStudents();
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

    /**
     * 添加学生到数据存储
     * @param student 要添加的学生对象
     */
    @Override
    public void addStudent(Student student) {
        // 添加学生到数据存储
        // 实现添加学生的方法
        studentLists.add(student);
        saveStudents();
        logger.info("成功添加学生: " + student.getName() + " (ID: " + student.getId() + ")");
    }

    /**
     * 更新学生信息
     * @param student 要更新的学生对象
     */
    @Override
    public void updateStudent(Student student) {
        // 实现更新学生的方法
        for (Student s : studentLists) {
            if (s.getId() == student.getId()) {
                s.setName(student.getName());
                s.setAge(student.getAge());
                s.setGender(student.getGender());
                s.setMajor(student.getMajor());
                break;
            }
        }
        logger.info("成功更新学生信息: " + student.getName() + " (ID: " + student.getId() + ")");
        saveStudents();
    }

    /**
     * 删除学生
     * @param id 要删除的学生ID
     */
    @Override
    public void deleteStudent(int id) {
        // 实现删除学生的方法
        for (Student s : studentLists) {
            if (s.getId() == id) {
                studentLists.remove(s);
                break;
            }
        }
        logger.info("成功删除学生 (ID: " + id + ")");
        saveStudents();
    }

    /**
     * 根据ID获取学生
     * @param id 要获取的学生ID
     * @return 如果找到指定ID的学生，返回该学生对象；否则返回null
     */
    @Override
    public Student getStudentById(int id) {
        // 实现根据ID获取学生的方法
        for (Student s : studentLists) {
            if (s.getId() == id) {
                return s;
            }
        }
        // 如果没有找到指定ID的学生，返回null
        return null;
    }

    /**
     * 统计学生数量
     * @return 学生数量
     */
    @Override
    public int countStudents() {
        // 实现统计学生数量的方法
        return studentLists.size();
    }

    /**
     * 清空所有学生信息
     */
    @Override
    public void clearStudents() {
        // 实现清空所有学生的方法
        studentLists.clear();
        saveStudents();
        logger.info("成功清空所有学生信息");
    }

    /**
     * 保存所有学生信息到文件
     */
    @Override
    public void saveStudents() {
        // 保存学生列表到文件
        FileUtil fileUtil = new FileUtil();
        try {
            fileUtil.saveStudentList(studentLists); 
        } catch (IOException e) {
            // 保存学生列表失败，打印错误信息
            e.printStackTrace();
        }
    }

    /**
     * 从文件加载所有学生信息
     * @return 加载到的学生列表
     */
    @Override
    public List<Student> loadStudents() {
        // 从文件加载学生列表
        FileUtil fileUtil = new FileUtil();
        try {
            return fileUtil.loadStudentList();
        } catch (IOException e) {
            // 加载学生列表失败，打印错误信息
            e.printStackTrace();
            return null;
        
        }
    }

    /**
     * 获取所有学生信息
     * @return 所有学生信息的列表
     */
    @Override
    public List<Student> getStudentLists() {
        return studentLists;
    }
    
}
   
