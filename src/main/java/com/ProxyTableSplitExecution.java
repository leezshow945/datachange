package com;

import com.user.ProxyTableSplit;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ProxyTableSplitExecution {

    static Logger logger = Logger.getLogger(ProxyTableSplitExecution.class);
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;


    public static void main(String[] args) {

        try {
            Properties properties = new Properties();
            properties.load(Execute01.class.getResourceAsStream("/config.properties"));
            String DRIVERNAME = properties.getProperty("DRIVERNAME");
            String URL = properties.getProperty("URL");
            String USER = properties.getProperty("USER");
            String PASSWORD = properties.getProperty("PASSWORD");
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
            ProxyTableSplit.exe(resultSet,statement);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                logger.info("user_proxy split done! please execute execute_user_proxy_site_code.sql!!!");
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

}
