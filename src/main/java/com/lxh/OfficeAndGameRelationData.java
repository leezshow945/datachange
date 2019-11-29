package com.lxh;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.log4j.Logger;

import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.Constant.DB_MAIN;
import static com.Constant.DB_RELATION;

/**
 * OfficeAndGameRelationData
 *
 * @author lxh
 * @date 2018/7/17
 */

public class OfficeAndGameRelationData {
    static Logger logger = Logger.getLogger(OfficeAndGameRelationData.class);
    static Statement statement = null;
    static ResultSet resultSet = null;

    public static void executeMain(ResultSet globalResultSet, Statement globalStatement, OutputStreamWriter bufferStream) {
        resultSet = globalResultSet;
        statement = globalStatement;
        insertChannelRelation(bufferStream, DB_MAIN + ".lot_site_channel", DB_RELATION + ".channel_relation");
        insertOfficeCategoryRelation(bufferStream, DB_MAIN + ".lot_site_office_category", DB_RELATION + ".office_category_relation");
        insertOfficeRelation(bufferStream, DB_MAIN + ".lot_site_office", DB_RELATION + ".office_relation");
        insertGameTemplateRelation(bufferStream, DB_MAIN + ".lot_game_template", DB_RELATION + ".game_template_relation");
        insertSiteGameConfigRelation(bufferStream, DB_MAIN + ".lot_site_game_config", DB_RELATION + ".site_game_config_relation");
        insertSiteGamePlayConfigRelation(bufferStream, DB_MAIN + ".lot_site_game_play_config", DB_RELATION + ".site_game_play_config_relation");
        insertSiteGamePlayTemplateRelation(bufferStream, DB_MAIN + ".lot_site_game_template", DB_RELATION + ".site_game_play_template_relation");
        insertSiteSettingRelation(bufferStream, DB_MAIN + ".lot_site_setting", DB_RELATION + ".site_setting_relation");
        insertSysTemplateRelation(bufferStream, DB_MAIN + ".lot_sys_template", DB_RELATION + ".sys_template_relation");

    }

    /**
     * 中间表数据插入
     *
     * @param bufferStream
     * @param oldTable
     * @param newTable
     */
    public static void insertRelation(OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
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
     * 插入栏目中间表数据
     *
     * @param bufferStream
     * @param oldTable
     * @param newTable
     */
    public static void insertChannelRelation(OutputStreamWriter bufferStream, String oldTable, String newTable) {
        insertRelation(bufferStream, oldTable, newTable);
    }

    /**
     * 插入文案分类中间表数据
     *
     * @param bufferStream
     * @param oldTable
     * @param newTable
     */
    public static void insertOfficeCategoryRelation(OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            /**
             *  create id relation value
             */
            StringBuilder relationSql = new StringBuilder();
            resultSet = statement.executeQuery("SELECT id,category_code FROM " + oldTable);
            relationSql.append("INSERT INTO " + newTable + " (new_id,old_id,category_code) VALUES ");
            while (resultSet.next()) {
                relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "','" + resultSet.getString("category_code") + "'),");
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
     * 插入文案中间表数据
     *
     * @param bufferStream
     * @param oldTable
     * @param newTable
     */
    public static void insertOfficeRelation(OutputStreamWriter bufferStream, String oldTable, String newTable) {
        insertRelation(bufferStream, oldTable, newTable);
    }

    /**
     * 插入站点游戏模板中间表数据
     *
     * @param bufferStream
     * @param oldTable
     * @param newTable
     */
    public static void insertGameTemplateRelation(OutputStreamWriter bufferStream, String oldTable, String newTable) {
        insertRelation(bufferStream, oldTable, newTable);
    }

    /**
     * 插入站点游戏配置中间表数据
     *
     * @param bufferStream
     * @param oldTable
     * @param newTable
     */
    public static void insertSiteGameConfigRelation(OutputStreamWriter bufferStream, String oldTable, String newTable) {
        insertRelation(bufferStream, oldTable, newTable);
    }

    /**
     * 插入站点游戏玩法开关中间表数据
     *
     * @param bufferStream
     * @param oldTable
     * @param newTable
     */
    public static void insertSiteGamePlayConfigRelation(OutputStreamWriter bufferStream, String oldTable, String newTable) {
        insertRelation(bufferStream, oldTable, newTable);
    }

    /**
     * 插入站点游戏赔率模板中间表数据
     *
     * @param bufferStream
     * @param oldTable
     * @param newTable
     */
    public static void insertSiteGamePlayTemplateRelation(OutputStreamWriter bufferStream, String oldTable, String newTable) {
        insertRelation(bufferStream, oldTable, newTable);
    }

    /**
     * 插入站点配置中间表数据
     *
     * @param bufferStream
     * @param oldTable
     * @param newTable
     */
    public static void insertSiteSettingRelation(OutputStreamWriter bufferStream, String oldTable, String newTable) {
        insertRelation(bufferStream, oldTable, newTable);
    }

    /**
     * 插入系统模板中间表数据
     *
     * @param bufferStream
     * @param oldTable
     * @param newTable
     */
    public static void insertSysTemplateRelation(OutputStreamWriter bufferStream, String oldTable, String newTable) {
        try {
            logger.info(newTable + " migration begin!");
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            resultSet = statement.executeQuery("SELECT id,template_name,type,is_private FROM " + oldTable);
            relationSql.append("INSERT INTO " + newTable + " (new_id,old_id,template_name,type,is_private) VALUES ");
            while (resultSet.next()) {
                String tempType = "";
                if ("system".equals(resultSet.getString("type"))) {
                    tempType = "SYSTEM";
                } else if ("site".equals(resultSet.getString("type"))) {
                    tempType = "SITE";
                }
                String isPrivate = null;
                if ("T".equals(resultSet.getString("is_private"))) {
                    isPrivate = "1";
                } else if ("F".equals(resultSet.getString("is_private"))) {
                    isPrivate = "0";
                }
                relationSql.append("(" + IdWorker.getId() + ",'" + resultSet.getString("id") + "','" + resultSet.getString("template_name") + "','" + tempType + "'," + isPrivate + "),");
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
}
