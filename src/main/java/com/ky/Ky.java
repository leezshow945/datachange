package com.ky;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.ky.common.Constant;
import com.ky.common.JdbcUtils;
import javafx.application.Application;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ky.ky
 */
public class Ky {
    public static Logger logger = Logger.getLogger(Application.class);
    public static String DRIVERNAME = "com.mysql.jdbc.Driver";
    public static String URL = "jdbc:mysql://192.168.11.231:32006/db_relation?useUnicode=true&characterEncoding=UTF8&useSSL=true";
    public static String USER = "root";
    public static String PASSWORD = "root";
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;
    static BufferedOutputStream bufferStream = null;
    static FileOutputStream fileStream = null;
    private static List<String> domainList = new ArrayList<>();


    public static boolean kyRelation(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        insertRelation(Constant.lot_site_line_group_table, resultSet, statement, bufferStream);
        //insertRelation(Constant.lot_sys_resource_table, resultSet, statement, bufferStream);
        insertRelation(Constant.lot_sys_role_table, resultSet, statement, bufferStream);
        insertRelation(Constant.lot_site_line_table, resultSet, statement, bufferStream);
        insertRelation(Constant.lot_site_denfense_table, resultSet, statement, bufferStream);
        return true;
    }

    public static boolean kyPlatform(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        domainGroup(resultSet, statement, bufferStream);
        domain1(resultSet, statement, bufferStream);
        domain2(resultSet, statement, bufferStream);
        //resource(resultSet, statement, bufferStream);
        role(resultSet, statement, bufferStream);
        //roleResource(resultSet, statement, bufferStream);
        userRole(resultSet, statement, bufferStream);
        ipLimit(resultSet, statement, bufferStream);
        addAdminRoleResource1(resultSet, statement, bufferStream);
        addAdminRoleResource2(resultSet, statement, bufferStream);
        addAdminRoleResource3(resultSet, statement, bufferStream);
        try {
            bufferStream.write(Constant.country_value);
            bufferStream.write(Constant.resourceCreate);
            bufferStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String kyDataChange() {
        tableExist(Constant.lot_site_line_group_relation_table, Constant.db_relation);
        tableExist(Constant.lot_sys_resource_relation_table, Constant.db_relation);
        tableExist(Constant.lot_sys_role_relation_table, Constant.db_relation);
        tableExist(Constant.lot_site_line_relation_table, Constant.db_relation);
        tableExist(Constant.lot_site_denfense_relation_table, Constant.db_relation);
        executeUpdate(Constant.lot_site_line_group_relation, Constant.db_relation);
        executeUpdate(Constant.lot_sys_resource_relation, Constant.db_relation);
        executeUpdate(Constant.lot_sys_role_relation, Constant.db_relation);
        executeUpdate(Constant.lot_site_line_relation, Constant.db_relation);
        executeUpdate(Constant.lot_site_denfense_relation, Constant.db_relation);
        insertRelation(Constant.lot_site_line_group_table);
        insertRelation(Constant.lot_sys_resource_table);
        insertRelation(Constant.lot_sys_role_table);
        insertRelation(Constant.lot_site_line_table);
        insertRelation(Constant.lot_site_denfense_table);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(domainGroup());
        stringBuilder.append(domain1());
        stringBuilder.append(domain2());
        stringBuilder.append(resource());
        stringBuilder.append(role());
        stringBuilder.append(roleResource());
        stringBuilder.append(userRole());
        stringBuilder.append(ipLimit());
        stringBuilder.append(Constant.country_value);
        return stringBuilder.toString();
    }

    public static String insertRelation(String tableName, ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT id FROM " + Constant.db_main + "." + tableName + " WHERE is_del = 'F'";
        try {
            resultSet = statement.executeQuery(sql);
            //传递 SQL 语句
            stringBuilder.append("insert into " + Constant.db_relation + "." + tableName + "_relation(" +
                    "new_id," +
                    "old_id" +
                    ") values");
            while (resultSet.next()) {
                Long newId = IdWorker.getId();
                String oldId = "'" + resultSet.getString("id") + "'";
                stringBuilder.append("(" +
                        newId + "," +
                        oldId +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error(e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String insertRelation(String tableName) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT id FROM " + tableName + " WHERE is_del = 'F'";
        Connection connection = JdbcUtils.getConnection(Constant.db_main);
        Connection connection2 = JdbcUtils.getConnection(Constant.db_relation);
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            Statement statement2 = connection2.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + tableName + "_relation(" +
                    "new_id," +
                    "old_id" +
                    ") values");
            while (resultSet.next()) {
                Long newId = IdWorker.getId();
                String oldId = "'" + resultSet.getString("id") + "'";
                stringBuilder.append("(" +
                        newId + "," +
                        oldId +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            resultSet.close();
            statement.close();
            statement2.executeUpdate(stringBuilder.toString());
            statement2.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
            JdbcUtils.closeConnection(connection2);
        }
        return stringBuilder.toString();
    }

    public static void fileWrite(String data) {
        String saveFile = "./ky.sql";

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(saveFile);
            fileWriter.write(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public static void dropTable(String tableName, String library) {
        Connection connection = JdbcUtils.getConnection(library);
        String sql = "DROP TABLE " + tableName;
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    public static void tableExist(String tableName, String library) {
        Connection connection = JdbcUtils.getConnection(library);
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            statement.executeQuery(sql);
            statement.close();
            dropTable(tableName, library);
        } catch (SQLException e) {
            // logger.info(e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }


    public static void executeUpdate(String createSql, String library) {
        Connection connection = JdbcUtils.getConnection(library);
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            statement.executeUpdate(createSql);
            statement.close();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    //    public static void delField(String delSql){
//        Connection connection = JdbcUtils.getConnection();
//        try {
//            //传递 SQL 语句
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(delSql);
//            statement.close();
//
//        } catch (SQLException e) {
//            logger.error("操作失败"+e.getMessage());
//        } finally {
//            JdbcUtils.closeConnection(connection);
//        }
//    }
//
    public static String domainGroup() {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tllgr.new_id id,\n" +
                "\tsr.new_id site_id,\n" +
                "\tsr.site_code site_code,\n" +
                "\tlg.group_name group_name,\n" +
                "\tlg.sort_id sort_id,\n" +
                "\tlg.is_enable is_enable,\n" +
                "\tlu.login_name create_by,\n" +
                "\tluu.login_name update_by,\n" +
                "\tlg.create_time create_time,\n" +
                "\tlg.update_time update_time,\n" +
                "\tlg.remark remark\n" +
                "FROM\n" +
                "\tlot_site_line_group lg\n" +
                "LEFT JOIN lot_sys_user lu ON lg.create_by = lu.id\n" +
                "LEFT JOIN lot_sys_user luu ON lg.update_by = luu.id\n" +
                "LEFT JOIN db_relation.site_relation sr ON lg.site_id = sr.old_id\n" +
                "LEFT JOIN db_relation.lot_site_line_group_relation llgr ON llgr.old_id = lg.id\n" +
                "WHERE\n" +
                "\tlg.is_del = 'F'";
        Connection connection = JdbcUtils.getConnection(Constant.db_main);
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".domain_group (" +
                    "id," +
                    "group_name," +
                    "site_id," +
                    "site_code," +
                    "create_by," +
                    "create_time," +
                    "update_by," +
                    "update_time," +
                    "sort_id," +
                    "is_enable," +
                    "remark" +
                    ") values");
            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("id"));
                String groupName = "'" + resultSet.getString("group_name") + "'";
                Long site_id = Long.parseLong(resultSet.getString("site_id"));
                String site_code = "'" + resultSet.getString("site_code") + "'";
                String create_by = resultSet.getString("create_by");
                if (create_by != null) {
                    create_by = "'" + create_by + "'";
                } else {
                    create_by = "''";
                }
                String create_time = resultSet.getString("create_time");
                if (create_time != null) {
                    create_time = "'" + create_time + "'";
                }
                String update_by = resultSet.getString("update_by");
                if (update_by != null) {
                    update_by = "'" + update_by + "'";
                } else {
                    update_by = "''";
                }
                String update_time = resultSet.getString("update_time");
                if (update_time != null) {
                    update_time = "'" + update_time + "'";
                }
                Integer sort_id = Integer.parseInt(resultSet.getString("sort_id"));
                String is_enable_str = resultSet.getString("is_enable");
                Integer is_enable;
                if (is_enable_str != null) {
                    is_enable = 1;
                } else {
                    is_enable = 0;
                }
                String remark = resultSet.getString("remark");
                if (remark != null) {
                    remark = "'" + remark + "'";
                } else {
                    remark = "''";
                }
                stringBuilder.append("(" +
                        id + "," +
                        groupName + "," +
                        site_id + "," +
                        site_code + "," +
                        create_by + "," +
                        create_time + "," +
                        update_by + "," +
                        update_time + "," +
                        sort_id + "," +
                        is_enable + "," +
                        remark +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            logger.error("操作失败" + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return stringBuilder.toString();
    }

    public static String domain1() {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlslr.new_id id,\n" +
                "\tls.site_title,\n" +
                "\tlsl.line_name,\n" +
                "  lsl.line_url domain,\n" +
                "\tsr.new_id site_id,\n" +
                "\tsr.site_code site_code,\n" +
                "\tlslgr.new_id group_id,\n" +
                "\tif(lsl.is_enable='T',1,0) is_enable,\n" +
                "\tlsl.sort_id,\n" +
                "\tlsu.login_name create_by,\n" +
                "\tlsl.create_time,\n" +
                "\tlsu2.login_name update_by,\n" +
                "\tlsl.update_time,\n" +
                "\tlsl.remark\n" +
                "FROM\n" +
                "\tdb_main.lot_site_line lsl\n" +
                "LEFT JOIN db_relation.lot_site_line_relation lslr ON lsl.ID=lslr.old_id\n" +
                "LEFT JOIN db_relation.lot_site_line_group_relation lslgr ON lslgr.old_id=lsl.group_id\n" +
                "LEFT JOIN db_relation.site_relation sr ON sr.old_id=lsl.site_id\n" +
                "LEFT JOIN db_main.lot_site ls ON ls.id=lsl.site_id\n" +
                "LEFT JOIN db_main.lot_sys_user lsu ON lsl.create_by=lsu.id\n" +
                "LEFT JOIN db_main.lot_sys_user lsu2 ON lsl.update_by=lsu2.id\n" +
                "WHERE lsl.is_del = \"F\"\n" +
                "GROUP BY lsl.line_url";
        Connection connection = JdbcUtils.getConnection(Constant.db_main);
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".domain (" +
                    "id," +
                    "site_id," +
                    "site_code," +
                    "line_name," +
                    "domain," +
                    "group_id," +
                    "proxy_url," +
                    "web_root," +
                    "create_by," +
                    "create_time," +
                    "update_by," +
                    "update_time," +
                    "sort_id," +
                    "is_enable," +
                    "is_mobile," +
                    "remark" +
                    ") values");
            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("id"));
                Long site_id = Long.parseLong(resultSet.getString("site_id"));
                String site_code = "'" + resultSet.getString("site_code") + "'";
                String line_name = "'" + resultSet.getString("line_name") + "'";
                String domain = "'" + resultSet.getString("domain") + "'";
                domainList.add(domain);
                String group_id_str = resultSet.getString("group_id");
                Long group_id = null;
                if (group_id_str != null) {
                    group_id = Long.parseLong(group_id_str);
                }
                String proxy_url = "'peach'";
                String site_title = resultSet.getString("site_title");
                String web_root = "''";
                Integer is_mobile = 0;
                if (site_title.equals("福盈彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a001_fuying/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a001_fuying/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a001_fuying/p2'";
                    }
                } else if (site_title.equals("聚彩彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a002_jucai/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a002_jucai/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a002_jucai/p2'";
                    }
                } else if (site_title.equals("皇家彩世界")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a003_huangjia/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a003_huangjia/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a003_huangjia/p2'";
                    }
                } else if (site_title.equals("环球彩票店")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'b001_huanqiu/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'b001_huanqiu/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'b001_huanqiu/desktop'";
                    }
                } else if (site_title.equals("利盈彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a006_liying/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a006_liying/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a006_liying/p2'";
                    }
                }
                String create_by = resultSet.getString("create_by");
                if (create_by != null) {
                    create_by = "'" + create_by + "'";
                } else {
                    create_by = "''";
                }
                String create_time = resultSet.getString("create_time");
                if (create_time != null) {
                    create_time = "'" + create_time + "'";
                }
                String update_by = resultSet.getString("update_by");
                if (update_by != null) {
                    update_by = "'" + update_by + "'";
                } else {
                    update_by = "''";
                }
                String update_time = resultSet.getString("update_time");
                if (update_time != null) {
                    update_time = "'" + update_time + "'";
                }
                Integer sort_id = Integer.parseInt(resultSet.getString("sort_id"));
                if (sort_id > 5) {
                    sort_id = 0;
                }
                Integer is_enable = Integer.parseInt(resultSet.getString("is_enable"));
                String remark = resultSet.getString("remark");
                if (remark != null) {
                    remark = "'" + remark + "'";
                } else {
                    remark = "''";
                }

                stringBuilder.append("(" +
                        id + "," +
                        site_id + "," +
                        site_code + "," +
                        line_name + "," +
                        domain + "," +
                        group_id + "," +
                        proxy_url + "," +
                        web_root + "," +
                        create_by + "," +
                        create_time + "," +
                        update_by + "," +
                        update_time + "," +
                        sort_id + "," +
                        is_enable + "," +
                        is_mobile + "," +
                        remark +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            logger.error("操作失败" + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return stringBuilder.toString();
    }

    public static String domain2() {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlslr.new_id id,\n" +
                "\tls.site_title,\n" +
                "\tlsl.site_line_name line_name,\n" +
                "  lsl.site_line_url domain,\n" +
                "\tsr.new_id site_id,\n" +
                "\tsr.site_code site_code,\n" +
                "\tif(lsl.is_enable='T',1,0) is_enable,\n" +
                "\tlsl.sort_id,\n" +
                "\tlsu.login_name create_by,\n" +
                "\tlsl.create_time,\n" +
                "\tlsu2.login_name update_by,\n" +
                "\tlsl.update_time,\n" +
                "\tlsl.remark\n" +
                "FROM\n" +
                "\tdb_main.lot_site_denfense lsl\n" +
                "LEFT JOIN db_relation.lot_site_denfense_relation lslr ON lsl.ID=lslr.old_id\n" +
                "LEFT JOIN db_relation.site_relation sr ON sr.old_id=lsl.site_id\n" +
                "LEFT JOIN db_main.lot_site ls ON ls.id=lsl.site_id\n" +
                "LEFT JOIN db_main.lot_sys_user lsu ON lsl.create_by=lsu.id\n" +
                "LEFT JOIN db_main.lot_sys_user lsu2 ON lsl.update_by=lsu2.id\n" +
                "WHERE lsl.is_del = \"F\"\n" +
                "GROUP BY lsl.site_line_url";
        Connection connection = JdbcUtils.getConnection(Constant.db_main);
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".domain (" +
                    "id," +
                    "site_id," +
                    "site_code," +
                    "line_name," +
                    "domain," +
                    "proxy_url," +
                    "web_root," +
                    "create_by," +
                    "create_time," +
                    "update_by," +
                    "update_time," +
                    "sort_id," +
                    "is_enable," +
                    "is_mobile," +
                    "remark" +
                    ") values");
            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("id"));
                Long site_id = Long.parseLong(resultSet.getString("site_id"));
                String site_code = "'" + resultSet.getString("site_code") + "'";
                String line_name = "'" + resultSet.getString("line_name") + "'";
                String domain = "'" + resultSet.getString("domain") + "'";
                Boolean domainExist = false;
                for (String domain1 : domainList) {
                    if (domain1.equals(domain)) {
                        domainExist = true;
                        break;
                    }
                }
                if (domainExist) {
                    continue;
                }
                String site_title = resultSet.getString("site_title");
                String web_root = "''";
                Integer is_mobile = 0;
                if (site_title.equals("福盈彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a001_fuying/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a001_fuying/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a001_fuying/p2'";
                    }
                } else if (site_title.equals("聚彩彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a002_jucai/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a002_jucai/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a002_jucai/p2'";
                    }
                } else if (site_title.equals("皇家彩世界")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a003_huangjia/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a003_huangjia/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a003_huangjia/p2'";
                    }
                } else if (site_title.equals("环球彩票店")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'b001_huanqiu/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'b001_huanqiu/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'b001_huanqiu/desktop'";
                    }
                } else if (site_title.equals("利盈彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a006_liying/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a006_liying/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a006_liying/p2'";
                    }
                }
                String proxy_url = "'peach'";
                String create_by = resultSet.getString("create_by");
                if (create_by != null) {
                    create_by = "'" + create_by + "'";
                } else {
                    create_by = "''";
                }
                String create_time = resultSet.getString("create_time");
                if (create_time != null) {
                    create_time = "'" + create_time + "'";
                }
                String update_by = resultSet.getString("update_by");
                if (update_by != null) {
                    update_by = "'" + update_by + "'";
                } else {
                    update_by = "''";
                }
                String update_time = resultSet.getString("update_time");
                if (update_time != null) {
                    update_time = "'" + update_time + "'";
                }
                Integer sort_id = Integer.parseInt(resultSet.getString("sort_id"));
                if (sort_id > 5) {
                    sort_id = 0;
                }
                Integer is_enable = Integer.parseInt(resultSet.getString("is_enable"));
                String remark = resultSet.getString("remark");
                if (remark != null) {
                    remark = "'" + remark + "'";
                } else {
                    remark = "''";
                }

                stringBuilder.append("(" +
                        id + "," +
                        site_id + "," +
                        site_code + "," +
                        line_name + "," +
                        domain + "," +
                        proxy_url + "," +
                        web_root + "," +
                        create_by + "," +
                        create_time + "," +
                        update_by + "," +
                        update_time + "," +
                        sort_id + "," +
                        is_enable + "," +
                        is_mobile + "," +
                        remark +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            logger.error("操作失败" + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return stringBuilder.toString();
    }

    public static String resource() {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlsrr.new_id id,\n" +
                "\tlsr.resource_name resource_name,\n" +
                "\tlsr.resource_type resource_type,\n" +
                "\tlsrrp.new_id pid,\n" +
                "\tlsr.url url,\n" +
                "\tlsr.rel rel,\n" +
                "\tlsr.create_time create_time,\n" +
                "\tlsr.create_by create_by,\n" +
                "\tlsr.update_time update_time,\n" +
                "\tlsr.update_by update_by,\n" +
                "\tlsr.is_auto_refresh is_auto_refresh,\n" +
                "\tlsr.module module,\n" +
                "\tlsr.privilege privilege,\n" +
                "\tlsr.sort_id sort_id,\n" +
                "\tlsr.remark remark\n" +
                "FROM\n" +
                "\tlot_sys_resource lsr\n" +
                "LEFT JOIN db_relation.lot_sys_resource_relation lsrr ON lsrr.old_id=lsr.id\n" +
                "LEFT JOIN db_relation.lot_sys_resource_relation lsrrp ON lsrrp.old_id=lsr.pid\n" +
                "WHERE\n" +
                "\tis_del = 'F'";
        Connection connection = JdbcUtils.getConnection(Constant.db_main);
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".resource (" +
                    "id," +
                    "pid," +
                    "resource_name," +
                    "resource_type," +
                    "sort_id," +
                    "url," +
                    "remark," +
                    "module," +
                    "privilege," +
                    "rel," +
                    "is_auto_refresh" +
                    ") values");
            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("id"));
                Long pid;
                if (resultSet.getString("pid") != null) {
                    pid = Long.parseLong(resultSet.getString("pid"));
                } else {
                    pid = 0L;
                }
                String resource_name = "'" + resultSet.getString("resource_name") + "'";
                String resource_type_str = resultSet.getString("resource_type");
                Integer resource_type;
                if (resource_type_str.equals("BUTTON")) {
                    resource_type = 1;
                } else {
                    resource_type = 0;
                }
                String sort_id_str = resultSet.getString("sort_id");
                Integer sort_id;
                if (sort_id_str != null) {
                    sort_id = Integer.valueOf(sort_id_str);
                } else {
                    sort_id = 0;
                }
                String url = "'" + resultSet.getString("url") + "'";
                String remark = "'" + resultSet.getString("remark") + "'";
                String module = "'" + resultSet.getString("module") + "'";
                String privilege = "'" + resultSet.getString("privilege") + "'";
                String rel = "'" + resultSet.getString("rel") + "'";
                String is_auto_refresh_str = resultSet.getString("is_auto_refresh");
                Integer is_auto_refresh;
                if (is_auto_refresh_str.equals("F")) {
                    is_auto_refresh = 0;
                } else {
                    is_auto_refresh = 1;
                }

                stringBuilder.append("(" + id + "," +
                        pid + "," +
                        resource_name + "," +
                        resource_type + "," +
                        sort_id + "," +
                        url + "," +
                        remark + "," +
                        module + "," +
                        privilege + "," +
                        rel + "," +
                        is_auto_refresh +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            logger.error("操作失败" + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return stringBuilder.toString();
    }

    //
//    public static String resourcePid(){
//        StringBuilder stringBuilder = new StringBuilder();
//        String sql = "SELECT\n" +
//                "\tr1.id id,\n" +
//                "\tr2.old_pid old_pid\n" +
//                "FROM\n" +
//                "\tresource r1\n" +
//                "LEFT JOIN resource r2 ON r2.old_pid = r1.old_id";
//        Connection connection = JdbcUtils.getConnection();
//        try {
//            //传递 SQL 语句
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//            StringBuilder oldPids = new StringBuilder();
//            stringBuilder.append("update resource set pid = case old_pid ");
//
//            while (resultSet.next()) {
//                String old_pid = resultSet.getString("old_pid");
//                if(old_pid == null){
//                    continue;
//                }
//                String oldPid = "'" + old_pid + "',";
//                oldPids.append(oldPid);
//                stringBuilder.append(" when ");
//                stringBuilder.append("'"+old_pid+"'");
//                stringBuilder.append(" THEN ");
//                Long id = Long.parseLong(resultSet.getString("id"));
//                stringBuilder.append(id);
//            }
//            stringBuilder.append(" END WHERE old_pid in ( "+oldPids+");");
//            stringBuilder.replace(stringBuilder.length()-3,stringBuilder.length()-2,"");
//            statement.executeUpdate(stringBuilder.toString());
//            resultSet.close();
//            statement.close();
//            return stringBuilder.toString();
//        } catch (SQLException e) {
//            logger.error("操作失败"+e.getMessage());
//        } finally {
//            JdbcUtils.closeConnection(connection);
//        }
//        return stringBuilder.toString();
//    }
//
    public static String role() {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlsrr.new_id id,\n" +
                "\tlr.role_name,\n" +
                "\tlr.remark remark,\n" +
                "\tsr.new_id site_id,\n" +
                "\tsr.site_code site_code,\n" +
                "\tlr.create_time create_time,\n" +
                "\tlu.login_name create_by,\n" +
                "\tluu.login_name update_by,\n" +
                "\tlr.update_time update_time\n" +
                "\t\n" +
                "FROM\n" +
                "\tlot_sys_role lr\n" +
                "LEFT JOIN db_relation.lot_sys_role_relation lsrr ON lsrr.old_id=lr.id \n" +
                "LEFT JOIN lot_sys_user lu ON lu.id = lr.create_by \n" +
                "LEFT JOIN lot_sys_user luu ON luu.id = lr.update_by\n" +
                "LEFT JOIN db_relation.site_relation sr ON sr.old_id=lr.site_id\n" +
                "WHERE lr.is_del = 'F'";
        Connection connection = JdbcUtils.getConnection(Constant.db_main);
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".role (" +
                    "id," +
                    "role_name," +
                    "remark," +
                    "site_id," +
                    "site_code," +
                    "create_by," +
                    "create_time," +
                    "update_by," +
                    "update_time" +
                    ") values");
            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("id"));
                String role_name = "'" + resultSet.getString("role_name") + "'";
                String remark = "'" + resultSet.getString("remark") + "'";
                String site_id_str = resultSet.getString("site_id");
                Long site_id = 0L;
                String site_code = "'" + resultSet.getString("site_code") + "'";
                if (site_id_str == null) {
                    site_id = 0L;
                    site_code = "'lotmall-0'";
                } else if ("sys".equals(site_id_str) || "site".equals(site_id_str)) {
                    site_id = 0L;
                    site_code = "'lotmall-0'";
                } else {
                    site_id = Long.parseLong(site_id_str);
                }
                String create_by = resultSet.getString("create_by");
                if (create_by != null) {
                    create_by = "'" + create_by + "'";
                } else {
                    create_by = "''";
                }
                String create_time = resultSet.getString("create_time");
                if (create_time != null) {
                    create_time = "'" + create_time + "'";
                }
                String update_by = resultSet.getString("update_by");
                if (update_by != null) {
                    update_by = "'" + update_by + "'";
                } else {
                    update_by = "''";
                }
                String update_time = resultSet.getString("update_time");
                if (update_time != null) {
                    update_time = "'" + update_time + "'";
                }

                stringBuilder.append("(" +
                        id + "," +
                        role_name + "," +
                        remark + "," +
                        site_id + "," +
                        site_code + "," +
                        create_by + "," +
                        create_time + "," +
                        update_by + "," +
                        update_time +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            logger.error("操作失败" + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return stringBuilder.toString();
    }

    public static String roleResource() {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlsrr2.new_id role_id,\n" +
                "\tlsrr3.new_id resource_id\n" +
                "FROM\n" +
                "\tlot_sys_role_resource lsrr\n" +
                "JOIN db_relation.lot_sys_role_relation lsrr2 ON lsrr2.old_id=lsrr.role_id\n" +
                "JOIN db_relation.lot_sys_resource_relation lsrr3 ON lsrr3.old_id=lsrr.resource_id";
        Connection connection = JdbcUtils.getConnection(Constant.db_main);
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".role_resource (" +
                    "id," +
                    "role_id," +
                    "resource_id" +
                    ") values");
            while (resultSet.next()) {
                Long id = IdWorker.getId();
                Long role_id = Long.parseLong(resultSet.getString("role_id"));
                Long resource_id = Long.parseLong(resultSet.getString("resource_id"));

                stringBuilder.append("(" + id + "," +
                        role_id + "," +
                        resource_id +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return stringBuilder.toString();
    }

    public static String addAdminRoleResource1(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlsrr.new_id role_id\n" +
                "FROM\n" +
                "\tdb_main.lot_sys_role lsr\n" +
                "LEFT JOIN db_relation.lot_sys_role_relation lsrr ON lsr.id = lsrr.old_id\n" +
                "WHERE\n" +
                "\trole_name = \"总后台维护权限\"";
        try {
            //传递 SQL 语句
            resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".role_resource (" +
                    "id," +
                    "role_id," +
                    "resource_id" +
                    ") values");
            List<Long> longList = Arrays.stream(Constant.resourceIds.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            resultSet.next();
            Long role_id = Long.parseLong(resultSet.getString("role_id"));
            for(Long resource_id:longList) {
                Long id = IdWorker.getId();
                stringBuilder.append("(" + id + "," +
                        role_id + "," +
                        resource_id +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error(e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String addAdminRoleResource2(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlsrr.new_id role_id\n" +
                "FROM\n" +
                "\tdb_main.lot_sys_role lsr\n" +
                "LEFT JOIN db_relation.lot_sys_role_relation lsrr ON lsr.id = lsrr.old_id\n" +
                "WHERE\n" +
                "\trole_name = \"系统管理员（默认）\"";
        try {
            //传递 SQL 语句
            resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".role_resource (" +
                    "id," +
                    "role_id," +
                    "resource_id" +
                    ") values");
            List<Long> longList = Arrays.stream(Constant.resourceIds.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            resultSet.next();
            Long role_id = Long.parseLong(resultSet.getString("role_id"));
            for(Long resource_id:longList) {
                Long id = IdWorker.getId();
                stringBuilder.append("(" + id + "," +
                        role_id + "," +
                        resource_id +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error(e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String addAdminRoleResource3(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlsrr.new_id role_id\n" +
                "FROM\n" +
                "\tdb_main.lot_sys_role lsr\n" +
                "LEFT JOIN db_relation.lot_sys_role_relation lsrr ON lsr.id = lsrr.old_id\n" +
                "WHERE\n" +
                "\trole_name = \"站点管理员(默认)\"";
        try {
            //传递 SQL 语句
            resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".role_resource (" +
                    "id," +
                    "role_id," +
                    "resource_id" +
                    ") values");
            List<Long> longList = Arrays.stream(Constant.defaultResourceIds.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            resultSet.next();
            Long role_id = Long.parseLong(resultSet.getString("role_id"));
            for(Long resource_id:longList) {
                Long id = IdWorker.getId();
                stringBuilder.append("(" + id + "," +
                        role_id + "," +
                        resource_id +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error(e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String userRole() {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tur.new_id user_id,\n" +
                "\tlsrr.new_id role_id\n" +
                "FROM\n" +
                "\tlot_sys_user_role lsur\n" +
                "JOIN db_relation.sys_user_relation ur ON ur.old_id=lsur.user_id\n" +
                "JOIN db_relation.lot_sys_role_relation lsrr ON lsrr.old_id=lsur.role_id";
        Connection connection = JdbcUtils.getConnection(Constant.db_main);
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".user_role (" +
                    "id," +
                    "user_id," +
                    "role_id" +
                    ") values");
            while (resultSet.next()) {
                Long id = IdWorker.getId();
                Long user_id = Long.parseLong(resultSet.getString("user_id"));
                Long role_id = Long.parseLong(resultSet.getString("role_id"));

                stringBuilder.append("(" + id + "," +
                        user_id + "," +
                        role_id +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            logger.error("操作失败" + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return stringBuilder.toString();
    }

    public static String ipLimit() {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT \n" +
                "\tlsil.id id,\n" +
                "\tlsil.start_ip ip_start,\n" +
                "\tlsil.end_ip ip_end,\n" +
                "\tlsil.create_time,\n" +
                "\tsur.login_name create_by,\n" +
                "\tlsil.update_time,\n" +
                "\tsurr.login_name update_by,\n" +
                "\tsr.new_id site_id,\n" +
                "\tsr.site_code site_code,\n" +
                "\tlsil.expire_time final_time,\n" +
                "\tlsil.remark,\n" +
                "\tlsil.operator type,\n" +
                "\tlsil.area_limit country_name,\n" +
                "\tlsil.limit_time\n" +
                "FROM lot_site_ip_limit lsil\n" +
                "LEFT JOIN lot_sys_user sur ON lsil.create_by=sur.id\n" +
                "LEFT JOIN lot_sys_user surr ON lsil.update_by=surr.id\n" +
                "JOIN db_relation.site_relation sr ON lsil.site_id=sr.old_id\n" +
                "WHERE lsil.is_del = \"F\"";
        Connection connection = JdbcUtils.getConnection(Constant.db_main);
        try {
            //传递 SQL 语句
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".ip_limit (" +
                    "id," +
                    "ip_start," +
                    "ip_end," +
                    "type," +
                    "country_name," +
                    "site_id," +
                    "site_code," +
                    "final_time," +
                    "create_by," +
                    "create_time," +
                    "update_by," +
                    "update_time," +
                    "limit_time," +
                    "remark" +
                    ") values");
            while (resultSet.next()) {
                Long id = IdWorker.getId();
                String ip_start = "'" + resultSet.getString("ip_start") + "'";
                String ip_end = "'" + resultSet.getString("ip_end") + "'";
                String operate = resultSet.getString("type");
                Integer type = 0;
                Integer oldType = Integer.valueOf(operate);
                if (oldType == 0) {
                    type = 11;
                } else if (oldType == 1) {
                    type = 12;
                } else if (oldType == 2) {
                    type = 10;
                } else if (oldType == 3) {
                    type = 20;
                }
                String country_name_str = resultSet.getString("country_name");
                String country_name = "''";
                if (country_name_str != null) {
                    country_name = "'" + country_name_str + "'";
                }
                String site_id_str = resultSet.getString("site_id");
                Long site_id = 0L;
                String site_code = "'" + resultSet.getString("site_code") + "'";
                if (site_id_str == null) {
                    site_id = null;
                    site_code = "''";
                } else {
                    site_id = Long.parseLong(site_id_str);
                }
                String finale_time = "'" + resultSet.getString("final_time") + "'";
                String create_by = resultSet.getString("create_by");
                if (create_by != null) {
                    create_by = "'" + create_by + "'";
                } else {
                    create_by = "''";
                }
                String create_time = resultSet.getString("create_time");
                if (create_time != null) {
                    create_time = "'" + create_time + "'";
                }
                String update_by = resultSet.getString("update_by");
                if (update_by != null) {
                    update_by = "'" + update_by + "'";
                } else {
                    update_by = "''";
                }
                String update_time = resultSet.getString("update_time");
                if (update_time != null) {
                    update_time = "'" + update_time + "'";
                }
                String limit_time = "'" + resultSet.getString("limit_time") + "'";
                String remark = "'" + resultSet.getString("remark") + "'";

                stringBuilder.append("(" + id + "," +
                        ip_start + "," +
                        ip_end + "," +
                        type + "," +
                        country_name + "," +
                        site_id + "," +
                        site_code + "," +
                        finale_time + "," +
                        create_by + "," +
                        create_time + "," +
                        update_by + "," +
                        update_time + "," +
                        limit_time + "," +
                        remark +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            logger.error("操作失败" + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return stringBuilder.toString();
    }

    /////////////////////////////////////////////////////////////
    public static String domainGroup(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tllgr.new_id id,\n" +
                "\tsr.new_id site_id,\n" +
                "\tsr.site_code site_code,\n" +
                "\tlg.group_name group_name,\n" +
                "\tlg.sort_id sort_id,\n" +
                "\tlg.is_enable is_enable,\n" +
                "\tlu.login_name create_by,\n" +
                "\tluu.login_name update_by,\n" +
                "\tlg.create_time create_time,\n" +
                "\tlg.update_time update_time,\n" +
                "\tlg.remark remark\n" +
                "FROM\n" +
                "\tdb_main.lot_site_line_group lg\n" +
                "LEFT JOIN db_main.lot_sys_user lu ON lg.create_by = lu.id\n" +
                "LEFT JOIN db_main.lot_sys_user luu ON lg.update_by = luu.id\n" +
                "LEFT JOIN db_relation.site_relation sr ON lg.site_id = sr.old_id\n" +
                "LEFT JOIN db_relation.lot_site_line_group_relation llgr ON llgr.old_id = lg.id\n" +
                "WHERE\n" +
                "\tlg.is_del = 'F'";
        try {
            //传递 SQL 语句
            resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".domain_group (" +
                    "id," +
                    "group_name," +
                    "site_id," +
                    "site_code," +
                    "create_by," +
                    "create_time," +
                    "update_by," +
                    "update_time," +
                    "sort_id," +
                    "is_enable," +
                    "remark" +
                    ") values");
            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("id"));
                String groupName = "'" + resultSet.getString("group_name") + "'";
                Long site_id = Long.parseLong(resultSet.getString("site_id"));
                String site_code = "'" + resultSet.getString("site_code") + "'";
                String create_by = resultSet.getString("create_by");
                if (create_by != null) {
                    create_by = "'" + create_by + "'";
                } else {
                    create_by = "''";
                }
                String create_time = resultSet.getString("create_time");
                if (create_time != null) {
                    create_time = "'" + create_time + "'";
                }
                String update_by = resultSet.getString("update_by");
                if (update_by != null) {
                    update_by = "'" + update_by + "'";
                } else {
                    update_by = "''";
                }
                String update_time = resultSet.getString("update_time");
                if (update_time != null) {
                    update_time = "'" + update_time + "'";
                }
                Integer sort_id = Integer.parseInt(resultSet.getString("sort_id"));
                String is_enable_str = resultSet.getString("is_enable");
                Integer is_enable;
                if (is_enable_str != null) {
                    is_enable = 1;
                } else {
                    is_enable = 0;
                }
                String remark = resultSet.getString("remark");
                if (remark != null) {
                    remark = "'" + remark + "'";
                } else {
                    remark = "''";
                }
                stringBuilder.append("(" +
                        id + "," +
                        groupName + "," +
                        site_id + "," +
                        site_code + "," +
                        create_by + "," +
                        create_time + "," +
                        update_by + "," +
                        update_time + "," +
                        sort_id + "," +
                        is_enable + "," +
                        remark +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error("操作失败" + e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String domain1(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlslr.new_id id,\n" +
                "\tls.site_title,\n" +
                "\tlsl.line_name,\n" +
                "  lsl.line_url domain,\n" +
                "\tsr.new_id site_id,\n" +
                "\tsr.site_code site_code,\n" +
                "\tlslgr.new_id group_id,\n" +
                "\tif(lsl.is_enable='T',1,0) is_enable,\n" +
                "\tlsl.sort_id,\n" +
                "\tlsu.login_name create_by,\n" +
                "\tlsl.create_time,\n" +
                "\tlsu2.login_name update_by,\n" +
                "\tlsl.update_time,\n" +
                "\tlsl.remark\n" +
                "FROM\n" +
                "\tdb_main.lot_site_line lsl\n" +
                "LEFT JOIN db_relation.lot_site_line_relation lslr ON lsl.ID=lslr.old_id\n" +
                "LEFT JOIN db_relation.lot_site_line_group_relation lslgr ON lslgr.old_id=lsl.group_id\n" +
                "LEFT JOIN db_relation.site_relation sr ON sr.old_id=lsl.site_id\n" +
                "LEFT JOIN db_main.lot_site ls ON ls.id=lsl.site_id\n" +
                "LEFT JOIN db_main.lot_sys_user lsu ON lsl.create_by=lsu.id\n" +
                "LEFT JOIN db_main.lot_sys_user lsu2 ON lsl.update_by=lsu2.id\n" +
                "WHERE lsl.is_del = \"F\"\n" +
                "GROUP BY lsl.line_url";
        try {
            //传递 SQL 语句
            resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".domain (" +
                    "id," +
                    "site_id," +
                    "site_code," +
                    "line_name," +
                    "domain," +
                    "group_id," +
                    "proxy_url," +
                    "web_root," +
                    "create_by," +
                    "create_time," +
                    "update_by," +
                    "update_time," +
                    "sort_id," +
                    "is_enable," +
                    "is_mobile," +
                    "remark" +
                    ") values");
            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("id"));
                Long site_id = Long.parseLong(resultSet.getString("site_id"));
                String site_code = "'" + resultSet.getString("site_code") + "'";
                String line_name = "'" + resultSet.getString("line_name") + "'";
                String domain = "'" + resultSet.getString("domain") + "'";
                domainList.add(domain);
                String group_id_str = resultSet.getString("group_id");
                Long group_id = null;
                if (group_id_str != null) {
                    group_id = Long.parseLong(group_id_str);
                }
                String proxy_url = "'peach'";
                String site_title = resultSet.getString("site_title");
                String web_root = "''";
                Integer is_mobile = 0;
                if (site_title.equals("福盈彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a001_fuying/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a001_fuying/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a001_fuying/p2'";
                    }
                } else if (site_title.equals("聚彩彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a002_jucai/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a002_jucai/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a002_jucai/p2'";
                    }
                } else if (site_title.equals("皇家彩世界")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a003_huangjia/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a003_huangjia/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a003_huangjia/p2'";
                    }
                } else if (site_title.equals("环球彩票店")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'b001_huanqiu/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'b001_huanqiu/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'b001_huanqiu/desktop'";
                    }
                } else if (site_title.equals("利盈彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a006_liying/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a006_liying/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a006_liying/p2'";
                    }
                }
                String create_by = resultSet.getString("create_by");
                if (create_by != null) {
                    create_by = "'" + create_by + "'";
                } else {
                    create_by = "''";
                }
                String create_time = resultSet.getString("create_time");
                if (create_time != null) {
                    create_time = "'" + create_time + "'";
                }
                String update_by = resultSet.getString("update_by");
                if (update_by != null) {
                    update_by = "'" + update_by + "'";
                } else {
                    update_by = "''";
                }
                String update_time = resultSet.getString("update_time");
                if (update_time != null) {
                    update_time = "'" + update_time + "'";
                }
                Integer sort_id = Integer.parseInt(resultSet.getString("sort_id"));
                if (sort_id > 5) {
                    sort_id = 0;
                }
                Integer is_enable = Integer.parseInt(resultSet.getString("is_enable"));
                String remark = resultSet.getString("remark");
                if (remark != null) {
                    remark = "'" + remark + "'";
                } else {
                    remark = "''";
                }

                stringBuilder.append("(" +
                        id + "," +
                        site_id + "," +
                        site_code + "," +
                        line_name + "," +
                        domain + "," +
                        group_id + "," +
                        proxy_url + "," +
                        web_root + "," +
                        create_by + "," +
                        create_time + "," +
                        update_by + "," +
                        update_time + "," +
                        sort_id + "," +
                        is_enable + "," +
                        is_mobile + "," +
                        remark +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error("操作失败" + e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String domain2(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlslr.new_id id,\n" +
                "\tls.site_title,\n" +
                "\tlsl.site_line_name line_name,\n" +
                "  lsl.site_line_url domain,\n" +
                "\tsr.new_id site_id,\n" +
                "\tsr.site_code site_code,\n" +
                "\tif(lsl.is_enable='T',1,0) is_enable,\n" +
                "\tlsl.sort_id,\n" +
                "\tlsu.login_name create_by,\n" +
                "\tlsl.create_time,\n" +
                "\tlsu2.login_name update_by,\n" +
                "\tlsl.update_time,\n" +
                "\tlsl.remark\n" +
                "FROM\n" +
                "\tdb_main.lot_site_denfense lsl\n" +
                "LEFT JOIN db_relation.lot_site_denfense_relation lslr ON lsl.ID=lslr.old_id\n" +
                "LEFT JOIN db_relation.site_relation sr ON sr.old_id=lsl.site_id\n" +
                "LEFT JOIN db_main.lot_site ls ON ls.id=lsl.site_id\n" +
                "LEFT JOIN db_main.lot_sys_user lsu ON lsl.create_by=lsu.id\n" +
                "LEFT JOIN db_main.lot_sys_user lsu2 ON lsl.update_by=lsu2.id\n" +
                "WHERE lsl.is_del = \"F\"\n" +
                "GROUP BY lsl.site_line_url";
        try {
            //传递 SQL 语句
            resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".domain (" +
                    "id," +
                    "site_id," +
                    "site_code," +
                    "line_name," +
                    "domain," +
                    "proxy_url," +
                    "web_root," +
                    "create_by," +
                    "create_time," +
                    "update_by," +
                    "update_time," +
                    "sort_id," +
                    "is_enable," +
                    "is_mobile," +
                    "remark" +
                    ") values");
            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("id"));
                Long site_id = Long.parseLong(resultSet.getString("site_id"));
                String site_code = "'" + resultSet.getString("site_code") + "'";
                String line_name = "'" + resultSet.getString("line_name") + "'";
                String domain = "'" + resultSet.getString("domain") + "'";
                Boolean domainExist = false;
                for (String domain1 : domainList) {
                    if (domain1.equals(domain)) {
                        domainExist = true;
                        break;
                    }
                }
                if (domainExist) {
                    continue;
                }
                String site_title = resultSet.getString("site_title");
                String web_root = "''";
                Integer is_mobile = 0;
                if (site_title.equals("福盈彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a001_fuying/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a001_fuying/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a001_fuying/p2'";
                    }
                } else if (site_title.equals("聚彩彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a002_jucai/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a002_jucai/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a002_jucai/p2'";
                    }
                } else if (site_title.equals("皇家彩世界")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a003_huangjia/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a003_huangjia/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a003_huangjia/p2'";
                    }
                } else if (site_title.equals("环球彩票店")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'b001_huanqiu/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'b001_huanqiu/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'b001_huanqiu/desktop'";
                    }
                } else if (site_title.equals("利盈彩票")) {
                    if (domain.contains("//m")
                            || domain.contains("//wap.")) {
                        web_root = "'a006_liying/m3'";
                        is_mobile = 1;
                    } else if (domain.contains("//app")
                            || domain.contains("//103")
                            || domain.contains("//104.")) {
                        web_root = "'a006_liying/m3'";
                        is_mobile = 1;
                    } else {
                        web_root = "'a006_liying/p2'";
                    }
                }
                String proxy_url = "'peach'";
                String create_by = resultSet.getString("create_by");
                if (create_by != null) {
                    create_by = "'" + create_by + "'";
                } else {
                    create_by = "''";
                }
                String create_time = resultSet.getString("create_time");
                if (create_time != null) {
                    create_time = "'" + create_time + "'";
                }
                String update_by = resultSet.getString("update_by");
                if (update_by != null) {
                    update_by = "'" + update_by + "'";
                } else {
                    update_by = "''";
                }
                String update_time = resultSet.getString("update_time");
                if (update_time != null) {
                    update_time = "'" + update_time + "'";
                }
                Integer sort_id = Integer.parseInt(resultSet.getString("sort_id"));
                if (sort_id > 5) {
                    sort_id = 0;
                }
                Integer is_enable = Integer.parseInt(resultSet.getString("is_enable"));
                String remark = resultSet.getString("remark");
                if (remark != null) {
                    remark = "'" + remark + "'";
                } else {
                    remark = "''";
                }

                stringBuilder.append("(" +
                        id + "," +
                        site_id + "," +
                        site_code + "," +
                        line_name + "," +
                        domain + "," +
                        proxy_url + "," +
                        web_root + "," +
                        create_by + "," +
                        create_time + "," +
                        update_by + "," +
                        update_time + "," +
                        sort_id + "," +
                        is_enable + "," +
                        is_mobile + "," +
                        remark +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error("操作失败" + e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String resource(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlsrr.new_id id,\n" +
                "\tlsr.resource_name resource_name,\n" +
                "\tlsr.resource_type resource_type,\n" +
                "\tlsrrp.new_id pid,\n" +
                "\tlsr.url url,\n" +
                "\tlsr.rel rel,\n" +
                "\tlsr.create_time create_time,\n" +
                "\tlsr.create_by create_by,\n" +
                "\tlsr.update_time update_time,\n" +
                "\tlsr.update_by update_by,\n" +
                "\tlsr.is_auto_refresh is_auto_refresh,\n" +
                "\tlsr.module module,\n" +
                "\tlsr.privilege privilege,\n" +
                "\tlsr.sort_id sort_id,\n" +
                "\tlsr.remark remark\n" +
                "FROM\n" +
                "\tdb_main.lot_sys_resource lsr\n" +
                "LEFT JOIN db_relation.lot_sys_resource_relation lsrr ON lsrr.old_id=lsr.id\n" +
                "LEFT JOIN db_relation.lot_sys_resource_relation lsrrp ON lsrrp.old_id=lsr.pid\n" +
                "WHERE\n" +
                "\tis_del = 'F'";
        try {
            //传递 SQL 语句
            resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".resource (" +
                    "id," +
                    "pid," +
                    "resource_name," +
                    "resource_type," +
                    "sort_id," +
                    "url," +
                    "remark," +
                    "module," +
                    "privilege," +
                    "rel," +
                    "is_auto_refresh" +
                    ") values");
            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("id"));
                Long pid;
                if (resultSet.getString("pid") != null) {
                    pid = Long.parseLong(resultSet.getString("pid"));
                } else {
                    pid = 0L;
                }
                String resource_name = "'" + resultSet.getString("resource_name") + "'";
                String resource_type_str = resultSet.getString("resource_type");
                Integer resource_type;
                if (resource_type_str.equals("BUTTON")) {
                    resource_type = 1;
                } else {
                    resource_type = 0;
                }
                String sort_id_str = resultSet.getString("sort_id");
                Integer sort_id;
                if (sort_id_str != null) {
                    sort_id = Integer.valueOf(sort_id_str);
                } else {
                    sort_id = 0;
                }
                String url = "'" + resultSet.getString("url") + "'";
                String remark = "'" + resultSet.getString("remark") + "'";
                String module = "'" + resultSet.getString("module") + "'";
                String privilege = "'" + resultSet.getString("privilege") + "'";
                String rel = "'" + resultSet.getString("rel") + "'";
                String is_auto_refresh_str = resultSet.getString("is_auto_refresh");
                Integer is_auto_refresh;
                if (is_auto_refresh_str.equals("F")) {
                    is_auto_refresh = 0;
                } else {
                    is_auto_refresh = 1;
                }

                stringBuilder.append("(" + id + "," +
                        pid + "," +
                        resource_name + "," +
                        resource_type + "," +
                        sort_id + "," +
                        url + "," +
                        remark + "," +
                        module + "," +
                        privilege + "," +
                        rel + "," +
                        is_auto_refresh +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error("操作失败" + e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String role(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlsrr.new_id id,\n" +
                "\tlr.role_name,\n" +
                "\tlr.remark remark,\n" +
                "\tsr.new_id site_id,\n" +
                "\tsr.site_code site_code,\n" +
                "\tlr.create_time create_time,\n" +
                "\tlu.login_name create_by,\n" +
                "\tluu.login_name update_by,\n" +
                "\tlr.update_time update_time\n" +
                "\t\n" +
                "FROM\n" +
                "\tdb_main.lot_sys_role lr\n" +
                "LEFT JOIN db_relation.lot_sys_role_relation lsrr ON lsrr.old_id=lr.id \n" +
                "LEFT JOIN db_main.lot_sys_user lu ON lu.id = lr.create_by \n" +
                "LEFT JOIN db_main.lot_sys_user luu ON luu.id = lr.update_by\n" +
                "LEFT JOIN db_relation.site_relation sr ON sr.old_id=lr.site_id\n" +
                "WHERE lr.is_del = 'F'";
        try {
            //传递 SQL 语句
            resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".role (" +
                    "id," +
                    "role_name," +
                    "remark," +
                    "site_id," +
                    "site_code," +
                    "create_by," +
                    "create_time," +
                    "update_by," +
                    "update_time" +
                    ") values");
            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("id"));
                String role_name = "'" + resultSet.getString("role_name") + "'";
                String remark = "'" + resultSet.getString("remark") + "'";
                String site_id_str = resultSet.getString("site_id");
                Long site_id = 0L;
                String site_code = "'" + resultSet.getString("site_code") + "'";
                if (site_id_str == null) {
                    site_id = 0L;
                    site_code = "'lotmall-0'";
                } else if ("sys".equals(site_id_str) || "site".equals(site_id_str)) {
                    site_id = 0L;
                    site_code = "'lotmall-0'";
                } else {
                    site_id = Long.parseLong(site_id_str);
                }
                String create_by = resultSet.getString("create_by");
                if (create_by != null) {
                    create_by = "'" + create_by + "'";
                } else {
                    create_by = "''";
                }
                String create_time = resultSet.getString("create_time");
                if (create_time != null) {
                    create_time = "'" + create_time + "'";
                }
                String update_by = resultSet.getString("update_by");
                if (update_by != null) {
                    update_by = "'" + update_by + "'";
                } else {
                    update_by = "''";
                }
                String update_time = resultSet.getString("update_time");
                if (update_time != null) {
                    update_time = "'" + update_time + "'";
                }

                stringBuilder.append("(" +
                        id + "," +
                        role_name + "," +
                        remark + "," +
                        site_id + "," +
                        site_code + "," +
                        create_by + "," +
                        create_time + "," +
                        update_by + "," +
                        update_time +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error("操作失败" + e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String roleResource(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tlsrr2.new_id role_id,\n" +
                "\tlsrr3.new_id resource_id\n" +
                "FROM\n" +
                "\tdb_main.lot_sys_role_resource lsrr\n" +
                "JOIN db_relation.lot_sys_role_relation lsrr2 ON lsrr2.old_id=lsrr.role_id\n" +
                "JOIN db_relation.lot_sys_resource_relation lsrr3 ON lsrr3.old_id=lsrr.resource_id";
        try {
            //传递 SQL 语句
            resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".role_resource (" +
                    "id," +
                    "role_id," +
                    "resource_id" +
                    ") values");
            while (resultSet.next()) {
                Long id = IdWorker.getId();
                Long role_id = Long.parseLong(resultSet.getString("role_id"));
                Long resource_id = Long.parseLong(resultSet.getString("resource_id"));

                stringBuilder.append("(" + id + "," +
                        role_id + "," +
                        resource_id +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error(e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String userRole(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT\n" +
                "\tur.new_id user_id,\n" +
                "\tlsrr.new_id role_id\n" +
                "FROM\n" +
                "\tdb_main.lot_sys_user_role lsur\n" +
                "JOIN db_relation.sys_user_relation ur ON ur.old_id=lsur.user_id\n" +
                "JOIN db_relation.lot_sys_role_relation lsrr ON lsrr.old_id=lsur.role_id";
        try {
            //传递 SQL 语句
            resultSet = statement.executeQuery(sql);
            stringBuilder.append("insert into " + Constant.db_platform + ".user_role (" +
                    "id," +
                    "user_id," +
                    "role_id" +
                    ") values");
            while (resultSet.next()) {
                Long id = IdWorker.getId();
                Long user_id = Long.parseLong(resultSet.getString("user_id"));
                Long role_id = Long.parseLong(resultSet.getString("role_id"));

                stringBuilder.append("(" + id + "," +
                        user_id + "," +
                        role_id +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error("操作失败" + e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String ipLimit(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT \n" +
                "\tlsil.id id,\n" +
                "\tlsil.start_ip ip_start,\n" +
                "\tlsil.end_ip ip_end,\n" +
                "\tlsil.create_time,\n" +
                "\tsur.login_name create_by,\n" +
                "\tlsil.update_time,\n" +
                "\tsurr.login_name update_by,\n" +
                "\tsr.new_id site_id,\n" +
                "\tsr.site_code site_code,\n" +
                "\tlsil.expire_time final_time,\n" +
                "\tlsil.remark,\n" +
                "\tlsil.operator type,\n" +
                "\tlsil.area_limit country_name,\n" +
                "\tlsil.limit_time\n" +
                "FROM db_main.lot_site_ip_limit lsil\n" +
                "LEFT JOIN db_main.lot_sys_user sur ON lsil.create_by=sur.id\n" +
                "LEFT JOIN db_main.lot_sys_user surr ON lsil.update_by=surr.id\n" +
                "JOIN db_relation.site_relation sr ON lsil.site_id=sr.old_id\n" +
                "WHERE lsil.is_del = \"F\"";
        try {
            //传递 SQL 语句
            stringBuilder.append("insert into " + Constant.db_platform + ".ip_limit (" +
                    "id," +
                    "ip_start," +
                    "ip_end," +
                    "type," +
                    "country_name," +
                    "site_id," +
                    "site_code," +
                    "final_time," +
                    "create_by," +
                    "create_time," +
                    "update_by," +
                    "update_time," +
                    "limit_time," +
                    "remark" +
                    ") values");
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Long id = IdWorker.getId();
                String ip_start = resultSet.getString("ip_start");
                if (ip_start != null) {
                    ip_start = "'" + ip_start + "'";
                }else{
                    ip_start = "''";
                }
                String ip_end = resultSet.getString("ip_end");
                if (ip_end != null) {
                    ip_end = "'" + ip_end + "'";
                }else{
                    ip_end = "''";
                }
                String operate = resultSet.getString("type");
                Integer type = 0;
                Integer oldType = Integer.valueOf(operate);
                if (oldType == 0) {
                    type = 11;
                } else if (oldType == 1) {
                    type = 12;
                } else if (oldType == 2) {
                    type = 10;
                } else if (oldType == 3) {
                    type = 20;
                }
                String country_name_str = resultSet.getString("country_name");
                String country_name = "''";
                if (country_name_str != null) {
                    country_name = "'" + country_name_str + "'";
                }
                String site_id_str = resultSet.getString("site_id");
                Long site_id = 0L;
                String site_code = "'" + resultSet.getString("site_code") + "'";
                if (site_id_str == null) {
                    site_id = null;
                    site_code = "''";
                } else {
                    site_id = Long.parseLong(site_id_str);
                }
                String finale_time = "'" + resultSet.getString("final_time") + "'";
                String create_by = resultSet.getString("create_by");
                if (create_by != null) {
                    create_by = "'" + create_by + "'";
                } else {
                    create_by = "''";
                }
                String create_time = resultSet.getString("create_time");
                if (create_time != null) {
                    create_time = "'" + create_time + "'";
                }
                String update_by = resultSet.getString("update_by");
                if (update_by != null) {
                    update_by = "'" + update_by + "'";
                } else {
                    update_by = "''";
                }
                String update_time = resultSet.getString("update_time");
                if (update_time != null) {
                    update_time = "'" + update_time + "'";
                }
                String limit_time = "'" + resultSet.getString("limit_time") + "'";
                String remark = "'" + resultSet.getString("remark") + "'";

                stringBuilder.append("(" + id + "," +
                        ip_start + "," +
                        ip_end + "," +
                        type + "," +
                        country_name + "," +
                        site_id + "," +
                        site_code + "," +
                        finale_time + "," +
                        create_by + "," +
                        create_time + "," +
                        update_by + "," +
                        update_time + "," +
                        limit_time + "," +
                        remark +
                        "),");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            bufferStream.write(stringBuilder.toString());
            bufferStream.flush();
        } catch (SQLException | IOException e) {
            logger.error("操作失败" + e.getMessage());
        }
        return stringBuilder.toString();
    }

}
