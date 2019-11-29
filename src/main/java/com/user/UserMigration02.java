package com.user;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.log4j.Logger;

import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.Constant.*;

public class UserMigration02 {

    static Logger logger = Logger.getLogger(UserMigration02.class);

    public static void executeRelation(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_rank", DB_RELATION + ".user_rank_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_score_way", DB_RELATION + ".user_rank_score_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_score", DB_RELATION + ".user_score_record_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_sign", DB_RELATION + ".user_sign_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_rebate_main", DB_RELATION + ".rebate_rule_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_rebate_son", DB_RELATION + ".rebate_rule_info_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_rebate_search", DB_RELATION + ".rebate_result_relation");
        insertRelation(resultSet, statement, bufferStream, DB_MAIN + ".lot_user_rebate_detail", DB_RELATION + ".rebate_result_info_relation");
    }

    public static void executeData(ResultSet resultSet, Statement statement, OutputStreamWriter out) {
        insertUserRank(resultSet, statement, out);
        insertUserRankScore(resultSet, statement, out);
        insertUserRankRecord(resultSet, statement, out);
        insertUserSign(resultSet, statement, out);
        insertRebateRule(out);
        insertRebateRuleInfo(out);
        insertRebateResult(out);
        insertRebateResultInfo(out);
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

    /**
     * migration user rank table
     */
    public static void insertUserRank(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user_rank migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_USER + ".user_rank " +
                    " (id, site_id, site_code, rank_name, rank_level, rank_img, max_score, " +
                    " remark, is_del, create_time, create_by, update_time, update_by) " +
                    " SELECT rankRelation.new_id,siteRelation.new_id,siteRelation.site_code," +
                    " rank.rank_name,rank.rank_code,rank.rank_img,rank.max_score," +
                    " rank.remark,CASE WHEN rank.is_del = 'F' THEN 0 ELSE 1 END," +
                    " rank.create_time,sysUserRelation.user_name,rank.update_time," +
                    " sysUserRelation2.user_name FROM " + DB_MAIN + ".lot_user_rank rank" +
                    " LEFT JOIN user_rank_relation rankRelation ON rank.id = rankRelation.old_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation ON rank.create_by = sysUserRelation.old_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation2 ON rank.update_by = sysUserRelation2.old_id" +
                    " LEFT JOIN site_relation siteRelation ON siteRelation.old_id = rank.site_id" +
                    " GROUP BY rank.id;");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("user_rank migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * migration user rank score table
     */
    public static void insertUserRankScore(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user_rank_score migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_USER + ".user_rank_score " +
                    " (id, rank_id, score_name, score_code, score_val, is_enable, remark, is_del, create_time, " +
                    " create_by, update_time, update_by)" +
                    " SELECT scoreRelation.new_id,rankRelation.new_id,score.way_name,score.way_code," +
                    " score.way_val,CASE WHEN score.is_enable = 'F' THEN 0 ELSE 1 END,score.remark," +
                    " CASE WHEN score.is_del = 'F' THEN 0 ELSE 1 END,score.create_time,sysUserRelation.user_name," +
                    " score.update_time,sysUserRelation2.user_name FROM " + DB_MAIN + ".lot_user_score_way score" +
                    " LEFT JOIN user_rank_score_relation scoreRelation ON score.id = scoreRelation.old_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation ON score.create_by = sysUserRelation.old_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation2 ON score.update_by = sysUserRelation.old_id" +
                    " LEFT JOIN user_rank_relation rankRelation ON score.rank_id = rankRelation.old_id" +
                    " GROUP BY score.id;");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("user_rank_score migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * migration user score record table
     */
    public static void insertUserRankRecord(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user_rank_record migration begin!");
            long thisTime = System.currentTimeMillis();

            String sqlHead = "INSERT INTO " + DB_USER + ".user_score_record " +
                    " (id, site_id, site_code, user_id, user_name, score_name, score_code, score_val,final_score,remark, " +
                    " create_time,is_del)";

            long size = 0;
            resultSet = statement.executeQuery("SELECT count(new_id) AS num FROM user_score_record_relation");
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 3000.0);//向上取整
            for (int i = 0; i < ceil; i++) {
                String insertSql = "SELECT relation.new_id,siteRelation.new_id,siteRelation.site_code," +
                        " userRelation.new_id,userRelation.user_name,score.name,score.score_type,score.score,score.surplus_score," +
                        " score.remark,score.create_time,CASE WHEN score.is_del = 'F' THEN 0 ELSE 1 END " +
                        " from " + DB_MAIN + ".lot_user_score score LEFT JOIN user_score_record_relation relation on score.id=relation.old_id " +
                        " LEFT JOIN user_relation userRelation on score.user_id=userRelation.old_id " +
                        " LEFT JOIN site_relation siteRelation on score.site_id=siteRelation.old_id " +
                        " GROUP BY score.id limit " + i * LIMIT + "," + LIMIT + ";";
                bufferStream.write(sqlHead + insertSql);
                bufferStream.flush();
            }
            logger.info("user_rank_record migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * migration user sign table
     * 关联用户最终表 获取站点数据
     */
    public static void insertUserSign(ResultSet resultSet, Statement statement, OutputStreamWriter bufferStream) {
        try {
            logger.info("user_sign migration begin!");
            long thisTime = System.currentTimeMillis();

            String sqlHead = "INSERT INTO " + DB_USER + ".user_sign (id, site_id, user_id,sign_time)";
            long size = 0;
            resultSet = statement.executeQuery("SELECT count(new_id) AS num FROM user_sign_relation");
            while (resultSet.next()) {
                size = resultSet.getLong("num");
            }
            double ceil = Math.ceil(size / 3000.0);//向上取整
            for (int i = 0; i < ceil; i++) {
                String insertSql = "SELECT relation.new_id,user.site_id,user.id,sign.sign_time " +
                        " from " + DB_MAIN + ".lot_user_sign sign LEFT JOIN user_sign_relation relation on sign.id=relation.old_id " +
                        " LEFT JOIN user_relation userRelation on sign.user_id=userRelation.old_id " +
                        " LEFT JOIN " + DB_USER + ".user user on user.id=userRelation.new_id " +
                        " GROUP BY sign.id limit " + i * LIMIT + "," + LIMIT + ";";
                bufferStream.write(sqlHead + insertSql);
                bufferStream.flush();
            }
            logger.info("user_sign migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * migration rebate rule table
     */
    public static void insertRebateRule(OutputStreamWriter bufferStream) {
        try {
            logger.info("rebate_rule migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_USER + ".rebate_rule " +
                    " (id,name,site_id,site_code,sort,remark, is_del, create_time, create_by, update_time, update_by) " +
                    " SELECT relation.new_id,main.name,siteRelation.new_id,siteRelation.site_code," +
                    " main.sort,main.remark,CASE WHEN main.is_del = 'F' THEN 0 ELSE 1 END," +
                    " main.create_time,sysUserRelation.user_name,main.update_time," +
                    " sysUserRelation2.user_name FROM " + DB_MAIN + ".lot_user_rebate_main main" +
                    " LEFT JOIN rebate_rule_relation relation ON main.id = relation.old_id" +
                    " LEFT JOIN site_relation siteRelation ON siteRelation.old_id = main.site_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation ON main.create_by = sysUserRelation.old_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation2 ON main.update_by = sysUserRelation2.old_id" +
                    " GROUP BY main.id;");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("rebate_rule migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * migration rebate rule info table
     */
    public static void insertRebateRuleInfo(OutputStreamWriter bufferStream) {
        try {
            logger.info("rule_info migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_USER + ".rebate_rule_info " +
                    " (id, rule_id,rebate_most,effective_bets, " +
                    " dpc_rebate, lhc_rebate0,lhc_rebate1,lhc_rebate2,lhc_rebate3, " +
                    " ty_rebate,qt_rebate,tycp_rebate,flc_rebate,gpc_rebate," +
                    " remark, is_del, create_time, create_by, update_time, update_by) " +
                    " SELECT relation.new_id,ruleRelation.new_id,son.rebate_limit_amount,son.all_effective_amount," +
                    " son.dpc_rebate,son.lhc_rebate0,son.lhc_rebate1,son.lhc_rebate2,son.lhc_rebate3," +
                    " son.ty_rebate,son.qt_rebate,son.tycp_rebate,son.flc_rebate,son.gpc_rebate," +
                    " son.remark,CASE WHEN son.is_del = 'F' THEN 0 ELSE 1 END," +
                    " son.create_time,sysUserRelation.user_name,son.update_time," +
                    " sysUserRelation2.user_name FROM " + DB_MAIN + ".lot_user_rebate_son son" +
                    " LEFT JOIN rebate_rule_info_relation relation ON son.id = relation.old_id" +
                    " LEFT JOIN rebate_rule_relation ruleRelation ON son.main_id = ruleRelation.old_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation ON son.create_by = sysUserRelation.old_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation2 ON son.update_by = sysUserRelation2.old_id" +
                    " GROUP BY son.id;");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("rule_info migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * migration rebate result table
     */
    public static void insertRebateResult(OutputStreamWriter bufferStream) {
        try {
            logger.info("rebate_result migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_USER + ".rebate_result " +
                    " (id,event_name,site_id,site_code,rule_id,begin_time,end_time,do_time,status," +
                    " final_rebate_num,rebate_num,all_rebates," +
                    " remark, is_del, create_time, create_by, update_time, update_by) " +
                    " SELECT relation.new_id,main.event_name,siteRelation.new_id,siteRelation.site_code," +
                    " ruleRelation.new_id,DATE_FORMAT(main.begin_time,'%Y-%m-%d'),DATE_FORMAT(main.end_time,'%Y-%m-%d'),main.implement_time," +
                    " main.rebate_status,main.actual_rebate_number,main.all_rebate_number,main.all_rebate_amount," +
                    " main.remark,CASE WHEN main.is_del = 'F' THEN 0 ELSE 1 END," +
                    " main.create_time,sysUserRelation.user_name,main.update_time," +
                    " sysUserRelation2.user_name FROM " + DB_MAIN + ".lot_user_rebate_search main" +
                    " LEFT JOIN rebate_result_relation relation ON main.id = relation.old_id" +
                    " LEFT JOIN site_relation siteRelation ON siteRelation.old_id = main.site_id" +
                    " LEFT JOIN rebate_rule_relation ruleRelation ON ruleRelation.old_id = main.main_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation ON main.create_by = sysUserRelation.old_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation2 ON main.update_by = sysUserRelation2.old_id" +
                    " GROUP BY main.id;");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("rebate_result migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * migration rebate result info table
     */
    public static void insertRebateResultInfo(OutputStreamWriter bufferStream) {
        try {
            logger.info("result_info migration begin!");
            long thisTime = System.currentTimeMillis();

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO " + DB_USER + ".rebate_result_info " +
                    " (id, result_id,user_id,user_name,high_level_Account,effective_bets,status,all_rebates," +
                    " dpc_rebate, lhc0_rebate,lhc1_rebate,lhc2_rebate,lhc3_rebate, " +
                    " ty_rebate,qt_rebate,tycp_rebate,flc_rebate,gpc_rebate," +
                    " dpc_bets, lhc0_bets,lhc1_bets,lhc2_bets,lhc3_bets, " +
                    " ty_bets,qt_bets,tycp_bets,flc_bets,gpc_bets," +
                    " remark, is_del, create_time, create_by, update_time, update_by) " +
                    " SELECT relation.new_id,resultRelation.new_id,userRelation.new_id,userRelation.user_name," +
                    " userRelation2.new_id,son.all_effective_amount,son.detail_status,son.all_rebate_amount," +
                    " son.dpc_water,son.lhc0_water,son.lhc1_water,son.lhc2_water,son.lhc3_water," +
                    " son.ty_water,son.qt_water,son.tycp_water,son.flc_water,son.gpc_water," +
                    " son.dpc_betting,son.lhc0_betting,son.lhc1_betting,son.lhc2_betting,son.lhc3_betting," +
                    " son.ty_betting,son.qt_betting,son.tycp_betting,son.flc_betting,son.gpc_betting," +
                    " son.remark,CASE WHEN son.is_del = 'F' THEN 0 ELSE 1 END," +
                    " son.create_time,sysUserRelation.user_name,son.update_time," +
                    " sysUserRelation2.user_name FROM " + DB_MAIN + ".lot_user_rebate_detail son" +
                    " LEFT JOIN rebate_result_info_relation relation ON son.id = relation.old_id" +
                    " LEFT JOIN rebate_result_relation resultRelation ON son.search_id = resultRelation.old_id" +
                    " LEFT JOIN user_rebate_relation userRelation ON son.user_id = userRelation.old_id" +
                    " LEFT JOIN user_rebate_relation userRelation2 ON son.parent_user_id = userRelation2.old_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation ON son.create_by = sysUserRelation.old_id" +
                    " LEFT JOIN sys_user_relation sysUserRelation2 ON son.update_by = sysUserRelation2.old_id" +
                    " GROUP BY son.id;");
            bufferStream.write(insertSql.toString());
            bufferStream.flush();
            logger.info("result_info migration end!used time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public static void main(String[] args) {

//        SELECT(SELECT count(id) FROM db_main.lot_user_rank),(SELECT count(*) FROM db_relation.user_rank_relation),(SELECT count(*) FROM db_user.user_rank),(SELECT count(id) FROM db_main.lot_user_score_way),(SELECT count(*) FROM db_relation.user_rank_score_relation),(SELECT count(*) FROM db_user.user_rank_score),(SELECT count(id) FROM db_main.lot_user_score),(SELECT count(*) FROM db_relation.user_score_record_relation),(SELECT count(*) FROM db_user.user_score_record),(SELECT count(id) FROM db_main.lot_user_sign),(SELECT count(*) FROM db_relation.user_sign_relation),(SELECT count(*) FROM db_user.user_sign),(SELECT count(id) FROM db_main.lot_user_rebate_main),(SELECT count(*) FROM db_relation.rebate_rule_relation),(SELECT count(*) FROM db_user.rebate_rule),(SELECT count(id) FROM db_main.lot_user_rebate_son),(SELECT count(*) FROM db_relation.rebate_rule_info_relation),(SELECT count(*) FROM db_user.rebate_rule_info),(SELECT count(id) FROM db_main.lot_user_rebate_search),(SELECT count(*) FROM db_relation.rebate_result_relation),(SELECT count(*) FROM db_user.rebate_result),(SELECT count(id) FROM db_main.lot_user_rebate_detail),(SELECT count(*) FROM db_relation.rebate_result_info_relation),(SELECT count(*) FROM db_user.rebate_result_info);

//        TRUNCATE user_rank_relation;TRUNCATE user_rank_score_relation;TRUNCATE user_score_record_relation;TRUNCATE user_sign_relation;TRUNCATE rebate_rule_relation;TRUNCATE rebate_rule_info_relation;TRUNCATE rebate_result_relation;TRUNCATE rebate_result_info_relation;TRUNCATE user_bonus_main_relation;TRUNCATE user_bonus_relation;TRUNCATE user_bonus_son_relation;TRUNCATE user_bonus_setting_relation;TRUNCATE user_refer_relation;TRUNCATE user_relation;TRUNCATE sys_user_relation;TRUNCATE sys_user_dept_relation;TRUNCATE log_user_relation;TRUNCATE user_bank_relation;TRUNCATE user_info_relation;TRUNCATE user_rebate_relation;TRUNCATE user_favorite_relation;

    }


}
