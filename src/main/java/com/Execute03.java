package com;

import com.cx.SiteAndSysDataMigration;
import com.ky.Ky;
import com.lxh.OfficeAndGameSQL;
import com.mh.MessageMigration;
import com.user.UserMigration01;
import com.user.UserMigration02;
import com.user.UserMigration03;
import com.user.UserMigration04;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * NO.3
 * 写入正式新库sql脚本
 */
public class Execute03 {

    static Logger logger = Logger.getLogger(Execute03.class);
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

            out = new OutputStreamWriter(new FileOutputStream("execute_03.sql"), "UTF-8");

            UserMigration01.executeData(resultSet, statement, out);
            UserMigration02.executeData(resultSet, statement, out);
            UserMigration03.executeData(resultSet, statement, out);
            UserMigration04.executeData(resultSet, statement, out);

            Ky.kyPlatform(resultSet, statement, out);
            OfficeAndGameSQL.executeMain(out);
            MessageMigration.excuteDate(out);

            SiteAndSysDataMigration.createNewTableDataSqlScript(out);
        } catch (Exception e) {
            logger.error(e.getMessage());

        } finally {
            try {
                logger.info("Execute03 done! please execute execute_03.sql!!!");
                logger.info("Execute03 done! please execute execute_03.sql!!!");
                logger.info("Execute03 done! please execute execute_03.sql!!!");
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
