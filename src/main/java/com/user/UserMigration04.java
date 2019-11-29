package com.user;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.log4j.Logger;

import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.Constant.*;

/**
 * 代理分红，推广链接 中间表操作
 */
public class UserMigration04 {

    static Logger logger = Logger.getLogger(UserMigration04.class);

    public static void executeRelation(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_bonus", DB_RELATION + ".user_bonus_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_bonus_main", DB_RELATION + ".user_bonus_main_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_bonus_son", DB_RELATION + ".user_bonus_son_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_bonus_setting", DB_RELATION + ".user_bonus_setting_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_refer", DB_RELATION + ".user_refer_relation");
    }

    public static void executeData(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        insertUserBonusMain(bufferStream);
        insertUserBonusSon(bufferStream);
        insertUserBonusSetting(bufferStream);
        insertUserBonus(resultSet, statement, bufferStream);
        insertUserRefer(bufferStream);
    }


    /**
     * 封装 生成关联表数据方法
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


    public static void insertUserBonus(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user_bonus migration begin!");
            long thisTime = System.currentTimeMillis();

            String sqlHead = "INSERT INTO " + DB_USER + ".user_bonus (\n" +
                    "\tid,\n" +
                    "\tgame_id,\n" +
                    "\tgame_category_id,\n" +
                    "\tplay_type,\n" +
                    "\tto_user_id,\n" +
                    "\tuser_id,\n" +
                    "\tperiods,\n" +
                    "\tall_betting_amount,\n" +
                    "\tall_betting_number,\n" +
                    "\trebate,\n" +
                    "\texamine_state,\n" +
                    "\twin_amount,\n" +
                    "\tteam_make_water,\n" +
                    "\twin_lose,\n" +
                    "\tactivity,\n" +
                    "\tactual_win_lose,\n" +
                    "\tbonus,\n" +
                    "\tauditor_id,\n" +
                    "\tcreate_time,\n" +
                    "\tcreate_by,\n" +
                    "\tupdate_time,\n" +
                    "\tupdate_by,\n" +
                    "\tis_del,\n" +
                    "\tdata_type,\n" +
                    "\tremark,\n" +
                    "\tsite_id,\n" +
                    "\tmain_id,\n" +
                    "\tsite_code\n" +
                    ")";

            long size = 0;
            resultSet = statement.executeQuery("SELECT count(new_id) AS num FROM user_bonus_relation");
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 3000.0);//向上取整
            for (int i = 0; i < ceil; i++) {
                String selectSql = "SELECT\n" +
                        "\tubr.new_id AS id,\n" +
                        "\tgame_id,\n" +
                        "\tgame_category_id,\n" +
                        "\tplay_type,\n" +
                        "\turr.new_id AS to_user_id,\n" +
                        "\tur.new_id AS user_id,\n" +
                        "\tperiods,\n" +
                        "\tall_betting_amount,\n" +
                        "\tall_betting_number,\n" +
                        "\trebate,\n" +
                        "\texamine_state,\n" +
                        "\twin_amount,\n" +
                        "\tteam_make_water,\n" +
                        "\twin_lose,\n" +
                        "\tactivity,\n" +
                        "\tactual_win_lose,\n" +
                        "\tbonus,\n" +
                        "\t0,\n" +
                        "\tcreate_time,\n" +
                        "\tcreate_by,\n" +
                        "\tupdate_time,\n" +
                        "\tupdate_by,\n" +
                        "\tCASE ub.is_del\n" +
                        "WHEN 'T' THEN\n" +
                        "\t1\n" +
                        "ELSE\n" +
                        "\t0\n" +
                        "END AS is_del,\n" +
                        " data_type,\n" +
                        " remark,\n" +
                        " sr.new_id AS site_id,\n" +
                        " ubmr.new_id AS main_id,\n" +
                        " sr.site_code\n" +
                        "FROM\n" + DB_MAIN + ".lot_user_bonus ub\n" +
                        "LEFT JOIN user_bonus_main_relation ubmr ON ubmr.old_id = ub.main_id\n" +
                        "LEFT JOIN user_relation ur ON ur.old_id = ub.user_id\n" +
                        "LEFT JOIN user_relation urr ON urr.old_id = ub.to_user_id\n" +
                        "LEFT JOIN site_relation sr ON sr.old_id = ub.site_id\n" +
                        "LEFT JOIN user_bonus_relation ubr ON ubr.old_id = ub.id " +
                        "GROUP BY ub.id limit " + i * LIMIT + "," + LIMIT + ";";
                bufferStream.write((sqlHead + selectSql.toString()));
                bufferStream.flush();
            }
            logger.info("user_bonus migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public static void insertUserBonusMain(OutputStreamWriter bufferStream) {
        try {
            logger.info("user_bonus_main migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_USER + ".user_bonus_main (\n" +
                    "\tid,\n" +
                    "\tgame_id,\n" +
                    "\tgame_category_id,\n" +
                    "\tplay_type,\n" +
                    "\tto_user_id,\n" +
                    "\tuser_id,\n" +
                    "\trebate_level,\n" +
                    "\tbonus_rule,\n" +
                    "\tbonus_strategy,\n" +
                    "\tbonus_cycle,\n" +
                    "\tcreate_by,\n" +
                    "\tcreate_time,\n" +
                    "\tupdate_by,\n" +
                    "\tupdate_time,\n" +
                    "\tis_del,\n" +
                    "\tdistribute,\n" +
                    "\tsign_status,\n" +
                    "\tsetting_type,\n" +
                    "\tremark,\n" +
                    "\tsite_id,\n" +
                    "\tsite_code\n" +
                    ") SELECT\n" +
                    "\tubmr.new_id AS id,\n" +
                    "\tbm.game_id,\n" +
                    "\tbm.game_category_id,\n" +
                    "\tbm.play_type,\n" +
                    "\turr.new_id AS to_user_id,\n" +
                    "\tur.new_id AS user_id,\n" +
                    "\tbm.rebate_level,\n" +
                    "\tbm.bonus_rule,\n" +
                    "\tbm.bonus_strategy,\n" +
                    "\tbm.bonus_cycle,\n" +
                    "\turrr.user_name AS create_by,\n" +
                    "\tbm.create_time,\n" +
                    "\turrrr.user_name AS update_by,\n" +
                    "\tbm.update_time,\n" +
                    "\tCASE bm.is_del\n" +
                    "WHEN 'T' THEN\n" +
                    "\t1\n" +
                    "ELSE\n" +
                    "\t0\n" +
                    "END AS is_del,\n" +
                    " bm.distribute,\n" +
                    " bm.sign_status,\n" +
                    " bm.setting_type,\n" +
                    " bm.remark,\n" +
                    " sr.new_id AS site_id,\n" +
                    " sr.site_code\n" +
                    "FROM\n" +
                    "\t " + DB_MAIN + ".lot_user_bonus_main bm\n" +
                    "LEFT JOIN user_relation ur ON ur.old_id = bm.user_id\n" +
                    "LEFT JOIN site_relation sr ON sr.old_id = bm.site_id\n" +
                    "LEFT JOIN user_relation urr ON urr.old_id = bm.to_user_id\n" +
                    "LEFT JOIN user_relation urrr ON urrr.old_id = bm.create_by\n" +
                    "LEFT JOIN user_relation urrrr ON urrrr.old_id = bm.update_by\n" +
                    "LEFT JOIN user_bonus_main_relation ubmr ON bm.id = ubmr.old_id;\n" +
                    "\n");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("user_bonus_main migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public static void insertUserBonusSon(OutputStreamWriter bufferStream) {
        try {
            logger.info("user_bonus_son migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_USER + ".user_bonus_son (\n" +
                    "\tid,\n" +
                    "\tmain_id,\n" +
                    "\tamount,\n" +
                    "\trebate,\n" +
                    "\tvalid_member,\n" +
                    "\tbonus_mode,\n" +
                    "\tbonus_amount,\n" +
                    "\tlimit_amount,\n" +
                    "\tbonus_per,\n" +
                    "\tcreate_time,\n" +
                    "\tupdate_time,\n" +
                    "\tcreate_by,\n" +
                    "\tupdate_by,\n" +
                    "\tis_del,\n" +
                    "\tremark\n" +
                    ") SELECT\n" +
                    "\tubsr.new_id AS id,\n" +
                    "\tubmr.new_id AS main_id,\n" +
                    "\tamount,\n" +
                    "\trebate,\n" +
                    "\tvalid_member,\n" +
                    "\tbonus_mode,\n" +
                    "\tbonus_amount,\n" +
                    "\tlimit_amount,\n" +
                    "\tbonus_per,\n" +
                    "\tcreate_time,\n" +
                    "\tupdate_time,\n" +
                    "\tur.user_name AS create_by,\n" +
                    "\turr.user_name AS update_by,\n" +
                    "\tCASE ubs.is_del\n" +
                    "WHEN 'T' THEN\n" +
                    "\t1\n" +
                    "ELSE\n" +
                    "\t0\n" +
                    "END AS is_del,\n" +
                    " remark\n" +
                    "FROM\n" +
                    "\t " + DB_MAIN + ".lot_user_bonus_son ubs\n" +
                    "LEFT JOIN user_bonus_main_relation ubmr ON ubmr.old_id = ubs.main_id\n" +
                    "LEFT JOIN user_relation ur ON ur.old_id = ubs.create_by\n" +
                    "LEFT JOIN user_relation urr ON urr.old_id = ubs.update_by\n" +
                    "LEFT JOIN user_bonus_son_relation ubsr ON ubsr.old_id = ubs.id;");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("user_bonus_son migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public static void insertUserBonusSetting(OutputStreamWriter bufferStream) {
        try {
            logger.info("user_bonus_setting migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_USER + ".user_bonus_setting (\n" +
                    "\tid,\n" +
                    "\tcreate_time,\n" +
                    "\tcreate_by,\n" +
                    "\tupdate_time,\n" +
                    "\tupdate_by,\n" +
                    "\tis_del,\n" +
                    "\tremark,\n" +
                    "\trebate,\n" +
                    "\tbonus_mode,\n" +
                    "\tvalid_member,\n" +
                    "\tteam_daily_amount,\n" +
                    "\tteam_actual_lose,\n" +
                    "\tbonus,\n" +
                    "\tdays_per,\n" +
                    "\tyk_per,\n" +
                    "\tmain_id\n" +
                    ") SELECT\n" +
                    "\tubsr.new_id AS id,\n" +
                    "\tcreate_time,\n" +
                    "\tur.user_name AS create_by,\n" +
                    "\tupdate_time,\n" +
                    "\turr.user_name AS update_by,\n" +
                    "\tCASE ubs.is_del\n" +
                    "WHEN 'T' THEN\n" +
                    "\t1\n" +
                    "ELSE\n" +
                    "\t0\n" +
                    "END AS is_del,\n" +
                    " remark,\n" +
                    " rebate,\n" +
                    " bonus_mode,\n" +
                    " valid_member,\n" +
                    " team_daily_amount,\n" +
                    " team_actual_lose,\n" +
                    " bonus,\n" +
                    " days_per,\n" +
                    " yk_per,\n" +
                    " ubmr.new_id AS main_id\n" +
                    "FROM\n" +
                    "\t " + DB_MAIN + ".lot_user_bonus_setting ubs\n" +
                    "LEFT JOIN user_bonus_main_relation ubmr ON ubs.main_id = ubmr.old_id\n" +
                    "LEFT JOIN user_relation ur ON ur.old_id = ubs.create_by\n" +
                    "LEFT JOIN user_relation urr ON urr.old_id = ubs.update_by\n" +
                    "LEFT JOIN user_bonus_setting_relation ubsr ON ubsr.old_id = ubs.id;");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("user_bonus_setting migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public static void insertUserRefer(OutputStreamWriter bufferStream) {
        try {
            logger.info("user_refer migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO  " + DB_USER + ".user_refer  (\n" +
                    "\t id ,\n" +
                    "\t user_id ,\n" +
                    "\t site_id ,\n" +
                    "\t site_code ,\n" +
                    "\t refer_code ,\n" +
                    "\t refer_url ,\n" +
                    "\t domain_url ,\n" +
                    "\t flc_rebate ,\n" +
                    "\t gpc_rebate ,\n" +
                    "\t tycp_rebate ,\n" +
                    "\t qt_rebate ,\n" +
                    "\t ty_rebate ,\n" +
                    "\t lhc_rebate3 ,\n" +
                    "\t lhc_rebate2 ,\n" +
                    "\t lhc_rebate1 ,\n" +
                    "\t lhc_rebate0 ,\n" +
                    "\t dpc_rebate ,\n" +
                    "\t is_proxy ,\n" +
                    "\t is_app ,\n" +
                    "\t is_del ,\n" +
                    "\t is_enable ,\n" +
                    "\t link_type ,\n" +
                    "\t remark ,\n" +
                    "\t create_by ,\n" +
                    "\t create_time ,\n" +
                    "\t update_by ,\n" +
                    "\t update_time \n" +
                    ") SELECT\n" +
                    "\turu.new_id AS id,ur.new_id AS user_id,\n" +
                    "\tsr.new_id AS site_id,sr.site_code,refer_code,refer_url," +
                    "\tdomain_url,\n" +
                    "\twlot_rebate,\n" +
                    "\thlot_rebate,\n" +
                    "\tslot_rebate,\n" +
                    "\tolot_rebate,\n" +
                    "\tgamb_rebate,\n" +
                    "\tlhc_rebate3,\n" +
                    "\tlhc_rebate2,\n" +
                    "\tlhc_rebate1,\n" +
                    "\tlhc_rebate0,\n" +
                    "\tdpc_rebate,\n" +
                    "\t CASE is_proxy WHEN 'T' THEN 1 ELSE 0 END,\n" +
                    "\t CASE is_app WHEN 'T' THEN 1 ELSE 0 END,\n" +
                    "\tCASE lur.is_del\n" +
                    "WHEN 'T' THEN\n" +
                    "\t1\n" +
                    "ELSE\n" +
                    "\t0\n" +
                    "END AS is_del,\n" +
                    " CASE lur.is_enable\n" +
                    "WHEN 'T' THEN\n" +
                    "\t1\n" +
                    "ELSE\n" +
                    "\t0\n" +
                    "END AS is_enable,0,\n" +
                    "\tremark,\n" +
                    "\turr.user_name AS create_by,\n" +
                    "\tcreate_time,\n" +
                    "\turrr.user_name AS update_by,\n" +
                    "\tupdate_time\n" +
                    "FROM\n" +
                    "\t " + DB_MAIN + ".lot_user_refer lur \n" +
                    "LEFT JOIN user_relation ur ON ur.old_id = lur.user_id\n" +
                    "LEFT JOIN user_relation urr ON urr.old_id = lur.create_by\n" +
                    "LEFT JOIN user_relation urrr ON urrr.old_id = lur.update_by\n" +
                    "LEFT JOIN site_relation sr ON sr.old_id = lur.site_id\n" +
                    "LEFT JOIN user_refer_relation uru ON uru.old_id = lur.id;");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("user_refer migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
