`准备工作：初始化数据与参数`
1. 新增数据库 db_relation,db_user,db_platform 
2. 分别向对应的数据库执行初始化脚本 db_relation.sql,db_user.sql,db_platform.sql 
3. 修改demo-1.0.jar\BOOT-INF\classes\config.properties 数据库连接信息

`具体操作：生成用户平台关联ID数据`
1. 根目录执行CMD命令:“java -jar demo-1.0.jar”,生成execute_01.sql 向db_relation库中执行
2. 修改demo-1.0.jar\META-INF\MANIFEST.MF,将Start-Class的value com.Execute01 改为 com.Execute02
3. 根目录执行CMD命令:“java -jar demo-1.0.jar”,生成execute_02.sql 向db_relation库中执行

`关联数据生成完毕，可执行所有厦门服务脚本`

`迁移所有数据`
1. 修改demo-1.0.jar\META-INF\MANIFEST.MF,将Start-Class的value com.Execute02 改为 com.Execute03
2. 根目录执行CMD命令:“java -jar demo-1.0.jar”,生成execute_03.sql 向db_relation库中执行

--------------------执行完毕--------------------


代理关系迁移
1.新增表user_proxy
2.修改数据库连接信息为目标环境
3.执行UserProxyExecution生成脚本execute_user_proxy.sql导入user_proxy中
迁移完毕

代理关系表拆分同步
1、修改数据库连接信息为目标环境
2、执行ProxyTableSplitExecution生成站点脚本 user_proxy_init.sql 导入至目标数据库生成各站点代理关系表
各站点代理表生成完毕
