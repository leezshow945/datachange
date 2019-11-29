package com.user;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.log4j.Logger;

import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import static com.Constant.*;

public class UserMigration01 {

    static Logger logger = Logger.getLogger(UserMigration01.class);

    public static void executeRelation(ResultSet resultSet, Statement statement, OutputStreamWriter out) {
        insertUserRelation(resultSet, statement, out, DB_MAIN + ".lot_user_rebate", DB_RELATION + ".user_relation");//user,user_info,user_rebate
        insertSysUserRelation(resultSet, statement, out, DB_MAIN + ".lot_sys_user", DB_RELATION + ".sys_user_relation");//sys_user
        insertRelation(resultSet, statement, out, DB_MAIN + ".lot_sys_dept", DB_RELATION + ".sys_user_dept_relation");
    }

    public static void executeData(ResultSet resultSet, Statement statement, OutputStreamWriter out) {
        insertUser(resultSet, statement, out);
        insertSysUserDept(resultSet, statement, out);
        insertSysUser(resultSet, statement, out);
    }

    /**
     * 封装 生成sys_user_dept关联表数据方法
     * 关联表字段为 new_id  old_id
     */
    public static void insertRelation(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            resultSet = statement.executeQuery("SELECT id FROM " + oldTable);
            relationSql.append("INSERT INTO " + newTable + " (new_id,old_id) VALUES ");
            while (resultSet.next()) {
                relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "'),");
            }
            relationSql.deleteCharAt(relationSql.length() - 1);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info(newTable + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 封装 生成sys_user_relation关联表数据方法
     * 关联表字段为 new_id  old_id user_name
     */
    public static void insertSysUserRelation(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();

            resultSet = statement.executeQuery("SELECT id,login_name as user_name FROM " + oldTable);
            relationSql.append("INSERT INTO " + newTable + " (new_id,old_id,user_name) VALUES ");
            while (resultSet.next()) {
                relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "','" + resultSet.getString("user_name") + "'),");
            }
            relationSql.deleteCharAt(relationSql.length() - 1);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();

            logger.info(newTable + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 封装 生成user_relation关联表数据方法
     * 关联表字段为 new_id  old_id user_name
     */
    public static void insertUserRelation(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            String keyField = "";
            resultSet = statement.executeQuery("SELECT ur.lot_user_id as id,u.login_name as user_name FROM " + oldTable + " as ur LEFT JOIN db_main.lot_user u on ur.lot_user_id = u.id LEFT JOIN db_main.lot_user_info ui on ur.lot_user_id = ui.id" + " ORDER BY id ASC limit " + LIMIT);
            relationSql.append("INSERT INTO " + newTable + " (new_id,old_id,user_name) VALUES ");
            while (resultSet.next()) {
                if (resultSet.isLast()) {
                    keyField = resultSet.getString("id");
                }
                relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "','" + resultSet.getString("user_name") + "'),");
            }
            relationSql.deleteCharAt(relationSql.length() - 1);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();

            //循环遍历执行生成sql语句脚本
            boolean over = false;
            while (!over) {
                resultSet = statement.executeQuery("SELECT ur.lot_user_id as id,u.login_name as user_name FROM " + oldTable + " as ur LEFT JOIN db_main.lot_user u on ur.lot_user_id = u.id LEFT JOIN db_main.lot_user_info ui on ur.lot_user_id = ui.id"
                 + " where ur.lot_user_id > '" + keyField + "' ORDER BY ur.lot_user_id ASC limit " + LIMIT);
                resultSet.last();
                if (resultSet.getRow() > 0) {
                    resultSet.beforeFirst();
                    relationSql.delete(0, relationSql.length());
                    relationSql.append("INSERT INTO " + newTable + " (new_id,old_id,user_name) VALUES ");
                    while (resultSet.next()) {
                        if (resultSet.isLast()) {
                            keyField = resultSet.getString("id");
                        }
                        relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "','" + resultSet.getString("user_name") + "'),");
                    }
                    relationSql.deleteCharAt(relationSql.length() - 1);
                    relationSql.append(";");
                    bufferStream.write(relationSql.toString());
                    bufferStream.flush();
                } else {
                    over = true;
                }
            }
            String type="commit;";
            bufferStream.write(type);
            bufferStream.flush();
            logger.info(newTable + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());

        }
    }

    public static void insertSysUserDept(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("sys_user_dept migration begin!");
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            relationSql.append(" INSERT INTO " + DB_USER + ".sys_dept " +
             " (id, dept_name, dept_phone,contact,contact_mobile, CREATE_by, create_time, update_by, update_time, remark, is_enable, site_code, site_id,is_del)" +
             " SELECT deptRelation.new_id id,dept.dept_name,dept.dept_phone,dept.linkman_id,dept.linkman_mobile,CASE WHEN create_by = 'SYS' THEN 'SYS' Else sysUserRelation.user_name END," +
             "dept.create_time,CASE WHEN update_by = 'SYS' THEN 'sys' Else sysUserRelation.user_name END," +
             "dept.update_time,dept.remark,CASE WHEN dept.is_enable = 'F' THEN 0 ELSE 1 END," +
             "siteRelation.site_code,siteRelation.new_id,CASE WHEN dept.is_del = 'F' THEN 0 ELSE 1 END " +
             "FROM " + DB_MAIN +
             ".lot_sys_dept as dept LEFT JOIN " + DB_RELATION + ".sys_user_dept_relation deptRelation ON dept.id = deptRelation.old_id LEFT JOIN " +
             DB_RELATION + ".sys_user_relation sysUserRelation ON dept.create_by = sysUserRelation.old_id LEFT JOIN " +
             DB_RELATION + ".sys_user_relation sysUserRelation2 ON dept.update_by = sysUserRelation2.old_id LEFT JOIN " +
             DB_RELATION + ".site_relation siteRelation ON siteRelation.old_id = dept.site_id" +
             " GROUP BY dept.id " +
             " ORDER BY dept.create_time;");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("sys_user_dept migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void insertSysUser(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("sys_user migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder relationSql = new StringBuilder();
            relationSql.append(" INSERT INTO " + DB_USER + ".sys_user " +
             " (id,user_name,`password`,site_id,site_code,real_name,sex,email,birthday,mobile,dept_id,login_count,login_pwd_err_count,is_enable,allow_ip,create_time,update_time,create_by,update_by,is_del,`key`,remark) " +
             " SELECT sysUserRelation.new_id,u.login_name,u.`password`,siteRelation.new_id,siteRelation.site_code,u.real_name,CASE when u.sex = 'M' THEN 0 ELSE 1 END,u.email,u.birth_date,u.mobile," +
             "sysUserDeptRelation.new_id,u.login_count,u.login_pass_error_count,CASE WHEN u.is_enable = 'T' THEN 1 ELSE 0 END,u.allow_ip,u.create_time,u.update_time,CASE when u.create_by = 'SYS' THEN 'SYS' ELSE sysUserRelation1.user_name END," +
             "CASE when u.update_by = 'SYS' THEN 'SYS' ELSE sysUserRelation2.user_name END," +
             "CASE WHEN u.is_del = 'T' THEN 1 ELSE 0 END,CASE when u.secret = null then 'F' ELSE u.secret end,u.remark " +
             " FROM " + DB_MAIN + ".lot_sys_user u " +
             " LEFT JOIN " + DB_RELATION + ".sys_user_relation sysUserRelation ON u.id = sysUserRelation.old_id " +
             " LEFT JOIN " + DB_RELATION + ".site_relation siteRelation ON u.site_id = siteRelation.old_id " +
             " LEFT JOIN " + DB_RELATION + ".sys_user_dept_relation sysUserDeptRelation on u.dept_id = sysUserDeptRelation.old_id " +
             " LEFT JOIN " + DB_RELATION + ".sys_user_relation sysUserRelation1 on u.create_by = sysUserRelation1.old_id " +
             " LEFT JOIN " + DB_RELATION + ".sys_user_relation sysUserRelation2 on u.update_by = sysUserRelation2.old_id " +
             " GROUP BY u.id " +
             " ORDER BY u.create_time;");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("sys_user migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    public static void insertUser(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user migration begin!");
            long thisTime = System.currentTimeMillis();

            String sqlHead = " INSERT INTO " + DB_USER + ".`user` (id,site_id,site_code,user_rank_id,sign_count,login_count," +
             "login_err_count,user_name,`password`,create_time,update_time,create_by,update_by,is_del," +
             "`key`,last_sign_time,last_login_time,last_change_pwd_time,last_login_ip,change_pwd_err_num," +
             "change_pay_pwd_err_num,freeze_score,can_score,score,`status`,pay_pwd,is_demo,last_playtime," +
             "last_category,transfer_time,random,remark) ";
            long size = 0;
            resultSet = statement.executeQuery("SELECT count(id) AS num FROM " + DB_MAIN + ".lot_user");
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 3000.0);//向上取整
            Random r = new Random();
            for (int i = 0; i < ceil; i++) {
                String insertSql = " SELECT userRelation.new_id,siteRelation.new_id,siteRelation.site_code,userRankRelation.new_id,u.sign_count,u.login_count,u.login_error_num,u.login_name,u.`password`," +
                 "u.create_time,u.update_time,userRelation1.user_name,userRelation2.user_name,CASE WHEN u.is_del = 'F' THEN 0 ELSE 1 END,CASE WHEN u.secret = NULL THEN 'F' ELSE u.secret END,u.last_sign_time,u.last_login_time,u.last_change_pwd_time," +
                 "u.last_login_ip,u.change_pass_word_error_num,u.change_pay_password_error_num,userFund.freeze_score,userFund.can_score,userFund.score,CASE WHEN u.`status` = '11' THEN 11 WHEN u.`status` = '21' THEN 21 WHEN u.`status` = '30' THEN 31 WHEN u.`status` = '41' THEN 41 ELSE 10 END," +
                 "u.pay_password,CASE WHEN u.is_demo = 'F' THEN 0 WHEN u.`status` = 'T' THEN 1 ELSE 2 END," +
                 "null,null,u.transfer_time," + "floor(rand()*6)" + ",u.remark " +
                 " from " + DB_MAIN + ".lot_user u LEFT JOIN " + DB_RELATION + ".user_relation AS userRelation ON u.id = userRelation.old_id " +
                 " LEFT JOIN " + DB_RELATION + ".site_relation AS siteRelation ON u.lot_site_id = siteRelation.old_id " +
                 " LEFT JOIN " + DB_RELATION + ".user_rank_relation AS userRankRelation ON u.lot_user_rank_id = userRankRelation.old_id " +
                 " LEFT JOIN " + DB_RELATION + ".user_relation AS userRelation1 ON u.create_by = userRelation1.old_id " +
                 " LEFT JOIN " + DB_RELATION + ".user_relation AS userRelation2 ON u.update_by = userRelation2.old_id " +
                 " LEFT JOIN " + DB_MAIN + ".lot_user_fund AS userFund ON u.id = userFund.lot_user_id " +
                 " GROUP BY u.id limit " + i * LIMIT + "," + LIMIT + ";";
                bufferStream.write(sqlHead + insertSql);
                bufferStream.flush();
            }
            logger.info("user migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

