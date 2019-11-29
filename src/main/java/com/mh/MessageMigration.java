package com.mh;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.log4j.Logger;

import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.Constant.*;

/**
 * MessageMigration
 *
 * @author mh
 * @date 2018/7/19
 */
public class MessageMigration {
    static Logger logger = Logger.getLogger(MessageMigration.class);

    public static void executeRelation(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_sys_message", DB_RELATION + ".message_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_sys_message_send", DB_RELATION + ".message_send_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_sys_message_user", DB_RELATION + ".message_user_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_sys_notice", DB_RELATION + ".notice_relation");
    }

    public static void excuteDate(OutputStreamWriter bufferStream) {
        insertMessage(bufferStream);
        insertMessageSend(bufferStream);
        insertNotice(bufferStream);
        inserMessageUser(bufferStream);
    }

    /**
     * @param resultSet
     * @param statement
     * @param bufferStream
     * @param oldTable
     * @param newTable
     */
    public static void insertRelation(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream, String oldTable, String newTable) {
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

    public static void insertMessage(OutputStreamWriter bufferStream) {
        try {
            logger.info("message migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_PLATFORM + ".message (id,create_time,create_name,update_name,update_time,title,content,type,remark,site_id,site_code,is_del)\n" +
                    "SELECT\n" +
                    "\tmr.new_id,\n" +
                    "\tlsm.create_time,\n" +
                    "\tCASE ISNULL(sur.user_name) WHEN 1 THEN (SELECT user_name FROM db_relation.user_relation ur WHERE lsm.create_by=ur.old_id) ELSE sur.user_name END AS create_name,\n" +
                    "  CASE ISNULL(sur2.user_name) WHEN 1 THEN (SELECT user_name FROM db_relation.user_relation ur WHERE lsm.update_by=ur.old_id) ELSE sur2.user_name END AS update_name,\n" +
                    "\tlsm.update_time,\n" +
                    "\tlsm.title,\n" +
                    "\tlsm.content,\n" +
                    "\tlsm.type,\n" +
                    "\tlsm.remark,\n" +
                    "\tsr.new_id AS site_id,\n" +
                    "\tsr.site_code,\n" +
                    "\tCASE lsm.is_del when 'T' THEN 1 ELSE 0 END  AS is_del\n" +
                    "FROM\n" +
                    DB_MAIN + ".lot_sys_message lsm\n" +
                    "INNER JOIN site_relation sr ON sr.old_id = lsm.site_id\n" +
                    "LEFT JOIN message_relation mr ON mr.old_id = lsm.id\n" +
                    "LEFT JOIN sys_user_relation sur ON sur.old_id = lsm.create_by\n" +
                    "LEFT JOIN sys_user_relation sur2 ON sur2.old_id = lsm.update_by;\n");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("user_rank migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void insertMessageSend(OutputStreamWriter bufferStream) {
        try {
            logger.info("messageSend migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_PLATFORM + ".message_send (id, from_user_id,to_user_id,send_type,message_id,group_id,site_id,site_code,send_time,is_del,remark)\n" +
                    "SELECT\n" +
                    "\tmsr.new_id AS id,\n" +
                    "\tCASE ISNULL(sur.new_id) WHEN 1 THEN (SELECT new_id FROM db_relation.user_relation ur WHERE lsms.from_user_id=ur.old_id) ELSE sur.new_id END AS from_user_id ,\n" +
                    "\tCASE ISNULL(sur2.new_id) WHEN 1 THEN (SELECT new_id FROM db_relation.user_relation ur WHERE lsms.to_user_id=ur.old_id) ELSE sur2.new_id END AS to_user_id,\n" +
                    "\tlsms.send_type,\n" +
                    "\tmr.new_id AS message_id,\n" +
                    "\t(SELECT new_group_id FROM lot_pay_user_group_rela_relation lpugr WHERE lsms.group_id=lpugr.old_group_id LIMIT 1) AS group_id,\n" +
                    "\tsr.new_id AS site_id,\n" +
                    "\tsr.site_code,\n" +
                    "\tlsms.send_time,\n" +
                    "\tCASE lsms.is_del WHEN 'T' THEN 1 ELSE 0 END AS is_del , \n" +
                    "\tlsms.remark\n" +
                    "FROM\n" +
                    DB_MAIN + ".lot_sys_message_send lsms\n" +
                    "LEFT JOIN site_relation sr ON sr.old_id = lsms.site_id\n" +
                    "LEFT JOIN sys_user_relation sur ON sur.old_id = lsms.from_user_id\n" +
                    "LEFT JOIN sys_user_relation sur2 on sur2.old_id = lsms.to_user_id\n" +
                    "LEFT JOIN message_send_relation msr ON msr.old_id = lsms.id\n" +
                    "LEFT JOIN message_relation mr on mr.old_id = lsms.message_id;\n");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("user_rank migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void insertNotice(OutputStreamWriter bufferStream) {
        try {
            logger.info("notice migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT into " + DB_PLATFORM + ".notice (id,create_time,create_name,update_name,update_time,remark,title,content,begin_time,end_time,type,status,is_popup,is_login_popup,is_site,is_del,site_id,site_code,platform_type)\n" +
                    "SELECT\n" +
                    "\tnr.new_id AS id,\n" +
                    "\tlsn.create_time,\n" +
                    "\tCASE ISNULL(sur.user_name) WHEN 1 THEN (SELECT user_name FROM db_relation.user_relation ur WHERE lsn.create_by=ur.old_id) ELSE sur.user_name END AS create_name,\n" +
                    "  CASE ISNULL(sur2.user_name) WHEN 1 THEN (SELECT user_name FROM db_relation.user_relation ur WHERE lsn.update_by=ur.old_id) ELSE sur2.user_name END AS update_name,\n" +
                    "\tlsn.update_time,\n" +
                    "\tlsn.remark,\n" +
                    "\tlsn.title,\n" +
                    "\tlsn.content,\n" +
                    "\tlsn.begin_time,\n" +
                    "\tlsn.end_time,\n" +
                    "\tlsn.type,\n" +
                    "\tlsn. STATUS,\n" +
                    "\tCASE lsn.is_popup WHEN \"T\" THEN 1 ELSE 0 END AS is_popup,\n" +
                    "\tCASE lsn.is_login_popup WHEN 'T' THEN 1 ELSE 0 END AS is_login_popup,\n" +
                    "\tCASE lsn.is_site WHEN 'T' THEN 1 ELSE 0 END AS is_site,\n" +
                    "\tCASE lsn.is_del WHEN 'T' THEN 1 ELSE 0 END AS is_del,\n" +
                    "\tsr.new_id as site_id,\n" +
                    "\tsr.site_code,\n" +
                    "\tlsn.platform_type\n"+
                    "FROM \n" +
                    DB_MAIN + ".lot_sys_notice lsn\n" +
                    "INNER JOIN site_relation sr ON sr.old_id = lsn.site_id\n" +
                    "LEFT JOIN notice_relation nr ON nr.old_id = lsn.id\n" +
                    "LEFT JOIN sys_user_relation sur  ON sur.old_id = lsn.create_by\n" +
                    "LEFT JOIN sys_user_relation sur2 ON sur2.old_id = lsn.update_by;\n");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("user_rank migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void inserMessageUser(OutputStreamWriter bufferStream) {
        try {
            logger.info("message_user migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_PLATFORM + ".message_user (id,create_time,create_name,update_name,update_time," +
                    "reply_content,reply_time,send_time,read_time,message_send_id,remark,message_id,status,is_del,user_id,site_id,site_code,from_user_id,user_name)\n" +
                    "SELECT\n" +
                    "\tmur.new_id AS id,\n" +
                    "\tlsmu.create_time,\n" +
                    "\tCASE ISNULL(sur.user_name) WHEN 1 THEN (SELECT user_name FROM db_relation.user_relation ur WHERE lsmu.create_by=ur.old_id) ELSE sur.user_name END AS create_name,\n" +
                    "  CASE ISNULL(sur2.user_name) WHEN 1 THEN (SELECT user_name FROM db_relation.user_relation ur WHERE lsmu.update_by=ur.old_id) ELSE sur2.user_name END AS update_name,\n" +
                    "\tlsmu.update_time ,\n" +
                    "\tlsmu.reply_content,\n" +
                    "\tlsmu.reply_time,\n" +
                    "\tlsmu.send_time,\n" +
                    "\tlsmu.read_time,\n" +
                    "\tmsr.new_id AS message_send_id,\n" +
                    "\tlsmu.remark,\n" +
                    "\tmr.new_id AS message_id, \n" +
                    "case lsmu.status WHEN 'NO_READ' THEN 'MESSAGE_UNREAD' WHEN 'READED' THEN 'MESSAGE_READ' WHEN 'REPLY' THEN 'MESSAGE_REPLY' WHEN 'NO_SEND' THEN 'MESSAGE_UNSEND' WHEN 'SENDED' THEN 'MESSAGE_SEND' END AS `status` ,\n" +
                    "CASE lsmu.is_del WHEN 'F' THEN 1 ELSE 0 END AS is_del,\n" +
                    "CASE ISNULL(sur3.new_id) WHEN 1 THEN (SELECT new_id FROM db_relation.user_relation ur WHERE lsmu.user_id=ur.old_id) ELSE sur3.new_id END AS user_id,\n" +
                    "sr.new_id AS site_id,\n" +
                    "sr.site_code,\n" +
                    "CASE ISNULL(sur4.new_id) WHEN 1 THEN (SELECT new_id FROM db_relation.user_relation ur WHERE lsmu.from_user_id=ur.old_id) ELSE sur.new_id END AS from_user_id,\n" +
                    "sur5.user_name AS user_name\n" +
                    "FROM\n" +
                    DB_MAIN + ".lot_sys_message_user lsmu\n" +
                    "LEFT JOIN site_relation sr ON sr.old_id = lsmu.site_id\n" +
                    "LEFT JOIN message_user_relation mur ON mur.old_id = lsmu.id\n" +
                    "LEFT JOIN sys_user_relation sur ON sur.old_id = lsmu.create_by\n" +
                    "LEFT JOIN sys_user_relation sur2 ON sur2.old_id = lsmu.update_by\n" +
                    "LEFT JOIN message_send_relation msr ON msr.old_id = lsmu.message_send_id\n" +
                    "LEFT JOIN message_relation mr ON mr.old_id = lsmu.message_id\n" +
                    "LEFT JOIN sys_user_relation sur3 ON sur3.old_id = lsmu.user_id\n" +
                    "LEFT JOIN sys_user_relation sur4 ON sur4.old_id = lsmu.from_user_id\n" +
                    "LEFT JOIN user_relation sur5 ON sur5.old_id = lsmu.user_id;\n");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("user_rank migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
