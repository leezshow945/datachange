package com.user;

import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyTableSplit {

    static BufferedOutputStream bufferStream = null;
    static FileOutputStream fileStream = null;
//    private final static String TABLE_NAME = "user_proxy_";
//    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `" + TABLE_NAME + "%s` LIKE user_proxy; \n";

    static Logger logger = Logger.getLogger(UserMigration04.class);
    public static void exe(ResultSet resultSet, Statement statement){
        splitBySiteCode(resultSet, statement);
    }

    private static void splitBySiteCode(ResultSet resultSet, Statement statement) {

        try {
            logger.info("proxy split begin!");
            Long thisTime = System.currentTimeMillis();

            resultSet = statement.executeQuery("select site_id,site_code from user_proxy group by site_code");
            List<String> siteCodeList = new ArrayList<>();
            Map<String,Long> codeIdMap = new HashMap<>(siteCodeList.size());
            while (resultSet.next()) {
                String siteCode = resultSet.getString("site_code");
                Long siteId = resultSet.getLong("site_id");
                siteCodeList.add(siteCode);
                codeIdMap.put(siteCode,siteId);
            }
            logger.info("user_proxy split start site num :"+siteCodeList.size());
            fileStream = new FileOutputStream(new File("user_proxy_init.sql"));
            bufferStream = new BufferedOutputStream(fileStream);
            for (int i = 0; i < siteCodeList.size(); i++) {
                long siteCodeTime = System.currentTimeMillis();
                String siteCode = siteCodeList.get(i);
                logger.info("站点："+siteCode+" proxy_table split start !");
                StringBuilder sql =  new StringBuilder("CREATE TABLE `user_proxy_" + siteCode + "` (\n" +
                        "  `id` bigint(20) NOT NULL COMMENT '主键id',\n" +
                        "  `user_id` bigint(20) NOT NULL COMMENT '用户id',\n" +
                        "  `high_user_id` bigint(20) NOT NULL COMMENT '上级代理id',\n" +
                        "  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',\n" +
                        "  `level` int(11) NOT NULL COMMENT '层级',\n" +
                        "  `high_user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '上级用户名',\n" +
                        "  `site_id` bigint(20) NOT NULL,\n" +
                        "  `site_code` varchar(20) NOT NULL,\n" +
                        "  `create_time` datetime DEFAULT NULL,\n" +
                        "  `update_time` datetime DEFAULT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `high_user_id` (`high_user_id`),\n" +
                        "  KEY `user_id_index` (`user_id`),\n" +
                        "  KEY `level_index` (`level`),\n" +
                        "  KEY `id_index` (`level`,`user_id`,`high_user_id`),\n" +
                        "  KEY `h_site_id_index` (`high_user_id`,`level`) USING BTREE\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8; \n");

                sql.append(" INSERT INTO `user_proxy_"+siteCode+"` (id,user_id,high_user_id,user_name,`level`," +
                        "high_user_name,site_id,site_code,create_time,update_time) \n");
                sql.append("select id,user_id,high_user_id,user_name,`level`,high_user_name,site_id,site_code,create_time,update_time from `user_proxy`" +
                        "where `site_id` = " + codeIdMap.get(siteCode)+"; \n");
                bufferStream.write(sql.toString().getBytes());
                bufferStream.flush();
                logger.info("进度: " + (i + 1) + "/" + siteCodeList.size() + ",站点:" + siteCode + " sql out end time---> " + (System.currentTimeMillis() - siteCodeTime) + "ms");
            }
            logger.info("proxy table split end time---->" + (System.currentTimeMillis() - thisTime) + ":ms");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
