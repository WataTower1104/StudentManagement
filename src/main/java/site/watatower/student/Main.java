package site.watatower.student;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 * Main类 - 学生管理系统的程序入口
 * 
 * 该类是JavaFX应用程序的主入口点，负责：
 * 1. 初始化日志系统
 * 2. 显示启动加载界面
 * 3. 在后台线程中加载数据和主界面
 * 4. 管理应用程序的生命周期
 * 
 * JavaFX应用程序必须继承Application类并实现start方法
 */
public class Main extends Application {
    /**
     * start方法 - JavaFX应用程序的主入口方法
     * 
     * 该方法在JavaFX应用程序启动时由系统自动调用，用于：
     * 1. 加载并显示启动界面（loading.fxml）
     * 2. 在后台线程中加载数据和主界面
     * 3. 实现平滑的界面切换
     * 
     * @param stage JavaFX的主舞台（窗口）对象
     * @throws IOException 如果加载FXML文件失败
     */
    @Override
    public void start(Stage stage) throws IOException {
        // ==================== 加载启动界面 ====================
        // FXMLLoader用于加载FXML界面文件
        FXMLLoader loadingLoader = new FXMLLoader(Main.class.getResource("loading.fxml"));
        VBox loadingRoot = loadingLoader.load();  // 加载界面根节点
        
        // 获取界面组件（通过ID查找）
        ProgressIndicator progressIndicator = (ProgressIndicator) loadingRoot.lookup("#progressIndicator");
        Label loadingLabel = (Label) loadingRoot.lookup("#loadingLabel");
        
        // 创建场景并显示
        Scene loadingScene = new Scene(loadingRoot, 400, 300);  // 创建场景（宽400，高300）
        stage.setTitle("学生管理系统");  // 设置窗口标题
        stage.setScene(loadingScene);    // 设置场景
        stage.show();                   // 显示窗口
        
        // ==================== 后台线程加载数据 ====================
        // 创建新线程在后台加载数据，避免阻塞UI线程
        new Thread(() -> {
            try {
                // 模拟加载时间（实际项目中这里会加载数据）
                Thread.sleep(1000);
                
                // 加载主界面
                FXMLLoader mainLoader = new FXMLLoader(Main.class.getResource("student-management.fxml"));
                Scene mainScene = new Scene(mainLoader.load(), 600, 500);  // 创建主界面场景
                
                // 在JavaFX主线程中更新UI
                // Platform.runLater用于在JavaFX应用线程中执行UI更新操作
                // 注意：JavaFX的UI更新必须在主线程中进行
                Platform.runLater(() -> {
                    stage.setScene(mainScene);  // 切换到主界面
                });
            } catch (IOException | InterruptedException e) {
                // 处理异常，显示错误信息
                e.printStackTrace();
                
                // 在主线程中更新加载失败信息
                Platform.runLater(() -> {
                    loadingLabel.setText("加载失败，请重新启动程序");
                    progressIndicator.setVisible(false);
                });
            }
        }).start();  // 启动后台线程
    }

    /**
     * main方法 - Java程序的标准入口点
     * 
     * 该方法是Java程序的入口点，用于：
     * 1. 初始化日志系统
     * 2. 调用JavaFX的launch方法启动应用程序
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        try {
            // ==================== 初始化日志系统 ====================
            
            // 创建logs目录（如果不存在）
            java.io.File logsDir = new java.io.File("logs");
            if (!logsDir.exists()) {
                logsDir.mkdirs();
            }
            
            // 配置文件处理器（将日志写入文件）
            // FileHandler参数说明：
            // 第一个参数：日志文件路径
            // 第二个参数：true表示追加模式，false表示覆盖模式
            FileHandler fileHandler = new FileHandler("logs/student-management.log", true);
            fileHandler.setFormatter(new SimpleFormatter());  // 设置日志格式
            fileHandler.setLevel(java.util.logging.Level.INFO);  // 设置日志级别为INFO
            
            // 配置控制台处理器（将日志输出到控制台）
            java.util.logging.ConsoleHandler consoleHandler = new java.util.logging.ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());  // 设置日志格式
            consoleHandler.setLevel(java.util.logging.Level.INFO);  // 设置日志级别为INFO
            
            // 获取根日志记录器并添加处理器
            Logger rootLogger = Logger.getLogger("");  // 获取根日志记录器
            rootLogger.addHandler(fileHandler);  // 添加文件处理器
            rootLogger.addHandler(consoleHandler);  // 添加控制台处理器
            rootLogger.setLevel(java.util.logging.Level.INFO);  // 设置根日志级别
            
        } catch (Exception e) {
            // 日志初始化失败时打印异常
            e.printStackTrace();
        }
        
        // 启动JavaFX应用程序
        // launch方法会调用start方法，并初始化JavaFX运行时环境
        launch();
    }
}