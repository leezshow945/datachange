package com;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.user.LogUserChange;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.Constant.DB_NAME;
import static com.Constant.LIMIT;

/**
 * Created by even on 2019/7/23.
 */
public class LogUserExecution {
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;
    static Logger logger = Logger.getLogger(LogUserExecution.class);

    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.load(LogUserExecution.class.getResourceAsStream("/config.properties"));
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

            resultSet = statement.executeQuery("select count(0) as num from " + DB_NAME);
            long size = 0;
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 2000);

            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new Task());
            if(ceil > 500){
                exec.execute(new Task1());
            }
            if(ceil > 1000) {
                exec.execute(new Task2());
            }
            if(ceil > 1500) {
                exec.execute(new Task3());
            }
            if(ceil > 2000) {
                exec.execute(new Task4());
            }
            if(ceil > 2500) {
                exec.execute(new Task5());
            }
            if(ceil >= 3000) {
                exec.execute(new Task6());
            }
        }catch(Exception e){
            logger.error(e);
        }finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}

class Task implements Runnable {
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;
    static Logger logger = Logger.getLogger(Task.class);

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            properties.load(LogUserExecution.class.getResourceAsStream("/config.properties"));
            String DRIVERNAME = properties.getProperty("DRIVERNAME");
            String URL = properties.getProperty("URL");
            String USER = properties.getProperty("USER");
            String PASSWORD = properties.getProperty("PASSWORD");
            String MONGOURL = properties.getProperty("MONGODB.URL");
            int MONGOPORT = Integer.valueOf(properties.getProperty("MONGODB.PORT"));
            String MONGOUSER = properties.getProperty("MONGODB.USER");
            String MONGOPASSWORD = properties.getProperty("MONGODB.PWD");

            if (conn == null) {
                Class.forName(DRIVERNAME);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            if (statement == null) {
                statement = conn.createStatement();
            }

            ServerAddress serverAddress = new ServerAddress(MONGOURL, MONGOPORT);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);
            MongoCredential credential = MongoCredential.createScramSha1Credential(MONGOUSER, "user", MONGOPASSWORD.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);
            MongoClient mongoClient = new MongoClient(addrs, credentials);
            MongoDatabase collection = mongoClient.getDatabase("user");

            logger.info(" changeLogUser begin task!");
            long thisTime = System.currentTimeMillis();

            for (int i = 0; i < 500; i++) {
                resultSet = statement.executeQuery("select id,plat,user_name,content,user_id,login_ip,login_time,login_area,is_diff_area_login,type,account_type,site_id,is_del,flag_type,login_url,create_time,update_time,site_code FROM " + DB_NAME + " limit " + i * LIMIT + "," + LIMIT + ";");
                LogUserChange.relationSql(resultSet, collection);
            }

            System.out.println("--------------------------------------------ok task");
            logger.info(" changeLogUser task end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                logger.info("LogUserExecution task done");
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}

class Task1 implements Runnable {
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;
    static Logger logger = Logger.getLogger(Task1.class);

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            properties.load(LogUserExecution.class.getResourceAsStream("/config.properties"));
            String DRIVERNAME = properties.getProperty("DRIVERNAME");
            String URL = properties.getProperty("URL");
            String USER = properties.getProperty("USER");
            String PASSWORD = properties.getProperty("PASSWORD");
            String MONGOURL = properties.getProperty("MONGODB.URL");
            int MONGOPORT = Integer.valueOf(properties.getProperty("MONGODB.PORT"));
            String MONGOUSER = properties.getProperty("MONGODB.USER");
            String MONGOPASSWORD = properties.getProperty("MONGODB.PWD");

            if (conn == null) {
                Class.forName(DRIVERNAME);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            if (statement == null) {
                statement = conn.createStatement();
            }

            ServerAddress serverAddress = new ServerAddress(MONGOURL, MONGOPORT);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);
            MongoCredential credential = MongoCredential.createScramSha1Credential(MONGOUSER, "user", MONGOPASSWORD.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);
            MongoClient mongoClient = new MongoClient(addrs, credentials);
            MongoDatabase collection = mongoClient.getDatabase("user");

            logger.info(" changeLogUser begin task1!");
            long thisTime = System.currentTimeMillis();

            for (int i = 500; i < 1000; i++) {
                resultSet = statement.executeQuery("select id,plat,user_name,content,user_id,login_ip,login_time,login_area,is_diff_area_login,type,account_type,site_id,is_del,flag_type,login_url,create_time,update_time,site_code FROM " + DB_NAME + " limit " + i * LIMIT + "," + LIMIT + ";");
                LogUserChange.relationSql(resultSet, collection);
            }

            System.out.println("--------------------------------------------ok task1");
            logger.info(" changeLogUser task1 end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                logger.info("LogUserExecution task1 done");
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}

class Task2 implements Runnable {
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;
    static Logger logger = Logger.getLogger(Task2.class);

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            properties.load(LogUserExecution.class.getResourceAsStream("/config.properties"));
            String DRIVERNAME = properties.getProperty("DRIVERNAME");
            String URL = properties.getProperty("URL");
            String USER = properties.getProperty("USER");
            String PASSWORD = properties.getProperty("PASSWORD");
            String MONGOURL = properties.getProperty("MONGODB.URL");
            int MONGOPORT = Integer.valueOf(properties.getProperty("MONGODB.PORT"));
            String MONGOUSER = properties.getProperty("MONGODB.USER");
            String MONGOPASSWORD = properties.getProperty("MONGODB.PWD");

            if (conn == null) {
                Class.forName(DRIVERNAME);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            if (statement == null) {
                statement = conn.createStatement();
            }

            ServerAddress serverAddress = new ServerAddress(MONGOURL, MONGOPORT);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);
            MongoCredential credential = MongoCredential.createScramSha1Credential(MONGOUSER, "user", MONGOPASSWORD.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);
            MongoClient mongoClient = new MongoClient(addrs, credentials);
            MongoDatabase collection = mongoClient.getDatabase("user");

            logger.info(" changeLogUser begin task2!");
            long thisTime = System.currentTimeMillis();

            for (int i = 1000; i < 1500; i++) {
                resultSet = statement.executeQuery("select id,plat,user_name,content,user_id,login_ip,login_time,login_area,is_diff_area_login,type,account_type,site_id,is_del,flag_type,login_url,create_time,update_time,site_code FROM " + DB_NAME + " limit " + i * LIMIT + "," + LIMIT + ";");
                LogUserChange.relationSql(resultSet, collection);
            }

            System.out.println("--------------------------------------------ok task2");
            logger.info(" changeLogUser task2 end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                logger.info("LogUserExecution task2 done");
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}

class Task3 implements Runnable {
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;
    static Logger logger = Logger.getLogger(Task3.class);

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            properties.load(LogUserExecution.class.getResourceAsStream("/config.properties"));
            String DRIVERNAME = properties.getProperty("DRIVERNAME");
            String URL = properties.getProperty("URL");
            String USER = properties.getProperty("USER");
            String PASSWORD = properties.getProperty("PASSWORD");
            String MONGOURL = properties.getProperty("MONGODB.URL");
            int MONGOPORT = Integer.valueOf(properties.getProperty("MONGODB.PORT"));
            String MONGOUSER = properties.getProperty("MONGODB.USER");
            String MONGOPASSWORD = properties.getProperty("MONGODB.PWD");

            if (conn == null) {
                Class.forName(DRIVERNAME);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            if (statement == null) {
                statement = conn.createStatement();
            }

            ServerAddress serverAddress = new ServerAddress(MONGOURL, MONGOPORT);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);
            MongoCredential credential = MongoCredential.createScramSha1Credential(MONGOUSER, "user", MONGOPASSWORD.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);
            MongoClient mongoClient = new MongoClient(addrs, credentials);
            MongoDatabase collection = mongoClient.getDatabase("user");

            logger.info(" changeLogUser begin task3!");
            long thisTime = System.currentTimeMillis();

            for (int i = 1500; i < 2000; i++) {
                resultSet = statement.executeQuery("select id,plat,user_name,content,user_id,login_ip,login_time,login_area,is_diff_area_login,type,account_type,site_id,is_del,flag_type,login_url,create_time,update_time,site_code FROM " + DB_NAME + " limit " + i * LIMIT + "," + LIMIT + ";");
                LogUserChange.relationSql(resultSet, collection);
            }

            System.out.println("--------------------------------------------ok task3");
            logger.info(" changeLogUser task3 end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                logger.info("LogUserExecution task6 done");
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}

class Task4 implements Runnable {
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;
    static Logger logger = Logger.getLogger(Task4.class);

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            properties.load(LogUserExecution.class.getResourceAsStream("/config.properties"));
            String DRIVERNAME = properties.getProperty("DRIVERNAME");
            String URL = properties.getProperty("URL");
            String USER = properties.getProperty("USER");
            String PASSWORD = properties.getProperty("PASSWORD");
            String MONGOURL = properties.getProperty("MONGODB.URL");
            int MONGOPORT = Integer.valueOf(properties.getProperty("MONGODB.PORT"));
            String MONGOUSER = properties.getProperty("MONGODB.USER");
            String MONGOPASSWORD = properties.getProperty("MONGODB.PWD");

            if (conn == null) {
                Class.forName(DRIVERNAME);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            if (statement == null) {
                statement = conn.createStatement();
            }

            ServerAddress serverAddress = new ServerAddress(MONGOURL, MONGOPORT);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);
            MongoCredential credential = MongoCredential.createScramSha1Credential(MONGOUSER, "user", MONGOPASSWORD.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);
            MongoClient mongoClient = new MongoClient(addrs, credentials);
            MongoDatabase collection = mongoClient.getDatabase("user");

            logger.info(" changeLogUser begin Task4!");
            long thisTime = System.currentTimeMillis();

            for (int i = 2000; i < 2500; i++) {
                resultSet = statement.executeQuery("select id,plat,user_name,content,user_id,login_ip,login_time,login_area,is_diff_area_login,type,account_type,site_id,is_del,flag_type,login_url,create_time,update_time,site_code FROM " + DB_NAME + " limit " + i * LIMIT + "," + LIMIT + ";");
                LogUserChange.relationSql(resultSet, collection);
            }
            System.out.println("--------------------------------------------ok Task4");
            logger.info(" changeLogUser task4 end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                logger.info("LogUserExecution task4 done");
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}

class Task5 implements Runnable {
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;
    static Logger logger = Logger.getLogger(Task5.class);

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            properties.load(LogUserExecution.class.getResourceAsStream("/config.properties"));
            String DRIVERNAME = properties.getProperty("DRIVERNAME");
            String URL = properties.getProperty("URL");
            String USER = properties.getProperty("USER");
            String PASSWORD = properties.getProperty("PASSWORD");
            String MONGOURL = properties.getProperty("MONGODB.URL");
            int MONGOPORT = Integer.valueOf(properties.getProperty("MONGODB.PORT"));
            String MONGOUSER = properties.getProperty("MONGODB.USER");
            String MONGOPASSWORD = properties.getProperty("MONGODB.PWD");

            if (conn == null) {
                Class.forName(DRIVERNAME);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            if (statement == null) {
                statement = conn.createStatement();
            }

            ServerAddress serverAddress = new ServerAddress(MONGOURL, MONGOPORT);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);
            MongoCredential credential = MongoCredential.createScramSha1Credential(MONGOUSER, "user", MONGOPASSWORD.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);
            MongoClient mongoClient = new MongoClient(addrs, credentials);
            MongoDatabase collection = mongoClient.getDatabase("user");

            logger.info(" changeLogUser begin Task5!");
            long thisTime = System.currentTimeMillis();

            for (int i = 2500; i < 3000; i++) {
                resultSet = statement.executeQuery("select id,plat,user_name,content,user_id,login_ip,login_time,login_area,is_diff_area_login,type,account_type,site_id,is_del,flag_type,login_url,create_time,update_time,site_code FROM " + DB_NAME + " limit " + i * LIMIT + "," + LIMIT + ";");
                LogUserChange.relationSql(resultSet, collection);
            }
            System.out.println("--------------------------------------------ok Task5");
            logger.info(" changeLogUser Task5 end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                logger.info("LogUserExecution Task5 done");
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}

class Task6 implements Runnable {
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;
    static Logger logger = Logger.getLogger(Task6.class);

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            properties.load(LogUserExecution.class.getResourceAsStream("/config.properties"));
            String DRIVERNAME = properties.getProperty("DRIVERNAME");
            String URL = properties.getProperty("URL");
            String USER = properties.getProperty("USER");
            String PASSWORD = properties.getProperty("PASSWORD");
            String MONGOURL = properties.getProperty("MONGODB.URL");
            int MONGOPORT = Integer.valueOf(properties.getProperty("MONGODB.PORT"));
            String MONGOUSER = properties.getProperty("MONGODB.USER");
            String MONGOPASSWORD = properties.getProperty("MONGODB.PWD");

            if (conn == null) {
                Class.forName(DRIVERNAME);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            if (statement == null) {
                statement = conn.createStatement();
            }

            ServerAddress serverAddress = new ServerAddress(MONGOURL, MONGOPORT);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);
            MongoCredential credential = MongoCredential.createScramSha1Credential(MONGOUSER, "user", MONGOPASSWORD.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);
            MongoClient mongoClient = new MongoClient(addrs, credentials);
            MongoDatabase collection = mongoClient.getDatabase("user");

            logger.info(" changeLogUser begin Task6!");
            long thisTime = System.currentTimeMillis();

            resultSet = statement.executeQuery("select count(0) as num from " + DB_NAME);
            long size = 0;
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 2000);

            for (int i = 3000; i < ceil; i++) {
                resultSet = statement.executeQuery("select id,plat,user_name,content,user_id,login_ip,login_time,login_area,is_diff_area_login,type,account_type,site_id,is_del,flag_type,login_url,create_time,update_time,site_code FROM " + DB_NAME + " limit " + i * LIMIT + "," + LIMIT + ";");
                LogUserChange.relationSql(resultSet, collection);
            }

            System.out.println("--------------------------------------------ok Task6");
            logger.info(" changeLogUser Task6 end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                logger.info("LogUserExecution Task6 done");
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
