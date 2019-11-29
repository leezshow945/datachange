package com.user;

import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by even on 2019/7/23.
 */
public class LogUserChange {

    static Logger logger = Logger.getLogger(LogUserChange.class);

     static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void relationSql(ResultSet resultSet, MongoDatabase collection) throws SQLException, IOException {

        while (resultSet.next()) {
            try {
                Document document = new Document();
                document.append("_id", resultSet.getLong("id"));
                document.append("plat", resultSet.getInt("plat"));
                if (resultSet.getString("user_name") != null)
                    document.append("userName", resultSet.getString("user_name"));
                if (resultSet.getString("content") != null)
                    document.append("content", resultSet.getString("content"));
                document.append("userId", resultSet.getLong("user_id"));
                if (resultSet.getString("login_ip") != null)
                    document.append("loginIp", resultSet.getString("login_ip"));
                if(resultSet.getString("login_time") != null){
                    document.append("loginTime", format.parse(resultSet.getString("login_time")));
                }
                if (resultSet.getString("login_area") != null)
                    document.append("loginArea", resultSet.getString("login_area"));
                document.append("isDiffAreaLogin", resultSet.getInt("is_diff_area_login"));
                if (resultSet.getString("type") != null)
                    document.append("type", resultSet.getString("type"));
                if (resultSet.getString("account_type") != null)
                    document.append("accountType", resultSet.getString("account_type"));
                document.append("siteId", resultSet.getLong("site_id"));
                document.append("isDel", resultSet.getInt("is_del"));
                if (resultSet.getString("flag_type") != null)
                    document.append("flagType", resultSet.getString("flag_type"));
                if (resultSet.getString("login_url") != null)
                    document.append("loginUrl", resultSet.getString("login_url"));
                if(resultSet.getString("create_time") != null){
                    document.append("createTime", format.parse(resultSet.getString("create_time")));
                }
                if(resultSet.getString("update_time") != null){
                    document.append("updateTime", format.parse(resultSet.getString("update_time")));
                }
                if (resultSet.getString("site_code") != null)
                    document.append("siteCode", resultSet.getString("site_code"));

                if ("FLAG_TYPE_LOGIN".equals(resultSet.getString("flag_type"))) {
                    collection.getCollection("LoginLogEntity-" + resultSet.getString("site_id")).insertOne(document);
                } else {
                    collection.getCollection("OprLogEntity-" + resultSet.getString("site_id")).insertOne(document);
                }
            } catch (Exception e) {
                logger.error(e);
                continue;
            }
        }
    }
}
