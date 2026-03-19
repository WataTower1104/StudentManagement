package site.watatower.student.Util.FileHandle;

import java.util.logging.Logger;

import site.watatower.student.Entity.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WataTower
 * @ProjectName: StudentManagement
 * @CreateTime: 2026/3/18  21:22
 * @Description: 文件读写工具
 */

public class FileUtil {

    private static final Logger logger = Logger.getLogger(FileUtil.class.getName());
    private String filePath = "src/main/resources/site/watatower/student/students.txt";

    /**
     * 保存学生列表到文件（使用文本格式，方便直接查看）
     * 格式：每个学生一行，字段用逗号分隔
     * @param students 学生列表
     * @throws IOException IO异常
     */
    public void saveStudentList(List<Student> students) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        // 使用BufferedWriter写入文本格式
        // 不使用ObjectOutputStream主要是为了能够在txt文件中更加直观地看到运行结果
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Student student : students) {
                // 格式：id,name,age,gender,major
                String line = student.getId() + "," + 
                             student.getName() + "," + 
                             student.getAge() + "," + 
                             student.getGender() + "," + 
                             student.getMajor();
                writer.write(line);
                writer.newLine(); // 换行
            }
            logger.info("学生列表已保存到文件: " + filePath);
        }
    }


    /**
     * 从文件加载学生列表（从文本格式读取）
     * 从文本文件读取数据并转换回Student对象
     * @return 学生列表
     * @throws IOException IO异常
     */
    public List<Student> loadStudentList() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>(); // 文件不存在时返回空列表
        }

        List<Student> students = new ArrayList<>();
        
        // 使用BufferedReader读取文本格式
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 按逗号分割每一行数据
                String[] parts = line.split(",");
                if (parts.length >= 5) { // 确保有足够的数据
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        int age = Integer.parseInt(parts[2]);
                        String gender = parts[3];
                        String major = parts[4];
                        
                        // 创建Student对象并添加到列表
                        students.add(new Student(id, name, age, gender, major));
                        logger.fine("成功加载学生: " + name + " (ID: " + id + ")");
                    } catch (NumberFormatException e) {
                        logger.severe("解析数据时出错: " + line);
                        e.printStackTrace();
                    }
                }
            }
        }
        
        return students;
    }
}
