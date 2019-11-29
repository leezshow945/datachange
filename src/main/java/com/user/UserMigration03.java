package com.user;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Constant.*;

public class UserMigration03 {

    static Logger logger = Logger.getLogger(UserMigration02.class);


    public static void executeRelation(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        /**
         *  insert id relation table
         */
        //user_rebate_relation
        insertUserLog(resultSet, statement, bufferStream, DB_MAIN + ".lot_log_user", DB_RELATION + ".log_user_relation");//user_rebate_relation
        insertUserBank(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_bank", DB_RELATION + ".user_bank_relation");//user_rebate_relation
        insertUserFavorite(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_favorite", DB_RELATION + ".user_favorite_relation");//user_rebate_relation
        insertRebateRelate(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_rebate", DB_RELATION + ".user_rebate_relation");
        insertUserInfo(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_info", DB_RELATION + ".user_info_relation");//user_rebate_relation

    }

    public static void executeData(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        /**
         *  insert relation table and new table
         */

        migUserRebate(resultSet, statement, bufferStream);
        migUserLog(resultSet, statement, bufferStream);
        migUserBank(resultSet, statement, bufferStream);
        migUserFavorite(resultSet, statement, bufferStream);
        migUserInfo(resultSet, statement, bufferStream);
        updatePath2(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_rebate", DB_USER + ".user_rebate");//user_rebate_relation
    }
    public static void executeUpdateUserProxy(ResultSet resultSet, Statement statement, BufferedOutputStream bufferStream){
        updateUserProxy(resultSet, statement, bufferStream);
    }
    public static void executeSiteProxyChange(ResultSet resultSet, Statement statement, BufferedOutputStream bufferStream,String siteId,String siteCode){
        siteProxyChange(resultSet, statement, bufferStream,siteId,siteCode);
    }

    public static void insertRebateRelate(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            long size = 0;
            resultSet = statement.executeQuery("SELECT count(id) as num FROM " + oldTable);
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            if (size <= LIMIT) {
                resultSet = statement.executeQuery("select dur.new_id,ur.lot_user_id,dur.user_name,hur.new_id,ur.high_level_id  from " + oldTable +
                        " as ur LEFT JOIN " + DB_RELATION + ".user_relation as dur on ur.lot_user_id = dur.old_id " +
                        "LEFT JOIN " + DB_RELATION + ".user_relation as hur on ur.high_level_id = hur.old_id" +
                        " GROUP BY ur.lot_user_id ASC limit " + LIMIT);
                relationSql.append("INSERT INTO " + newTable + " (new_id,old_id,user_name,new_high_id,old_high_id) VALUES ");
                while (resultSet.next()) {
                    String user_name = resultSet.getString("dur.user_name");
                    if (user_name == null || user_name.equals("") || user_name.equals("null")) {
                        user_name = "";
                    }
                    String high_level_id = resultSet.getString("hur.new_id");
                    if (high_level_id == null || high_level_id.equals("")) {
                        relationSql.append("(" + resultSet.getString("dur.new_id") + ",'" + resultSet.getString("ur.lot_user_id") +
                                "','" + user_name + "',null,null),");
                    } else {
                        relationSql.append("(" + resultSet.getString("dur.new_id") + ",'" + resultSet.getString("ur.lot_user_id") +
                                "','" + user_name + "'," + high_level_id + ",'" + resultSet.getString("ur.high_level_id") + "'),");
                    }
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();
            } else {
                String keyField = "";
                resultSet = statement.executeQuery("select dur.new_id,ur.lot_user_id,dur.user_name,hur.new_id,ur.high_level_id  from " + oldTable +
                        " as ur LEFT JOIN " + DB_RELATION + ".user_relation as dur on ur.lot_user_id = dur.old_id " +
                        "LEFT JOIN " + DB_RELATION + ".user_relation as hur on ur.high_level_id = hur.old_id" +
                        " GROUP BY ur.lot_user_id ASC limit " + LIMIT);
                relationSql.append("INSERT INTO " + newTable + " (new_id,old_id,user_name,new_high_id,old_high_id) VALUES ");
                while (resultSet.next()) {
                    if (resultSet.isLast()) {
                        keyField = resultSet.getString("dur.new_id");
                    }
                    String user_name = resultSet.getString("dur.user_name");
                    if (user_name == null || user_name.equals("") || user_name.equals("null")) {
                        user_name = "";
                    }
                    String high_level_id = resultSet.getString("hur.new_id");
                    if (high_level_id == null || high_level_id.equals("")) {
                        relationSql.append("(" + resultSet.getString("dur.new_id") + ",'" + resultSet.getString("ur.lot_user_id") +
                                "','" + user_name + "',null,null),");
                    } else {
                        relationSql.append("(" + resultSet.getString("dur.new_id") + ",'" + resultSet.getString("ur.lot_user_id") +
                                "','" + user_name + "'," + high_level_id + ",'" + resultSet.getString("ur.high_level_id") + "'),");
                    }
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();

                //循环遍历执行生成sql语句脚本
                boolean over = false;
                while (!over) {
                    resultSet = statement.executeQuery("select dur.new_id,ur.lot_user_id,dur.user_name,hur.new_id,ur.high_level_id  from " + oldTable +
                            " as ur LEFT JOIN " + DB_RELATION + ".user_relation as dur on ur.lot_user_id = dur.old_id " +
                            "LEFT JOIN " + DB_RELATION + ".user_relation as hur on ur.high_level_id = hur.old_id"
                            + " where dur.new_id > " + keyField +
                            " GROUP BY ur.lot_user_id ASC limit " + LIMIT);
                    resultSet.last();
                    if (resultSet.getRow() > 0) {
                        resultSet.beforeFirst();
                        relationSql.delete(0, relationSql.length());
                        relationSql.append("INSERT INTO " + newTable + " (new_id,old_id,user_name,new_high_id,old_high_id) VALUES ");
                        while (resultSet.next()) {
                            if (resultSet.isLast()) {
                                keyField = resultSet.getString("dur.new_id");
                            }
                            String user_name = resultSet.getString("dur.user_name");
                            if (user_name == null || user_name.equals("") || user_name.equals("null")) {
                                user_name = "";
                            }
                            String high_level_id = resultSet.getString("hur.new_id");
                            if (high_level_id == null || high_level_id.equals("")) {
                                relationSql.append("(" + resultSet.getString("dur.new_id") + ",'" + resultSet.getString("ur.lot_user_id") +
                                        "','" + user_name + "',null,null),");
                            } else {
                                relationSql.append("(" + resultSet.getString("dur.new_id") + ",'" + resultSet.getString("ur.lot_user_id") +
                                        "','" + user_name + "'," + high_level_id + ",'" + resultSet.getString("ur.high_level_id") + "'),");
                            }
                        }
                        relationSql.deleteCharAt(relationSql.length() - 1);
                        relationSql.append(";");
                        bufferStream.write(relationSql.toString());
                        bufferStream.flush();
                    } else {
                        over = true;
                    }
                }
            }
            logger.info(newTable + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public static void insertUserLog(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            long size = 0;
            resultSet = statement.executeQuery("SELECT count(id) as num FROM " + oldTable);
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            if (size <= LIMIT) {
                resultSet = statement.executeQuery("SELECT id FROM " + oldTable);
                relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                while (resultSet.next()) {
                    relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "'),");
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();
            } else {
                String keyField = "";
                resultSet = statement.executeQuery("SELECT id FROM " + oldTable + " ORDER BY id ASC limit " + LIMIT);
                relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                while (resultSet.next()) {
                    if (resultSet.isLast()) {
                        keyField = resultSet.getString("id");
                    }
                    relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "'),");
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();

                //循环遍历执行生成sql语句脚本
                boolean over = false;
                while (!over) {
                    resultSet = statement.executeQuery("SELECT id FROM " + oldTable
                            + " where id > '" + keyField + "' ORDER BY id ASC limit " + LIMIT);
                    resultSet.last();
                    if (resultSet.getRow() > 0) {
                        resultSet.beforeFirst();
                        relationSql.delete(0, relationSql.length());
                        relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                        while (resultSet.next()) {
                            if (resultSet.isLast()) {
                                keyField = resultSet.getString("id");
                            }
                            relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "'),");
                        }
                        relationSql.deleteCharAt(relationSql.length() - 1);
                        relationSql.append(";");
                        bufferStream.write(relationSql.toString());
                        bufferStream.flush();
                    } else {
                        over = true;
                    }
                }
            }
            logger.info(newTable + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());

        }

    }

    public static void insertUserBank(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            long size = 0;
            resultSet = statement.executeQuery("SELECT count(id) as num FROM " + oldTable);
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            if (size <= LIMIT) {
                resultSet = statement.executeQuery("SELECT id FROM " + oldTable);
                relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                while (resultSet.next()) {
                    relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "'),");
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();
            } else {
                String keyField = "";
                resultSet = statement.executeQuery("SELECT id FROM " + oldTable + " ORDER BY id ASC limit " + LIMIT);
                relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                while (resultSet.next()) {
                    if (resultSet.isLast()) {
                        keyField = resultSet.getString("id");
                    }
                    relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "'),");
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();

                //循环遍历执行生成sql语句脚本
                boolean over = false;
                while (!over) {
                    resultSet = statement.executeQuery("SELECT id FROM " + oldTable
                            + " where id > '" + keyField + "' ORDER BY id ASC limit " + LIMIT);
                    resultSet.last();
                    if (resultSet.getRow() > 0) {
                        resultSet.beforeFirst();
                        relationSql.delete(0, relationSql.length());
                        relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                        while (resultSet.next()) {
                            if (resultSet.isLast()) {
                                keyField = resultSet.getString("id");
                            }
                            relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "'),");
                        }
                        relationSql.deleteCharAt(relationSql.length() - 1);
                        relationSql.append(";");
                        bufferStream.write(relationSql.toString());
                        bufferStream.flush();
                    } else {
                        over = true;
                    }
                }
            }
            logger.info(newTable + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());

        }
    }

    public static void insertUserFavorite(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            long size = 0;
            resultSet = statement.executeQuery("SELECT count(id) as num FROM " + oldTable);
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            if (size <= LIMIT) {
                resultSet = statement.executeQuery("SELECT id FROM " + oldTable);
                relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                while (resultSet.next()) {
                    relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "'),");
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();
            } else {
                String keyField = "";
                resultSet = statement.executeQuery("SELECT id FROM " + oldTable + " ORDER BY id ASC limit " + LIMIT);
                relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                while (resultSet.next()) {
                    if (resultSet.isLast()) {
                        keyField = resultSet.getString("id");
                    }
                    relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "'),");
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();

                //循环遍历执行生成sql语句脚本
                boolean over = false;
                while (!over) {
                    resultSet = statement.executeQuery("SELECT id FROM " + oldTable
                            + " where id > '" + keyField + "' ORDER BY id ASC limit " + LIMIT);
                    resultSet.last();
                    if (resultSet.getRow() > 0) {
                        resultSet.beforeFirst();
                        relationSql.delete(0, relationSql.length());
                        relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                        while (resultSet.next()) {
                            if (resultSet.isLast()) {
                                keyField = resultSet.getString("id");
                            }
                            relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "'),");
                        }
                        relationSql.deleteCharAt(relationSql.length() - 1);
                        relationSql.append(";");
                        bufferStream.write(relationSql.toString());
                        bufferStream.flush();
                    } else {
                        over = true;
                    }
                }
            }
            logger.info(newTable + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());

        }
    }

    public static void insertUserInfo(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            long size = 0;
            resultSet = statement.executeQuery("SELECT count(id) as num FROM " + oldTable);
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            if (size <= LIMIT) {
                resultSet = statement.executeQuery("SELECT uri.new_id,ur.lot_user_id FROM " + DB_RELATION + ".user_relation uri  LEFT JOIN  " + oldTable + " as ur ON ur.lot_user_id = uri.old_id"
                        + "  WHERE ur.lot_user_id > '0' limit " + LIMIT);
                relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                while (resultSet.next()) {
                    relationSql.append("(" + resultSet.getString("uri.new_id") + ",'" + resultSet.getString("ur.lot_user_id") + "'),");
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();
            } else {
                String keyField = "";
                resultSet = statement.executeQuery("SELECT uri.new_id,ur.lot_user_id FROM " + DB_RELATION + ".user_relation uri  LEFT JOIN  " + oldTable + " as ur ON ur.lot_user_id = uri.old_id"
                        + "  WHERE ur.lot_user_id > '0' limit " + LIMIT);
                relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                while (resultSet.next()) {
                    if (resultSet.isLast()) {
                        keyField = resultSet.getString("uri.new_id");
                    }
                    relationSql.append("(" + resultSet.getString("uri.new_id") + ",'" + resultSet.getString("ur.lot_user_id") + "'),");
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();

                //循环遍历执行生成sql语句脚本
                boolean over = false;
                while (!over) {
                    resultSet = statement.executeQuery("SELECT uri.new_id,ur.lot_user_id FROM " + DB_RELATION + ".user_relation uri  LEFT JOIN  " + oldTable + " as ur ON ur.lot_user_id = uri.old_id"
                            + "  WHERE ur.lot_user_id > '0' and uri.new_id >  '" + keyField + "' limit " + LIMIT);
                    resultSet.last();
                    if (resultSet.getRow() > 0) {
                        resultSet.beforeFirst();
                        relationSql.delete(0, relationSql.length());
                        relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
                        while (resultSet.next()) {
                            if (resultSet.isLast()) {
                                keyField = resultSet.getString("uri.new_id");
                            }
                            relationSql.append("(" + resultSet.getString("uri.new_id") + ",'" + resultSet.getString("ur.lot_user_id") + "'),");
                        }
                        relationSql.deleteCharAt(relationSql.length() - 1);
                        relationSql.append(";");
                        bufferStream.write(relationSql.toString());
                        bufferStream.flush();
                    } else {
                        over = true;
                    }
                }
            }
            logger.info(newTable + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());

        }
    }

    public static void migUserLog(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("log_user migration begin!");
            long thisTime = System.currentTimeMillis();

            String sqlHead = "INSERT INTO " + DB_USER + ".log_user" +
                    " (`id`, `plat`, `user_name`, `content`, `user_id`,`login_ip`, `login_time`,\n" +
                    "  `login_area`, `is_diff_area_login`, `type`, `account_type`, `site_id`,\n" +
                    " `is_del`, `flag_type`, `login_url`, `create_time`, `update_time`, `site_code`)";

            long size = 0;
            resultSet = statement.executeQuery("SELECT count(new_id) AS num FROM " + DB_RELATION + ".log_user_relation");
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 3000.0);//向上取整
            for (int i = 0; i < ceil; i++) {
                String insertSql = "select lu.new_id id,\n" +
                        " case plat\n" +
                        " when 'WEB' then 1\n" +
                        " when 'PC'  then 1\n" +
                        " when '电脑' then 1\n" +
                        "else 2 end as plat ,\n" +
                        "IFNULL(sur.user_name,ur.user_name) as login_accout,\n" +
                        "content,\n" +
                        "IFNULL(sur.new_id,ur.new_id) as login_id,\n" +
                        "login_ip,\n" +
                        "login_time,\n" +
                        "login_area,\n" +
                        "case is_diff_area_login\n" +
                        "when 'T' then 1 else 0 end  as is_diff_area_login,\n" +
                        "`type`,\n" +
                        "account_type,\n" +
                        "site.new_id as site_id,\n" +
                        "CASE l.is_del\n" +
                        "WHEN 'T' THEN\n" +
                        "\t1\n" +
                        "ELSE\n" +
                        "\t0\n" +
                        "END AS is_del,\n" +
                        "flag_type,\n" +
                        "login_url,\n" +
                        "null as create_time,\n" +
                        "null as update_time,\n" +
                        "site.site_code\n" +
                        "FROM db_main.lot_log_user l \n" +
                        "LEFT JOIN db_relation.log_user_relation lu on l.id = lu.old_id\n" +
                        "LEFT JOIN db_relation.user_relation ur on l.login_id = ur.old_id\n" +
                        "LEFT JOIN db_relation.sys_user_relation sur on l.login_id = sur.old_id\n" +
                        "LEFT JOIN db_relation.site_relation site on l.site_id = site.old_id" +
                        " GROUP BY l.id limit " + i * LIMIT + "," + LIMIT + ";";
                bufferStream.write((sqlHead + insertSql.toString()));
                bufferStream.flush();
            }
            logger.info("log_user_migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void migUserRebate(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user_rebate migration begin!");
            long thisTime = System.currentTimeMillis();

            String sqlHead = "INSERT INTO " + DB_USER + ".user_rebate" +
                    " (id,site_id,site_code,high_level_id,high_level_account,sub_level_count,level,path,is_proxy,refer_url,\n" +
                    " gpc_rebate,flc_rebate,tycp_rebate,qt_rebate,ty_rebate,lhc_rebate3,lhc_rebate2,lhc_rebate1,lhc_rebate0,dpc_rebate,create_time\n" +
                    ",update_time)";

            long size = 0;
            resultSet = statement.executeQuery("SELECT count(new_id) AS num FROM " + DB_RELATION + ".user_rebate_relation");
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 3000.0);//向上取整
            for (int i = 0; i < ceil; i++) {
                String insertSql = " select ur.new_id as `id`,\n" +
                        "site.new_id as `site_id`,\n" +
                        "IFNULL(site.site_code,\"\") as `site_code`,\n" +
                        "ur.new_high_id as  high_level_id,\n" +
                        "u.user_name as high_level_account, \n" +
                        "sub_level_count,\n" +
                        "level,\n" +
                        "path,\n" +
                        "case is_porxy\n" +
                        "WHEN 'T' THEN\n" +
                        "'1'\n" +
                        "ELSE '0'\n" +
                        "end as is_proxy,\n" +
                        "refer_url,\n" +
                        "gpc_rebate,\n" +
                        "flc_rebate,\n" +
                        "tycp_rebate,\n" +
                        "qt_rebate,\n" +
                        "ty_rebate,\n" +
                        "lhc_rebate3,\n" +
                        "lhc_rebate2,\n" +
                        "lhc_rebate1,\n" +
                        "lhc_rebate0,\n" +
                        "dpc_rebate,\n" +
                        "lur.create_time,\n" +
                        "lur.update_time\n" +
                        "from " + DB_MAIN + ".lot_user_rebate lur\n" +
                        "LEFT JOIN " + DB_RELATION + ".user_rebate_relation ur on lur.lot_user_id = ur.old_id\n" +
                        "LEFT JOIN " + DB_RELATION + ".user_relation u on lur.high_level_id = u.old_id\n" +
                        "LEFT JOIN " + DB_MAIN + ".lot_user u on lur.lot_user_id = u.id\n" +
                        "LEFT JOIN " + DB_RELATION + ".site_relation site on u.lot_site_id = site.old_id " +
                        " GROUP BY lur.id limit " + i * LIMIT + "," + LIMIT + "; ";
                bufferStream.write(sqlHead + insertSql);
                bufferStream.flush();
            }
            logger.info("user_rebate migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public static void migUserBank(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user_bank migration begin!");
            long thisTime = System.currentTimeMillis();

            String sqlHead = "INSERT INTO " + DB_USER + ".user_bank" +
                    " (id,user_id,province_id, city_id,card_no, bank_id ,\n" +
                    "bank_name,net_addr_id,is_enable,is_default,create_time,create_by,\n" +
                    "update_by,update_time,remark,is_del,card_user_name,province,city,net_addr)";

            long size = 0;
            resultSet = statement.executeQuery("SELECT count(new_id) AS num FROM " + DB_RELATION + ".user_bank_relation");
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 3000.0);//向上取整
            for (int i = 0; i < ceil; i++) {
                String insertSql = "select ubr.new_id as id,\n" +
                        "ur.new_id as user_id,\n" +
                        "pro.new_id as province_id,\n" +
                        "ar.new_id as city_id,\n" +
                        "replace(card_no,'+abc-','') card_no,\n" +
                        "br.new_id as bank_id,\n" +
                        "ub.bank_name,\n" +
                        "bbr.new_id net_addr_id,\n" +
                        "  case ub.is_enable\n" +
                        "    when 'T' then 1 else 0 end  as is_enable,\n" +
                        "case ub.is_default\n" +
                        "when \"T\" THEN\n" +
                        "1\n" +
                        "ELSE\n" +
                        "0\n" +
                        "end as is_default,\n" +
                        "ub.create_time,\n" +
                        "createBy.new_id as create_by,\n" +
                        "updateBy.new_id as update_by,\n" +
                        "update_time,\n" +
                        "remark,\n" +
                        "case ub.is_del\n" +
                        "when \"T\" THEN\n" +
                        "1\n" +
                        "ELSE\n" +
                        "0\n" +
                        "end as is_del,\n" +
                        "replace(card_user_name,'+abc-','') as card_user_name,\n" +
                        "province.area_name as province,\n" +
                        "city.area_name as city,\n" +
                        "newaddr.net_addr FROM db_main.lot_user_bank ub\n" +
                        "LEFT JOIN db_relation.user_bank_relation  ubr on ub.id = ubr.old_id\n" +
                        "left join db_relation.user_relation ur on ub.user_id = ur.old_id\n" +
                        "LEFT JOIN db_relation.area_relation ar on ub.city_id = ar.old_id\n" +
                        "LEFT JOIN db_relation.area_relation pro on ub.province_id = pro.old_id\n" +
                        "LEFT JOIN db_relation.bank_relation br on ub.bank_id = br.old_id\n" +
                        "LEFT JOIN db_relation.bank_branch_relation bbr on ub.net_addr_id = bbr.old_id\n" +
                        "LEFT JOIN db_relation.user_relation createBy on ub.create_by = createBy.old_id\n" +
                        "LEFT JOIN db_relation.user_relation updateBy on ub.update_by = updateBy.old_id\n" +
                        "LEFT JOIN db_main.lot_sys_area province ON ub.province_id = province.id\n" +
                        "LEFT JOIN db_main.lot_sys_area city ON ub.city_id = city.id\n" +
                        "LEFT JOIN db_main.lot_sys_bank_network newaddr ON ub.net_addr_id = newaddr.id " +
                        " GROUP BY ub.id limit " + i * LIMIT + "," + LIMIT + ";";
                bufferStream.write(sqlHead + insertSql);
                bufferStream.flush();
            }
            logger.info("log_user_migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void migUserFavorite(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user_favorite migration begin!");
            long thisTime = System.currentTimeMillis();

            String sqlHead = "INSERT INTO " + DB_USER + ".user_favorite" +
                    " (id,remark,create_by,update_by,create_time,update_time,sort_id,is_del," +
                    "game_id ,site_id,user_id,site_code,game_code)";

            long size = 0;
            resultSet = statement.executeQuery("SELECT count(new_id) AS num FROM " + DB_RELATION + ".user_favorite_relation");
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 3000.0);//向上取整
            for (int i = 0; i < ceil; i++) {
                String insertSql = "SELECT ufr.new_id as id,\n" +
                        "remark,\n" +
                        "create_by,\n" +
                        "update_by,\n" +
                        "create_time,\n" +
                        "update_time,\n" +
                        "sort_id,\n" +
                        "case is_del\n" +
                        "when 'T' THEN\n" +
                        "1\n" +
                        "else '0'\n" +
                        "end as is_del ,\n" +
                        "g.id as game_id ,\n" +
                        "IFNULL(site.new_id,0) as site_id,\n" +
                        "u.new_id as user_id,\n" +
                        "IFNULL(site.site_code,'')  as site_code,\n" +
                        "g.game_code as game_code\n" +
                        "from " + DB_MAIN + ".lot_user_favorite uf \n" +
                        "LEFT JOIN " + DB_RELATION + ".user_favorite_relation ufr on uf.id = ufr.old_id\n" +
                        "LEFT JOIN " + DB_RELATION + ".lot_game_relation g on uf.game_id = g.char_id\n" +
                        "LEFT JOIN " + DB_RELATION + ".site_relation site on uf.site_id = site.old_id\n" +
                        "LEFT JOIN " + DB_RELATION + ".user_relation u on uf.user_id = u.old_id " +
                        " GROUP BY uf.id limit " + i * LIMIT + "," + LIMIT + ";";
                bufferStream.write((sqlHead + insertSql.toString()));
                bufferStream.flush();
            }
            logger.info("user_favorite migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void migUserInfo(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user_info migration begin!");
            long thisTime = System.currentTimeMillis();

            String sqlHead = "INSERT INTO " + DB_USER + ".user_info" +
                    " (id,photo,sex,birthday,province_id,region_id,city_id,\n" +
                    "address,card_no,qq,we_chat,inviter,reg_ip,reg_url,reg_source,create_time,update_time,real_name,mobile,email)";

            long size = 0;
            resultSet = statement.executeQuery("SELECT count(new_id) AS num FROM " + DB_RELATION + ".user_info_relation");
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 3000.0);//向上取整
            for (int i = 0; i < ceil; i++) {
                String insertSql = "SELECT ur.new_id id,\n" +
                        "photo,\n" +
                        "case sex\n" +
                        "WHEN 'M' THEN\n" +
                        "'1'\n" +
                        "else '0'\n" +
                        "end as sex,\n" +
                        "  birth_date as birthday,\n" +
                        "province.new_id as province_id,\n" +
                        "null,\n" +
                        "city.new_id as  city_id,\n" +
                        "address,\n" +
                        "cart_no,\n" +
                        "replace(qq,'+abc-','') as qq,\n" +
                        "replace(we_chat,'+abc-','') as we_chat,\n" +
                        "inviter.new_id as inviter,\n" +
                        "reg_ip,\n" +
                        "reg_url,\n" +
                        "reg_source,\n" +
                        "ui.create_time,\n" +
                        "ui.update_time,\n" +
                        "replace(lu.real_name,'+abc-','') as real_name,\n" +
                        "replace(lu.mobile,'+abc-','') as mobile,\n" +
                        "replace(lu.email,'+abc-','') as eamil\n" +
                        "from db_main.lot_user_info ui \n" +
                        "LEFT JOIN db_relation.user_relation ur on ui.lot_user_id = ur.old_id\n" +
                        "LEFT JOIN db_relation.area_relation province on ui.province_id = province.old_id\n" +
                        "LEFT JOIN db_relation.area_relation city on ui.city_id = city.old_id\n" +
                        "LEFT JOIN db_relation.user_relation inviter on ui.inviter = inviter.old_id\n" +
                        "LEFT JOIN db_main.lot_user lu on ui.lot_user_id =lu.id  where ur.new_id is not null" +
                        " GROUP BY ui.id limit " + i * LIMIT + "," + LIMIT + ";";
                bufferStream.write((sqlHead + insertSql.toString()));
                bufferStream.flush();
            }
            logger.info("user_info migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    // 更新path 不分页
    public static void updatePath2(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            resultSet = statement.executeQuery("SELECT old_id,new_id FROM " + DB_RELATION + ".user_relation ");
            Map<String, String> map = new HashMap<>();
            while (resultSet.next()) {
                map.put(resultSet.getString("old_id"), resultSet.getString("new_id"));
            }
            resultSet = statement.executeQuery("SELECT lot_user_id,path FROM " + DB_MAIN + ".lot_user_rebate");
            while (resultSet.next()) {
                relationSql.append("update " + newTable + " set path = '");
                String path = resultSet.getString("path");
                String[] split = path.split(",");
                List<String> strings = Arrays.asList(split);
                StringBuilder newPath = new StringBuilder();
                for (String oldId : strings) {
                    newPath.append(map.get(oldId) == null ? "" : map.get(oldId)).append(",");
                }
                newPath.deleteCharAt(newPath.length() - 1);
                relationSql.append(newPath).append("' where id = " + map.get(resultSet.getString("lot_user_id")));
                relationSql.append(";");
            }
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info(newTable + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void updateUserProxy(ResultSet resultSet, Statement statement, BufferedOutputStream bufferStream) {
        try {
            logger.info(" updateUserProxy begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            resultSet = statement.executeQuery("select id,path FROM user_rebate");
            Map<String, String> pathMap = new HashMap<>();
            while (resultSet.next()) {
                pathMap.put(resultSet.getString("id"), resultSet.getString("path"));
            }
            resultSet = statement.executeQuery("select id,user_name,site_id,site_code,create_time,update_time FROM `user`");
            Map<String, UserEntity> userEntityMap = new HashMap<>();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                UserEntity userEntity = new UserEntity();
                userEntity.setId(id);
                userEntity.setUserName(resultSet.getString("user_name"));
                userEntity.setSiteId(resultSet.getString("site_id"));
                userEntity.setSiteCode(resultSet.getString("site_code"));
                userEntity.setCreateTime(resultSet.getTimestamp("create_time"));
                userEntity.setUpdateTime(resultSet.getTimestamp("update_time"));
                userEntityMap.put(id, userEntity);
            }
            for (String id : pathMap.keySet()) {
                UserEntity user = userEntityMap.get(id);
                if (user == null) {
                    continue;
                }
                String path = pathMap.get(id);
                relationSql.append("insert into user_proxy(id,high_user_id,user_name,high_user_name,level,user_id,site_id,site_code,create_time,update_time)" +
                        " values(" + IdWorker.getId() + "," + id + ",'" + user.getUserName() + "','" + user.getUserName() +
                        "'," + 0 + "," + id + "," + user.getSiteId() + ",'" + user.getSiteCode() + "','" + user.getCreateTime() + "','" + user.getUpdateTime() + "'); ");
                if (StringUtils.isNotEmpty(path)) {
                    String[] highUserIdArry = path.split(",");
                    for (int i = 0; i < highUserIdArry.length; i++) {// 主键id,上级id,用户名,上级用户名,层级,用户id,站点id,站点code,创建时间,更新时间
                        UserEntity highUser = userEntityMap.get(highUserIdArry[i]);
                        if (highUser == null) {
                            continue;
                        }
                        relationSql.append("insert into user_proxy(id,high_user_id,user_name,high_user_name,level,user_id,site_id,site_code,create_time,update_time)" +
                                " values(" + IdWorker.getId() + "," + highUserIdArry[i] + ",'" + user.getUserName() + "','" + highUser.getUserName() +
                                "'," + (highUserIdArry.length - i) + "," + id + "," + user.getSiteId() + ",'" + user.getSiteCode() + "','" + user.getCreateTime() + "','" + user.getUpdateTime() + "'); ");
                    }
                }
            }
            System.out.println("ok");
            bufferStream.write(relationSql.toString().getBytes());
            bufferStream.flush();
            logger.info(" updateUserProxy end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void siteProxyChange(ResultSet resultSet, Statement statement, BufferedOutputStream bufferStream,String siteId ,String siteCode) {
        try {
            logger.info(" siteProxyChange begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            resultSet = statement.executeQuery("select account_name,info FROM user_proxy");
            Map<String, String> pathMap = new HashMap<>();
            while (resultSet.next()) {
                pathMap.put(resultSet.getString("account_name"), resultSet.getString("info"));
            }
            resultSet = statement.executeQuery("select account_id,account_name,create_time FROM `user`");
            Map<String, SiteUserEntity> userEntityMap = new HashMap<>();
            while (resultSet.next()) {
                String accountName = resultSet.getString("account_name");
                SiteUserEntity userEntity = new SiteUserEntity();
                userEntity.setAccountId(resultSet.getString("account_id"));
                userEntity.setAccountName(accountName);
                userEntity.setCreateTime(resultSet.getTimestamp("create_time"));
                userEntityMap.put(accountName, userEntity);
            }
            for (String accountName : pathMap.keySet()) {
                SiteUserEntity user = userEntityMap.get(accountName);
                if (user == null) {
                    continue;
                }
                String path = pathMap.get(accountName);
                relationSql.append("insert into user_proxy(id,high_user_id,user_name,high_user_name,level,user_id,site_id,site_code,create_time,update_time)" +
                        " values(" + IdWorker.getId() + "," + user.getAccountId() + ",'" + user.getAccountName() + "','" + user.getAccountName() +
                        "'," + 0 + "," + user.getAccountId() + "," + siteId + ",'" + siteCode + "','" + user.getCreateTime() + "','" + user.getCreateTime() + "'); ");
                if (StringUtils.isNotEmpty(path)) {
                    String[] highUserIdArry = path.split("/");
                    for (int i = 0; i < highUserIdArry.length; i++) {// 主键id,上级id,用户名,上级用户名,层级,用户id,站点id,站点code,创建时间,更新时间
                        SiteUserEntity highUser = userEntityMap.get(highUserIdArry[i]);
                        if (highUser == null) {
                            continue;
                        }
                        relationSql.append("insert into user_proxy(id,high_user_id,user_name,high_user_name,level,user_id,site_id,site_code,create_time,update_time)" +
                                " values(" + IdWorker.getId() + "," + highUser.getAccountId() + ",'" + user.getAccountName() + "','" + highUser.getAccountName() +
                                "'," + (highUserIdArry.length - i) + "," + user.getAccountId() + "," + siteId + ",'" + siteCode + "','" + user.getCreateTime() + "','" + user.getCreateTime() + "'); ");
                    }
                }
            }
            System.out.println("ok");
            bufferStream.write(relationSql.toString().getBytes());
            bufferStream.flush();
            logger.info(" siteProxyChange end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
