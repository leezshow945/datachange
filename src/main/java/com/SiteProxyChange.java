package com;

import com.user.UserMigration03;
import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class SiteProxyChange {


    static Logger logger = Logger.getLogger(UserProxyExecution.class);
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;
    static BufferedOutputStream bufferStream = null;
    static FileOutputStream fileStream = null;

    public static void main(String[] args) {

        try {
            Properties properties = new Properties();
            properties.load(Execute01.class.getResourceAsStream("/config.properties"));
            String DRIVERNAME = properties.getProperty("DRIVERNAME");
            String URL = properties.getProperty("URL");
            String USER = properties.getProperty("USER");
            String PASSWORD = properties.getProperty("PASSWORD");
            String siteId = properties.getProperty("siteId");
            String siteCode = properties.getProperty("siteCode");
            /**
             * 写入正式新库sql脚本
             */
            if (conn == null) {
                Class.forName(DRIVERNAME);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            if (statement == null) {
                statement = conn.createStatement();
            }
            fileStream = new FileOutputStream(new File("site_user_proxy.sql"));
            bufferStream = new BufferedOutputStream(fileStream);
            UserMigration03.executeSiteProxyChange(resultSet, statement, bufferStream,siteId,siteCode);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                logger.info("UserProxyExecution done! please execute site_user_proxy.sql!!!");
                logger.info("UserProxyExecution done! please execute site_user_proxy.sql!!!");
                logger.info("UserProxyExecution done! please execute site_user_proxy.sql!!!");
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
