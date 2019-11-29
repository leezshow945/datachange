package com.lxh;

import com.Constant;
import org.apache.log4j.Logger;

import java.io.OutputStreamWriter;

/**
 * OfficeAndGameSQL
 *
 * @author lxh
 * @date 2018/7/17
 */

public class OfficeAndGameSQL {
    static Logger logger = Logger.getLogger(OfficeAndGameRelationData.class);


    public static void executeMain(OutputStreamWriter bufferStream) {
        channel(bufferStream, Constant.DB_MAIN, Constant.DB_RELATION, Constant.DB_PLATFORM);
        office(bufferStream, Constant.DB_MAIN, Constant.DB_RELATION, Constant.DB_PLATFORM);
        officeCategory(bufferStream, Constant.DB_MAIN, Constant.DB_RELATION, Constant.DB_PLATFORM);
        gameTemplate(bufferStream, Constant.DB_MAIN, Constant.DB_RELATION, Constant.DB_PLATFORM);
        siteGameConfig(bufferStream, Constant.DB_MAIN, Constant.DB_RELATION, Constant.DB_PLATFORM);
        siteGamePlayConfig(bufferStream, Constant.DB_MAIN, Constant.DB_RELATION, Constant.DB_PLATFORM);
        siteGamePlayTemplate(bufferStream, Constant.DB_MAIN, Constant.DB_RELATION, Constant.DB_PLATFORM);
        siteSetting(bufferStream, Constant.DB_MAIN, Constant.DB_RELATION, Constant.DB_PLATFORM);
        sysTemplate(bufferStream, Constant.DB_MAIN, Constant.DB_RELATION, Constant.DB_PLATFORM);
    }

    public static void channel(OutputStreamWriter bufferStream, String oldDB, String relationDB, String newDB) {
        try {
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            String sql = "INSERT INTO `" + newDB + "`.`channel` (\n" +
                    "\t`id`,\n" +
                    "\t`site_id`,\n" +
                    "\t`site_code`,\n" +
                    "\t`channel_name`,\n" +
                    "\t`channel_code`,\n" +
                    "\t`path`,\n" +
                    "\t`category_code`,\n" +
                    "\t`img_url`,\n" +
                    "\t`is_display`,\n" +
                    "\t`sort_id`,\n" +
                    "\t`create_name`,\n" +
                    "\t`update_name`,\n" +
                    "\t`create_time`,\n" +
                    "\t`update_time`,\n" +
                    "\t`is_del`,\n" +
                    "\t`remark`\n" +
                    ")SELECT\n" +
                    "\tc.new_id AS id,\n" +
                    "\tb.new_id AS site_id,\n" +
                    "\tb.site_code,\n" +
                    "\ta.channel_name,\n" +
                    "\ta.channel_code,\n" +
                    "\ta.channel_url AS path,\n" +
                    "\tCASE a.category_id WHEN \"0\" THEN \"0\" ELSE (SELECT code FROM " + relationDB + ".lot_game_category_relation d  WHERE a.category_id=d.char_id)END AS category_code,\n" +
                    "\ta.channel_logo_url AS img_url,\n" +
                    "\tCASE a.is_display WHEN 'T' THEN 1 ELSE 0 END AS is_display,\n" +
                    "\ta.sort_id,\n" +
                    "\tCASE a.create_by WHEN 'sys' THEN 'sys' ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation x WHERE x.old_id=a.create_by) END AS create_name,\n" +
                    "\tCASE a.update_by WHEN 'sys' THEN 'sys' ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation y WHERE y.old_id=a.update_by) END AS update_name,\n" +
                    "\ta.create_time,\n" +
                    "\ta.update_time,\n" +
                    "\tCASE a.site_id WHEN \"0\" THEN 0 ELSE (CASE a.is_del WHEN 'T' THEN 1 ELSE 0 END)END AS is_del,\n" +
                    "\ta.remark\n" +
                    "FROM\n" +
                    "\t" + oldDB + ".lot_site_channel AS a\n" +
                    "INNER JOIN " + relationDB + ".site_relation AS b ON a.site_id = b.old_id\t\n" +
                    "INNER JOIN " + relationDB + ".channel_relation AS c ON a.id = c.old_id\n";
            relationSql.append(sql);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("channel" + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void office(OutputStreamWriter bufferStream, String oldDB, String relationDB, String newDB) {
        try {
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            String sql = "INSERT INTO `" + newDB + "`.`office` (\n" +
                    "\t`id`,\n" +
                    "\t`site_id`,\n" +
                    "\t`site_code`,\n" +
                    "\t`office_code`,\n" +
                    "\t`title`,\n" +
                    "\t`detail`,\n" +
                    "\t`img_url`,\n" +
                    "\t`url`,\n" +
                    "\t`is_mouse`,\n" +
                    "\t`win_height`,\n" +
                    "\t`win_width`,\n" +
                    "\t`content`,\n" +
                    "\t`start_time`,\n" +
                    "\t`end_time`,\n" +
                    "\t`is_del`,\n" +
                    "\t`sort_id`,\n" +
                    "\t`create_name`,\n" +
                    "\t`update_name`,\n" +
                    "\t`create_time`,\n" +
                    "\t`update_time`,\n" +
                    "\t`remark`\n" +
                    ") SELECT\n" +
                    "\tc.new_id AS id,\n" +
                    "\tb.new_id AS site_id,\n" +
                    "\tb.site_code AS site_code,\n" +
                    "\td.category_code AS office_code,\n" +
                    "\ta.title,\n" +
                    "    a.detail,\n" +
                    "\ta.img AS img_url,\n" +
                    "\ta.url,\n" +
                    "\tCASE a.is_mouse WHEN \"T\" THEN 1 ELSE 0 END AS is_mouse,\n" +
                    "\ta.win_height,\n" +
                    "\ta.win_width,\n" +
                    "\ta.content,\n" +
                    "\ta.begin_time AS start_time,\n" +
                    "\ta.end_time,\n" +
                    "\tCASE a.is_del WHEN \"T\" THEN 1 ELSE 0 END AS is_del,\n" +
                    "\ta.sort_id,\n" +
                    "\tCASE a.create_by WHEN \"sys\" THEN \"sys\" ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation x WHERE x.old_id=a.create_by) END AS create_name,\n" +
                    "\tCASE a.update_by WHEN \"sys\" THEN \"sys\" ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation y WHERE y.old_id=a.update_by) END AS update_name,\n" +
                    "\ta.create_time,\n" +
                    "\ta.update_time,\n" +
                    "\ta.remark\n" +
                    "FROM\n" +
                    "\t" + oldDB + ".lot_site_office a\n" +
                    "INNER JOIN " + relationDB + ".site_relation b ON a.site_id = b.old_id\n" +
                    "INNER JOIN " + relationDB + ".office_relation c ON a.id = c.old_id\n" +
                    "INNER JOIN " + relationDB + ".office_category_relation d ON a.channel_id = d.old_id ";
            relationSql.append(sql);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("channel" + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void officeCategory(OutputStreamWriter bufferStream, String oldDB, String relationDB, String newDB) {
        try {
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            String sql = "INSERT INTO `" + newDB + "`.`office_category` (\n" +
                    "\t`id`,\n" +
                    "\t`pid`,\n" +
                    "\t`site_id`,\n" +
                    "\t`site_code`,\n" +
                    "\t`office_name`,\n" +
                    "\t`office_code`,\n" +
                    "\t`category_image`,\n" +
                    "\t`is_multis`,\n" +
                    "\t`is_title_display`,\n" +
                    "\t`is_desc_display`,\n" +
                    "\t`is_img_display`,\n" +
                    "\t`is_url_display`,\n" +
                    "\t`is_float_display`,\n" +
                    "\t`is_mouse_display`,\n" +
                    "\t`is_height_display`,\n" +
                    "\t`is_width_display`,\n" +
                    "\t`is_content_display`,\n" +
                    "\t`sort_id`,\n" +
                    "\t`create_time`,\n" +
                    "\t`update_time`,\n" +
                    "\t`create_name`,\n" +
                    "\t`update_name`,\n" +
                    "\t`is_del`,\n" +
                    "\t`remark`\n" +
                    ")\n" +
                    "SELECT * FROM \n" +
                    "(SELECT\n" +
                    "\tc.new_id AS id,\n" +
                    "\tCASE a.pid WHEN \"0\" THEN 0 WHEN a.site_id THEN 0 ELSE (SELECT e.new_id FROM " + relationDB + ".office_category_relation e WHERE a.pid=e.old_id ) END AS pid,\n" +
                    "\tb.new_id AS site_id,\n" +
                    "\tb.site_code AS site_code,\n" +
                    "\ta.channel_name AS office_name,\n" +
                    "\tIFNULL(a.category_code,CASE a.channel_name WHEN \"公共\" THEN \"common\" ELSE \"index\" END) AS office_code,\n" +
                    "\ta.img_url AS category_image,\n" +
                    "\tCASE a.is_multis WHEN \"T\" THEN 1 ELSE 0 END AS is_multis,\n" +
                    "\tCASE a.is_title_display WHEN \"T\" THEN 1 ELSE 0 END AS is_title_display,\n" +
                    "\tCASE a.is_desc_display WHEN \"T\" THEN 1 ELSE 0 END AS is_desc_display,\n" +
                    "\tCASE a.is_img_display WHEN \"T\" THEN 1 ELSE 0 END AS is_img_display,\n" +
                    "\tCASE a.is_url_display WHEN \"T\" THEN 1 ELSE 0 END AS is_url_display,\n" +
                    "\tCASE a.is_float_display WHEN \"T\" THEN 1 ELSE 0 END AS is_float_display,\n" +
                    "\tCASE a.is_mouse_display WHEN \"T\" THEN 1 ELSE 0 END AS is_mouse_display,\n" +
                    "\tCASE a.is_height_display WHEN \"T\" THEN 1 ELSE 0 END AS is_height_display,\n" +
                    "\tCASE a.is_width_display WHEN \"T\" THEN 1 ELSE 0 END AS is_width_display,\n" +
                    "\tCASE a.is_content_display WHEN \"T\" THEN 1 ELSE 0 END AS is_content_display,\n" +
                    "\ta.sort_id,\n" +
                    "\ta.create_time,\n" +
                    "\ta.update_time,\n" +
                    "\tCASE a.create_by WHEN \"sys\" THEN \"sys\" ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation x WHERE x.old_id=a.create_by) END AS create_name,\n" +
                    "\tCASE a.update_by WHEN \"sys\" THEN \"sys\" ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation y WHERE y.old_id=a.update_by) END AS update_name,\n" +
                    "\tCASE a.is_del WHEN \"T\" THEN 1 ELSE 0 END AS is_del,\n" +
                    "\ta.remark\n" +
                    "FROM\n" +
                    "\t" + oldDB + ".lot_site_office_category a\n" +
                    "INNER JOIN " + relationDB + ".site_relation b ON a.site_id = b.old_id   \n" +
                    "LEFT JOIN " + relationDB + ".office_category_relation c ON a.id = c.old_id) z WHERE z.pid is not null\n" +
                    "\n";
            relationSql.append(sql);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("channel" + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void gameTemplate(OutputStreamWriter bufferStream, String oldDB, String relationDB, String newDB) {
        try {
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            String sql = "INSERT INTO `" + newDB + "`.`game_template` (\n" +
                    "\t`id`,\n" +
                    "\t`game_code`,\n" +
                    "\t`temp_id`,\n" +
                    "\t`temp_name`,\n" +
                    "\t`site_code`,\n" +
                    "\t`site_id`,\n" +
                    "\t`type`,\n" +
                    "\t`is_del`\n" +
                    ") SELECT\n" +
                    "\te.new_id AS id,\n" +
                    "\td.game_code AS game_code,\n" +
                    "\tc.new_id AS temp_id,\n" +
                    "\tc.template_name,\n" +
                    "\tb.site_code,\n" +
                    "\tb.new_id AS site_id,\n" +
                    "\tc.type,\n" +
                    "\t0 AS is_del\n" +
                    "FROM\n" +
                    "\t" + oldDB + ".lot_game_template a\n" +
                    "INNER JOIN\n" +
                    "\t" + relationDB + ".site_relation b ON a.site_id=b.old_id\n" +
                    "INNER JOIN\n" +
                    "\t" + relationDB + ".sys_template_relation c ON a.temp_id=c.old_id\n" +
                    "INNER JOIN\n" +
                    "\t" + relationDB + ".lot_game_relation d ON a.game_id=d.char_id\n" +
                    "LEFT JOIN\n" +
                    "\t" + relationDB + ".game_template_relation e ON a.id=e.old_id";
            relationSql.append(sql);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("channel" + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void siteGameConfig(OutputStreamWriter bufferStream, String oldDB, String relationDB, String newDB) {
        try {
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            String sql = "INSERT INTO `" + newDB + "`.`site_game_config` (\n" +
                    "\t`id`,\n" +
                    "\t`site_id`,\n" +
                    "\t`site_code`,\n" +
                    "\t`game_code`,\n" +
                    "\t`game_name`,\n" +
                    "\t`play_type`,\n" +
                    "\t`temp_id`,\n" +
                    "\t`pid`,\n" +
                    "\t`category_code`,\n" +
                    "\t`logo_url`,\n" +
                    "\t`is_enable`,\n" +
                    "\t`is_joint_buy`,\n" +
                    "\t`is_open`,\n" +
                    "\t`is_chase`,\n" +
                    "\t`is_cancel_order`,\n" +
                    "\t`is_times`,\n" +
                    "\t`is_yuan`,\n" +
                    "\t`is_jiao`,\n" +
                    "\t`is_fen`,\n" +
                    "\t`is_hot`,\n" +
                    "\t`is_display`,\n" +
                    "\t`is_app_display`,\n" +
                    "\t`is_private`,\n" +
                    "\t`sort_id`,\n" +
                    "\t`create_time`,\n" +
                    "\t`update_time`,\n" +
                    "\t`create_name`,\n" +
                    "\t`update_name`,\n" +
                    "\t`is_del`,\n" +
                    "\t`remark`\n" +
                    ")\n" +
                    "SELECT * FROM (\n" +
                    "SELECT\n" +
                    "\tc.new_id AS id,\n" +
                    "\tb.new_id AS site_id,\n" +
                    "\tb.site_code AS site_code,\n" +
                    "\td.game_code,\n" +
                    "\te.game_name AS game_name,\n" +
                    "\ta.play_type,\n" +
                    "\tCASE ISNULL(f.new_id) WHEN 0 THEN f.new_id ELSE (SELECT y.new_id FROM " + oldDB + ".lot_sys_template x," + relationDB + ".sys_template_relation y WHERE a.play_type=x.play_type AND x.is_private=e.is_private AND x.template_type=\"LIMIT\" AND x.type=\"system\" AND x.is_default=\"T\" AND x.is_del=\"F\"\n" +
                    "AND x.id=y.old_id LIMIT 1) END AS temp_id,\n" +
                    "\tg.id AS pid,\n" +
                    "\te.game_category_code AS category_code,\n" +
                    "\ta.logo_url,\n" +
                    "\tCASE a.is_enable WHEN \"T\" THEN 1 ELSE 0 END AS is_enable,\n" +
                    "\tCASE a.is_joint_buy WHEN \"T\" THEN 1 ELSE 0 END AS is_joint_buy,\n" +
                    "  CASE a.is_open WHEN \"T\" THEN 1 ELSE 0 END AS is_open,\n" +
                    "  CASE a.is_chase WHEN \"T\" THEN 1 ELSE 0 END AS is_chase,\n" +
                    "  CASE a.is_cancel_order WHEN \"T\" THEN 1 ELSE 0 END AS is_cancel_order,\n" +
                    "\tCASE a.is_times WHEN \"T\" THEN 1 ELSE 0 END AS is_times,\n" +
                    "  CASE a.is_yuan WHEN \"T\" THEN 1 ELSE 0 END AS is_yuan,\n" +
                    "  CASE a.is_jiao WHEN \"T\" THEN 1 ELSE 0 END AS is_jiao,\n" +
                    "  CASE a.is_fen WHEN \"T\" THEN 1 ELSE 0 END AS is_fen,\n" +
                    "\tCASE a.is_hot WHEN \"T\" THEN 1 ELSE 0 END AS is_hot,\n" +
                    "  CASE a.is_display WHEN \"T\" THEN 1 ELSE 0 END AS is_display,\n" +
                    "  CASE a.is_app_display WHEN \"T\" THEN 1 ELSE 0 END AS is_app_display,\n" +
                    "\tCASE e.is_private WHEN \"T\" THEN 1 ELSE 0 END AS is_private,\n" +
                    "\ta.sort_id,\n" +
                    "\ta.create_time,\n" +
                    "\ta.update_time,\n" +
                    "\tCASE a.create_by WHEN 'sys' THEN 'sys' ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation x WHERE x.old_id=a.create_by) END AS create_name,\n" +
                    "\tCASE a.update_by WHEN 'sys' THEN 'sys' ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation y WHERE y.old_id=a.update_by) END AS update_name,\n" +
                    "\tCASE a.is_del WHEN \"T\" THEN 1 ELSE 0 END AS is_del,\n" +
                    "\ta.remark\n" +
                    "FROM\n" +
                    "\t" + oldDB + ".lot_site_game_config a\n" +
                    "INNER JOIN\n" +
                    "\t" + relationDB + ".site_relation b ON a.site_id=b.old_id\n" +
                    "INNER JOIN\n" +
                    "  " + relationDB + ".lot_game_relation d ON  a.game_id=d.char_id\n" +
                    "LEFT JOIN\n" +
                    "\t" + relationDB + ".site_game_config_relation c ON a.id=c.old_id\n" +
                    "LEFT JOIN\n" +
                    "  " + oldDB + ".lot_game e ON a.game_id=e.id \n" +
                    "LEFT JOIN\n" +
                    "\t" + relationDB + ".sys_template_relation f ON a.temp_id=f.old_id\n" +
                    "LEFT JOIN\n" +
                    "\t" + relationDB + ".lot_game_category_relation g ON a.pid=g.char_id)z WHERE z.temp_id is not null\n";
            relationSql.append(sql);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("channel" + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void siteGamePlayConfig(OutputStreamWriter bufferStream, String oldDB, String relationDB, String newDB) {
        try {
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            String sql = "INSERT INTO `" + newDB + "`.`site_game_play_config` (\n" +
                    "\t`id`,\n" +
                    "\t`site_id`,\n" +
                    "\t`site_code`,\n" +
                    "\t`game_code`,\n" +
                    "\t`game_name`,\n" +
                    "\t`play_id`,\n" +
                    "\t`play_name`,\n" +
                    "\t`status`,\n" +
                    "\t`pstatus`,\n" +
                    "\t`play_type`,\n" +
                    "\t`is_private`\n" +
                    ")\n" +
                    "SELECT\n" +
                    "\tc.new_id AS id,\n" +
                    "\tb.new_id AS site_id,\n" +
                    "\tb.site_code AS site_code,\n" +
                    "\tCASE ISNULL(d.game_code) WHEN 1 THEN 0 ELSE d.game_code END AS game_code,\n" +
                    "\ta.game_name,\n" +
                    "\te.id AS play_id,\n" +
                    "\ta.game_play_category_name AS play_name,\n" +
                    "\tCASE a.`status` WHEN \"T\" THEN 1 ELSE 0 END AS `status`,\n" +
                    "\tCASE a.pstatus WHEN \"T\" THEN 1 ELSE 0 END AS pstatus,\n" +
                    "\ta.play_type,\n" +
                    "\tCASE a.is_private WHEN \"T\" THEN 1 ELSE 0 END AS is_private\n" +
                    "FROM\n" +
                    "\t" + oldDB + ".lot_site_game_play_config a\n" +
                    "INNER JOIN\n" +
                    "\t" + relationDB + ".site_relation b ON a.site_id=b.old_id\n" +
                    "LEFT JOIN\n" +
                    "\t" + relationDB + ".site_game_play_config_relation c ON a.id=c.old_id\n" +
                    "LEFT JOIN\n" +
                    "\t" + relationDB + ".lot_game_relation d ON a.game_id=d.char_id\n" +
                    "LEFT JOIN\n" +
                    "\t" + relationDB + ".lot_game_play_category_relation e ON a.game_play_category_id=e.char_id";
            relationSql.append(sql);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("channel" + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void siteGamePlayTemplate(OutputStreamWriter bufferStream, String oldDB, String relationDB, String newDB) {
        try {
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            String sql = "INSERT INTO `" + newDB + "`.`site_game_play_template` (\n" +
                    "\t`id`,\n" +
                    "\t`play_id`,\n" +
                    "\t`temp_id`,\n" +
                    "\t`select_type`,\n" +
                    "\t`adds`,\n" +
                    "\t`max_adds`,\n" +
                    "\t`multi_adds`,\n" +
                    "\t`rebate_scale`,\n" +
                    "\t`max_price`,\n" +
                    "\t`max_times`,\n" +
                    "\t`max_prize`,\n" +
                    "\t`is_private`,\n" +
                    "\t`item_code`,\n" +
                    "\t`sort_id`,\n" +
                    "\t`create_time`,\n" +
                    "\t`update_time`,\n" +
                    "\t`create_name`,\n" +
                    "\t`update_name`\n" +
                    ")\n" +
                    "SELECT\n" +
                    "\tb.new_id AS id,\n" +
                    "\td.id AS play_id,\n" +
                    "\te.new_id AS temp_id,\n" +
                    "\ta.select_type,\n" +
                    "\ta.adds,\n" +
                    "\ta.max_adds,\n" +
                    "\tCASE ISNULL(a.multi_adds) WHEN 0 THEN a.multi_adds ELSE \"\" END AS multi_adds,\n" +
                    "\ta.rebate_scale,\n" +
                    "\ta.max_price,\n" +
                    "\ta.max_times,\n" +
                    "\ta.max_prize,\n" +
                    "\te.is_private,\n" +
                    "\ta.item_code,\n" +
                    "\ta.sort_id,\n" +
                    "\ta.create_time,\n" +
                    "\ta.update_time,\n" +
                    "\tCASE a.create_by WHEN 'sys' THEN 'sys' ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation x WHERE x.old_id=a.create_by) END AS create_name,\n" +
                    "\tCASE a.update_by WHEN 'sys' THEN 'sys' ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation y WHERE y.old_id=a.update_by) END AS update_name\n" +
                    "FROM\n" +
                    "\t" + oldDB + ".lot_site_game_template a\n" +
                    "INNER JOIN\n" +
                    "\t" + relationDB + ".sys_template_relation e ON a.temp_id=e.old_id\n" +
                    "LEFT JOIN\n" +
                    "\t" + relationDB + ".site_game_play_template_relation b ON a.id=b.old_id\n" +
                    "LEFT JOIN\n" +
                    "\t" + relationDB + ".lot_game_relation c ON a.game_id=c.char_id\n" +
                    "LEFT JOIN\n" +
                    "\t" + relationDB + ".lot_game_play_category_relation d ON a.play_id=d.char_id";
            relationSql.append(sql);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("channel" + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void siteSetting(OutputStreamWriter bufferStream, String oldDB, String relationDB, String newDB) {
        try {
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            String sql = "INSERT INTO `" + newDB + "`.`site_setting` (\n" +
                    "\t`id`,\n" +
                    "\t`site_id`,\n" +
                    "\t`site_code`,\n" +
                    "\t`single_switch`,\n" +
                    "\t`single_times`,\n" +
                    "\t`single_max_money`,\n" +
                    "\t`one_order_high_money`,\n" +
                    "\t`one_period_high_money`,\n" +
                    "\t`member_chip_money`\n" +
                    ")\n" +
                    "SELECT\n" +
                    "\tc.new_id AS id,\n" +
                    "\tb.new_id AS site_id,\n" +
                    "\tb.site_code,\n" +
                    "\tCASE a.single_switch WHEN \"T\" THEN 1 ELSE 0 END AS single_switch,\n" +
                    "  a.single_times AS single_times,\n" +
                    "\ta.single_max_money AS single_max_money,\n" +
                    "\ta.one_order_high_money AS one_order_high_money,\n" +
                    "\ta.one_period_high_money AS one_period_high_money,\n" +
                    "  CASE ISNULL(a.member_chip_money) WHEN 1 THEN NULL ELSE a.member_chip_money+0 END AS member_chip_money\n" +
                    "FROM\n" +
                    "\t" + oldDB + ".lot_site_setting a\n" +
                    "INNER JOIN " + relationDB + ".site_relation b ON a.site_id = b.old_id\n" +
                    "LEFT JOIN " + relationDB + ".site_setting_relation c ON a.id = c.old_id";
            relationSql.append(sql);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("channel" + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void sysTemplate(OutputStreamWriter bufferStream, String oldDB, String relationDB, String newDB) {
        try {
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            String sql = "INSERT INTO `" + newDB + "`.`sys_template` (\n" +
                    "\t`id`,\n" +
                    "\t`site_id`,\n" +
                    "\t`site_code`,\n" +
                    "\t`game_code`,\n" +
                    "\t`template_name`,\n" +
                    "\t`template_type`,\n" +
                    "\t`is_default`,\n" +
                    "\t`play_type`,\n" +
                    "\t`is_private`,\n" +
                    "\t`type`,\n" +
                    "\t`sys_template_id`,\n" +
                    "\t`rebate`,\n" +
                    "\t`is_del`,\n" +
                    "\t`is_enable`,\n" +
                    "\t`create_time`,\n" +
                    "\t`create_by`,\n" +
                    "\t`update_by`,\n" +
                    "\t`update_time`,\n" +
                    "\t`remark`,\n" +
                    "\t`sort_id`\n" +
                    ") SELECT\n" +
                    "\tb.new_id AS id,\n" +
                    "  c.new_id AS site_id,\n" +
                    "  c.site_code,\n" +
                    "  d.game_code,\n" +
                    "\ta.template_name,\n" +
                    "\tCASE a.template_type WHEN \"LIMIT\" THEN \"LIMIT_TEMPLATE\" WHEN \"WITHDRAW\" THEN \"WITHDRAW_TEMPLATE\" ELSE \"DEPOSIT_TEMPLATE\" END AS template_type,\n" +
                    "\tCASE a.is_default WHEN \"T\" THEN 1 ELSE 0 END AS is_default,\n" +
                    "\ta.play_type,\n" +
                    "\tCASE a.is_private WHEN \"T\" THEN 1 WHEN \"F\" THEN 0 ELSE NULL END AS is_private,\n" +
                    "\tCASE a.type WHEN \"system\" THEN \"SYSTEM\" WHEN \"site\" THEN \"SITE\" ELSE \"\" END AS type,\n" +
                    "\tCASE ISNULL(a.sys_template_id) WHEN 0 THEN (SELECT new_id FROM " + relationDB + ".sys_template_relation e WHERE a.sys_template_id=e.old_id) ELSE NULL END AS sys_template_id,\n" +
                    "\ta.rebate,\n" +
                    "\tCASE a.is_del WHEN \"T\" THEN 1 ELSE 0 END AS is_del,\n" +
                    "\tCASE a.is_enable WHEN \"F\" THEN 0 ELSE 1 END AS is_enable,\n" +
                    "\ta.create_time,\n" +
                    "\tCASE a.create_by WHEN 'sys' THEN 'sys' ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation x WHERE x.old_id=a.create_by) END AS create_name,\n" +
                    "\tCASE a.update_by WHEN 'sys' THEN 'sys' ELSE (SELECT user_name FROM " + relationDB + ".sys_user_relation y WHERE y.old_id=a.update_by) END AS update_name,\n" +
                    "\ta.update_time,\n" +
                    "\ta.remark,\n" +
                    "\t0\n" +
                    "FROM\n" +
                    "\t" + oldDB + ".lot_sys_template a\n" +
                    "LEFT JOIN\n" +
                    " " + relationDB + ".sys_template_relation b ON a.id=b.old_id \n" +
                    "LEFT JOIN\n" +
                    " " + relationDB + ".site_relation c ON a.site_id=c.old_id \n" +
                    "LEFT JOIN\n" +
                    " " + relationDB + ".lot_game_relation d ON a.game_id=d.char_id";
            relationSql.append(sql);
            relationSql.append(";");
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("channel" + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    //初始化出入款模板数据
    public static void initsysTemplateData(OutputStreamWriter bufferStream, String oldDB, String relationDB, String newDB) {
        try {
            long thisTime = System.currentTimeMillis();
            StringBuilder relationSql = new StringBuilder();
            String sql = "INSERT INTO `" + newDB + "`.`sys_template` (`id`, `site_id`, `site_code`, `game_code`, `template_name`, `template_type`, `is_default`, `play_type`, `is_private`, `type`, `sys_template_id`, `rebate`, `is_del`, `is_enable`, `create_time`, `create_by`, `update_by`, `update_time`, `remark`, `sort_id`) VALUES ('1001372430250369025', NULL, '', '', '系统(默认)出款模板', 'WITHDRAW_TEMPLATE', '1', '', NULL, 'SYSTEM', NULL, '0', '0', '1', '2018-06-14 12:46:28', '', '', '2018-07-03 10:57:13', '', '0');\n" +
                    "INSERT INTO `" + newDB + "`.`sys_template` (`id`, `site_id`, `site_code`, `game_code`, `template_name`, `template_type`, `is_default`, `play_type`, `is_private`, `type`, `sys_template_id`, `rebate`, `is_del`, `is_enable`, `create_time`, `create_by`, `update_by`, `update_time`, `remark`, `sort_id`) VALUES ('1001373036532817921', NULL, '', '', '系统(默认)入款模板', 'DEPOSIT_TEMPLATE', '1', '', NULL, 'SYSTEM', NULL, '0', '0', '1', '2018-06-14 12:46:48', '', '', '2018-07-03 10:57:17', '', '0');";
            relationSql.append(sql);
            bufferStream.write(relationSql.toString());
            bufferStream.flush();
            logger.info("channel" + " migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}
