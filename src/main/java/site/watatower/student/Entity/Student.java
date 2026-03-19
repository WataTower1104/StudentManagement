package site.watatower.student.Entity;

import java.io.Serializable;

/**
 * Student类的属性：id、name、age、gender、major等
 * @author WataTower
 * @ProjectName: StudentManagement
 * @CreateTime: 2026/3/18  21:19
 * @Description: 实体类/学生类
 */

/**
 * implements Serializable：实现序列化接口，标记该类可以被序列化（保存到文件或网络传输）
 * 序列化：将对象转换为字节流的过程，可以保存到文件或在网络上传输
 * 反序列化：将字节流转换回对象的过程，从文件读取或接收网络数据后还原对象
 */
public class Student implements Serializable {
    /**
     * serialVersionUID：序列化版本号，用于验证序列化和反序列化时使用的类版本是否一致
     * 1. 它是类的"身份证"，所有该类的对象共享同一个版本号
     * 2. 不需要getter/setter方法，因为它是静态常量
     * 3. 只有在类结构发生重大变化时才需要修改（如删除字段、修改字段类型等）
     * 4. 添加新字段时，通常保持UID不变，旧数据仍能读取，新字段使用默认值
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int age;
    private String gender;
    private String major;

    /**
     * 默认构造函数：为所有字段设置默认值
     * 当没有提供参数时使用这些默认值创建对象
     */
    public Student() {
        this.id = 0;          // 默认ID为0
        this.name = "未知";    // 默认姓名为"未知"
        this.age = 0;         // 默认年龄为0
        this.gender = "未知";  // 默认性别为"未知"
        this.major = "未知";   // 默认专业为"未知"
    }

    public Student(int id, String name, int age, String gender, String major) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.major = major;
    }

    // getter和setter
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}

    public String getMajor() {return major;}
    public void setMajor(String major) {this.major = major;}

    public String toString() {
        return "{id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", major=" + major + "}";
    }
}
