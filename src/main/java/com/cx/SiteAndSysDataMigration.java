package com.cx;

import com.Constant;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.Constant.DB_MAIN;
import static com.Constant.DB_RELATION;

public class SiteAndSysDataMigration {

    static Logger logger = Logger.getLogger(SiteAndSysDataMigration.class);

    static Statement statement = null;
    static ResultSet resultSet = null;

    public static void main(String[] args) {

    }

    /**
     * 生成中间表数据sql脚本
     */
    public static void createRelationSqlScript(ResultSet globalResultSet, Statement globalStatement, OutputStreamWriter outStream) {

        resultSet = globalResultSet;
        statement = globalStatement;

        try {
            /** 站点中间表sql脚本 */
            String site_relation_sql = insertSiteRelation(DB_MAIN + ".lot_site", DB_RELATION + ".site_relation");
            outStream.write("-- -------------------------\n");
            outStream.write("-- 站点中间表sql脚本\n");
            outStream.write("-- -------------------------\n");
            outStream.write(site_relation_sql);
            outStream.write("\n\n");
            outStream.flush();

            /** 注册会员配置中间表sql脚本 */
            String site_register_config_relation_sql = insertRelation("SELECT id FROM " + DB_MAIN + ".lot_site_reg_config", DB_RELATION + ".site_register_config_relation");
            outStream.write("-- -------------------------\n");
            outStream.write("-- 注册会员配置中间表sql脚本\n");
            outStream.write("-- -------------------------\n");
            outStream.write(site_register_config_relation_sql);
            outStream.write("\n\n");
            outStream.flush();

            /** 字典中间表sql脚本 */
            String key_value_relation_sql = insertRelation("SELECT id FROM " + DB_MAIN + ".lot_sys_key_value", DB_RELATION + ".key_value_relation");
            outStream.write("-- -------------------------\n");
            outStream.write("-- 字典中间表sql脚本\n");
            outStream.write("-- -------------------------\n");
            outStream.write(key_value_relation_sql);
            outStream.write("\n\n");
            outStream.flush();

            /** 系统配置中间表sql脚本 */
            String config_relation_sql = insertRelation("SELECT id FROM " + DB_MAIN + ".lot_sys_config where site_id NOT IN ('0','SYS')", DB_RELATION + ".config_relation");
            outStream.write("-- -------------------------\n");
            outStream.write("-- 系统配置中间表sql脚本\n");
            outStream.write("-- -------------------------\n");
            outStream.write(config_relation_sql);
            outStream.write("\n\n");
            outStream.flush();

            /** 银行中间表sql脚本 */
            String bank_relation_sql = insertRelation("SELECT id FROM " + DB_MAIN + ".lot_sys_bank", DB_RELATION + ".bank_relation");
            outStream.write("-- -------------------------\n");
            outStream.write("-- 银行中间表sql脚本\n");
            outStream.write("-- -------------------------\n");
            outStream.write(bank_relation_sql);
            outStream.write("\n\n");
            outStream.flush();

            /** 区域中间表sql脚本 */
            String area_relation_sql = insertRelation("SELECT id FROM " + DB_MAIN + ".lot_sys_area", DB_RELATION + ".area_relation");
            outStream.write("-- -------------------------\n");
            outStream.write("-- 区域中间表sql脚本\n");
            outStream.write("-- -------------------------\n");
            outStream.write(area_relation_sql);
            outStream.write("\n\n");
            outStream.flush();

            /** 银行网点中间表sql脚本 */
            String bank_branch_relation_sql = insertRelation("SELECT id FROM " + DB_MAIN + ".lot_sys_bank_network", DB_RELATION + ".bank_branch_relation");
            outStream.write("-- -------------------------\n");
            outStream.write("-- 银行网点中间表sql脚本\n");
            outStream.write("-- -------------------------\n");
            outStream.write(bank_branch_relation_sql);
            outStream.write("\n\n");
            outStream.flush();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 生成新表数据迁移Sql脚本
     */
    public static void createNewTableDataSqlScript(OutputStreamWriter bufferStream) {
        try {
            /** 站点表数据sql脚本 */
            String site_sql = createSiteTableData();
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write("-- 站点(site)表sql脚本\n" );
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write(site_sql );
            bufferStream.write("\n\n" );
            bufferStream.flush();

            /** 注册会员配置sql脚本 */
            String siteRegisterConfig_sql = createSiteRegisterConfigTableData();
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write("-- 站点会员注册配置(site_Register_Config)表sql脚本\n" );
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write(siteRegisterConfig_sql );
            bufferStream.write("\n\n" );
            bufferStream.flush();

            /** 系统配置sql脚本 */
            String config_sql = createConfigTableData();
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write("-- 系统配置(config)表sql脚本\n" );
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write(config_sql );
            bufferStream.write("\n\n" );
            bufferStream.flush();

            /** 字典sql脚本 */
            String keyValue_sql = createKeyValueTableData();
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write("-- 字典(key_value)表sql脚本\n" );
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write(keyValue_sql );
            bufferStream.write("\n\n" );
            bufferStream.flush();

            /** 区域sql脚本 */
            String area_sql = createAreaTableData();
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write("-- 区域(area)表sql脚本\n" );
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write(area_sql );
            bufferStream.write("\n\n" );
            bufferStream.flush();

            /** 银行sql脚本 */
            String bank_sql = createBankTableData();
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write("-- 银行(bank)表sql脚本\n" );
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write(bank_sql );
            bufferStream.write("\n\n" );
            bufferStream.flush();

            /** 银行网点sql脚本 */
            String bankBranch_sql = createBankBranchTableData();
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write("-- 银行网点(bank_branch)表sql脚本\n" );
            bufferStream.write("-- -------------------------\n" );
            bufferStream.write(bankBranch_sql );
            bufferStream.write("\n\n" );
            bufferStream.flush();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * 插入中间表数据(site表)
     * 获取id为0的站点数据 为它新增一个指定默认数据
     * 获取其他所有站点数据 并生成随机数site code
     *
     * @return
     */
    public static String insertSiteRelation(String oldTable, String relationTable) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            long beginTime = System.currentTimeMillis();

            stringBuilder.append("insert into " + relationTable + " (" +
                    "old_id," +
                    "new_id," +
                    "site_code," +
                    "site_title" +
                    ") values");

            String lotmallTitle = "平台站点";
            resultSet = statement.executeQuery("SELECT id,site_title  FROM " + oldTable + " where id ='0'");
            if (resultSet.next()) {
                lotmallTitle = resultSet.getString("site_title");
            }
            stringBuilder.append("('0',0,'lotmall-0','" + lotmallTitle + "'),");


            stringBuilder.append("('sys',0,'lotmall-0','" + lotmallTitle + "'),");
            stringBuilder.append("('site',0,'lotmall-0','" + lotmallTitle + "'),");

            resultSet = statement.executeQuery("SELECT count(*) FROM " + oldTable + " where id !='0'");
            int rowCount = 0;
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }

            List<String> codeList = new ArrayList<>();
            int number = 0;
            for (int i = 0; i < rowCount; i++) {
                String sixCode = RandomStringUtils.random(6, "abcdefghijklmnopqrstuvwxyz");
                if (codeList.contains(sixCode)) {
                    i--;
                    continue;
                }
                codeList.add(sixCode + "-" + number);
                number++;
                if (number > 9) {
                    number = 0;
                }
            }


            int i = 0;
            resultSet = statement.executeQuery("SELECT id,site_title FROM " + oldTable + " where id !='0'");
            while (resultSet.next()) {
                Long newId = IdWorker.getId();
                String oldId = "'" + SiteAndSysDataMigration.resultSet.getString("id") + "'";
                String siteCode = "'" + codeList.get(i) + "'";
                String siteTitle = "'" + SiteAndSysDataMigration.resultSet.getString("site_title") + "'";
                stringBuilder.append("(" +
                        oldId + "," +
                        newId + "," +
                        siteCode + "," +
                        siteTitle +
                        "),");
                i++;
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ";");
            logger.info(relationTable + " migration end!used time---->" + (System.currentTimeMillis() - beginTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return stringBuilder.toString();
    }

    /**
     * 通用插入中间表数据方法
     *
     * @param selectOldTableSql 旧表的查询sql
     * @param relationTable     中间表的表名
     * @return
     */
    public static String insertRelation(String selectOldTableSql, String relationTable) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            long beginTime = System.currentTimeMillis();
            ResultSet resultSet = statement.executeQuery(selectOldTableSql);
            stringBuilder.append("insert into " + relationTable + "(" +
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
            logger.info(relationTable + " migration end!used time---->" + (System.currentTimeMillis() - beginTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return stringBuilder.toString();
    }


    /**
     * 生成站点sql脚本
     *
     * @return
     */
    public static String createSiteTableData() {
        StringBuilder sb = new StringBuilder();
        String sql = "INSERT INTO " + Constant.DB_PLATFORM + ".site\n" +
                "SELECT\n" +
                "\tsr.new_id AS id,\n" +
                "\tsr.site_code AS site_code,\n" +
                "\tls.site_title,\n" +
                "\tIFNULL(ls.site_keywords,'') AS site_keywords,\n" +
                "\tIFNULL(ls.site_desc,'') AS site_desc,\n" +
                "\tCASE ls.is_enable WHEN 'F' THEN 0 ELSE 1 END AS is_enable,\n" +
                "\tCASE ls.is_del WHEN 'F' THEN 0 ELSE 1 END AS is_del,\n" +
                "\tCASE ls.is_white WHEN 'F' THEN 0 ELSE 1 END AS is_white_list,\n" +
                "\tCASE ls.is_open_demo WHEN 'F' THEN 0 ELSE 1 END AS is_demo,\n" +
                "\tCASE ls.is_reg WHEN 'F' THEN 0 ELSE 1 END AS is_register,\n" +
                "\tCASE ls.is_show WHEN 'F' THEN 0 ELSE 1 END AS is_show_salary_bonus,\n" +
                "\tls.max_commission,\n" +
                "\tCASE ls.is_joint_buy WHEN 'F' THEN 0 ELSE 1 END AS is_joint_buy,\n" +
                "\tls.min_progres,\n" +
                "\tls.max_order_count,\n" +
                "\tIFNULL(ls.domain,'') AS nav_domain,\n" +
                "\tIFNULL(ls.app_domain_prefix,'') AS app_domain_prefix,\n" +
                "\turr.new_id AS default_proxy_id,\n" +
                "\turr.new_id AS demo_default_proxy_id,\n" +
                "\tls.logo_url AS pc_logo_url,\n" +
                "\tls.m_logo_url AS mobile_logo_url,\n" +
                "\tls.ios_url,\n" +
                "\tls.android_url,\n" +
                "\tls.link_ident,\n" +
                "\tls.check_rate,\n" +
                "\tls.skin,\n" +
                "\tls.copyright,\n" +
                "\tls.tel1,\n" +
                "\tls.tel2,\n" +
                "\tls.qq,\n" +
                "\tls.skype,\n" +
                "\tsur1.user_name AS create_by,\n" +
                "\tls.create_time,\n" +
                "\tsur2.user_name AS update_by,\n" +
                "\tls.update_time,\n" +
                "\tls.remark\n" +
                "FROM " + Constant.DB_MAIN + ".lot_site ls\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".site_relation sr ON ls.id = sr.old_id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".user_refer_relation urr ON ls.default_proxy_id = urr.old_id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".sys_user_relation sur1 ON ls.create_by = sur1.old_id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".sys_user_relation sur2 ON ls.update_by = sur2.old_id ;";
        sb.append(sql);
        return sb.toString();
    }

    /**
     * 生成注册会员配置sql脚本
     *
     * @return
     */
    public static String createSiteRegisterConfigTableData() {
        StringBuilder sb = new StringBuilder();
        String sql = "INSERT INTO " + Constant.DB_PLATFORM + ".site_register_config\n" +
                "SELECT\n" +
                "\tsrcr.new_id AS id,\n" +
                "\tsr.new_id AS site_id,\n" +
                "\tsr.site_code,\n" +
                "\tsr.site_title,\n" +
                "\tlsrc.field_name,\n" +
                "\tlsrc.field_code,\n" +
                "\t'' AS regex_rule,\n" +
                "\tCASE lsrc.is_display WHEN 'F' THEN 0 ELSE 1 END AS is_display,\n" +
                "\tCASE lsrc.is_required WHEN 'F' THEN 0 ELSE 1 END AS is_required,\n" +
                "\tCASE lsrc.is_verify WHEN 'F' THEN 0 ELSE 1 END AS is_verify,\n" +
                "\tCASE lsrc.is_del WHEN 'F' THEN 0 ELSE 1 END AS is_del,\n" +
                "\tlsrc.remark,\n" +
                "\tIFNULL(sur1.user_name,'') AS create_by,\n" +
                "\tIFNULL(lsrc.create_time,NOW()) AS create_time,\n" +
                "\tIFNULL(sur2.user_name,'') AS update_by,\n" +
                "\tIFNULL(lsrc.update_time,NOW()) AS update_time\n" +
                "FROM " + Constant.DB_MAIN + ".lot_site_reg_config lsrc\n" +
                "INNER JOIN " + Constant.DB_RELATION + ".site_register_config_relation srcr ON lsrc.id = srcr.old_id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".site_relation sr ON lsrc.site_id = sr.old_id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".sys_user_relation sur1 ON lsrc.create_by = sur1.old_id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".sys_user_relation sur2 ON lsrc.update_by = sur2.old_id ;";
        sb.append(sql);
        return sb.toString();
    }

    /**
     * 生成系统配置sql脚本
     *
     * @return
     */
    public static String createConfigTableData() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO " + Constant.DB_PLATFORM + ".config (id, set_code, set_name, set_key, set_value, is_enable, is_del, remark, site_id, create_by, create_time, update_by, update_time) VALUES \n" +
                "("+ IdWorker.getId() +", 'SETTING', '代理查看下级资料', 'PROXY_LIMIT', '1', 1, 0, '平台站点', 0, 'sys', '2018-5-30 17:20:24', 'sys', '2018-6-2 14:21:44'),\n" +
                "("+ IdWorker.getId() +", 'SETTING', '快捷金额', 'QUICK_MONEY', '1', 1, 0, '平台站点', 0, 'sys', '2018-5-30 17:20:24', 'sys', '2018-6-2 14:21:44'),\n" +
                "("+ IdWorker.getId() +", 'SETTING', '预付卡', 'PREPAID_CARD', '1', 1, 0, '平台站点', 0, 'sys', '2018-5-30 17:20:24', 'sys', '2018-8-7 08:08:20'),\n" +
                "("+ IdWorker.getId() +", 'CUE_TONE', '公司入款', 'COMPANY_DEPOSIT', '/static/music/lineapp_end_16k.wav', 1, 0, '平台站点', 0, 'sys', '2018-6-12 19:49:33', 'sys', '2018-8-7 08:08:22'),\n" +
                "("+ IdWorker.getId() +", 'CUE_TONE', '通知', 'NOTICE', '/static/music/user_online_sound1.mp3', 1, 0, '平台站点', 0, 'sys', '2018-6-13 10:38:37', 'sys', '2018-8-7 08:08:25'),\n" +
                "("+ IdWorker.getId() +", 'CUE_TONE', '取款', 'WITHDRAW_MONEY', '/static/music/sound.wav', 1, 0, '平台站点', 0, 'sys', '2018-6-13 10:39:35', 'sys', '2018-8-7 08:08:27'),\n" +
                "("+ IdWorker.getId() +", 'CUE_TONE', '会员留言', 'MEMBER_MESSAGE', '/static/music/sys_message_sound1.mp3', 1, 0, '平台站点', 0, 'sys', '2018-6-13 10:39:35', 'sys', '2018-8-7 08:08:31');\n");
        String sql = "INSERT INTO " + Constant.DB_PLATFORM + ".config\n" +
                "SELECT\n" +
                "\tcr.new_id AS id,\n" +
                "\tlsc.set_code,\n" +
                "\tlsc.set_name,\n" +
                "\tlsc.set_key,\n" +
                "\tlsc.set_value,\n" +
                "\tCASE lsc.is_enable WHEN 'F' THEN 0 ELSE 1 END AS is_enable,\n" +
                "\tCASE lsc.is_del WHEN 'F' THEN 0 ELSE 1 END AS is_del,\n" +
                "\tlsc.remark,\n" +
                "\tsr.new_id AS site_id,\n" +
                "\tsur1.user_name AS create_by,\n" +
                "\tlsc.create_time,\n" +
                "\tsur2.user_name AS update_by,\n" +
                "\tlsc.update_time\n" +
                "FROM " + Constant.DB_MAIN + ".lot_sys_config lsc\n" +
                "INNER JOIN " + Constant.DB_RELATION + ".config_relation cr ON lsc.id = cr.old_id\n" +
                "INNER JOIN " + Constant.DB_RELATION + ".site_relation sr ON lsc.site_id = sr.old_id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".sys_user_relation sur1 ON lsc.create_by = sur1.old_id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".sys_user_relation sur2 ON lsc.update_by = sur2.old_id;";
        sb.append(sql);
        return sb.toString();
    }

    /**
     * 生成字典sql脚本
     *
     * @return
     */
    public static String createKeyValueTableData() {
        StringBuilder sb = new StringBuilder();
        String sql = "INSERT INTO " + Constant.DB_PLATFORM + ".key_value\n" +
                "SELECT\n" +
                "\tkvr.new_id AS id,\n" +
                "\tlskv.dict_code,\n" +
                "\tlskv.dict_name,\n" +
                "\tlskv.dict_key,\n" +
                "\tlskv.dict_value,\n" +
                "\tCASE lskv.is_enable WHEN 'F' THEN 0 ELSE 1 END AS is_enable,\n" +
                "\tlskv.remark\n" +
                "FROM " + Constant.DB_MAIN + ".lot_sys_key_value lskv\n" +
                "INNER JOIN " + Constant.DB_RELATION + ".key_value_relation kvr ON lskv.id = kvr.old_id;";
        sb.append(sql);
        return sb.toString();
    }

    /**
     * 生成区域sql脚本
     *
     * @return
     */
    public static String createAreaTableData() {
        StringBuilder sb = new StringBuilder();
        String sql = "INSERT INTO " + Constant.DB_PLATFORM + ".area\n" +
                "SELECT\n" +
                "\tar.new_id AS id,\n" +
                "\tIFNULL((SELECT r.new_id pid FROM db_relation.area_relation r where lsa.pid = r.old_id),0) AS pid,\n" +
                "\tlsa.`level`,\n" +
                "\tlsa.level_name,\n" +
                "\tIFNULL(lsa.daqu,'') AS large_area,\n" +
                "\tlsa.area_code,\n" +
                "\tlsa.area_name,\n" +
                "\tlsa.help_code,\n" +
                "\tlsa.pinyin AS area_pinyin,\n" +
                "\tlsa.full_name,\n" +
                "\tIFNULL(sur.user_name,'sys') AS create_by,\n" +
                "\tlsa.create_time,\n" +
                "\tlsa.area_code_like AS tel_area_code\n" +
                "FROM " + Constant.DB_MAIN + ".lot_sys_area lsa \n" +
                "INNER JOIN " + Constant.DB_RELATION + ".area_relation ar ON lsa.id = ar.old_id \n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".sys_user_relation sur ON lsa.create_by = sur.old_id;";
        sb.append(sql);
        return sb.toString();
    }

    /**
     * 生成银行sql脚本
     *
     * @return
     */
    public static String createBankTableData() {
        StringBuilder sb = new StringBuilder();
        String sql = "INSERT INTO " + Constant.DB_PLATFORM + ".bank\n" +
                "SELECT\n" +
                "\tbr.new_id id,\n" +
                "\tIFNULL(lsb.bank_name,'') AS bank_name,\n" +
                "\tIFNULL(lsb.bank_code,'') AS bank_code,\n" +
                "\tIFNULL(lsb.bank_logo,'') AS bank_logo,\n" +
                "\tIFNULL(lsb.bank_region,'') AS bank_region,\n" +
                "\tCASE lsb.is_enable WHEN 'F' THEN 0 ELSE 1 END AS is_enable,\n" +
                "\tCASE lsb.is_del WHEN 'F' THEN 0 ELSE 1 END AS is_del,\n" +
                "\tIFNULL(lsb.remark,'') AS remark,\n" +
                "\tIFNULL(lsb.tel,'') AS tel,\n" +
                "\tIFNULL(sur1.user_name,'')  AS create_by,\n" +
                "\tlsb.create_time,\n" +
                "\tIFNULL(sur2.user_name,'') AS update_by,\n" +
                "\tIFNULL(lsb.update_time,NOW()) AS update_time\n" +
                "FROM " + Constant.DB_MAIN + ".lot_sys_bank lsb\n" +
                "INNER JOIN " + Constant.DB_RELATION + ".bank_relation br ON lsb.id = br.old_id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".sys_user_relation sur1 ON lsb.create_by = sur1.old_id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".sys_user_relation sur2 ON lsb.update_by = sur2.old_id ;";
        sb.append(sql);
        return sb.toString();
    }

    /**
     * 生成银行网点sql脚本
     *
     * @return
     */
    public static String createBankBranchTableData() {
        StringBuilder sb = new StringBuilder();
        String sql = "INSERT INTO " + Constant.DB_PLATFORM + ".bank_branch\n" +
                "SELECT\n" +
                "\tbbr.new_id id,\n" +
                "\tbr.new_id bank_id,\n" +
                "\tlsbn.bank_name,\n" +
                "\tar.new_id area_id,\n" +
                "\tlsbn.area_name,\n" +
                "\tlsbn.net_name AS branch_name,\n" +
                "\tlsbn.net_addr AS branch_addr\n" +
                "FROM " + Constant.DB_MAIN + ".lot_sys_bank_network lsbn\n" +
                "INNER JOIN " + Constant.DB_RELATION + ".bank_branch_relation bbr ON bbr.old_id = lsbn.id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".bank_relation br ON br.old_id = lsbn.bank_id\n" +
                "LEFT JOIN " + Constant.DB_RELATION + ".area_relation ar ON ar.old_id = lsbn.area_id;";
        sb.append(sql);
        return sb.toString();
    }
}
