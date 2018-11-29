---
  title: Hive集群环境配置
  date: 2018-08-16 17:20:00
  categories: 大数据
  tags: [Hive,集群]
  description: Hive集群环境配置
---



# 1.解压Hive，配置环境变量

> export HIVE_HOME=/usr/soft/hive2.1
> export PATH=.:$PATH:${HIVE_HOME}/bin:

# 2.配置Hive

> cd $HIVE_HOME/conf
> cp hive-default.xml.template hive-site.xml

修改hive-site.xml内容如下：

``` xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
    <property>
        <name>javax.jdo.option.ConnectionURL</name>
        <value>jdbc:mysql://192.168.2.102:3306/hive?createDatabaseIfNotExsit=true; characterEncoding=UTFq8</va
lue>    </property>
    <property>
        <name>javax.jdo.option.ConnectionDriverName</name>
        <value>com.mysql.jdbc.Driver</value>
    </property>
    <property>
        <name>javax.jdo.option.ConnectionUserName</name>
        <value>root</value>
    </property>
    <property>
        <name>javax.jdo.option.ConnectionPassword</name>
        <value>123456</value>
    </property>
    <property>
        <name>datanucleus.readOnlyDatastore</name>
        <value>false</value>
    </property>
    <property>
        <name>datanucleus.fixedDatastore</name>
        <value>false</value>
    </property>
    <property>
        <name>datanucleus.autoCreateSchema</name>
        <value>true</value>
    </property>
    <property>
        <name>datanucleus.autoCreateTables</name>
        <value>true</value>
    </property>
    <property>
        <name>datanucleus.autoCreateColumns</name>
        <value>true</value>
    </property>
</configuration>
```

# 把MySql的驱动jar包复制到lib目录下面

# 启动服务
./bin/hive --service metastore &

nohup hive --service metastore &
nohup hive --service metastore > metastore.log 2>&1 &

查看服务
ps -aux|grep metastore
ps -ef |grep metastore




# 启动Hive
./bin/hive

# 测试
``` sql
CREATE TABLE tbDate(dateID string,theyearmonth string,theyear string,themonth string,thedate string,theweek string,theweeks string,thequot string,thetenday string,thehalfmonth string) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' ;


CREATE TABLE tbStock(ordernumber STRING,locationid string,dateID string) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' ;


CREATE TABLE tbStockDetail(ordernumber STRING,rownum int,itemid string,qty int,price int ,amount int) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' ;


CREATE TABLE SOGOUQ2(DT STRING,WEBSESSION STRING,WORD STRING,S_SEQ INT,C_SEQ INT,WEBSITE STRING) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' ;


CREATE EXTERNAL TABLE SOGOUQ1(DT STRING,WEBSESSION STRING,WORD STRING,S_SEQ INT,C_SEQ INT,WEBSITE STRING) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LINES TERMINATED
 BY '\n' STORED AS TEXTFILE LOCATION '/class5/sogouq1';
```

# 常见问题

## 无法方法MySql服务
``` sql
grant all privileges on *.* to 'root'@'%' identified by '123456';
flush privileges;
```

如下正确：
mysql>grant all privileges on *.* to '用户名'@'ip地址' identified by '密码';
all privileges 所有权限 也可以写成select ,update等
*.* 所有库的所有表 如databasename.*
用户名 随便起
IP  数据库所在的IP
identified by ‘密码’ 表示通过密码连接

执行完上述命令后用下面的命令刷新一下权限
mysql>flush privileges;



## 异常问题1：
```
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.apache.logging.slf4j.Log4jLoggerFactory]
javax.jdo.JDODataStoreException: Required table missing : "`VERSION`" in Catalog "" Schema "". DataNucleus requires this table to perform its persistence operations. Either your MetaData is incorrect,
or you need to enable "datanucleus.schema.autoCreateTables"	at org.datanucleus.api.jdo.NucleusJDOHelper.getJDOExceptionForNucleusException(NucleusJDOHelper.java:553)
	at org.datanucleus.api.jdo.JDOPersistenceManager.jdoMakePersistent(JDOPersistenceManager.java:720)
	at org.datanucleus.api.jdo.JDOPersistenceManager.makePersistent(JDOPersistenceManager.java:740)
	at org.apache.hadoop.hive.metastore.ObjectStore.setMetaStoreSchemaVersion(ObjectStore.java:7763)
	at org.apache.hadoop.hive.metastore.ObjectStore.checkSchema(ObjectStore.java:7657)
	at org.apache.hadoop.hive.metastore.ObjectStore.verifySchema(ObjectStore.java:7632)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.apache.hadoop.hive.metastore.RawStoreProxy.invoke(RawStoreProxy.java:101)
	at com.sun.proxy.$Proxy18.verifySchema(Unknown Source)
	at org.apache.hadoop.hive.metastore.HiveMetaStore$HMSHandler.getMS(HiveMetaStore.java:547)
	at org.apache.hadoop.hive.metastore.HiveMetaStore$HMSHandler.createDefaultDB(HiveMetaStore.java:612)
	at org.apache.hadoop.hive.metastore.HiveMetaStore$HMSHandler.init(HiveMetaStore.java:398)
	at org.apache.hadoop.hive.metastore.RetryingHMSHandler.<init>(RetryingHMSHandler.java:78)
	at org.apache.hadoop.hive.metastore.RetryingHMSHandler.getProxy(RetryingHMSHandler.java:84)
	at org.apache.hadoop.hive.metastore.HiveMetaStore.newRetryingHMSHandler(HiveMetaStore.java:6390)
	at org.apache.hadoop.hive.metastore.HiveMetaStore.newRetryingHMSHandler(HiveMetaStore.java:6385)
	at org.apache.hadoop.hive.metastore.HiveMetaStore.startMetaStore(HiveMetaStore.java:6643)
	at org.apache.hadoop.hive.metastore.HiveMetaStore.main(HiveMetaStore.java:6570)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.apache.hadoop.util.RunJar.run(RunJar.java:221)
	at org.apache.hadoop.util.RunJar.main(RunJar.java:136)
NestedThrowablesStackTrace:
```
解决方法：

> 进入到hive_home然后执行下面命令:
> bin/schematool -dbType mysql -initSchema


```
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/usr/soft/hive2.1/lib/log4j-slf4j-impl-2.4.1.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/soft/hadoop-2.6.0/share/hadoop/common/lib/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.apache.logging.slf4j.Log4jLoggerFactory]
javax.jdo.JDOFatalDataStoreException: Unable to open a test connection to the given database. JDBC url = jdbc:mysql://192.168.52.1:3306/hive?createDatabas
eIfNotExsit=true; characterEncoding=UTFq8, username = root. Terminating connection pool (set lazyInit to true if you expect to start your database after your app). Original Exception: ------com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown database 'hive'
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:422)
	at com.mysql.jdbc.Util.handleNewInstance(Util.java:404)
	at com.mysql.jdbc.Util.getInstance(Util.java:387)
	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:939)
	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3878)
	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3814)
	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:871)
	at com.mysql.jdbc.MysqlIO.proceedHandshakeWithPluggableAuthentication(MysqlIO.java:1694)
	at com.mysql.jdbc.MysqlIO.doHandshake(MysqlIO.java:1215)
	at com.mysql.jdbc.ConnectionImpl.coreConnect(ConnectionImpl.java:2255)
	at com.mysql.jdbc.ConnectionImpl.connectOneTryOnly(ConnectionImpl.java:2286)
	at com.mysql.jdbc.ConnectionImpl.createNewIO(ConnectionImpl.java:2085)
	at com.mysql.jdbc.ConnectionImpl.<init>(ConnectionImpl.java:795)
	at com.mysql.jdbc.JDBC4Connection.<init>(JDBC4Connection.java:44)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:422)
	at com.mysql.jdbc.Util.handleNewInstance(Util.java:404)
	at com.mysql.jdbc.ConnectionImpl.getInstance(ConnectionImpl.java:400)
	at com.mysql.jdbc.NonRegisteringDriver.connect(NonRegisteringDriver.java:327)
	at java.sql.DriverManager.getConnection(DriverManager.java:664)
	at java.sql.DriverManager.getConnection(DriverManager.java:208)
	at com.jolbox.bonecp.BoneCP.obtainRawInternalConnection(BoneCP.java:361)
	at com.jolbox.bonecp.BoneCP.<init>(BoneCP.java:416)
	at com.jolbox.bonecp.BoneCPDataSource.getConnection(BoneCPDataSource.java:120)
	at org.datanucleus.store.rdbms.ConnectionFactoryImpl$ManagedConnectionImpl.getConnection(ConnectionFactoryImpl.java:483)
	at org.datanucleus.store.rdbms.RDBMSStoreManager.<init>(RDBMSStoreManager.java:296)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:422)
	at org.datanucleus.plugin.NonManagedPluginRegistry.createExecutableExtension(NonManagedPluginRegistry.java:606)
	at org.datanucleus.plugin.PluginManager.createExecutableExtension(PluginManager.java:301)
	at org.datanucleus.NucleusContextHelper.createStoreManagerForProperties(NucleusContextHelper.java:133)
	at org.datanucleus.PersistenceNucleusContextImpl.initialise(PersistenceNucleusContextImpl.java:420)
	at org.datanucleus.api.jdo.JDOPersistenceManagerFactory.freezeConfiguration(JDOPersistenceManagerFactory.java:821)
	at org.datanucleus.api.jdo.JDOPersistenceManagerFactory.createPersistenceManagerFactory(JDOPersistenceManagerFactory.java:338)
	at org.datanucleus.api.jdo.JDOPersistenceManagerFactory.getPersistenceManagerFactory(JDOPersistenceManagerFactory.java:217)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at javax.jdo.JDOHelper$16.run(JDOHelper.java:1965)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.jdo.JDOHelper.invoke(JDOHelper.java:1960)
	at javax.jdo.JDOHelper.invokeGetPersistenceManagerFactoryOnImplementation(JDOHelper.java:1166)
	at javax.jdo.JDOHelper.getPersistenceManagerFactory(JDOHelper.java:808)
	at javax.jdo.JDOHelper.getPersistenceManagerFactory(JDOHelper.java:701)
	at org.apache.hadoop.hive.metastore.ObjectStore.getPMF(ObjectStore.java:424)
	at org.apache.hadoop.hive.metastore.ObjectStore.getPersistenceManager(ObjectStore.java:453)
	at org.apache.hadoop.hive.metastore.ObjectStore.initialize(ObjectStore.java:327)
	at org.apache.hadoop.hive.metastore.ObjectStore.setConf(ObjectStore.java:294)
	at org.apache.hadoop.util.ReflectionUtils.setConf(ReflectionUtils.java:73)
	at org.apache.hadoop.util.ReflectionUtils.newInstance(ReflectionUtils.java:133)
	at org.apache.hadoop.hive.metastore.RawStoreProxy.<init>(RawStoreProxy.java:58)
	at org.apache.hadoop.hive.metastore.RawStoreProxy.getProxy(RawStoreProxy.java:67)
	at org.apache.hadoop.hive.metastore.HiveMetaStore$HMSHandler.newRawStore(HiveMetaStore.java:581)
	at org.apache.hadoop.hive.metastore.HiveMetaStore$HMSHandler.getMS(HiveMetaStore.java:546)
	at org.apache.hadoop.hive.metastore.HiveMetaStore$HMSHandler.createDefaultDB(HiveMetaStore.java:612)
	at org.apache.hadoop.hive.metastore.HiveMetaStore$HMSHandler.init(HiveMetaStore.java:398)
	at org.apache.hadoop.hive.metastore.RetryingHMSHandler.<init>(RetryingHMSHandler.java:78)
	at org.apache.hadoop.hive.metastore.RetryingHMSHandler.getProxy(RetryingHMSHandler.java:84)
	at org.apache.hadoop.hive.metastore.HiveMetaStore.newRetryingHMSHandler(HiveMetaStore.java:6390)
	at org.apache.hadoop.hive.metastore.HiveMetaStore.newRetryingHMSHandler(HiveMetaStore.java:6385)
	at org.apache.hadoop.hive.metastore.HiveMetaStore.startMetaStore(HiveMetaStore.java:6643)
	at org.apache.hadoop.hive.metastore.HiveMetaStore.main(HiveMetaStore.java:6570)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.apache.hadoop.util.RunJar.run(RunJar.java:221)
	at org.apache.hadoop.util.RunJar.main(RunJar.java:136)

```
