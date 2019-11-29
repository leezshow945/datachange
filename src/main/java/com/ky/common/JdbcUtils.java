package com.ky.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author ky.ky
 */
public class JdbcUtils {

   public static Connection getConnection(String library) {
        Connection connection = null;
        try {
            // 加载数据库驱动
            String className = "com.mysql.jdbc.Driver";
            Class.forName(className);
            // 获取数据库连接
            String jdbcUrl = "jdbc:mysql://192.168.11.232:32006/"+library+"?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(jdbcUrl, user, password);
        } catch (Exception e) {
            System.out.println("连接失败");
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("关闭失败");
        }
    }

}