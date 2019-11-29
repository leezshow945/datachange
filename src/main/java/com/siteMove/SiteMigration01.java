package com.siteMove;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.log4j.Logger;

import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import static com.Constant.*;

/**
 * @Author: Brady
 * @Description :
 * @Date: Create in 16:19 2018/12/4
 */
public class SiteMigration01 {
    static Logger logger = Logger.getLogger(SiteMigration01.class);

    public static void executeRelation(ResultSet resultSet, Statement statement, OutputStreamWriter out) {
        insertUserRelation(resultSet, statement, out, SITE_MOVE + ".user", DB_RELATION + ".user_relation");
        insertUserRankRelation(resultSet, statement, out, SITE_MOVE + ".user_level", DB_RELATION + ".user_rank_relation");
    }

    public static void executeData(ResultSet resultSet, Statement statement, OutputStreamWriter out) {
        insertUser(resultSet, statement, out);
        insertUserInfo(resultSet, statement, out);
        insertSysUser(resultSet, statement, out);
    }

    /**
     * 封装 生成user_rank关联表数据方法
     * 关联表字段为 new_id  old_id
     */
    public static void insertUserRankRelation(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            long size = 0;
            resultSet = statement.executeQuery("SELECT count(account_name) as num FROM " + oldTable);
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            if (size <= LIMIT) {
                resultSet = statement.executeQuery("SELECT account_name as user_name FROM " + oldTable);
                relationSql.append("INSERT INTO " + newTable + " (new_id,user_name) VALUES ");
                while (resultSet.next()) {
                    relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("user_name") + "'),");
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();
            } else {
                String keyField = "";
                resultSet = statement.executeQuery("SELECT account_name as user_name FROM " + oldTable + " ORDER BY account_name ASC limit " + LIMIT);
                relationSql.append("INSERT INTO " + newTable + " (new_id,user_name) VALUES ");
                while (resultSet.next()) {
                    if (resultSet.isLast()) {
                        keyField = resultSet.getString("user_name");
                    }
                    relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("user_name") + "'),");
                }
                relationSql.deleteCharAt(relationSql.length() - 1);
                relationSql.append(";");
                bufferStream.write(relationSql.toString());
                bufferStream.flush();

                //循环遍历执行生成sql语句脚本
                boolean over = false;
                while (!over) {
                    resultSet = statement.executeQuery("SELECT account_name as user_name,id FROM " + oldTable
                            +" where id > '" + keyField + "' ORDER BY id ASC limit " + LIMIT);
                    resultSet.last();
                    if (resultSet.getRow() > 0) {
                        resultSet.beforeFirst();
                        relationSql.delete(0, relationSql.length());
                        relationSql.append("INSERT INTO " + newTable + " (new_id,user_name) VALUES ");
                        while (resultSet.next()) {
                            if (resultSet.isLast()) {
                                keyField = resultSet.getString("id");
                            }
                            relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("user_name") + "'),");
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
            resultSet = statement.executeQuery("SELECT account_id as id,account_name as user_name FROM " + oldTable + "ORDER BY id ASC limit " + LIMIT);
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
                resultSet = statement.executeQuery("account_id as id,account_name as user_name FROM " + oldTable
                        + " where account_id > '" + keyField + "' ORDER BY account_id ASC limit " + LIMIT);
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

    public static void insertUserInfo(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user_info migration begin!");
            long thisTime = System.currentTimeMillis();

            String sqlHead = "INSERT INTO " + FINAL_MOVE + ".user_info" +
                    " (id,photo,sex,birthday,province_id,region_id,city_id,\n" +
                    "address,card_no,qq,we_chat,inviter,reg_ip,reg_url,reg_source,create_time,update_time,real_name,mobile,email)";
            long size = 0;
            resultSet = statement.executeQuery("SELECT count(new_id) AS num FROM " + DB_RELATION + ".user_info_relation");
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 3000.0);//向上取整
            for (int i = 0; i < ceil; i++) {
                String insertSql = "SELECT ur.new_id id,null,null,u.birthday,0,0,0,null,null,base64_encode('u.qq'),null," +
                        "0,null,null,1,u.create_time,u.create_time,base64_encode('u.real_name'),base64_encode('u.phone'),null," +
                        "from " + SITE_MOVE + ".user u \n" +
                        "LEFT JOIN " + DB_RELATION + ".user_relation ur on u.account_name = ur.user_name\n" +
                        "LEFT JOIN db_main.lot_user lu on u.lot_user_id =lu.id  where ur.new_id is not null" +
                        " GROUP BY u.id limit " + i * LIMIT + "," + LIMIT + ";";
                bufferStream.write((sqlHead + insertSql.toString()));
                bufferStream.flush();
            }
            logger.info("user_info migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void insertUser(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user migration begin!");
            long thisTime = System.currentTimeMillis();

            String sqlHead = " INSERT INTO " + FINAL_MOVE + ".`user` (id,site_id,site_code,user_rank_id,sign_count,login_count," +
                    "login_err_count,user_name,`password`,create_time,update_time,create_by,update_by,is_del," +
                    "`key`,last_sign_time,last_login_time,last_change_pwd_time,last_login_ip,change_pwd_err_num," +
                    "change_pay_pwd_err_num,freeze_score,can_score,score,`status`,pay_pwd,is_demo,last_playtime," +
                    "last_category,transfer_time,random,remark) ";
            long size = 0;
            resultSet = statement.executeQuery("SELECT count(id) AS num FROM " + SITE_MOVE + ".user");
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 3000.0);//向上取整
            Random r = new Random();
            for (int i = 0; i < ceil; i++) {
                String insertSql = " SELECT userRelation.new_id,"+ SITE_ID +","+ SITE_CODE+ ",userRankRelation.new_id,0,0,0,u.account_name,null," +
                        "u.create_time,u.create_time,null,null,0,null,null,null,null," +
                        "null,0,0,0,userRank.level_value,userRank.level_value,CASE WHEN u.`status` = '暂停投注' THEN 21 ELSE 10 END," +
                        "null,0,null,null,null," + "floor(rand()*6)" + ",null " +
                        " from " + SITE_MOVE + ".user u LEFT JOIN " + DB_RELATION + ".user_relation AS userRelation ON u.account_id = userRelation.old_id " +
                        " LEFT JOIN " + DB_RELATION + ".user_rank_relation AS userRankRelation ON u.account_name = userRankRelation.user_name " +
                        " LEFT JOIN " + SITE_MOVE + ".user_level AS userRank ON u.account_name = userRank.account_name " +
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
