package com;

import com.cx.SiteAndSysDataMigration;
import com.user.UserMigration01;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


/**
 * No.1
 * 生成关联库依赖关联表
 */
public class Execute01 {

    public static Logger logger = Logger.getLogger(Execute01.class);
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;

    static OutputStreamWriter out = null;

    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.load(Execute01.class.getResourceAsStream("/config.properties"));
            String DRIVERNAME = properties.getProperty("DRIVERNAME");
            String URL = properties.getProperty("URL");
            String USER = properties.getProperty("USER");
            String PASSWORD = properties.getProperty("PASSWORD");

            if (conn == null) {
                Class.forName(DRIVERNAME);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            if (statement == null) {
                statement = conn.createStatement();
            }
            /**
             * OutputStreamWriter 定义utf-8编码 解决中文乱码
             */
            out = new OutputStreamWriter(new FileOutputStream("execute_01.sql"),"UTF-8");

            /**
             * 生成user_relation site_relation
             */
            UserMigration01.executeRelation(resultSet, statement, out);
            SiteAndSysDataMigration.createRelationSqlScript(resultSet, statement, out);

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                logger.info("Execute01 done!!! please execute execute_01.sql!!!");
                logger.info("Execute01 done!!! please execute execute_01.sql!!!");
                logger.info("Execute01 done!!! please execute execute_01.sql!!!");

                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
                if (out != null) out.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

}
