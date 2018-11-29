---
title: CentOS6.8安装hadoop2.7
categories: 操作系统
tags:
  - hexo
description: CentOS6.8安装hadoop2.7
date: 2018-08-16 17:20:00
---


# 前提

## 配置Java环境

## 配置SSH免密登录

## 配置Hadoop

# 配置Hive

## 配置环境变量

> sudo vim /etc/profile

```

export HIVE_HOME=/opt/soft/apache-hive-2.3.4-bin
export PATH=$HIVE_HOME/bin:$HIVE_HOME/conf:$PATH  

```

## 配置 hive-env.sh

> cd $HIVE_HOME/conf

> cp hive-env.sh.template hive-env.sh

> vim hive-env.sh

```

export HADOOP_HOME=/opt/soft/hadoop-2.7.7
export HIVE_CONF_DIR=/opt/soft/apache-hive-2.3.4-bin/conf

```

## 创建数据库


> mysql> show variables like 'char%';


> mysql> create database hive character set latin1;
> mysql> show databases;

创建Hive用户,并授权

> mysql> create user 'hive' identified by 'hive';
> mysql> grant all on *.* TO 'hive'@'%' identified by 'hive' with grant option;
> mysql> grant all on *.* TO 'hive'@'localhost' identified by 'hive' with grant option;
> mysql>
> mysql> flush privileges;


## 设置hive-site.xml配置文件
> cp hive-default.xml.template hive-site.xml

> vim hive-site.xml

修改以下配置
```xml

<property>
  <name>javax.jdo.option.ConnectionURL</name>
  <value>jdbc:mysql://192.168.126.1:3306/hive?createDatabaseIfNotExsit=true; characterEncoding=UTFq8</value>
  <description>
    JDBC connect string for a JDBC metastore.
    To use SSL to encrypt/authenticate the connection, provide database-specific SSL flag in the connection URL.
    For example, jdbc:postgresql://myhost/db?ssl=true for postgres database.
  </description>
</property>
<property>
  <name>javax.jdo.option.ConnectionDriverName</name>
  <value>com.mysql.jdbc.Driver</value>
  <description>Driver class name for a JDBC metastore</description>
</property>
<property>
  <name>javax.jdo.option.ConnectionUserName</name>
  <value>hive</value>
  <description>Username to use against metastore database</description>
</property>
<property>
  <name>javax.jdo.option.ConnectionPassword</name>
  <value>hive</value>
  <description>password to use against metastore database</description>
</property>
<property>
  <name>hive.metastore.schema.verification.record.version</name>
  <value>false</value>
  <description>
    值为 false  即可解决 “Caused by: MetaException(message:Version information not found in metastore. )
  </description>
</property>
```

拷贝 mysql-connector-java-5.1.26-bin.jar 放到hive/lib 目录下


## 启动元数据服务

> hive --service metastore &

启动后，将会在hive数据库下生成表结构


更改如下表字段为字符集编码为 utf8(不然中文注释会显示为乱乱)

> mysql> alter table COLUMNS_V2 modify column COMMENT varchar(256) character set utf8;
> mysql> alter table TABLE_PARAMS modify column PARAM_VALUE varchar(4000) character set utf8;
> mysql> alter table PARTITION_PARAMS  modify column PARAM_VALUE varchar(4000) character set utf8;
> mysql> alter table PARTITION_KEYS  modify column PKEY_COMMENT varchar(4000) character set utf8;
> mysql> alter table  INDEX_PARAMS  modify column PARAM_VALUE  varchar(4000) character set utf8;

## 进入Hive cli命令行
> hive

## 进入beeline命令行

需要先启动hiveserver2服务
> hive --service hiveserver2 &

beeline -u jdbc:hive2://o1:10000 -n hadoop

> beeline
hive> !connect jdbc:hive2://localhost:10000 hadoop h
