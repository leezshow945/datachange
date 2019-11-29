package com;

import com.ky.Ky;
import com.lxh.OfficeAndGameRelationData;
import com.mh.MessageMigration;
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
 * No.2
 * 根据关联库依赖关联表生成关联数据
 */
public class Execute02 {

    static Logger logger = Logger.getLogger(Execute02.class);
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
            out = new OutputStreamWriter(new FileOutputStream("execute_02.sql"), "UTF-8");

            UserMigration02.executeRelation(resultSet, statement, out);
            UserMigration03.executeRelation(resultSet, statement, out);
            UserMigration04.executeRelation(resultSet, statement, out);

            Ky.kyRelation(resultSet, statement, out);
            OfficeAndGameRelationData.executeMain(resultSet, statement, out);
            MessageMigration.executeRelation(resultSet, statement, out);


        } catch (Exception e) {
            logger.error(e.getMessage());

        } finally {
            try {
                logger.info("Execute02 done! please execute execute_02.sql!!!");
                logger.info("Execute02 done! please execute execute_02.sql!!!");
                logger.info("Execute02 done! please execute execute_02.sql!!!");
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
