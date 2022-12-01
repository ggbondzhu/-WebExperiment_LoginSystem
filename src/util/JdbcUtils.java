package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static String url;// 连接数据库的驱动
    private static String driver;
    private static String username;
    private static String password;
    public static Connection conn;

    static {
        //读取配置文件
        InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("config/jdbc.properties");
        Properties properties = new Properties();
        //从输入字节流读取属性列表（键和元素对）
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //用此属性列表中指定的键搜索属性，获取驱动，url，username，password
        driver = properties.getProperty("driverName");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");

        JdbcUtils.conn = open();
    }

    public static Connection open() {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.print("数据库已连接！");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print("数据库打开失败！");
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
