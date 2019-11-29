package com.ky.common;

/**
 * @author ky.ky
 */
public class Constant {
    public final static String db_relation = "db_relation";
    public final static String db_main = "db_main";
    public final static String db_platform = "db_platform";
    public final static String lot_site_line_group_relation_table = "lot_site_line_group_relation";

    public final static String lot_sys_resource_relation_table = "lot_sys_resource_relation";

    public final static String lot_site_line_relation_table = "lot_site_line_relation";

    public final static String lot_sys_role_relation_table = "lot_sys_role_relation";

    public final static String lot_site_denfense_relation_table = "lot_site_denfense_relation";

    public final static String lot_site_line_group_table= "lot_site_line_group";

    public final static String lot_sys_resource_table= "lot_sys_resource";

    public final static String lot_sys_role_table = "lot_sys_role";

    public final static String lot_site_line_table = "lot_site_line";

    public final static String lot_site_denfense_table = "lot_site_denfense";

    public final static String lot_site_line_group_relation="CREATE TABLE `lot_site_line_group_relation` (\n" +
            "  `new_id` bigint(20) NOT NULL,\n" +
            "  `old_id` char(32) NOT NULL,\n" +
            "  PRIMARY KEY (`new_id`),\n" +
            "  KEY `old_id` (`old_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    public final static String lot_sys_resource_relation="CREATE TABLE `lot_sys_resource_relation` (\n" +
            "  `new_id` bigint(20) NOT NULL,\n" +
            "  `old_id` char(32) NOT NULL,\n" +
            "  PRIMARY KEY (`new_id`),\n" +
            "  KEY `old_id` (`old_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    public final static String lot_sys_role_relation="CREATE TABLE `lot_sys_role_relation` (\n" +
            "  `new_id` bigint(20) NOT NULL,\n" +
            "  `old_id` char(32) NOT NULL,\n" +
            "  PRIMARY KEY (`new_id`),\n" +
            "  KEY `old_id` (`old_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    public final static String lot_site_line_relation="CREATE TABLE `lot_site_line_relation` (\n" +
            "  `new_id` bigint(20) NOT NULL,\n" +
            "  `old_id` char(32) NOT NULL,\n" +
            "  PRIMARY KEY (`new_id`),\n" +
            "  KEY `old_id` (`old_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    public final static String lot_site_denfense_relation="CREATE TABLE `lot_site_denfense_relation` (\n" +
            "  `new_id` bigint(20) NOT NULL,\n" +
            "  `old_id` char(32) NOT NULL,\n" +
            "  PRIMARY KEY (`new_id`),\n" +
            "  KEY `old_id` (`old_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";



    public final static String domain_group = "domain_group";
    public final static String resource = "resource";
    public final static String role = "role";
    public final static String role_resource = "role_resource";
    public final static String user_role = "user_role";
    public final static String ip_limit = "ip_limit";
    public final static String monitor_url = "monitor_url";
    public final static String menu = "menu";
    public final static String country = "country";
    public final static String createDomain="CREATE TABLE `domain` (\n" +
            "  `id` bigint(20) NOT NULL,\n" +
            "  `site_id` bigint(20) NOT NULL,\n" +
            "  `site_code` varchar(50) NOT NULL DEFAULT '',\n" +
            "  `line_name` varchar(50) NOT NULL DEFAULT '' COMMENT '线路名称，在首页线路导航会用到',\n" +
            "  `domain` varchar(50) NOT NULL DEFAULT '' COMMENT '域名',\n" +
            "  `m_domain` varchar(50) NOT NULL DEFAULT '' COMMENT '手机访问域名',\n" +
            "  `rewrite_url` varchar(50) NOT NULL DEFAULT '' COMMENT '跳转URL地址',\n" +
            "  `point` tinyint(4) NOT NULL DEFAULT '1' COMMENT '域名指向 ;1-> 会员端(默认);2 ->商家端 ;3 ->平台端',\n" +
            "  `group_id` bigint(20) DEFAULT NULL COMMENT '分组ID',\n" +
            "  `proxy_url` varchar(255) NOT NULL DEFAULT '' COMMENT 'nginx反向代理IP',\n" +
            "  `web_root` varchar(255) NOT NULL DEFAULT '' COMMENT 'nginx里面的root目录地址',\n" +
            "  `version` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1->旧版UI数据消费（默认）；2->新版UI数据消费',\n" +
            "  `is_del` tinyint(4) NOT NULL DEFAULT '0',\n" +
            "  `create_by` varchar(50) NOT NULL DEFAULT '',\n" +
            "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  `update_by` varchar(50) NOT NULL DEFAULT '',\n" +
            "  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
            "  `sort_id` tinyint(4) NOT NULL DEFAULT '0',\n" +
            "  `is_enable` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否启用；0->禁用，1->启用',\n" +
            "  `remark` varchar(255) NOT NULL DEFAULT '',\n" +
            "  `is_mobile` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是手机域名；0->不是，1->是',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE KEY `domain` (`domain`) USING BTREE\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

   public final static String createDomainGroup="CREATE TABLE `domain_group` (\n" +
           "  `id` bigint(20) NOT NULL,\n" +
           "  `group_name` varchar(255) NOT NULL,\n" +
           "  `site_id` bigint(20) DEFAULT NULL,\n" +
           "  `site_code` varchar(50) DEFAULT NULL,\n" +
           "  `create_by` varchar(50) NOT NULL DEFAULT '',\n" +
           "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
           "  `update_by` varchar(50) NOT NULL DEFAULT '',\n" +
           "  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
           "  `sort_id` tinyint(4) NOT NULL DEFAULT '0',\n" +
           "  `is_enable` tinyint(4) NOT NULL DEFAULT '0',\n" +
           "  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',\n" +
           "  `is_del` tinyint(4) NOT NULL DEFAULT '0',\n" +
           "  PRIMARY KEY (`id`)\n" +
           ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

   public final static String createRole="CREATE TABLE `role` (\n" +
           "  `id` bigint(20) NOT NULL COMMENT '角色ID',\n" +
           "  `role_name` varchar(100) NOT NULL DEFAULT '' COMMENT '角色名',\n" +
           "  `site_id` bigint(20) NOT NULL COMMENT '站点ID',\n" +
           "  `site_code` char(20) NOT NULL DEFAULT '',\n" +
           "  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
           "  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
           "  `create_by` varchar(100) NOT NULL DEFAULT '' COMMENT '创建人',\n" +
           "  `update_by` varchar(100) NOT NULL DEFAULT '' COMMENT '更新人',\n" +
           "  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '角色描述',\n" +
           "  `is_del` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0为未删除；1为已删除',\n" +
           "  PRIMARY KEY (`id`)\n" +
           ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息表';";

   public final static String createResource="CREATE TABLE `resource` (\n" +
           "  `id` bigint(20) NOT NULL COMMENT '资源ID',\n" +
           "  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级ID',\n" +
           "  `resource_name` varchar(100) NOT NULL DEFAULT '' COMMENT '资源名称',\n" +
           "  `resource_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '资源类型（0->菜单，1->按钮）',\n" +
           "  `sort_id` int(4) NOT NULL DEFAULT '0' COMMENT '排序',\n" +
           "  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '链接',\n" +
           "  `version` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1->旧版资源（默认）；2->新版资源',\n" +
           "  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',\n" +
           "  `is_auth` tinyint(1) NOT NULL DEFAULT '0',\n" +
           "  `is_del` tinyint(1) NOT NULL DEFAULT '0',\n" +
           "  `module` varchar(50) NOT NULL DEFAULT '' COMMENT '模块名，资源类型为按钮时必填',\n" +
           "  `privilege` varchar(50) NOT NULL DEFAULT '' COMMENT '权限值，资源类型为按钮时必填',\n" +
           "  `rel` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单标识',\n" +
           "  `is_auto_refresh` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否自动刷新（0->否；1->是）',\n" +
           "  PRIMARY KEY (`id`)\n" +
           ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='内部资源表，通过资源ID映射资源';";

   public final static String createRoleResource="CREATE TABLE `role_resource` (\n" +
           "  `id` bigint(20) NOT NULL COMMENT 'ID',\n" +
           "  `role_id` bigint(20) NOT NULL COMMENT '内部角色ID',\n" +
           "  `resource_id` bigint(20) NOT NULL COMMENT '内部资源ID',\n" +
           "  PRIMARY KEY (`id`)\n" +
           ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='内部角色资源映射表';\n";

   public final static String createUserRole="CREATE TABLE `user_role` (\n" +
           "  `id` bigint(20) NOT NULL COMMENT '主键',\n" +
           "  `user_id` bigint(20) NOT NULL COMMENT '用户ID',\n" +
           "  `role_id` bigint(20) NOT NULL COMMENT '用户角色ID',\n" +
           "  PRIMARY KEY (`id`),\n" +
           "  UNIQUE KEY `ur` (`user_id`,`role_id`)\n" +
           ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户绑定角色表，一个用户可以绑定多个角色';";

   public final static String createIplimit="CREATE TABLE `ip_limit` (\n" +
           "  `id` bigint(20) NOT NULL COMMENT '主键',\n" +
           "  `ip_start` char(15) NOT NULL DEFAULT '' COMMENT '起始ip',\n" +
           "  `ip_end` char(15) NOT NULL DEFAULT '' COMMENT '截止ip',\n" +
           "  `type` tinyint(2) unsigned zerofill NOT NULL COMMENT '10为前台允许ip；11为前台区域禁止ip；12为前台禁止ip；20为后台允许ip；',\n" +
           "  `country_name` varchar(20) NOT NULL DEFAULT '' COMMENT '国家或地区名称',\n" +
           "  `site_id` bigint(20) NOT NULL COMMENT '站点id',\n" +
           "  `final_time` datetime NOT NULL COMMENT 'IP限制失效时间',\n" +
           "  `create_by` varchar(50) NOT NULL DEFAULT '' COMMENT '创建人',\n" +
           "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
           "  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
           "  `update_by` varchar(50) NOT NULL DEFAULT '' COMMENT '更新人',\n" +
           "  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0为未删除;1为删除',\n" +
           "  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',\n" +
           "  `limit_time` char(20) NOT NULL DEFAULT '' COMMENT '限制时间',\n" +
           "  `site_code` char(20) NOT NULL DEFAULT '' COMMENT '站点code',\n" +
           "  PRIMARY KEY (`id`)\n" +
           ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

   public final static String createMonitorUrl="CREATE TABLE `monitor_url` (\n" +
           "  `id` bigint(20) NOT NULL,\n" +
           "  `url` varchar(500) NOT NULL DEFAULT '' COMMENT '完整url',\n" +
           "  `path` varchar(500) NOT NULL DEFAULT '' COMMENT 'url的路径，不包含请求参数',\n" +
           "  `param` varchar(500) NOT NULL DEFAULT '' COMMENT '请求参数',\n" +
           "  `times` int(11) NOT NULL DEFAULT '0' COMMENT 'url执行时间，单位：毫秒',\n" +
           "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
           "  PRIMARY KEY (`id`)\n" +
           ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='url监控表，记录url执行时间超过1.5秒的url';\n";

   public final static String createMenu="CREATE TABLE `menu` (\n" +
           "  `id` bigint(20) NOT NULL COMMENT '主键',\n" +
           "  `parent` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级节点主键',\n" +
           "  `text` varchar(255) DEFAULT '' COMMENT '显示文本',\n" +
           "  `icon` varchar(255) DEFAULT '' COMMENT '图标',\n" +
           "  `link` varchar(255) DEFAULT '' COMMENT '导航链接',\n" +
           "  `leaf` tinyint(1) DEFAULT '0' COMMENT '是否为叶子节点, 第三级必须是叶子节点',\n" +
           "  `target` tinyint(1) DEFAULT '0' COMMENT '跳转类型;1-> 新窗口打开,0->本窗口打开(0)【默认】',\n" +
           "  `route` tinyint(1) DEFAULT '1' COMMENT '是否为前端内部路由;0->不是；1->是',\n" +
           "  `depth` tinyint(1) DEFAULT '1' COMMENT '树深度;1->一级；2->二级；3->三级',\n" +
           "  `index` tinyint(4) DEFAULT '0' COMMENT '排序'\n" +
           ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";
   public final static String delDomainGroupOldId="ALTER TABLE `domain_group`\n" +
           "DROP COLUMN `old_id`;";
   public final static String delResourceOldId="ALTER TABLE `resource`\n" +
           "DROP COLUMN `old_pid`,"+
           "DROP COLUMN `old_id`;";
    public final static String delRoleOldId="ALTER TABLE `role`\n" +
            "DROP COLUMN `old_id`;";

    public final static String createCountry ="/*\n" +
            "Navicat MySQL Data Transfer\n" +
            "\n" +
            "Source Server         : 192.168.11.231\n" +
            "Source Server Version : 50712\n" +
            "Source Host           : 192.168.11.231:32006\n" +
            "Source Database       : platform\n" +
            "\n" +
            "Target Server Type    : MYSQL\n" +
            "Target Server Version : 50712\n" +
            "File Encoding         : 65001\n" +
            "\n" +
            "Date: 2018-07-05 19:43:53\n" +
            "*/\n" +
            "\n" +
            "SET FOREIGN_KEY_CHECKS=0;\n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Table structure for country\n" +
            "-- ----------------------------\n" +
            "DROP TABLE IF EXISTS `country`;\n" +
            "CREATE TABLE `country` (\n" +
            "  `id` char(32) NOT NULL,\n" +
            "  `chinese_name` varchar(50) NOT NULL DEFAULT '' COMMENT '国名中文',\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='国家';\n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Records of country\n" +
            "-- ----------------------------\n" +
            "INSERT INTO `country` VALUES ('ABW', '阿鲁巴');\n" +
            "INSERT INTO `country` VALUES ('AFG', '阿富汗');\n" +
            "INSERT INTO `country` VALUES ('AGO', '安哥拉');\n" +
            "INSERT INTO `country` VALUES ('AIA', '安圭拉');\n" +
            "INSERT INTO `country` VALUES ('ALA', '奥兰群岛');\n" +
            "INSERT INTO `country` VALUES ('ALB', '阿尔巴尼亚');\n" +
            "INSERT INTO `country` VALUES ('AND', '安道尔');\n" +
            "INSERT INTO `country` VALUES ('ANT', '荷属安地列斯');\n" +
            "INSERT INTO `country` VALUES ('ARE', '阿拉伯联合酋长国');\n" +
            "INSERT INTO `country` VALUES ('ARG', '阿根廷');\n" +
            "INSERT INTO `country` VALUES ('ARM', '亚美尼亚');\n" +
            "INSERT INTO `country` VALUES ('ASC', '阿森松岛');\n" +
            "INSERT INTO `country` VALUES ('ASM', '美属萨摩亚');\n" +
            "INSERT INTO `country` VALUES ('ATA', '南极洲');\n" +
            "INSERT INTO `country` VALUES ('ATF', '法属南部领地');\n" +
            "INSERT INTO `country` VALUES ('ATG', '安提瓜岛和巴布达');\n" +
            "INSERT INTO `country` VALUES ('AUS', '澳大利亚');\n" +
            "INSERT INTO `country` VALUES ('AUT', '奥地利');\n" +
            "INSERT INTO `country` VALUES ('AZE', '阿塞拜疆');\n" +
            "INSERT INTO `country` VALUES ('BDI', '布隆迪');\n" +
            "INSERT INTO `country` VALUES ('BEL', '比利时');\n" +
            "INSERT INTO `country` VALUES ('BEN', '贝宁');\n" +
            "INSERT INTO `country` VALUES ('BFA', '布基纳法索');\n" +
            "INSERT INTO `country` VALUES ('BGD', '孟加拉');\n" +
            "INSERT INTO `country` VALUES ('BGR', '保加利亚');\n" +
            "INSERT INTO `country` VALUES ('BHR', '巴林');\n" +
            "INSERT INTO `country` VALUES ('BHS', '巴哈马');\n" +
            "INSERT INTO `country` VALUES ('BIH', '波斯尼亚和黑塞哥维那');\n" +
            "INSERT INTO `country` VALUES ('BLR', '白俄罗斯');\n" +
            "INSERT INTO `country` VALUES ('BLZ', '伯利兹');\n" +
            "INSERT INTO `country` VALUES ('BMU', '百慕大');\n" +
            "INSERT INTO `country` VALUES ('BOL', '玻利维亚');\n" +
            "INSERT INTO `country` VALUES ('BRA', '巴西');\n" +
            "INSERT INTO `country` VALUES ('BRB', '巴巴多斯岛');\n" +
            "INSERT INTO `country` VALUES ('BRN', '文莱');\n" +
            "INSERT INTO `country` VALUES ('BTN', '不丹');\n" +
            "INSERT INTO `country` VALUES ('BVT', '布韦岛');\n" +
            "INSERT INTO `country` VALUES ('BWA', '博茨瓦纳');\n" +
            "INSERT INTO `country` VALUES ('CAF', '中非共和国');\n" +
            "INSERT INTO `country` VALUES ('CAN', '加拿大');\n" +
            "INSERT INTO `country` VALUES ('CCK', '科科斯群岛');\n" +
            "INSERT INTO `country` VALUES ('CHE', '瑞士');\n" +
            "INSERT INTO `country` VALUES ('CHL', '智利');\n" +
            "INSERT INTO `country` VALUES ('CIV', '科特迪瓦');\n" +
            "INSERT INTO `country` VALUES ('CMR', '喀麦隆');\n" +
            "INSERT INTO `country` VALUES ('CN', '中国');\n" +
            "INSERT INTO `country` VALUES ('COD', '刚果民主共和国');\n" +
            "INSERT INTO `country` VALUES ('COG', '刚果');\n" +
            "INSERT INTO `country` VALUES ('COK', '库克群岛');\n" +
            "INSERT INTO `country` VALUES ('COL', '哥伦比亚');\n" +
            "INSERT INTO `country` VALUES ('COM', '科摩罗');\n" +
            "INSERT INTO `country` VALUES ('CPV', '佛得角');\n" +
            "INSERT INTO `country` VALUES ('CRI', '哥斯达黎加');\n" +
            "INSERT INTO `country` VALUES ('CUB', '古巴');\n" +
            "INSERT INTO `country` VALUES ('CXR', '圣诞岛');\n" +
            "INSERT INTO `country` VALUES ('CYM', '开曼群岛');\n" +
            "INSERT INTO `country` VALUES ('CYP', '塞浦路斯');\n" +
            "INSERT INTO `country` VALUES ('CZE', '捷克共和国');\n" +
            "INSERT INTO `country` VALUES ('DEU', '德国');\n" +
            "INSERT INTO `country` VALUES ('DJI', '吉布提');\n" +
            "INSERT INTO `country` VALUES ('DMA', '多米尼加');\n" +
            "INSERT INTO `country` VALUES ('DNK', '丹麦');\n" +
            "INSERT INTO `country` VALUES ('DOM', '多米尼加共和国');\n" +
            "INSERT INTO `country` VALUES ('DZA', '阿尔及利亚');\n" +
            "INSERT INTO `country` VALUES ('ECU', '厄瓜多尔');\n" +
            "INSERT INTO `country` VALUES ('EGY', '埃及');\n" +
            "INSERT INTO `country` VALUES ('ERI', '厄立特里亚');\n" +
            "INSERT INTO `country` VALUES ('ESP', '西班牙');\n" +
            "INSERT INTO `country` VALUES ('EST', '爱沙尼亚');\n" +
            "INSERT INTO `country` VALUES ('ETH', '埃塞俄比亚');\n" +
            "INSERT INTO `country` VALUES ('FIN', '芬兰');\n" +
            "INSERT INTO `country` VALUES ('FJI', '斐济');\n" +
            "INSERT INTO `country` VALUES ('FLK', '弗兰克群岛');\n" +
            "INSERT INTO `country` VALUES ('FRA', '法国');\n" +
            "INSERT INTO `country` VALUES ('FRO', '法罗群岛');\n" +
            "INSERT INTO `country` VALUES ('FSM', '密克罗尼西亚');\n" +
            "INSERT INTO `country` VALUES ('GAB', '加蓬');\n" +
            "INSERT INTO `country` VALUES ('GBR', '英国');\n" +
            "INSERT INTO `country` VALUES ('GEO', '乔治亚');\n" +
            "INSERT INTO `country` VALUES ('GGY', '格恩西岛');\n" +
            "INSERT INTO `country` VALUES ('GHA', '加纳');\n" +
            "INSERT INTO `country` VALUES ('GIB', '直布罗陀');\n" +
            "INSERT INTO `country` VALUES ('GIN', '几内亚');\n" +
            "INSERT INTO `country` VALUES ('GLP', '瓜德罗普');\n" +
            "INSERT INTO `country` VALUES ('GMB', '冈比亚');\n" +
            "INSERT INTO `country` VALUES ('GNB', '几内亚比绍');\n" +
            "INSERT INTO `country` VALUES ('GRC', '希腊');\n" +
            "INSERT INTO `country` VALUES ('GRD', '格林纳达');\n" +
            "INSERT INTO `country` VALUES ('GRL', '格陵兰');\n" +
            "INSERT INTO `country` VALUES ('GTM', '危地马拉');\n" +
            "INSERT INTO `country` VALUES ('GUF', '法属圭亚那');\n" +
            "INSERT INTO `country` VALUES ('GUM', '关岛');\n" +
            "INSERT INTO `country` VALUES ('GUY', '圭亚那');\n" +
            "INSERT INTO `country` VALUES ('HMD', '赫德和麦克唐纳群岛');\n" +
            "INSERT INTO `country` VALUES ('HND', '洪都拉斯');\n" +
            "INSERT INTO `country` VALUES ('HRV', '克罗地亚');\n" +
            "INSERT INTO `country` VALUES ('HTI', '海地');\n" +
            "INSERT INTO `country` VALUES ('HUN', '匈牙利');\n" +
            "INSERT INTO `country` VALUES ('IDN', '印度尼西亚');\n" +
            "INSERT INTO `country` VALUES ('IMN', '曼岛');\n" +
            "INSERT INTO `country` VALUES ('IND', '印度');\n" +
            "INSERT INTO `country` VALUES ('IOT', '英属印度洋领地');\n" +
            "INSERT INTO `country` VALUES ('IRL', '爱尔兰');\n" +
            "INSERT INTO `country` VALUES ('IRN', '伊朗');\n" +
            "INSERT INTO `country` VALUES ('IRQ', '伊拉克');\n" +
            "INSERT INTO `country` VALUES ('ISL', '冰岛');\n" +
            "INSERT INTO `country` VALUES ('ISR', '以色列');\n" +
            "INSERT INTO `country` VALUES ('ITA', '意大利');\n" +
            "INSERT INTO `country` VALUES ('JAM', '牙买加');\n" +
            "INSERT INTO `country` VALUES ('JEY', '泽西岛');\n" +
            "INSERT INTO `country` VALUES ('JOR', '约旦');\n" +
            "INSERT INTO `country` VALUES ('JPN', '日本');\n" +
            "INSERT INTO `country` VALUES ('KAZ', '哈萨克斯坦');\n" +
            "INSERT INTO `country` VALUES ('KEN', '肯尼亚');\n" +
            "INSERT INTO `country` VALUES ('KGZ', '吉尔吉斯斯坦');\n" +
            "INSERT INTO `country` VALUES ('KHM', '柬埔寨');\n" +
            "INSERT INTO `country` VALUES ('KIR', '基里巴斯');\n" +
            "INSERT INTO `country` VALUES ('KNA', '圣基茨和尼维斯');\n" +
            "INSERT INTO `country` VALUES ('KOR', '韩国');\n" +
            "INSERT INTO `country` VALUES ('KWT', '科威特');\n" +
            "INSERT INTO `country` VALUES ('LAO', '老挝');\n" +
            "INSERT INTO `country` VALUES ('LBN', '黎巴嫩');\n" +
            "INSERT INTO `country` VALUES ('LBR', '利比里亚');\n" +
            "INSERT INTO `country` VALUES ('LBY', '利比亚');\n" +
            "INSERT INTO `country` VALUES ('LCA', '圣卢西亚');\n" +
            "INSERT INTO `country` VALUES ('LIE', '列支敦士登');\n" +
            "INSERT INTO `country` VALUES ('LKA', '斯里兰卡');\n" +
            "INSERT INTO `country` VALUES ('LSO', '莱索托');\n" +
            "INSERT INTO `country` VALUES ('LTU', '立陶宛');\n" +
            "INSERT INTO `country` VALUES ('LUX', '卢森堡');\n" +
            "INSERT INTO `country` VALUES ('LVA', '拉脱维亚');\n" +
            "INSERT INTO `country` VALUES ('MAR', '摩洛哥');\n" +
            "INSERT INTO `country` VALUES ('MCO', '摩纳哥');\n" +
            "INSERT INTO `country` VALUES ('MDA', '摩尔多瓦');\n" +
            "INSERT INTO `country` VALUES ('MDG', '马达加斯加');\n" +
            "INSERT INTO `country` VALUES ('MDV', '马尔代夫');\n" +
            "INSERT INTO `country` VALUES ('MEX', '墨西哥');\n" +
            "INSERT INTO `country` VALUES ('MHL', '马绍尔群岛');\n" +
            "INSERT INTO `country` VALUES ('MKD', '马其顿');\n" +
            "INSERT INTO `country` VALUES ('MLI', '马里');\n" +
            "INSERT INTO `country` VALUES ('MLT', '马耳他');\n" +
            "INSERT INTO `country` VALUES ('MMR', '缅甸');\n" +
            "INSERT INTO `country` VALUES ('MNG', '蒙古');\n" +
            "INSERT INTO `country` VALUES ('MNP', '北马里亚纳群岛');\n" +
            "INSERT INTO `country` VALUES ('MOZ', '莫桑比克');\n" +
            "INSERT INTO `country` VALUES ('MRT', '毛里塔尼亚');\n" +
            "INSERT INTO `country` VALUES ('MSR', '蒙特塞拉特');\n" +
            "INSERT INTO `country` VALUES ('MTQ', '马提尼克');\n" +
            "INSERT INTO `country` VALUES ('MUS', '毛里求斯');\n" +
            "INSERT INTO `country` VALUES ('MWI', '马拉维');\n" +
            "INSERT INTO `country` VALUES ('MYS', '马来西亚');\n" +
            "INSERT INTO `country` VALUES ('MYT', '马约特岛');\n" +
            "INSERT INTO `country` VALUES ('NAM', '纳米比亚');\n" +
            "INSERT INTO `country` VALUES ('NCL', '新喀里多尼亚');\n" +
            "INSERT INTO `country` VALUES ('NER', '尼日尔');\n" +
            "INSERT INTO `country` VALUES ('NFK', '诺福克');\n" +
            "INSERT INTO `country` VALUES ('NGA', '尼日利亚');\n" +
            "INSERT INTO `country` VALUES ('NIC', '尼加拉瓜');\n" +
            "INSERT INTO `country` VALUES ('NIU', '纽埃');\n" +
            "INSERT INTO `country` VALUES ('NLD', '荷兰');\n" +
            "INSERT INTO `country` VALUES ('NOR', '挪威');\n" +
            "INSERT INTO `country` VALUES ('NPL', '尼泊尔');\n" +
            "INSERT INTO `country` VALUES ('NRU', '瑙鲁');\n" +
            "INSERT INTO `country` VALUES ('NZL', '新西兰');\n" +
            "INSERT INTO `country` VALUES ('OMN', '阿曼');\n" +
            "INSERT INTO `country` VALUES ('PAK', '巴基斯坦');\n" +
            "INSERT INTO `country` VALUES ('PAN', '巴拿马');\n" +
            "INSERT INTO `country` VALUES ('PCN', '皮特凯恩');\n" +
            "INSERT INTO `country` VALUES ('PER', '秘鲁');\n" +
            "INSERT INTO `country` VALUES ('PHL', '菲律宾');\n" +
            "INSERT INTO `country` VALUES ('PLW', '帕劳群岛');\n" +
            "INSERT INTO `country` VALUES ('PNG', '巴布亚新几内亚');\n" +
            "INSERT INTO `country` VALUES ('POL', '波兰');\n" +
            "INSERT INTO `country` VALUES ('PRI', '波多黎各');\n" +
            "INSERT INTO `country` VALUES ('PRK', '朝鲜');\n" +
            "INSERT INTO `country` VALUES ('PRT', '葡萄牙');\n" +
            "INSERT INTO `country` VALUES ('PRY', '巴拉圭');\n" +
            "INSERT INTO `country` VALUES ('PSE', '巴勒斯坦');\n" +
            "INSERT INTO `country` VALUES ('PYF', '法属波利尼西亚');\n" +
            "INSERT INTO `country` VALUES ('QAT', '卡塔尔');\n" +
            "INSERT INTO `country` VALUES ('REU', '留尼旺岛');\n" +
            "INSERT INTO `country` VALUES ('ROU', '罗马尼亚');\n" +
            "INSERT INTO `country` VALUES ('RUS', '俄罗斯');\n" +
            "INSERT INTO `country` VALUES ('RWA', '卢旺达');\n" +
            "INSERT INTO `country` VALUES ('SAU', '沙特阿拉伯');\n" +
            "INSERT INTO `country` VALUES ('SCG', '塞尔维亚,黑山');\n" +
            "INSERT INTO `country` VALUES ('SDN', '苏丹');\n" +
            "INSERT INTO `country` VALUES ('SEN', '塞内加尔');\n" +
            "INSERT INTO `country` VALUES ('SGP', '新加坡');\n" +
            "INSERT INTO `country` VALUES ('SGS', '南乔治亚和南桑德威奇群岛');\n" +
            "INSERT INTO `country` VALUES ('SHN', '圣赫勒拿');\n" +
            "INSERT INTO `country` VALUES ('SJM', '斯瓦尔巴和扬马廷');\n" +
            "INSERT INTO `country` VALUES ('SLB', '所罗门群岛');\n" +
            "INSERT INTO `country` VALUES ('SLE', '塞拉利昂');\n" +
            "INSERT INTO `country` VALUES ('SLV', '萨尔瓦多');\n" +
            "INSERT INTO `country` VALUES ('SMR', '圣马力诺');\n" +
            "INSERT INTO `country` VALUES ('SOM', '索马里');\n" +
            "INSERT INTO `country` VALUES ('SPM', '圣皮埃尔和米克隆群岛');\n" +
            "INSERT INTO `country` VALUES ('STP', '圣多美和普林西比');\n" +
            "INSERT INTO `country` VALUES ('SUR', '苏里南');\n" +
            "INSERT INTO `country` VALUES ('SVK', '斯洛伐克');\n" +
            "INSERT INTO `country` VALUES ('SVN', '斯洛文尼亚');\n" +
            "INSERT INTO `country` VALUES ('SWE', '瑞典');\n" +
            "INSERT INTO `country` VALUES ('SWZ', '斯威士兰');\n" +
            "INSERT INTO `country` VALUES ('SYC', '塞舌尔');\n" +
            "INSERT INTO `country` VALUES ('SYR', '叙利亚');\n" +
            "INSERT INTO `country` VALUES ('TAA', '特里斯坦达昆哈');\n" +
            "INSERT INTO `country` VALUES ('TCA', '特克斯和凯克特斯群岛');\n" +
            "INSERT INTO `country` VALUES ('TCD', '乍得');\n" +
            "INSERT INTO `country` VALUES ('TGO', '多哥');\n" +
            "INSERT INTO `country` VALUES ('THA', '泰国');\n" +
            "INSERT INTO `country` VALUES ('TJK', '塔吉克斯坦');\n" +
            "INSERT INTO `country` VALUES ('TKL', '托克劳');\n" +
            "INSERT INTO `country` VALUES ('TKM', '土库曼斯坦');\n" +
            "INSERT INTO `country` VALUES ('TLS', '东帝汶');\n" +
            "INSERT INTO `country` VALUES ('TON', '汤加');\n" +
            "INSERT INTO `country` VALUES ('TTO', '特立尼达和多巴哥');\n" +
            "INSERT INTO `country` VALUES ('TUN', '突尼斯');\n" +
            "INSERT INTO `country` VALUES ('TUR', '土耳其');\n" +
            "INSERT INTO `country` VALUES ('TUV', '图瓦卢');\n" +
            "INSERT INTO `country` VALUES ('TZA', '坦桑尼亚');\n" +
            "INSERT INTO `country` VALUES ('UGA', '乌干达');\n" +
            "INSERT INTO `country` VALUES ('UKR', '乌克兰');\n" +
            "INSERT INTO `country` VALUES ('UMI', '美属外岛');\n" +
            "INSERT INTO `country` VALUES ('URY', '乌拉圭');\n" +
            "INSERT INTO `country` VALUES ('USA', '美国');\n" +
            "INSERT INTO `country` VALUES ('UZB', '乌兹别克斯坦');\n" +
            "INSERT INTO `country` VALUES ('VAT', '梵蒂冈');\n" +
            "INSERT INTO `country` VALUES ('VCT', '圣文森特和格林纳丁斯');\n" +
            "INSERT INTO `country` VALUES ('VEN', '委内瑞拉');\n" +
            "INSERT INTO `country` VALUES ('VGB', '维尔京群岛，英属');\n" +
            "INSERT INTO `country` VALUES ('VIR', '维尔京群岛，美属');\n" +
            "INSERT INTO `country` VALUES ('VNM', '越南');\n" +
            "INSERT INTO `country` VALUES ('VUT', '瓦努阿图');\n" +
            "INSERT INTO `country` VALUES ('WLF', '瓦利斯和福图纳');\n" +
            "INSERT INTO `country` VALUES ('WSM', '萨摩亚');\n" +
            "INSERT INTO `country` VALUES ('YEM', '也门');\n" +
            "INSERT INTO `country` VALUES ('ZAF', '南非');\n" +
            "INSERT INTO `country` VALUES ('ZMB', '赞比亚');\n" +
            "INSERT INTO `country` VALUES ('ZWE', '津巴布韦');\n";

    public final static String country_value=
            "INSERT INTO db_platform.country VALUES ('ABW', '阿鲁巴');\n" +
            "INSERT INTO db_platform.country VALUES ('AFG', '阿富汗');\n" +
            "INSERT INTO db_platform.country VALUES ('AGO', '安哥拉');\n" +
            "INSERT INTO db_platform.country VALUES ('AIA', '安圭拉');\n" +
            "INSERT INTO db_platform.country VALUES ('ALA', '奥兰群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('ALB', '阿尔巴尼亚');\n" +
            "INSERT INTO db_platform.country VALUES ('AND', '安道尔');\n" +
            "INSERT INTO db_platform.country VALUES ('ANT', '荷属安地列斯');\n" +
            "INSERT INTO db_platform.country VALUES ('ARE', '阿拉伯联合酋长国');\n" +
            "INSERT INTO db_platform.country VALUES ('ARG', '阿根廷');\n" +
            "INSERT INTO db_platform.country VALUES ('ARM', '亚美尼亚');\n" +
            "INSERT INTO db_platform.country VALUES ('ASC', '阿森松岛');\n" +
            "INSERT INTO db_platform.country VALUES ('ASM', '美属萨摩亚');\n" +
            "INSERT INTO db_platform.country VALUES ('ATA', '南极洲');\n" +
            "INSERT INTO db_platform.country VALUES ('ATF', '法属南部领地');\n" +
            "INSERT INTO db_platform.country VALUES ('ATG', '安提瓜岛和巴布达');\n" +
            "INSERT INTO db_platform.country VALUES ('AUS', '澳大利亚');\n" +
            "INSERT INTO db_platform.country VALUES ('AUT', '奥地利');\n" +
            "INSERT INTO db_platform.country VALUES ('AZE', '阿塞拜疆');\n" +
            "INSERT INTO db_platform.country VALUES ('BDI', '布隆迪');\n" +
            "INSERT INTO db_platform.country VALUES ('BEL', '比利时');\n" +
            "INSERT INTO db_platform.country VALUES ('BEN', '贝宁');\n" +
            "INSERT INTO db_platform.country VALUES ('BFA', '布基纳法索');\n" +
            "INSERT INTO db_platform.country VALUES ('BGD', '孟加拉');\n" +
            "INSERT INTO db_platform.country VALUES ('BGR', '保加利亚');\n" +
            "INSERT INTO db_platform.country VALUES ('BHR', '巴林');\n" +
            "INSERT INTO db_platform.country VALUES ('BHS', '巴哈马');\n" +
            "INSERT INTO db_platform.country VALUES ('BIH', '波斯尼亚和黑塞哥维那');\n" +
            "INSERT INTO db_platform.country VALUES ('BLR', '白俄罗斯');\n" +
            "INSERT INTO db_platform.country VALUES ('BLZ', '伯利兹');\n" +
            "INSERT INTO db_platform.country VALUES ('BMU', '百慕大');\n" +
            "INSERT INTO db_platform.country VALUES ('BOL', '玻利维亚');\n" +
            "INSERT INTO db_platform.country VALUES ('BRA', '巴西');\n" +
            "INSERT INTO db_platform.country VALUES ('BRB', '巴巴多斯岛');\n" +
            "INSERT INTO db_platform.country VALUES ('BRN', '文莱');\n" +
            "INSERT INTO db_platform.country VALUES ('BTN', '不丹');\n" +
            "INSERT INTO db_platform.country VALUES ('BVT', '布韦岛');\n" +
            "INSERT INTO db_platform.country VALUES ('BWA', '博茨瓦纳');\n" +
            "INSERT INTO db_platform.country VALUES ('CAF', '中非共和国');\n" +
            "INSERT INTO db_platform.country VALUES ('CAN', '加拿大');\n" +
            "INSERT INTO db_platform.country VALUES ('CCK', '科科斯群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('CHE', '瑞士');\n" +
            "INSERT INTO db_platform.country VALUES ('CHL', '智利');\n" +
            "INSERT INTO db_platform.country VALUES ('CIV', '科特迪瓦');\n" +
            "INSERT INTO db_platform.country VALUES ('CMR', '喀麦隆');\n" +
            "INSERT INTO db_platform.country VALUES ('CN', '中国');\n" +
            "INSERT INTO db_platform.country VALUES ('COD', '刚果民主共和国');\n" +
            "INSERT INTO db_platform.country VALUES ('COG', '刚果');\n" +
            "INSERT INTO db_platform.country VALUES ('COK', '库克群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('COL', '哥伦比亚');\n" +
            "INSERT INTO db_platform.country VALUES ('COM', '科摩罗');\n" +
            "INSERT INTO db_platform.country VALUES ('CPV', '佛得角');\n" +
            "INSERT INTO db_platform.country VALUES ('CRI', '哥斯达黎加');\n" +
            "INSERT INTO db_platform.country VALUES ('CUB', '古巴');\n" +
            "INSERT INTO db_platform.country VALUES ('CXR', '圣诞岛');\n" +
            "INSERT INTO db_platform.country VALUES ('CYM', '开曼群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('CYP', '塞浦路斯');\n" +
            "INSERT INTO db_platform.country VALUES ('CZE', '捷克共和国');\n" +
            "INSERT INTO db_platform.country VALUES ('DEU', '德国');\n" +
            "INSERT INTO db_platform.country VALUES ('DJI', '吉布提');\n" +
            "INSERT INTO db_platform.country VALUES ('DMA', '多米尼加');\n" +
            "INSERT INTO db_platform.country VALUES ('DNK', '丹麦');\n" +
            "INSERT INTO db_platform.country VALUES ('DOM', '多米尼加共和国');\n" +
            "INSERT INTO db_platform.country VALUES ('DZA', '阿尔及利亚');\n" +
            "INSERT INTO db_platform.country VALUES ('ECU', '厄瓜多尔');\n" +
            "INSERT INTO db_platform.country VALUES ('EGY', '埃及');\n" +
            "INSERT INTO db_platform.country VALUES ('ERI', '厄立特里亚');\n" +
            "INSERT INTO db_platform.country VALUES ('ESP', '西班牙');\n" +
            "INSERT INTO db_platform.country VALUES ('EST', '爱沙尼亚');\n" +
            "INSERT INTO db_platform.country VALUES ('ETH', '埃塞俄比亚');\n" +
            "INSERT INTO db_platform.country VALUES ('FIN', '芬兰');\n" +
            "INSERT INTO db_platform.country VALUES ('FJI', '斐济');\n" +
            "INSERT INTO db_platform.country VALUES ('FLK', '弗兰克群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('FRA', '法国');\n" +
            "INSERT INTO db_platform.country VALUES ('FRO', '法罗群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('FSM', '密克罗尼西亚');\n" +
            "INSERT INTO db_platform.country VALUES ('GAB', '加蓬');\n" +
            "INSERT INTO db_platform.country VALUES ('GBR', '英国');\n" +
            "INSERT INTO db_platform.country VALUES ('GEO', '乔治亚');\n" +
            "INSERT INTO db_platform.country VALUES ('GGY', '格恩西岛');\n" +
            "INSERT INTO db_platform.country VALUES ('GHA', '加纳');\n" +
            "INSERT INTO db_platform.country VALUES ('GIB', '直布罗陀');\n" +
            "INSERT INTO db_platform.country VALUES ('GIN', '几内亚');\n" +
            "INSERT INTO db_platform.country VALUES ('GLP', '瓜德罗普');\n" +
            "INSERT INTO db_platform.country VALUES ('GMB', '冈比亚');\n" +
            "INSERT INTO db_platform.country VALUES ('GNB', '几内亚比绍');\n" +
            "INSERT INTO db_platform.country VALUES ('GRC', '希腊');\n" +
            "INSERT INTO db_platform.country VALUES ('GRD', '格林纳达');\n" +
            "INSERT INTO db_platform.country VALUES ('GRL', '格陵兰');\n" +
            "INSERT INTO db_platform.country VALUES ('GTM', '危地马拉');\n" +
            "INSERT INTO db_platform.country VALUES ('GUF', '法属圭亚那');\n" +
            "INSERT INTO db_platform.country VALUES ('GUM', '关岛');\n" +
            "INSERT INTO db_platform.country VALUES ('GUY', '圭亚那');\n" +
            "INSERT INTO db_platform.country VALUES ('HMD', '赫德和麦克唐纳群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('HND', '洪都拉斯');\n" +
            "INSERT INTO db_platform.country VALUES ('HRV', '克罗地亚');\n" +
            "INSERT INTO db_platform.country VALUES ('HTI', '海地');\n" +
            "INSERT INTO db_platform.country VALUES ('HUN', '匈牙利');\n" +
            "INSERT INTO db_platform.country VALUES ('IDN', '印度尼西亚');\n" +
            "INSERT INTO db_platform.country VALUES ('IMN', '曼岛');\n" +
            "INSERT INTO db_platform.country VALUES ('IND', '印度');\n" +
            "INSERT INTO db_platform.country VALUES ('IOT', '英属印度洋领地');\n" +
            "INSERT INTO db_platform.country VALUES ('IRL', '爱尔兰');\n" +
            "INSERT INTO db_platform.country VALUES ('IRN', '伊朗');\n" +
            "INSERT INTO db_platform.country VALUES ('IRQ', '伊拉克');\n" +
            "INSERT INTO db_platform.country VALUES ('ISL', '冰岛');\n" +
            "INSERT INTO db_platform.country VALUES ('ISR', '以色列');\n" +
            "INSERT INTO db_platform.country VALUES ('ITA', '意大利');\n" +
            "INSERT INTO db_platform.country VALUES ('JAM', '牙买加');\n" +
            "INSERT INTO db_platform.country VALUES ('JEY', '泽西岛');\n" +
            "INSERT INTO db_platform.country VALUES ('JOR', '约旦');\n" +
            "INSERT INTO db_platform.country VALUES ('JPN', '日本');\n" +
            "INSERT INTO db_platform.country VALUES ('KAZ', '哈萨克斯坦');\n" +
            "INSERT INTO db_platform.country VALUES ('KEN', '肯尼亚');\n" +
            "INSERT INTO db_platform.country VALUES ('KGZ', '吉尔吉斯斯坦');\n" +
            "INSERT INTO db_platform.country VALUES ('KHM', '柬埔寨');\n" +
            "INSERT INTO db_platform.country VALUES ('KIR', '基里巴斯');\n" +
            "INSERT INTO db_platform.country VALUES ('KNA', '圣基茨和尼维斯');\n" +
            "INSERT INTO db_platform.country VALUES ('KOR', '韩国');\n" +
            "INSERT INTO db_platform.country VALUES ('KWT', '科威特');\n" +
            "INSERT INTO db_platform.country VALUES ('LAO', '老挝');\n" +
            "INSERT INTO db_platform.country VALUES ('LBN', '黎巴嫩');\n" +
            "INSERT INTO db_platform.country VALUES ('LBR', '利比里亚');\n" +
            "INSERT INTO db_platform.country VALUES ('LBY', '利比亚');\n" +
            "INSERT INTO db_platform.country VALUES ('LCA', '圣卢西亚');\n" +
            "INSERT INTO db_platform.country VALUES ('LIE', '列支敦士登');\n" +
            "INSERT INTO db_platform.country VALUES ('LKA', '斯里兰卡');\n" +
            "INSERT INTO db_platform.country VALUES ('LSO', '莱索托');\n" +
            "INSERT INTO db_platform.country VALUES ('LTU', '立陶宛');\n" +
            "INSERT INTO db_platform.country VALUES ('LUX', '卢森堡');\n" +
            "INSERT INTO db_platform.country VALUES ('LVA', '拉脱维亚');\n" +
            "INSERT INTO db_platform.country VALUES ('MAR', '摩洛哥');\n" +
            "INSERT INTO db_platform.country VALUES ('MCO', '摩纳哥');\n" +
            "INSERT INTO db_platform.country VALUES ('MDA', '摩尔多瓦');\n" +
            "INSERT INTO db_platform.country VALUES ('MDG', '马达加斯加');\n" +
            "INSERT INTO db_platform.country VALUES ('MDV', '马尔代夫');\n" +
            "INSERT INTO db_platform.country VALUES ('MEX', '墨西哥');\n" +
            "INSERT INTO db_platform.country VALUES ('MHL', '马绍尔群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('MKD', '马其顿');\n" +
            "INSERT INTO db_platform.country VALUES ('MLI', '马里');\n" +
            "INSERT INTO db_platform.country VALUES ('MLT', '马耳他');\n" +
            "INSERT INTO db_platform.country VALUES ('MMR', '缅甸');\n" +
            "INSERT INTO db_platform.country VALUES ('MNG', '蒙古');\n" +
            "INSERT INTO db_platform.country VALUES ('MNP', '北马里亚纳群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('MOZ', '莫桑比克');\n" +
            "INSERT INTO db_platform.country VALUES ('MRT', '毛里塔尼亚');\n" +
            "INSERT INTO db_platform.country VALUES ('MSR', '蒙特塞拉特');\n" +
            "INSERT INTO db_platform.country VALUES ('MTQ', '马提尼克');\n" +
            "INSERT INTO db_platform.country VALUES ('MUS', '毛里求斯');\n" +
            "INSERT INTO db_platform.country VALUES ('MWI', '马拉维');\n" +
            "INSERT INTO db_platform.country VALUES ('MYS', '马来西亚');\n" +
            "INSERT INTO db_platform.country VALUES ('MYT', '马约特岛');\n" +
            "INSERT INTO db_platform.country VALUES ('NAM', '纳米比亚');\n" +
            "INSERT INTO db_platform.country VALUES ('NCL', '新喀里多尼亚');\n" +
            "INSERT INTO db_platform.country VALUES ('NER', '尼日尔');\n" +
            "INSERT INTO db_platform.country VALUES ('NFK', '诺福克');\n" +
            "INSERT INTO db_platform.country VALUES ('NGA', '尼日利亚');\n" +
            "INSERT INTO db_platform.country VALUES ('NIC', '尼加拉瓜');\n" +
            "INSERT INTO db_platform.country VALUES ('NIU', '纽埃');\n" +
            "INSERT INTO db_platform.country VALUES ('NLD', '荷兰');\n" +
            "INSERT INTO db_platform.country VALUES ('NOR', '挪威');\n" +
            "INSERT INTO db_platform.country VALUES ('NPL', '尼泊尔');\n" +
            "INSERT INTO db_platform.country VALUES ('NRU', '瑙鲁');\n" +
            "INSERT INTO db_platform.country VALUES ('NZL', '新西兰');\n" +
            "INSERT INTO db_platform.country VALUES ('OMN', '阿曼');\n" +
            "INSERT INTO db_platform.country VALUES ('PAK', '巴基斯坦');\n" +
            "INSERT INTO db_platform.country VALUES ('PAN', '巴拿马');\n" +
            "INSERT INTO db_platform.country VALUES ('PCN', '皮特凯恩');\n" +
            "INSERT INTO db_platform.country VALUES ('PER', '秘鲁');\n" +
            "INSERT INTO db_platform.country VALUES ('PHL', '菲律宾');\n" +
            "INSERT INTO db_platform.country VALUES ('PLW', '帕劳群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('PNG', '巴布亚新几内亚');\n" +
            "INSERT INTO db_platform.country VALUES ('POL', '波兰');\n" +
            "INSERT INTO db_platform.country VALUES ('PRI', '波多黎各');\n" +
            "INSERT INTO db_platform.country VALUES ('PRK', '朝鲜');\n" +
            "INSERT INTO db_platform.country VALUES ('PRT', '葡萄牙');\n" +
            "INSERT INTO db_platform.country VALUES ('PRY', '巴拉圭');\n" +
            "INSERT INTO db_platform.country VALUES ('PSE', '巴勒斯坦');\n" +
            "INSERT INTO db_platform.country VALUES ('PYF', '法属波利尼西亚');\n" +
            "INSERT INTO db_platform.country VALUES ('QAT', '卡塔尔');\n" +
            "INSERT INTO db_platform.country VALUES ('REU', '留尼旺岛');\n" +
            "INSERT INTO db_platform.country VALUES ('ROU', '罗马尼亚');\n" +
            "INSERT INTO db_platform.country VALUES ('RUS', '俄罗斯');\n" +
            "INSERT INTO db_platform.country VALUES ('RWA', '卢旺达');\n" +
            "INSERT INTO db_platform.country VALUES ('SAU', '沙特阿拉伯');\n" +
            "INSERT INTO db_platform.country VALUES ('SCG', '塞尔维亚,黑山');\n" +
            "INSERT INTO db_platform.country VALUES ('SDN', '苏丹');\n" +
            "INSERT INTO db_platform.country VALUES ('SEN', '塞内加尔');\n" +
            "INSERT INTO db_platform.country VALUES ('SGP', '新加坡');\n" +
            "INSERT INTO db_platform.country VALUES ('SGS', '南乔治亚和南桑德威奇群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('SHN', '圣赫勒拿');\n" +
            "INSERT INTO db_platform.country VALUES ('SJM', '斯瓦尔巴和扬马廷');\n" +
            "INSERT INTO db_platform.country VALUES ('SLB', '所罗门群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('SLE', '塞拉利昂');\n" +
            "INSERT INTO db_platform.country VALUES ('SLV', '萨尔瓦多');\n" +
            "INSERT INTO db_platform.country VALUES ('SMR', '圣马力诺');\n" +
            "INSERT INTO db_platform.country VALUES ('SOM', '索马里');\n" +
            "INSERT INTO db_platform.country VALUES ('SPM', '圣皮埃尔和米克隆群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('STP', '圣多美和普林西比');\n" +
            "INSERT INTO db_platform.country VALUES ('SUR', '苏里南');\n" +
            "INSERT INTO db_platform.country VALUES ('SVK', '斯洛伐克');\n" +
            "INSERT INTO db_platform.country VALUES ('SVN', '斯洛文尼亚');\n" +
            "INSERT INTO db_platform.country VALUES ('SWE', '瑞典');\n" +
            "INSERT INTO db_platform.country VALUES ('SWZ', '斯威士兰');\n" +
            "INSERT INTO db_platform.country VALUES ('SYC', '塞舌尔');\n" +
            "INSERT INTO db_platform.country VALUES ('SYR', '叙利亚');\n" +
            "INSERT INTO db_platform.country VALUES ('TAA', '特里斯坦达昆哈');\n" +
            "INSERT INTO db_platform.country VALUES ('TCA', '特克斯和凯克特斯群岛');\n" +
            "INSERT INTO db_platform.country VALUES ('TCD', '乍得');\n" +
            "INSERT INTO db_platform.country VALUES ('TGO', '多哥');\n" +
            "INSERT INTO db_platform.country VALUES ('THA', '泰国');\n" +
            "INSERT INTO db_platform.country VALUES ('TJK', '塔吉克斯坦');\n" +
            "INSERT INTO db_platform.country VALUES ('TKL', '托克劳');\n" +
            "INSERT INTO db_platform.country VALUES ('TKM', '土库曼斯坦');\n" +
            "INSERT INTO db_platform.country VALUES ('TLS', '东帝汶');\n" +
            "INSERT INTO db_platform.country VALUES ('TON', '汤加');\n" +
            "INSERT INTO db_platform.country VALUES ('TTO', '特立尼达和多巴哥');\n" +
            "INSERT INTO db_platform.country VALUES ('TUN', '突尼斯');\n" +
            "INSERT INTO db_platform.country VALUES ('TUR', '土耳其');\n" +
            "INSERT INTO db_platform.country VALUES ('TUV', '图瓦卢');\n" +
            "INSERT INTO db_platform.country VALUES ('TZA', '坦桑尼亚');\n" +
            "INSERT INTO db_platform.country VALUES ('UGA', '乌干达');\n" +
            "INSERT INTO db_platform.country VALUES ('UKR', '乌克兰');\n" +
            "INSERT INTO db_platform.country VALUES ('UMI', '美属外岛');\n" +
            "INSERT INTO db_platform.country VALUES ('URY', '乌拉圭');\n" +
            "INSERT INTO db_platform.country VALUES ('USA', '美国');\n" +
            "INSERT INTO db_platform.country VALUES ('UZB', '乌兹别克斯坦');\n" +
            "INSERT INTO db_platform.country VALUES ('VAT', '梵蒂冈');\n" +
            "INSERT INTO db_platform.country VALUES ('VCT', '圣文森特和格林纳丁斯');\n" +
            "INSERT INTO db_platform.country VALUES ('VEN', '委内瑞拉');\n" +
            "INSERT INTO db_platform.country VALUES ('VGB', '维尔京群岛，英属');\n" +
            "INSERT INTO db_platform.country VALUES ('VIR', '维尔京群岛，美属');\n" +
            "INSERT INTO db_platform.country VALUES ('VNM', '越南');\n" +
            "INSERT INTO db_platform.country VALUES ('VUT', '瓦努阿图');\n" +
            "INSERT INTO db_platform.country VALUES ('WLF', '瓦利斯和福图纳');\n" +
            "INSERT INTO db_platform.country VALUES ('WSM', '萨摩亚');\n" +
            "INSERT INTO db_platform.country VALUES ('YEM', '也门');\n" +
            "INSERT INTO db_platform.country VALUES ('ZAF', '南非');\n" +
            "INSERT INTO db_platform.country VALUES ('ZMB', '赞比亚');\n" +
            "INSERT INTO db_platform.country VALUES ('ZWE', '津巴布韦');";

    public final static String resourceCreate=
            "INSERT INTO db_platform.resource VALUES (1004939518793060353, 0, '游戏管理', 0, 1, '', 1, '游戏管理菜单', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939521204785153, 1004939518793060353, '游戏类型', 0, 10, '/game/lotGameCategory/queryRecordByPage', 1, '', 0, 0, '', '', 'lotGameCategoryLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939523431960578, 1004939521204785153, '修改', 1, 0, '', 1, '', 0, 0, 'game/lotGameCategory', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939525654941697, 1004939521204785153, '删除', 1, 0, '', 1, '', 0, 0, 'game/lotGameCategory', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939527873728513, 1004939521204785153, '新增', 1, 0, '', 1, '', 0, 0, 'game/lotGameCategory', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939530096709633, 1004939518793060353, '游戏管理', 0, 11, '/game/lotGame/queryRecordByPage', 1, '', 0, 0, '', '', 'lotGameLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939532319690753, 1004939530096709633, '游戏限额', 1, 0, '', 1, '', 0, 0, 'game/lotGame', 'queryPageForLookup', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939534542671873, 1004939530096709633, '删除', 1, 0, '', 1, '', 0, 0, 'game/lotGame', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939536765652994, 1004939530096709633, '新增', 1, 0, '', 1, '', 0, 0, 'game/lotGame', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939538988634113, 1004939530096709633, '编辑', 1, 0, '', 1, '', 0, 0, 'game/lotGame', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939541224198145, 1004939518793060353, '玩法分类管理', 0, 12, '/game/lotGamePlayCategory/queryRecordByPage', 1, '', 0, 0, 'tt', 'ttt', 'lotGamePlayCategoryLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939543451373569, 1004939541224198145, '删除/批量删除', 1, 0, '', 1, '', 0, 0, 'game/lotGamePlayCategory', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939545665966082, 1004939541224198145, '新增', 1, 0, '', 1, '', 0, 0, 'game/lotGamePlayCategory', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939547888947202, 1004939541224198145, '编辑', 1, 0, '', 1, '', 0, 0, 'game/lotGamePlayCategory', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939550120316929, 1004939518793060353, '玩法详情管理', 0, 13, '/game/lotGamePlayItem/queryRecordByPage', 1, '', 0, 0, '', '', 'lotGamePlayItemLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939552334909441, 1004939550120316929, '新增', 1, 0, '', 1, '', 0, 0, 'game/lotGamePlayItem', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939554557890561, 1004939550120316929, '删除', 1, 0, '', 1, '', 0, 0, 'game/lotGamePlayItem', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939556785065986, 1004939550120316929, '游戏拷贝', 1, 0, '', 1, '', 0, 0, 'game/lotGamePlayItem', 'copyItem', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939559003852802, 1004939550120316929, '编辑', 1, 0, '', 1, '', 0, 0, 'game/lotGamePlayItem', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939561226833922, 1004939518793060353, '标准期号管理', 0, 14, '/game/lotGameNoTemplate/queryRecordByPage', 1, '', 0, 0, '', '', 'lotGameNoTemplateLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939563454009345, 1004939561226833922, '封盘设置时间', 1, 0, '', 1, '', 0, 0, 'game/lotGameNoTemplate', 'endTimeAdvance', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939565681184769, 1004939561226833922, '编辑', 1, 0, '', 1, '', 0, 0, 'game/lotGameNoTemplate', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939567908360193, 1004939561226833922, '删除', 1, 0, '', 1, '', 0, 0, 'game/lotGameNoTemplate', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939570122952705, 1004939561226833922, '新增', 1, 0, '', 1, '', 0, 0, 'game/lotGameNoTemplate', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939572341739521, 1004939518793060353, '游戏期数管理', 0, 15, '/game/lotGameNo/queryRecordByPage', 1, '', 0, 0, '', '', 'lotGameNoLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939574564720642, 1004939572341739521, '删除', 1, 0, '', 1, '', 0, 0, 'game/lotGameNo', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939576791896065, 1004939572341739521, '编辑', 1, 0, '', 1, '', 0, 0, 'game/lotGameNo', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939579014877185, 1004939572341739521, '重新采集未开奖', 1, 0, '', 1, '', 0, 0, 'game/lotGameNo', 'reSettleAll', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939581233664001, 1004939518793060353, '游戏赔率模板', 0, 16, '/game/lotGamePlayTemplate/queryRecordByPage', 1, '', 0, 0, '', '', 'lotGamePlayTemplateLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939583456645122, 1004939581233664001, '编辑', 1, 0, '', 1, '', 0, 0, 'game/lotGamePlayTemplate', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939585675431937, 1004939581233664001, '新增', 1, 0, '', 1, '', 0, 0, 'game/lotGamePlayTemplate', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939587898413057, 1004939581233664001, '删除', 1, 0, '', 1, '', 0, 0, 'game/lotGamePlayTemplate', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939590125588481, 1004939581233664001, '设置默认', 1, 0, '', 1, '', 0, 0, 'game/lotGamePlayTemplate', 'setDefaultTemp', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939592352763905, 0, '即时注单', 0, 2, '/order/lotOrder/queryRecordByPage', 1, '', 0, 0, '', '', 'glyphicon glyphicon-check', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939594571550722, 1004939592352763905, '注单管理', 0, 0, '/order/lotOrder/queryRecordByPage', 1, '', 0, 0, '', '', 'lotOrderLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939596790337537, 1004939594571550722, '删除', 1, 0, 'order/lotOrder', 1, '', 0, 0, 'order/lotOrder', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939599017512961, 1004939594571550722, '批量删除', 1, 0, '', 1, '', 0, 0, 'order/lotOrder', 'deletes', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939601248882690, 1004939592352763905, '追号管理', 0, 0, '/order/lotOrderChase/queryRecordByPage', 1, '', 0, 0, '', '', 'lotOrderChaseLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939603476058113, 1004939601248882690, '删除', 1, 0, '', 1, '', 0, 0, 'order/lotOrderChase', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939605703233538, 1004939592352763905, '异常注单', 0, 0, '/order/lotOrder/unusualOrderList', 1, '', 0, 0, '', '', 'lotOldOrderLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939607930408962, 1004939592352763905, '注单队列', 0, 0, '/order/lotOrderQueue/queryRecordByPage', 1, '', 0, 0, '', '', 'lotOrderQueueLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939610153390081, 1004939607930408962, '开启队列', 1, 0, '', 1, '', 0, 0, 'order/lotOrderQueue', 'openQueue', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939612380565506, 1004939592352763905, '历史注单管理', 0, 0, '/order/lotOldOrder/queryRecordByPage', 1, '', 0, 0, '', '', 'lotOldOrderLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939614607740929, 0, '用户管理', 0, 3, '/user/lotUser/queryRecordByPage', 1, '', 0, 0, '', '', 'glyphicon glyphicon-user', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939616834916354, 1004939614607740929, '推广链接', 0, 0, '/user/lotUserRefer/queryRecordByPage', 1, '', 0, 0, '', '', 'lotUserReferLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939619053703170, 1004939616834916354, '删除', 1, 0, '', 1, '', 0, 0, 'user/lotUserRefer', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939621276684290, 1004939616834916354, '修改', 1, 0, '', 1, '', 0, 0, 'user/lotUserRefer', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939623499665409, 1004939616834916354, '新增', 1, 0, '', 1, '', 0, 0, 'user/lotUserRefer', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939625726840834, 1004939616834916354, '停用启用', 1, 0, '', 1, '', 0, 0, 'user/lotUserRefer', 'updateReferEnable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939627949821954, 1004939614607740929, '代理会员', 0, 0, '/user/lotUser/queryRecordByPage', 1, '', 0, 0, '', '', 'lotUserLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939630172803074, 1004939627949821954, '控制', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'changeStatus', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939632395784194, 1004939627949821954, '启用/禁用', 1, 0, '', 1, '银行卡管理操作按钮', 0, 0, 'user/lotUserBank', 'updateEnableStatus', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939634622959617, 1004939627949821954, '限额', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'selectUserLimit', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939636850135042, 1004939627949821954, '完整资料', 1, 0, '', 1, '', 0, 0, 'user/lotUserInfo', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939639068921858, 1004939627949821954, '相关资料', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'userRelevantInfo', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939641291902978, 1004939627949821954, '修改银行卡', 1, 0, '', 1, '银行卡管理操作按钮', 0, 0, 'user/lotUserBank', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939643544244226, 1004939627949821954, '新增', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939645771419650, 1004939627949821954, '删除', 1, 0, '', 1, '', 0, 1, 'user/lotUser', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939647994400769, 1004939627949821954, '日志', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'userLog', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939650263519233, 1004939627949821954, '修改', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939652490694658, 1004939627949821954, '设置默认', 1, 0, '', 1, '银行卡管理操作按钮', 0, 0, 'user/lotUserBank', 'updateDefeaultStatus', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939654726258690, 1004939627949821954, '下级', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'queryByPage', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939656945045505, 1004939627949821954, '删除银行卡', 1, 0, '', 1, '', 0, 0, 'user/lotUserBank', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939659168026626, 1004939627949821954, '银行卡资料', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'selectUserBank', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939661395202050, 1004939627949821954, '新增银行卡', 1, 0, '', 1, '银行卡管理操作按钮', 0, 0, 'user/lotUserBank', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939663618183169, 1004939627949821954, '会员批量迁移', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'transferUser', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939665857941506, 1004939627949821954, '显示会员完整资料', 1, 0, '', 1, '', 0, 0, 'user/lotUserInfo', 'showAllInfo', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939668080922625, 1004939627949821954, '代理迁移', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'transfer', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939670308098049, 1004939627949821954, '代理会员数据导出', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'checkOutExcel', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939672577216513, 1004939614607740929, '用户等级', 0, 0, '/user/lotUserRank/queryRecordByPage', 1, '', 0, 0, '', '', 'lotUserRankLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939674812780546, 1004939672577216513, '修改', 1, 0, '', 1, '', 0, 0, 'user/lotUserRank', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939677119647745, 1004939672577216513, '删除', 1, 0, '', 1, '', 0, 0, 'user/lotUserRank', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939679351017473, 1004939672577216513, '新增', 1, 0, '', 1, '', 0, 0, 'user/lotUserRank', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939681578192898, 1004939614607740929, '用户积分', 0, 0, '/user/lotUserScore/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939683817951234, 1004939681578192898, '用户积分', 0, 0, '/user/lotUser/queryUserRecordByPage', 1, '', 0, 0, '', '', 'lotUserScoreAccessLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939686040932353, 1004939683817951234, '取出积分', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'takeOutScore', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939688268107777, 1004939683817951234, '下级', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'lowUser', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939690541420546, 1004939683817951234, '存入积分', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'depositScore', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939692776984578, 1004939683817951234, '积分流水', 1, 0, '', 1, '', 0, 0, 'user/lotUser', 'scoreStream', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939695046103042, 1004939681578192898, '积分流水', 0, 0, '/user/lotUserScore/queryRecordByPage', 1, '', 0, 0, '', '', 'lotUserScoreLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939701824098305, 1004939699605311489, '修改', 1, 0, '', 1, '', 0, 0, '/user/LotExchangeType', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939704109993986, 1004939699605311489, '新增', 1, 0, '', 1, '', 0, 0, '/user/LotExchangeType', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939706341363713, 1004939614607740929, '测试账户', 0, 0, '/user/lotTestUser/queryRecordByPage', 1, '', 0, 0, '', '', 'lotTestUserLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939708564344833, 1004939706341363713, '详情', 1, 0, '', 1, '', 0, 0, 'user/lotTestUser', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939710787325953, 1004939706341363713, '下级', 1, 0, '', 1, '', 0, 0, 'user/lotTestUser', 'queryByPage', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939713001918466, 1004939706341363713, '新增', 1, 0, '', 1, '', 0, 0, 'user/lotTestUser', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939715220705282, 1004939706341363713, '日志', 1, 0, '', 1, '', 0, 0, 'user/lotTestUser', 'userLog', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939717443686402, 1004939706341363713, '控制', 1, 0, '', 1, '', 0, 0, 'user/lotTestUser', 'changeStatus', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939719658278913, 1004939614607740929, '试玩账户', 0, 0, '/user/lotDemoUser/queryDemoByPage', 1, '', 0, 0, '', '', 'lotDemoUserLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939721889648642, 1004939719658278913, '删除/批量删除', 1, 0, '', 1, '', 0, 0, '/user/lotDemoUser', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939724112629761, 1004939614607740929, '代理分红', 0, 10, '/user/lotUserBonusSetting/queryBonusSetting', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939726335610882, 1004939724112629761, '分红设定', 0, 0, '/user/lotUserAonus/queryRecordByPage', 1, '', 0, 0, '', '', 'lotUserBonusLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939728566980610, 1004939724112629761, '分红列表', 0, 0, '/user/lotUserBonus/bonus/queryRecordByPage', 1, '', 0, 0, '', '', 'queryUserLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939730789961730, 1004939728566980610, '分红/日工资审核', 1, 0, '', 1, '', 0, 0, 'user/lotUserBonus', 'updateState', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939733012942849, 1004939728566980610, '分红/日工资批量审核', 1, 0, '', 1, '', 0, 0, 'user/lotUserBonus', 'updateStateAll', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939735244312578, 1004939724112629761, '契约分红设置', 0, 1, '/user/lotUserContractBonus/queryRecordByPage', 1, '', 0, 0, '', '', 'lotUserContractBonusLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939737505042434, 1004939724112629761, '契约分红列表', 0, 2, '/user/lotUserBonus/contract/queryRecordByPage', 1, '', 0, 0, '', '', 'queryUserContractLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939739778355202, 1004939614607740929, '日工资管理', 0, 11, '/user/lotUserBonusSetting/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939742034890753, 1004939739778355202, '日工资设置', 0, 0, '/user/lotUserBonusSetting/queryRecordByPage', 1, '', 0, 0, '', '', 'lotUserDailyLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939744253677569, 1004939739778355202, '日工资列表', 0, 0, '/user/lotUserBonus/wage/queryRecordByPage', 1, '', 0, 0, '', '', 'queryUserWageLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939747521040385, 1004939739778355202, '契约工资设置', 0, 1, '/user/lotUserBonusSetting/contract/queryRecordByPage', 1, '', 0, 0, '', '', 'salaryListLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939749744021505, 1004939739778355202, '契约工资列表', 0, 2, '/user/lotUserBonus/dailyWage/queryRecordByPage', 1, '', 0, 0, '', '', 'queryUserDailyWageLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939751962808321, 1004939614607740929, '天天返水', 0, 12, '/user/lotUserRebateMain/rebateRule/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939754185789442, 1004939751962808321, '返水规则设定', 0, 0, '/user/lotUserRebateMain/rebateRule/queryRecordByPage', 1, '', 0, 0, '', '', 'queryRebateRuleLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939756412964865, 1004939754185789442, '编辑', 1, 0, '', 1, '', 0, 0, 'user/lotUserRebateMain', 'edit', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939758640140290, 1004939751962808321, '返水统计', 0, 1, '/user/lotUserRebateCount/queryRecordByPage', 1, '', 0, 0, '', '', 'queryRebateCountLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939760858927106, 1004939758640140290, '返水', 1, 0, '', 1, '', 0, 0, 'user/lotUserRebateCount', 'rebateWater', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939763081908226, 1004939751962808321, '返水结果查询', 0, 2, '/user/lotUserRebateResult/rebateResult/queryRecordByPage', 1, '', 0, 0, '', '', 'queryRebateResultLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939765313277954, 1004939763081908226, '返水冲销', 1, 0, '', 1, '', 0, 0, 'user/lotUserRebateResult', 'writeOff', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939767532064769, 0, '系统管理', 0, 4, '/sys/lotSysUser/queryRecordByPage', 1, '', 0, 0, '', '', 'glyphicon glyphicon-cog', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939769750851585, 1004939767532064769, '公告消息', 0, 0, '/sys/lotSysMessage/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939771973832705, 1004939769750851585, '消息管理', 0, 0, '/sys/lotSysMessage/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSysMessageLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939774217785345, 1004939771973832705, '修改', 1, 0, '', 1, '', 0, 0, 'sys/lotSysMessage', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939776444960769, 1004939771973832705, '新增', 1, 0, '', 1, '', 0, 0, 'sys/lotSysMessage', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939778667941889, 1004939771973832705, '删除', 1, 0, '', 1, '', 0, 0, 'sys/lotSysMessage', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939780895117313, 1004939771973832705, '发送', 1, 0, '', 1, '', 0, 0, 'sys/lotSysMessageSend', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939783118098433, 1004939771973832705, '记录', 1, 0, '', 1, '', 0, 0, 'sys/lotSysMessageSend', 'queryRecordByPage', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939785336885250, 1004939769750851585, '公告管理', 0, 0, '/sys/lotSysNotice/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSysNoticeLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939787551477761, 1004939785336885250, '启用/禁用', 1, 0, '', 1, '', 0, 0, 'sys/lotSysNotice', 'changeNoticeStatus', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939789778653186, 1004939785336885250, '删除', 1, 0, '', 1, '', 0, 0, 'sys/lotSysNotice', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939791997440001, 1004939785336885250, '新增', 1, 0, '', 1, '', 0, 0, 'sys/lotSysNotice', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939794220421121, 1004939785336885250, '修改', 1, 0, '', 1, '', 0, 0, 'sys/lotSysNotice', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939796447596545, 1004939769750851585, '会员留言', 0, 0, '/sys/lotSysMessage/queryRecordUserLeaveByPage', 1, '', 0, 0, '', '', 'lotSysMessageLeaveLiNav', 1);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939798666383361, 1004939796447596545, '回复', 1, 0, '', 1, '', 0, 0, 'sys/lotSysMessageUser', 'updateReplyMessage', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939800893558785, 1004939796447596545, '删除', 1, 0, '', 1, '', 0, 0, 'sys/lotSysMessageUser', 'deleteUserLeave', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939803116539905, 1004939767532064769, '权限管理', 0, 0, '/sys/lotSysUser/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939805339521025, 1004939803116539905, '资源管理', 0, 0, '/sys/lotSysResource/initTreePage', 1, '', 0, 0, '', '', 'lotSysResouceLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939807562502145, 1004939805339521025, '保存', 1, 0, 'sys/lotSysResource', 1, '', 0, 0, 'sys/lotSysResource', 'save', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939809789677570, 1004939805339521025, '删除', 1, 0, '', 1, '', 0, 0, 'sys/lotSysResource', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939812008464386, 1004939803116539905, '角色管理', 0, 0, '/sys/lotSysRole/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSysRoleLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939814235639809, 1004939812008464386, '删除', 1, 0, '', 1, '', 0, 0, 'sys/lotSysRole', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939816462815234, 1004939812008464386, '编辑', 1, 0, '', 1, '', 0, 0, 'sys/lotSysRole', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939818689990657, 1004939812008464386, '角色设置', 1, 0, '', 1, '', 0, 0, 'sys/lotSysRole', 'editRole', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939820912971777, 1004939812008464386, '新增', 1, 0, '', 1, '', 0, 0, 'sys/lotSysRole', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939823140147201, 1004939803116539905, '部门管理', 0, 0, '/sys/lotSysDept/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSysDeptLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939825363128322, 1004939823140147201, '删除', 1, 0, '', 1, '', 0, 0, 'sys/lotSysDept', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939827586109442, 1004939823140147201, '新增', 1, 0, '', 1, '', 0, 0, 'sys/lotSysDept', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939829804896258, 1004939823140147201, '编辑', 1, 0, '', 1, '', 0, 0, 'sys/lotSysDept', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939832141123585, 1004939803116539905, '员工管理', 0, 0, '/sys/lotSysUser/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSysUserLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939834376687617, 1004939832141123585, '新增', 1, 0, '', 1, '', 0, 0, 'sys/lotSysUser', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939836599668738, 1004939832141123585, '解绑谷歌验证器', 1, 0, '', 1, '', 0, 0, 'sys/lotSysUser', 'unbind', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939838818455553, 1004939832141123585, '重置密码', 1, 0, '', 1, '', 0, 0, 'sys/lotSysUser', 'resetPwd', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939841041436673, 1004939832141123585, '角色授权', 1, 0, '', 1, '', 0, 0, 'sys/lotSysUser', 'editRole', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939843264417794, 1004939832141123585, '编辑', 1, 0, '', 1, '', 0, 0, 'sys/lotSysUser', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939845479010306, 1004939832141123585, '删除', 1, 0, '', 1, '', 0, 0, 'sys/lotSysUser', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939847727157250, 1004939767532064769, '开奖结果', 0, 0, '/sys/lotSysLotteryResults/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSysLotteryResultsLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939849954332674, 1004939767532064769, '系统设置', 0, 0, '', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939852177313794, 1004939849954332674, '区域管理', 0, 0, '/sys/lotSysArea/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSysAreaLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939854404489217, 1004939849954332674, '银行表管理', 0, 0, '/sys/lotSysBank/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSysBankLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939856627470337, 1004939854404489217, '新增', 1, 0, '', 1, '', 0, 0, 'sys/lotSysBank', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939858846257154, 1004939854404489217, '停用/启用', 1, 0, '', 1, '', 0, 0, 'sys/lotSysBank', 'edit', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939861065043970, 1004939854404489217, '编辑', 1, 0, '', 1, '', 0, 0, 'sys/lotSysBank', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939863292219394, 1004939854404489217, '删除', 1, 0, '', 1, '', 0, 0, 'sys/lotSysBank', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939865519394817, 1004939849954332674, '字典表管理', 0, 0, '/sys/lotSysKeyValue/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSysKeyValueLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939867746570241, 1004939865519394817, '新增', 1, 0, '', 1, '', 0, 0, 'sys/lotSysKeyValue', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939869965357057, 1004939865519394817, '删除/批量删除', 1, 0, '', 1, '', 0, 0, 'sys/lotSysKeyValue', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939872188338178, 1004939865519394817, '编辑', 1, 0, '', 1, '', 0, 0, 'sys/lotSysKeyValue', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939874402930689, 1004939849954332674, '提示音设置', 0, 0, '/sys/lotSysConfig/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSysConfigLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939876630106113, 1004939874402930689, '编辑', 1, 0, '', 1, '', 0, 0, 'sys/lotSysConfig', 'edit', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939881080262658, 1004939878857281537, '删除/批量删除', 1, 0, '', 1, '', 0, 0, 'sys/lotSysBlacklist', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939883303243778, 1004939878857281537, '编辑', 1, 0, '', 1, '', 0, 0, 'sys/lotSysBlacklist', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939885522030594, 1004939878857281537, '新增', 1, 0, '', 1, '', 0, 0, 'sys/lotSysBlacklist', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939889967992833, 1004939849954332674, '银行网点', 0, 4, '/sys/lotSysBankNetwork/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSysBankNetworkLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939892199362561, 1004939889967992833, '编辑', 1, 0, '', 1, '', 0, 0, 'sys/lotSysBankNetwork', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939894422343681, 1004939889967992833, '新增', 1, 0, '', 1, '', 0, 0, 'sys/lotSysBankNetwork', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939896645324801, 1004939889967992833, '删除/批量删除', 1, 0, '', 1, '', 0, 0, 'sys/lotSysBankNetwork', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939898872500226, 1004939849954332674, '系统相关', 0, 7, '/sys/lotSysConfig/querySysOnOffByPage', 1, '', 0, 0, '', '', 'lotSysConfigLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939901091287042, 0, '平台管理', 0, 5, '/site/lotSite/queryRecordByPage', 1, '', 0, 0, '', '', 'glyphicon glyphicon-th-large', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939905554026498, 1004939903322656769, '相册管理', 0, 0, '/site/lotSiteAlbum/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteAlbumLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939907777007618, 1004939905554026498, '编辑', 1, 0, '', 1, '', 0, 0, 'site/lotSiteAlbum', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939909999988738, 1004939905554026498, '删除', 1, 0, '', 1, '', 0, 0, 'site/lotSiteAlbum', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939912222969858, 1004939905554026498, '新增', 1, 0, '', 1, '', 0, 0, 'site/lotSiteAlbum', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939914445950978, 1004939903322656769, '图片管理', 0, 0, '/site/lotSiteAlbumPic/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteAlbumPicLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939916668932097, 1004939914445950978, '编辑', 1, 0, '', 1, '', 0, 0, 'site/lotSiteAlbumPic', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939918891913218, 1004939914445950978, '删除', 1, 0, '', 1, '', 0, 0, 'site/lotSiteAlbumPic', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939921114894337, 1004939914445950978, '新增', 1, 0, '', 1, '', 0, 0, 'site/lotSiteAlbumPic', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939923329486849, 1004939901091287042, '站点配置', 0, 0, '/site/lotSite/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939925556662274, 1004939923329486849, '游戏设置', 0, 0, '/site/lotSiteGameConfig/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteGameConfigLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939927775449090, 1004939925556662274, '编辑', 1, 0, '', 1, '', 0, 0, 'site/lotSiteGameConfig', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939929994235906, 1004939925556662274, '添加', 1, 0, '', 1, '', 0, 0, 'site/lotSiteGameConfig', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939932217217025, 1004939925556662274, '限额限定', 1, 0, '', 1, '', 0, 0, 'site/lotSiteGameConfig', 'initTempSelect', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939934448586753, 1004939925556662274, '限额编辑', 1, 0, '', 1, '', 0, 0, 'site/lotSiteGameConfig', 'addOrUpdate', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939936679956481, 1004939925556662274, '游戏配置', 1, 0, '', 1, '', 0, 0, 'site/gameManager', 'gameManager', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939938898743298, 1004939925556662274, '游戏玩法配置', 1, 0, '', 1, '', 0, 0, 'site/lotSiteGameConfig', 'siteGamePlayConfig', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939943374065665, 1004939923329486849, '注册会员配置', 0, 0, '/site/lotSiteRegConfig/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteRegConfigLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939945638989826, 1004939943374065665, '添加', 1, 0, '', 1, '', 0, 0, 'site/lotSiteRegConfig', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939947861970945, 1004939943374065665, '删除', 1, 0, '', 1, '', 0, 0, 'site/lotSiteRegConfig', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939950084952065, 1004939943374065665, '编辑', 1, 0, '', 1, '', 0, 0, 'site/lotSiteRegConfig', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939952312127490, 1004939923329486849, '站点管理', 0, 0, '/site/lotSite/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939954539302914, 1004939952312127490, '游戏管理', 1, 0, '', 1, '', 0, 0, 'site/lotSite', 'gameConfig', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939956770672641, 1004939952312127490, '编辑', 1, 0, '', 1, '', 0, 0, 'site/lotSite', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939958993653761, 1004939952312127490, '删除', 1, 0, '', 1, '', 0, 0, 'site/lotSite', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939961220829186, 1004939952312127490, '新增', 1, 0, 'site/lotSite', 1, '', 0, 0, 'site/lotSite', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939963448004609, 1004939952312127490, '根据站点查询', 1, 0, '', 1, '', 0, 0, 'site/lotSite', 'querySiteForLookUp', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939965675180033, 1004939952312127490, '批量维护', 1, 0, '', 1, '', 0, 0, 'site/lotSite', 'updateIsEnable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939967898161154, 1004939923329486849, '玩法设置（总开关）', 0, 0, '/site/siteGameSetTotal/queryRecordByPage', 1, '', 0, 0, '', '', 'gameSetTotalLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939970116947970, 1004939967898161154, '保存', 1, 0, '', 1, '', 0, 0, 'site/siteGameSetTotal', 'saveOrUpdateTotal', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939972348317698, 1004939923329486849, '玩法设置（细开关）', 0, 0, '/site/siteGameSetFine/queryRecordByPage', 1, '', 0, 0, '', '', 'gameSetFineLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939974571298818, 1004939972348317698, '保存', 1, 0, '', 1, '', 0, 0, 'site/siteGameSetFine', 'saveOrUpdateFine', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939976798474242, 1004939901091287042, '站点栏目', 0, 0, '/site/lotSiteOfficeCategory/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939979021455361, 1004939976798474242, '栏目管理', 0, 0, '/site/lotSiteChannel/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteChannelLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939981236047873, 1004939979021455361, '新增', 1, 0, '', 1, '', 0, 0, 'site/lotSiteChannel', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939983459028993, 1004939979021455361, '删除', 1, 0, '', 1, '', 0, 0, 'site/lotSiteChannel', 'del', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939985686204417, 1004939979021455361, '编辑', 1, 0, '', 1, '', 0, 0, 'site/lotSiteChannel', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939987913379842, 1004939976798474242, '文案分类', 0, 0, '/site/lotSiteOfficeCategory/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteOfficeCategoryLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939990136360962, 1004939987913379842, '新增', 1, 0, '', 1, '', 0, 0, 'site/lotSiteOfficeCategory', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939992430645249, 1004939987913379842, '文案', 1, 0, '', 1, '', 0, 0, 'site/lotSiteOfficeCategory', 'office', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939994657820674, 1004939987913379842, '文案送审', 1, 0, '', 1, '', 0, 0, 'site/lotSiteOffice', 'enable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939996880801794, 1004939987913379842, '删除', 1, 0, 'site/lotSiteOfficeCategory', 1, '', 0, 0, 'site/lotSiteOfficeCategory', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004939999099588609, 1004939987913379842, '修改', 1, 0, '', 1, '', 0, 0, 'site/lotSiteOfficeCategory', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940001347735553, 1004939901091287042, '域名配置', 0, 0, '/site/lotSiteNav/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940003570716673, 1004940001347735553, '域名管理', 0, 541, '/site/lotSiteDenfense/queryRecordByPage', 1, '新版域名管理', 0, 0, '', '', 'lotSiteDenfenseLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940005793697794, 1004940003570716673, '新增', 1, 0, 'site/lotSiteDenfense', 1, '', 0, 0, 'site/lotSiteDenfense', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940008016678913, 1004940003570716673, '删除', 1, 0, '', 1, '', 0, 0, 'site/lotSiteDenfense', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940010248048642, 1004940003570716673, '修改', 1, 0, '', 1, '', 0, 0, 'site/lotSiteDenfense', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940021358759938, 1004940001347735553, '线路组管理', 0, 543, '/site/lotSiteLineGroup/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteLineGroupLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940023581741058, 1004940021358759938, '删除', 1, 0, '', 1, '', 0, 0, 'site/lotSiteLineGroup', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940025813110786, 1004940021358759938, '修改', 1, 0, '', 1, '', 0, 0, 'site/lotSiteLineGroup', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940028040286209, 1004940021358759938, '启用/停用', 1, 0, '', 1, '', 0, 0, 'site/lotSiteLineGroup', 'enable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940030275850241, 1004940021358759938, '添加', 1, 0, '', 1, '', 0, 0, 'site/lotSiteLineGroup', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940034730201089, 1004940032503025666, '删除', 1, 0, '', 1, '', 0, 0, 'site/lotSiteLine', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940036944793601, 1004940032503025666, '添加', 1, 0, '', 1, '', 0, 0, 'site/lotSiteLine', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940039159386113, 1004940032503025666, '修改', 1, 0, '', 1, '', 0, 0, 'site/lotSiteLine', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940041386561538, 1004940032503025666, '启用/停用', 1, 0, '', 1, '', 0, 0, 'site/lotSiteLine', 'enable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940048055504898, 1004940045836718082, '限制地区', 0, 0, '/site/lotSiteCountryLimit/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteRegionLimitLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940050291068930, 1004940045836718082, '限制IP', 0, 1, '/site/lotSiteIpLimit/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteIpLimitLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940052514050050, 1004940045836718082, '允许IP', 0, 2, '/site/lotSiteIpLimit/queryHomeWhiteByPage', 1, '', 0, 0, '', '', 'lotSiteIpLimitLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940054737031170, 1004940045836718082, '后台允许IP', 0, 3, '/site/lotSiteIpLimit/queryAdminWhiteByPage', 1, '', 0, 0, '', '', 'lotSiteIpLimitLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940056960012290, 0, '现金管理', 0, 5, '/pay/lotPayDepositMode/queryRecordByPage', 1, '', 0, 0, '', '', 'glyphicon glyphicon-usd', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940059191382017, 1004940056960012290, '支付平台管理', 0, 0, '/pay/lotPayPlatform/queryRecordByPage', 1, '', 0, 0, '', '', 'lotPayPlatformLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940061418557441, 1004940059191382017, '删除支付银行', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPlatformBank', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940063641538562, 1004940059191382017, '新增支付银行', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPlatformBank', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940065868713986, 1004940059191382017, '修改支付银行', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPlatformBank', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940068142026754, 1004940059191382017, '新增', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPlatform', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940070440505346, 1004940059191382017, '停用', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPlatform', 'disable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940072957087746, 1004940059191382017, '启用', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPlatform', 'enable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940075364618241, 1004940059191382017, '修改', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPlatform', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940077587599362, 1004940059191382017, '删除', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPlatform', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940079810580481, 1004940056960012290, '用户分组管理', 0, 0, '/pay/lotPayUserGroup/queryRecordByPage', 1, '', 0, 0, '', '', 'lotPayUserGroupLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940082037755905, 1004940079810580481, '保存分组模式设定', 1, 0, '', 1, '', 0, 0, 'pay/lotPayGroupSetting', 'addGroupMode', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940084260737026, 1004940079810580481, '批量回归用户', 1, 0, '', 1, '', 0, 0, 'pay/lotPayUserGroupRela', 'batchChageReturn', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940086487912449, 1004940079810580481, '手动分组', 1, 0, '', 1, '', 0, 0, 'pay/lotPayUserGroupRela', 'batchUpdate', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940088706699266, 1004940079810580481, '批量锁定/解锁用户', 1, 0, '', 1, '', 0, 0, 'pay/lotPayUserGroupRela', 'batchChangeLock', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940090933874690, 1004940079810580481, '用户自动分组', 1, 0, 'pay/lotPayUserGroupRela', 1, '', 0, 0, 'pay/lotPayUserGroupRela', 'changeAutoGroup', 'changeAutoGroup', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940093156855809, 1004940079810580481, '转移用户', 1, 0, '', 1, '', 0, 0, 'pay/lotPayUserGroupRela', 'changeGroup', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940095375642626, 1004940079810580481, '批量转移用户', 1, 0, '', 1, '', 0, 0, 'pay/lotPayUserGroupRela', 'batchChangeTransfer', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940097594429441, 1004940079810580481, '新增组', 1, 0, '', 1, '', 0, 0, 'pay/lotPayUserGroup', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940099817410562, 1004940079810580481, '锁定/解锁用户', 1, 0, '', 1, '', 0, 0, 'pay/lotPayUserGroupRela', 'changeLock', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940102036197378, 1004940079810580481, '保存分组支付方式设定', 1, 0, '', 1, '', 0, 0, 'pay/lotPayGroupSetting', 'saveGroupPay', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940104263372802, 1004940079810580481, '修改用户组信息', 1, 0, '', 1, '', 0, 0, 'pay/lotPayUserGroup', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940106486353922, 1004940079810580481, '删除组', 1, 0, '', 1, '', 0, 0, 'pay/lotPayUserGroup', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940108709335041, 1004940079810580481, '批量导出分组会员', 1, 0, '', 1, '', 0, 0, 'pay/lotPayUserGroupRela', 'batchCheckOutExcel', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940110928121857, 1004940056960012290, '收款账户管理', 0, 0, '/pay/lotPayOfflineAccount/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940113155297281, 1004940110928121857, '第三方支付账户', 0, 0, '/pay/lotPayThirdAccount/queryRecordByPage', 1, '', 0, 0, '', '', 'lotPayThirdAccountLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940115374084098, 1004940113155297281, '新增', 1, 0, 'pay/lotPayThirdAccount', 1, '', 0, 0, 'pay/lotPayThirdAccount', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940117601259522, 1004940113155297281, '测试充值', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOnlineDeposit', 'initNormalPay_', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940119824240642, 1004940113155297281, '启用', 1, 0, '', 1, '', 0, 0, 'pay/lotPayThirdAccount', 'enable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940122043027457, 1004940113155297281, '停用', 1, 0, '', 1, '', 0, 0, 'pay/lotPayThirdAccount', 'disable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940124266008577, 1004940113155297281, '修改', 1, 0, '', 1, '', 0, 0, 'pay/lotPayThirdAccount', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940126484795394, 1004940113155297281, '银行信息,启用/停用', 1, 0, '', 1, '', 0, 0, 'pay/lotPayThirdBank', 'enable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940128707776513, 1004940113155297281, '删除', 1, 0, '', 1, '', 0, 0, 'pay/lotPayThirdAccount', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940130926563330, 1004940110928121857, '预付卡支付', 0, 0, '/pay/lotPayPrepaycard/queryRecordByPage', 1, '', 0, 0, '', '', 'lotPayPrepaycardLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940133149544450, 1004940130926563330, '派发', 1, 0, 'pay/lotPayPrepaycard', 1, '', 0, 0, 'pay/lotPayPrepaycard', 'toGiveCard', 'toGiveCard', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940135368331265, 1004940130926563330, '修改', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPrepaycard', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940137591312385, 1004940130926563330, '删除', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPrepaycard', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940139814293505, 1004940130926563330, '新增', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPrepaycard', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940142033080321, 1004940130926563330, '回收', 1, 0, 'pay/lotPayPrepaycard', 1, '', 0, 0, 'pay/lotPayPrepaycard', 'giveBackCard', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940144256061442, 1004940110928121857, '支付平台日志', 0, 0, '/log/lotLogPayplatform/queryRecordByPage', 1, '', 0, 0, '', '', 'lotLogPayplatformLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940146474848258, 1004940110928121857, '线下收款账号', 0, 0, '/pay/lotPayOfflineAccount/queryRecordByPage', 1, '', 0, 0, '', '', 'lotPayOfflineAccountLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940148702023682, 1004940146474848258, '删除', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOfflineAccount', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940150966947842, 1004940146474848258, '修改', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOfflineAccount', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940153210900481, 1004940146474848258, '新增', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOfflineAccount', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940155433881601, 1004940056960012290, '人工线上存提', 0, 0, '/pay/lotPayManual/queryRecordByPage?queryManualType=income', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940157661057026, 1004940155433881601, '人工存入', 0, 1, '/pay/lotPayManual/queryRecordByPage?queryManualType=income', 1, '', 0, 0, '', '', 'lotPayManualIncomeLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940159888232450, 1004940157661057026, '人工存入', 1, 0, '', 1, '', 0, 0, 'pay/lotPayManual', 'addManualIncome', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940162115407874, 1004940155433881601, '人工提出', 0, 2, '/pay/lotPayManual/queryRecordByPage?queryManualType=pay', 1, '', 0, 0, '', '', 'lotPayManualPayLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940164338388993, 1004940162115407874, '人工提现', 1, 0, 'pay/lotPayManual', 1, '', 0, 0, 'pay/lotPayManual', 'addManualPay', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940166557175809, 1004940155433881601, '人工入款管理', 0, 3, '/pay/lotPayManual/queryManualPage?type=income', 1, '', 0, 0, '', '', 'queryManualPageIncomeLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940168788545537, 1004940155433881601, '人工出款管理', 0, 4, '/pay/lotPayManual/queryManualPage?type=pay', 1, '', 0, 0, '', '', 'queryManualPagePayLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940171011526657, 1004940056960012290, '出入款记录', 0, 3, '/pay/lotPayOfflineDeposit/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940173238702082, 1004940171011526657, '预付卡记录', 0, 0, '/pay/lotPayPrepaycardDeposit/queryRecordByPage', 1, '', 0, 0, '', '', 'lotPayPrepaycardDepositLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940175465877506, 1004940173238702082, '删除', 1, 0, '', 1, '', 0, 0, 'pay/lotPayPrepaycardDeposit', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940177693052930, 1004940171011526657, '线上入款记录', 0, 0, '/pay/lotPayOnlineDeposit/queryRecordByPage', 1, '', 0, 0, '', '', 'lotPayOnlineDepositLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940179916034049, 1004940177693052930, '修改订单', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOnlineDeposit', 'updateStatus', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940182134820866, 1004940177693052930, '删除', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOnlineDeposit', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940184357801985, 1004940177693052930, '确认订单', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOnlineDeposit', 'modifyOrder', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940186580783105, 1004940177693052930, '恢复订单', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOnlineDeposit', 'recoveryOrder', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940188803764226, 1004940177693052930, '撤销订单', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOnlineDeposit', 'revokeOrder', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940191072882690, 1004940171011526657, '线下入款记录', 0, 3, '/pay/lotPayOfflineDeposit/queryRecordByPage', 1, '', 0, 0, '', '', 'lotPayOfflineDepositLiNav', 1);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940193295863809, 1004940191072882690, '修改订单状态', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOfflineDeposit', 'modifyOrder', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940195518844930, 1004940191072882690, '恢复订单', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOfflineDeposit', 'restoreOrder', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940197741826050, 1004940191072882690, '查看订单', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOfflineDeposit', 'queryRecordByPage', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940199960612866, 1004940191072882690, '撤销订单', 1, 0, '', 1, '', 0, 0, 'pay/lotPayOfflineDeposit', 'revokeOrder', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940202179399681, 1004940171011526657, '出入款总账', 0, 5, '/log/lotLogPayflow/selectCountByPage', 1, '', 0, 0, '', '', 'selectCountByPageLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940204406575106, 1004940202179399681, '出入款总账细单2', 1, 0, '', 1, '', 0, 0, 'pay/lotLogPayflow', 'selectCountDetailed', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940206633750530, 1004940202179399681, '出入款总账细单3', 1, 0, '', 1, '', 0, 0, 'pay/lotLogPayflow', 'selectDetailed', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940208856731649, 1004940056960012290, '存取款模式设定', 0, 21, '/pay/lotPayDepositMode/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940211083907074, 1004940208856731649, '存款模式管理', 0, 211, '/pay/lotPayDepositMode/queryRecordByPage', 1, '', 0, 0, '', '', 'lotPayDepositModeLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940213306888193, 1004940211083907074, '修改', 1, 0, '', 1, '', 0, 0, 'pay/lotPayDepositMode', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940215525675009, 1004940211083907074, '新增', 1, 0, '', 1, '', 0, 0, 'pay/lotPayDepositMode', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940217757044737, 1004940211083907074, '删除', 1, 0, '', 1, '', 0, 0, 'pay/lotPayDepositMode', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940219984220161, 1004940211083907074, '设置默认', 1, 0, '', 1, '', 0, 0, 'pay/lotPayDepositMode', 'setDefault', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940222203006978, 1004940211083907074, '启用/停用', 1, 0, '', 1, '', 0, 0, 'pay/lotPayDepositMode', 'setEnable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940224430182401, 1004940208856731649, '取款模式管理', 0, 212, '/pay/lotPayWithdrawMode/queryRecordByPage', 1, '', 0, 0, '', '', 'lotPayWithdrawModeLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940226648969218, 1004940224430182401, '设置默认', 1, 0, '', 1, '', 0, 0, 'pay/lotPayWithdrawMode', 'setDefault', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940228867756034, 1004940224430182401, '启用/停用', 1, 0, '', 1, '', 0, 0, 'pay/lotPayWithdrawMode', 'setEnable', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940231090737154, 1004940224430182401, '删除', 1, 0, '', 1, '', 0, 0, 'pay/lotPayWithdrawMode', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940233309523969, 1004940224430182401, '新增', 1, 0, '', 1, '', 0, 0, 'pay/lotPayWithdrawMode', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940235540893697, 1004940224430182401, '修改', 1, 0, '', 1, '', 0, 0, 'pay/lotPayWithdrawMode', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940237759680514, 1004940056960012290, '出款管理', 0, 99, '/pay/lotPayWithdrawCheck/queryRecordByPage', 1, '', 0, 0, '', '', 'lotPayWithdrawCheckLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940239978467329, 1004940237759680514, '详情', 1, 0, '', 1, '', 0, 0, 'pay/lotPayWithdrawCheck', 'getWithdrawInfo', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940242201448450, 1004940237759680514, '删除', 1, 0, '', 1, '', 0, 0, 'pay/lotPayWithdrawCheck', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940244424429570, 1004940237759680514, '出款', 1, 0, '', 1, '', 0, 0, 'pay/lotPayWithdrawCheck', 'check', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940246651604994, 1004940237759680514, '拒绝', 1, 0, '', 1, '会员申请提出的金额，不会返还到会员账户', 0, 0, 'pay/lotPayWithdrawCheck', 'refuseWithdrwaOrder', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940248866197506, 1004940237759680514, '取消', 1, 0, '', 1, '会员申请提出的金额，会相应的返还到会员账户', 0, 0, 'pay/lotPayWithdrawCheck', 'cancelWithdrwaOrder', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940251093372929, 1004940056960012290, '即时稽核', 0, 100, '/pay/lotPayWithdrawCheck/queryImmdiate/queryRecordByPage', 1, '', 0, 0, '', '', 'queryImmdiateLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940253312159746, 1004940056960012290, '站点支付设置', 0, 101, '/pay/lotPayPlatform/sitePayPlateform', 1, '', 0, 0, '', '', 'lotSitePayPlatformLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940255535140865, 0, '任务管理', 0, 6, '', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940257753927682, 1004940255535140865, '任务列表', 0, 0, '/quartz/lotQuartz/queryRecordByPage', 1, '', 0, 0, '', '', 'lotQuartzLiNav', 1);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940259985297409, 1004940255535140865, '缓存管理', 0, 0, '/cache/lotCache/queryRecordByPage', 1, '', 0, 0, '', '', 'lotCacheLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940262208278530, 1004940255535140865, '锁管理', 0, 0, '/lock/lotLock/queryRecordByPage', 1, '', 0, 0, '', '', 'lotLockLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940264427065346, 1004940262208278530, '删除', 1, 0, '', 1, '', 0, 0, 'lock/lotLock', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940266650046465, 0, '日志管理', 0, 7, '/log/lotLogPayflow/queryRecordByPage', 1, '', 0, 0, '', '', 'glyphicon glyphicon-globe', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940268864638978, 1004940266650046465, '操作日志', 0, 0, '/log/lotLogUser/queryOperRecordByPage', 1, '', 0, 0, '', '', 'lotOperLogUserLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940271083425793, 1004940268864638978, '批量删除', 1, 0, '', 1, '', 0, 0, 'log/lotLogUser', 'deletesOper', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940273310601218, 1004940268864638978, '删除登录/操作日志', 1, 0, '', 1, '', 0, 0, 'log/lotLogUser', 'delete', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940275529388034, 1004940268864638978, '删除', 1, 0, '', 1, '', 0, 0, 'log/lotLogUser', 'deleteLog', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940277752369153, 1004940266650046465, '登录日志', 0, 0, '/log/lotLogUser/queryRecordByPage', 1, '', 0, 0, '', '', 'lotLogUserLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940279979544578, 1004940277752369153, '批量删除', 1, 0, '', 1, '', 0, 0, 'log/lotLogUser', 'deletesLogin', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940284425506818, 1004940266650046465, '资金流向', 0, 2, '/log/lotLogPayflow/queryRecordByPage', 1, '', 0, 0, '', '', 'lotLogPayflowLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940286648487937, 1004940284425506818, '批量删除', 1, 0, '', 1, '', 0, 0, 'log/lotLogPayflow', 'deletes', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940288867274753, 0, '报表查询', 0, 8, '/report/pay/deviceReport', 1, '', 0, 0, '', '', 'glyphicon glyphicon-search', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940291090255873, 1004940288867274753, '会员报表', 0, 0, '/log/lotLogPayflow/selectUserEarningsReport', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940293313236994, 1004940291090255873, '会员报表', 0, 0, '/log/lotLogPayflow/selectUserEarningsReport', 1, '', 0, 0, '', '', 'UserEarningsReportLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940295540412417, 1004940293313236994, '会员报表', 1, 0, '', 1, '', 0, 0, 'pay/lotLogPayflow', 'selectUserEarningsHistoryReport', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940299994763266, 1004940288867274753, '盈亏报表', 0, 2, '/log/lotLogPayflow/selectProfitAndLossReport', 1, '', 0, 0, '', '', 'selectProfitAndLossReportLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940302217744385, 1004940288867274753, '首充报表', 0, 3, '/report/pay/firstPayReport', 1, '', 0, 0, '', '', 'firstPayReportLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940304436531202, 1004940288867274753, '终端报表', 0, 4, '/report/pay/deviceReport', 1, '', 0, 0, '', '', 'deviceReportLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940306659512322, 1004940288867274753, '游戏报表', 0, 5, '/game/report/queryRecordByPage', 1, '哈哈哈', 0, 0, '', '', 'gameReportLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940308882493442, 0, '风控管理', 0, 9, '/site/lotSiteIpLimit/queryRecordByPage', 1, '', 0, 0, '', '', 'glyphicon glyphicon-remove-circle', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940311101280258, 1004940308882493442, '访问限制', 0, 0, '/site/lotSiteIpLimit/queryRecordByPage', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940313320067074, 1004940311101280258, '前台限制地区', 0, 0, '/site/lotSiteCountryLimit/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteCountryLimitLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940315543048193, 1004940311101280258, '前台限制IP', 0, 1, '/site/lotSiteIpLimit/queryRecordByPage', 1, '', 0, 0, '', '', 'lotSiteIpLimitLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1004940315543048194, 1004940315543048193, '新增', 1, 0, '', 1, '', 0, 0, 'site/lotSiteIpLimit', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1009758820461793281, 1004940311101280258, '前台允许ip', 0, 2, '/site/lotSiteIpLimit/queryHomeWhiteByPage', 1, '', 0, 0, '', '', 'lotSiteIpLimitLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1009758924342120450, 1004940311101280258, '后台允许ip', 0, 3, '/site/lotSiteIpLimit/queryAdminWhiteByPage', 1, '', 0, 0, '', '', 'lotSiteIpLimitLiNav', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1012955341000380418, 1004940308882493442, '全域设置', 0, 0, '/site/lotSiteSetting/queryRecordByPage', 1, '', 0, 0, '', '', '/site/lotSiteSetting/queryRecordByPage', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1012955460659679233, 1012955341000380418, '编辑', 1, 0, '', 1, '', 0, 0, 'site/lotSiteSetting', 'update', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1014067563164438529, 1014067495711641601, '测试啊啊', 0, 0, '', 1, '', 0, 0, '', '', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1017316267712868353, 1004939952312127490, '站点URL模板配置', 1, 0, '', 1, '', 0, 0, 'site/addSiteUrlTemplate', 'add', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1018042800038055938, 1004939952312127490, '站点配置', 1, 0, '/site/lotSite', 1, '', 0, 0, 'site/lotSite', 'initData', 'initData', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1022031107291885569, 1004940277752369153, '分页查询', 1, 0, '', 1, '', 0, 0, 'log/lotLogUser', 'findLogin', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1022031569739067394, 1004940268864638978, '分页查询', 1, 0, '', 1, '', 0, 0, 'log/lotLogUser', 'queryOperation', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1022031900409606145, 1004940284425506818, '分页查询', 1, 0, '', 1, '', 0, 0, 'log/lotLogPayflow', 'query', '', 0);\n" +
            "INSERT INTO db_platform.resource VALUES (1023139930907373569, 1004940171011526657, '出入款流水', 0, 4, '/log/lotLogPayflow/selectUserCountByPage', 1, '', 0, 0, '', '', 'selectUserCountByPageLiNav', 0);\n";

    public static final String resourceIds =
            "1004939518793060353,\n" +
                    "1004939521204785153,\n" +
                    "1004939523431960578,\n" +
                    "1004939525654941697,\n" +
                    "1004939527873728513,\n" +
                    "1004939530096709633,\n" +
                    "1004939532319690753,\n" +
                    "1004939534542671873,\n" +
                    "1004939536765652994,\n" +
                    "1004939538988634113,\n" +
                    "1004939541224198145,\n" +
                    "1004939543451373569,\n" +
                    "1004939545665966082,\n" +
                    "1004939547888947202,\n" +
                    "1004939550120316929,\n" +
                    "1004939552334909441,\n" +
                    "1004939554557890561,\n" +
                    "1004939556785065986,\n" +
                    "1004939559003852802,\n" +
                    "1004939561226833922,\n" +
                    "1004939563454009345,\n" +
                    "1004939565681184769,\n" +
                    "1004939567908360193,\n" +
                    "1004939570122952705,\n" +
                    "1004939572341739521,\n" +
                    "1004939574564720642,\n" +
                    "1004939576791896065,\n" +
                    "1004939579014877185,\n" +
                    "1004939581233664001,\n" +
                    "1004939583456645122,\n" +
                    "1004939585675431937,\n" +
                    "1004939587898413057,\n" +
                    "1004939590125588481,\n" +
                    "1004939592352763905,\n" +
                    "1004939594571550722,\n" +
                    "1004939596790337537,\n" +
                    "1004939599017512961,\n" +
                    "1004939601248882690,\n" +
                    "1004939603476058113,\n" +
                    "1004939605703233538,\n" +
                    "1004939607930408962,\n" +
                    "1004939610153390081,\n" +
                    "1004939612380565506,\n" +
                    "1004939614607740929,\n" +
                    "1004939616834916354,\n" +
                    "1004939619053703170,\n" +
                    "1004939621276684290,\n" +
                    "1004939623499665409,\n" +
                    "1004939625726840834,\n" +
                    "1004939627949821954,\n" +
                    "1004939630172803074,\n" +
                    "1004939632395784194,\n" +
                    "1004939634622959617,\n" +
                    "1004939636850135042,\n" +
                    "1004939639068921858,\n" +
                    "1004939641291902978,\n" +
                    "1004939643544244226,\n" +
                    "1004939645771419650,\n" +
                    "1004939647994400769,\n" +
                    "1004939650263519233,\n" +
                    "1004939652490694658,\n" +
                    "1004939654726258690,\n" +
                    "1004939656945045505,\n" +
                    "1004939659168026626,\n" +
                    "1004939661395202050,\n" +
                    "1004939663618183169,\n" +
                    "1004939665857941506,\n" +
                    "1004939668080922625,\n" +
                    "1004939670308098049,\n" +
                    "1004939672577216513,\n" +
                    "1004939674812780546,\n" +
                    "1004939677119647745,\n" +
                    "1004939679351017473,\n" +
                    "1004939681578192898,\n" +
                    "1004939683817951234,\n" +
                    "1004939686040932353,\n" +
                    "1004939688268107777,\n" +
                    "1004939690541420546,\n" +
                    "1004939692776984578,\n" +
                    "1004939695046103042,\n" +
                    "1004939701824098305,\n" +
                    "1004939704109993986,\n" +
                    "1004939706341363713,\n" +
                    "1004939708564344833,\n" +
                    "1004939710787325953,\n" +
                    "1004939713001918466,\n" +
                    "1004939715220705282,\n" +
                    "1004939717443686402,\n" +
                    "1004939719658278913,\n" +
                    "1004939721889648642,\n" +
                    "1004939724112629761,\n" +
                    "1004939726335610882,\n" +
                    "1004939728566980610,\n" +
                    "1004939730789961730,\n" +
                    "1004939733012942849,\n" +
                    "1004939735244312578,\n" +
                    "1004939737505042434,\n" +
                    "1004939739778355202,\n" +
                    "1004939742034890753,\n" +
                    "1004939744253677569,\n" +
                    "1004939747521040385,\n" +
                    "1004939749744021505,\n" +
                    "1004939751962808321,\n" +
                    "1004939754185789442,\n" +
                    "1004939756412964865,\n" +
                    "1004939758640140290,\n" +
                    "1004939760858927106,\n" +
                    "1004939763081908226,\n" +
                    "1004939765313277954,\n" +
                    "1004939767532064769,\n" +
                    "1004939769750851585,\n" +
                    "1004939771973832705,\n" +
                    "1004939774217785345,\n" +
                    "1004939776444960769,\n" +
                    "1004939778667941889,\n" +
                    "1004939780895117313,\n" +
                    "1004939783118098433,\n" +
                    "1004939785336885250,\n" +
                    "1004939787551477761,\n" +
                    "1004939789778653186,\n" +
                    "1004939791997440001,\n" +
                    "1004939794220421121,\n" +
                    "1004939796447596545,\n" +
                    "1004939798666383361,\n" +
                    "1004939800893558785,\n" +
                    "1004939803116539905,\n" +
                    "1004939805339521025,\n" +
                    "1004939807562502145,\n" +
                    "1004939809789677570,\n" +
                    "1004939812008464386,\n" +
                    "1004939814235639809,\n" +
                    "1004939816462815234,\n" +
                    "1004939818689990657,\n" +
                    "1004939820912971777,\n" +
                    "1004939823140147201,\n" +
                    "1004939825363128322,\n" +
                    "1004939827586109442,\n" +
                    "1004939829804896258,\n" +
                    "1004939832141123585,\n" +
                    "1004939834376687617,\n" +
                    "1004939836599668738,\n" +
                    "1004939838818455553,\n" +
                    "1004939841041436673,\n" +
                    "1004939843264417794,\n" +
                    "1004939845479010306,\n" +
                    "1004939847727157250,\n" +
                    "1004939849954332674,\n" +
                    "1004939852177313794,\n" +
                    "1004939854404489217,\n" +
                    "1004939856627470337,\n" +
                    "1004939858846257154,\n" +
                    "1004939861065043970,\n" +
                    "1004939863292219394,\n" +
                    "1004939865519394817,\n" +
                    "1004939867746570241,\n" +
                    "1004939869965357057,\n" +
                    "1004939872188338178,\n" +
                    "1004939874402930689,\n" +
                    "1004939876630106113,\n" +
                    "1004939881080262658,\n" +
                    "1004939883303243778,\n" +
                    "1004939885522030594,\n" +
                    "1004939889967992833,\n" +
                    "1004939892199362561,\n" +
                    "1004939894422343681,\n" +
                    "1004939896645324801,\n" +
                    "1004939898872500226,\n" +
                    "1004939901091287042,\n" +
                    "1004939905554026498,\n" +
                    "1004939907777007618,\n" +
                    "1004939909999988738,\n" +
                    "1004939912222969858,\n" +
                    "1004939914445950978,\n" +
                    "1004939916668932097,\n" +
                    "1004939918891913218,\n" +
                    "1004939921114894337,\n" +
                    "1004939923329486849,\n" +
                    "1004939925556662274,\n" +
                    "1004939927775449090,\n" +
                    "1004939929994235906,\n" +
                    "1004939932217217025,\n" +
                    "1004939934448586753,\n" +
                    "1004939936679956481,\n" +
                    "1004939938898743298,\n" +
                    "1004939943374065665,\n" +
                    "1004939945638989826,\n" +
                    "1004939947861970945,\n" +
                    "1004939950084952065,\n" +
                    "1004939952312127490,\n" +
                    "1004939954539302914,\n" +
                    "1004939956770672641,\n" +
                    "1004939958993653761,\n" +
                    "1004939961220829186,\n" +
                    "1004939963448004609,\n" +
                    "1004939965675180033,\n" +
                    "1004939967898161154,\n" +
                    "1004939970116947970,\n" +
                    "1004939972348317698,\n" +
                    "1004939974571298818,\n" +
                    "1004939976798474242,\n" +
                    "1004939979021455361,\n" +
                    "1004939981236047873,\n" +
                    "1004939983459028993,\n" +
                    "1004939985686204417,\n" +
                    "1004939987913379842,\n" +
                    "1004939990136360962,\n" +
                    "1004939992430645249,\n" +
                    "1004939994657820674,\n" +
                    "1004939996880801794,\n" +
                    "1004939999099588609,\n" +
                    "1004940001347735553,\n" +
                    "1004940003570716673,\n" +
                    "1004940005793697794,\n" +
                    "1004940008016678913,\n" +
                    "1004940010248048642,\n" +
                    "1004940021358759938,\n" +
                    "1004940023581741058,\n" +
                    "1004940025813110786,\n" +
                    "1004940028040286209,\n" +
                    "1004940030275850241,\n" +
                    "1004940034730201089,\n" +
                    "1004940036944793601,\n" +
                    "1004940039159386113,\n" +
                    "1004940041386561538,\n" +
                    "1004940048055504898,\n" +
                    "1004940050291068930,\n" +
                    "1004940052514050050,\n" +
                    "1004940054737031170,\n" +
                    "1004940056960012290,\n" +
                    "1004940059191382017,\n" +
                    "1004940061418557441,\n" +
                    "1004940063641538562,\n" +
                    "1004940065868713986,\n" +
                    "1004940068142026754,\n" +
                    "1004940070440505346,\n" +
                    "1004940072957087746,\n" +
                    "1004940075364618241,\n" +
                    "1004940077587599362,\n" +
                    "1004940079810580481,\n" +
                    "1004940082037755905,\n" +
                    "1004940084260737026,\n" +
                    "1004940086487912449,\n" +
                    "1004940088706699266,\n" +
                    "1004940090933874690,\n" +
                    "1004940093156855809,\n" +
                    "1004940095375642626,\n" +
                    "1004940097594429441,\n" +
                    "1004940099817410562,\n" +
                    "1004940102036197378,\n" +
                    "1004940104263372802,\n" +
                    "1004940106486353922,\n" +
                    "1004940108709335041,\n" +
                    "1004940110928121857,\n" +
                    "1004940113155297281,\n" +
                    "1004940115374084098,\n" +
                    "1004940117601259522,\n" +
                    "1004940119824240642,\n" +
                    "1004940122043027457,\n" +
                    "1004940124266008577,\n" +
                    "1004940126484795394,\n" +
                    "1004940128707776513,\n" +
                    "1004940130926563330,\n" +
                    "1004940133149544450,\n" +
                    "1004940135368331265,\n" +
                    "1004940137591312385,\n" +
                    "1004940139814293505,\n" +
                    "1004940142033080321,\n" +
                    "1004940144256061442,\n" +
                    "1004940146474848258,\n" +
                    "1004940148702023682,\n" +
                    "1004940150966947842,\n" +
                    "1004940153210900481,\n" +
                    "1004940155433881601,\n" +
                    "1004940157661057026,\n" +
                    "1004940159888232450,\n" +
                    "1004940162115407874,\n" +
                    "1004940164338388993,\n" +
                    "1004940166557175809,\n" +
                    "1004940168788545537,\n" +
                    "1004940171011526657,\n" +
                    "1004940173238702082,\n" +
                    "1004940175465877506,\n" +
                    "1004940177693052930,\n" +
                    "1004940179916034049,\n" +
                    "1004940182134820866,\n" +
                    "1004940184357801985,\n" +
                    "1004940186580783105,\n" +
                    "1004940188803764226,\n" +
                    "1004940191072882690,\n" +
                    "1004940193295863809,\n" +
                    "1004940195518844930,\n" +
                    "1004940197741826050,\n" +
                    "1004940199960612866,\n" +
                    "1004940202179399681,\n" +
                    "1004940204406575106,\n" +
                    "1004940206633750530,\n" +
                    "1004940208856731649,\n" +
                    "1004940211083907074,\n" +
                    "1004940213306888193,\n" +
                    "1004940215525675009,\n" +
                    "1004940217757044737,\n" +
                    "1004940219984220161,\n" +
                    "1004940222203006978,\n" +
                    "1004940224430182401,\n" +
                    "1004940226648969218,\n" +
                    "1004940228867756034,\n" +
                    "1004940231090737154,\n" +
                    "1004940233309523969,\n" +
                    "1004940235540893697,\n" +
                    "1004940237759680514,\n" +
                    "1004940239978467329,\n" +
                    "1004940242201448450,\n" +
                    "1004940244424429570,\n" +
                    "1004940246651604994,\n" +
                    "1004940248866197506,\n" +
                    "1004940251093372929,\n" +
                    "1004940253312159746,\n" +
                    "1004940255535140865,\n" +
                    "1004940257753927682,\n" +
                    "1004940259985297409,\n" +
                    "1004940262208278530,\n" +
                    "1004940264427065346,\n" +
                    "1004940266650046465,\n" +
                    "1004940268864638978,\n" +
                    "1004940271083425793,\n" +
                    "1004940273310601218,\n" +
                    "1004940275529388034,\n" +
                    "1004940277752369153,\n" +
                    "1004940279979544578,\n" +
                    "1004940284425506818,\n" +
                    "1004940286648487937,\n" +
                    "1004940288867274753,\n" +
                    "1004940291090255873,\n" +
                    "1004940293313236994,\n" +
                    "1004940295540412417,\n" +
                    "1004940299994763266,\n" +
                    "1004940302217744385,\n" +
                    "1004940304436531202,\n" +
                    "1004940306659512322,\n" +
                    "1004940308882493442,\n" +
                    "1004940311101280258,\n" +
                    "1004940313320067074,\n" +
                    "1004940315543048193,\n" +
                    "1004940315543048194,\n" +
                    "1009758820461793281,\n" +
                    "1009758924342120450,\n" +
                    "1012955341000380418,\n" +
                    "1012955460659679233,\n" +
                    "1014067563164438529,\n" +
                    "1017316267712868353,\n" +
                    "1018042800038055938,\n" +
                    "1022031107291885569,\n" +
                    "1022031569739067394,\n" +
                    "1022031900409606145,\n" +
                    "1023139930907373569";
    public static final String defaultResourceIds =
            "1004939592352763905,\n" +
            "1004939594571550722,\n" +
            "1004939596790337537,\n" +
            "1004939599017512961,\n" +
            "1004939601248882690,\n" +
            "1004939603476058113,\n" +
            "1004939612380565506,\n" +
            "1004939614607740929,\n" +
            "1004939616834916354,\n" +
            "1004939619053703170,\n" +
            "1004939621276684290,\n" +
            "1004939623499665409,\n" +
            "1004939625726840834,\n" +
            "1004939627949821954,\n" +
            "1004939630172803074,\n" +
            "1004939632395784194,\n" +
            "1004939634622959617,\n" +
            "1004939636850135042,\n" +
            "1004939639068921858,\n" +
            "1004939641291902978,\n" +
            "1004939643544244226,\n" +
            "1004939647994400769,\n" +
            "1004939650263519233,\n" +
            "1004939652490694658,\n" +
            "1004939654726258690,\n" +
            "1004939656945045505,\n" +
            "1004939659168026626,\n" +
            "1004939661395202050,\n" +
            "1004939663618183169,\n" +
            "1004939665857941506,\n" +
            "1004939668080922625,\n" +
            "1004939670308098049,\n" +
            "1004939672577216513,\n" +
            "1004939674812780546,\n" +
            "1004939677119647745,\n" +
            "1004939679351017473,\n" +
            "1004939681578192898,\n" +
            "1004939683817951234,\n" +
            "1004939686040932353,\n" +
            "1004939688268107777,\n" +
            "1004939690541420546,\n" +
            "1004939692776984578,\n" +
            "1004939695046103042,\n" +
            "1004939706341363713,\n" +
            "1004939708564344833,\n" +
            "1004939710787325953,\n" +
            "1004939713001918466,\n" +
            "1004939715220705282,\n" +
            "1004939717443686402,\n" +
            "1004939719658278913,\n" +
            "1004939721889648642,\n" +
            "1004939724112629761,\n" +
            "1004939726335610882,\n" +
            "1004939728566980610,\n" +
            "1004939730789961730,\n" +
            "1004939733012942849,\n" +
            "1004939735244312578,\n" +
            "1004939737505042434,\n" +
            "1004939739778355202,\n" +
            "1004939742034890753,\n" +
            "1004939744253677569,\n" +
            "1004939747521040385,\n" +
            "1004939749744021505,\n" +
            "1004939751962808321,\n" +
            "1004939754185789442,\n" +
            "1004939756412964865,\n" +
            "1004939758640140290,\n" +
            "1004939760858927106,\n" +
            "1004939763081908226,\n" +
            "1004939765313277954,\n" +
            "1004939767532064769,\n" +
            "1004939769750851585,\n" +
            "1004939771973832705,\n" +
            "1004939774217785345,\n" +
            "1004939776444960769,\n" +
            "1004939778667941889,\n" +
            "1004939780895117313,\n" +
            "1004939783118098433,\n" +
            "1004939785336885250,\n" +
            "1004939787551477761,\n" +
            "1004939789778653186,\n" +
            "1004939791997440001,\n" +
            "1004939794220421121,\n" +
            "1004939796447596545,\n" +
            "1004939798666383361,\n" +
            "1004939800893558785,\n" +
            "1004939803116539905,\n" +
            "1004939812008464386,\n" +
            "1004939814235639809,\n" +
            "1004939816462815234,\n" +
            "1004939818689990657,\n" +
            "1004939820912971777,\n" +
            "1004939823140147201,\n" +
            "1004939825363128322,\n" +
            "1004939827586109442,\n" +
            "1004939829804896258,\n" +
            "1004939832141123585,\n" +
            "1004939834376687617,\n" +
            "1004939836599668738,\n" +
            "1004939838818455553,\n" +
            "1004939841041436673,\n" +
            "1004939843264417794,\n" +
            "1004939845479010306,\n" +
            "1004939847727157250,\n" +
            "1004939849954332674,\n" +
            "1004939874402930689,\n" +
            "1004939876630106113,\n" +
            "1004939898872500226,\n" +
            "1004939901091287042,\n" +
            "1004939923329486849,\n" +
            "1004939925556662274,\n" +
            "1004939927775449090,\n" +
            "1004939929994235906,\n" +
            "1004939932217217025,\n" +
            "1004939934448586753,\n" +
            "1004939936679956481,\n" +
            "1004939938898743298,\n" +
            "1004939943374065665,\n" +
            "1004939945638989826,\n" +
            "1004939947861970945,\n" +
            "1004939950084952065,\n" +
            "1004939967898161154,\n" +
            "1004939970116947970,\n" +
            "1004939972348317698,\n" +
            "1004939974571298818,\n" +
            "1004939976798474242,\n" +
            "1004939979021455361,\n" +
            "1004939981236047873,\n" +
            "1004939983459028993,\n" +
            "1004939985686204417,\n" +
            "1004939987913379842,\n" +
            "1004939990136360962,\n" +
            "1004939992430645249,\n" +
            "1004939994657820674,\n" +
            "1004939996880801794,\n" +
            "1004939999099588609,\n" +
            "1004940001347735553,\n" +
            "1004940003570716673,\n" +
            "1004940005793697794,\n" +
            "1004940008016678913,\n" +
            "1004940010248048642,\n" +
            "1004940056960012290,\n" +
            "1004940079810580481,\n" +
            "1004940082037755905,\n" +
            "1004940084260737026,\n" +
            "1004940086487912449,\n" +
            "1004940088706699266,\n" +
            "1004940090933874690,\n" +
            "1004940093156855809,\n" +
            "1004940095375642626,\n" +
            "1004940097594429441,\n" +
            "1004940099817410562,\n" +
            "1004940102036197378,\n" +
            "1004940104263372802,\n" +
            "1004940106486353922,\n" +
            "1004940108709335041,\n" +
            "1004940110928121857,\n" +
            "1004940113155297281,\n" +
            "1004940115374084098,\n" +
            "1004940117601259522,\n" +
            "1004940119824240642,\n" +
            "1004940122043027457,\n" +
            "1004940124266008577,\n" +
            "1004940126484795394,\n" +
            "1004940128707776513,\n" +
            "1004940130926563330,\n" +
            "1004940133149544450,\n" +
            "1004940135368331265,\n" +
            "1004940137591312385,\n" +
            "1004940139814293505,\n" +
            "1004940142033080321,\n" +
            "1004940146474848258,\n" +
            "1004940148702023682,\n" +
            "1004940150966947842,\n" +
            "1004940153210900481,\n" +
            "1004940155433881601,\n" +
            "1004940157661057026,\n" +
            "1004940159888232450,\n" +
            "1004940162115407874,\n" +
            "1004940164338388993,\n" +
            "1004940166557175809,\n" +
            "1004940168788545537,\n" +
            "1004940171011526657,\n" +
            "1004940173238702082,\n" +
            "1004940175465877506,\n" +
            "1004940177693052930,\n" +
            "1004940179916034049,\n" +
            "1004940182134820866,\n" +
            "1004940184357801985,\n" +
            "1004940186580783105,\n" +
            "1004940188803764226,\n" +
            "1004940191072882690,\n" +
            "1004940193295863809,\n" +
            "1004940195518844930,\n" +
            "1004940197741826050,\n" +
            "1004940199960612866,\n" +
            "1004940202179399681,\n" +
            "1004940204406575106,\n" +
            "1004940206633750530,\n" +
            "1023139930907373569,\n" +
            "1004940208856731649,\n" +
            "1004940211083907074,\n" +
            "1004940213306888193,\n" +
            "1004940215525675009,\n" +
            "1004940217757044737,\n" +
            "1004940219984220161,\n" +
            "1004940222203006978,\n" +
            "1004940224430182401,\n" +
            "1004940226648969218,\n" +
            "1004940228867756034,\n" +
            "1004940231090737154,\n" +
            "1004940233309523969,\n" +
            "1004940235540893697,\n" +
            "1004940237759680514,\n" +
            "1004940239978467329,\n" +
            "1004940242201448450,\n" +
            "1004940244424429570,\n" +
            "1004940246651604994,\n" +
            "1004940248866197506,\n" +
            "1004940251093372929,\n" +
            "1004940266650046465,\n" +
            "1004940268864638978,\n" +
            "1022031569739067394,\n" +
            "1004940277752369153,\n" +
            "1022031107291885569,\n" +
            "1004940284425506818,\n" +
            "1022031900409606145,\n" +
            "1004940288867274753,\n" +
            "1004940291090255873,\n" +
            "1004940293313236994,\n" +
            "1004940295540412417,\n" +
            "1004940299994763266,\n" +
            "1004940302217744385,\n" +
            "1004940304436531202,\n" +
            "1004940306659512322,\n" +
            "1004940308882493442,\n" +
            "1004940311101280258,\n" +
            "1004940313320067074,\n" +
            "1004940315543048193,\n" +
            "1004940315543048194,\n" +
            "1009758820461793281,\n" +
            "1009758924342120450,\n" +
            "1012955341000380418,\n" +
            "1012955460659679233";
}
