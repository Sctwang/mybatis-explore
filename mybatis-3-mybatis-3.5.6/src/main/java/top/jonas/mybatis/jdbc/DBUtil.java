package top.jonas.mybatis.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wyz
 * @date 2021/4/15 21:18
 */
public class DBUtil {
    /**
     * 数据库连接信息
     */
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        driver = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://127.0.0.1:3306/test_mybatis?useCharacter=UTF-8&serverTimezone=Asia/Shanghai";
        username = "root";
        password = "root";
    }

    /**
     * 获取数据库连接
     */
    public static Connection open() {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("数据库连接失败！！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放数据库连接资源
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
