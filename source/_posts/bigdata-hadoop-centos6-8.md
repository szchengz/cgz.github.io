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

# 配置Hadoop

## 配置环境变量

> sudo vim /etc/profile

```
export HADOOP_HOME=/opt/soft/hadoop-2.7.7
export PATH=$PATH:${HADOOP_HOME}/bin:
```


## 配置hadoop-env.sh

> vim $HADOOP_HOME/etc/hadoop/hadoop-env.sh

```
export JAVA_HOME=/usr/java/jdk1.8.0_73
export HADOOP_PREFIX=/opt/soft/hadoop-2.7.7
```



## 配置 core-site.xml

> vim $HADOOP_HOME/etc/hadoop/core-site.xml

``` xml

<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://o1:9000</value>
    </property>
    <property>
        <name>hadoop.tmp.dir</name>
        <value>/opt/soft/hadoop-2.7.7/tmp</value>
    </property>
</configuration>

```

## 配置 hdfs-site.xml

> vim $HADOOP_HOME/etc/hadoop/hdfs-site.xml

``` xml
<configuration>
 <property>
  <name>dfs.namenode.secondary.http-address</name>
  <value>o1:9001</value>
 </property>
 <property>
   <name>dfs.namenode.name.dir</name>
   <value>file:/opt/soft/hadoop-2.7.7/dfs/name</value>
 </property>
 <property>
  <name>dfs.datanode.data.dir</name>
  <value>file:/opt/soft/hadoop-2.7.7/dfs/data</value>
  </property>
 <property>
  <name>dfs.replication</name>
  <value>2</value>
 </property>
 <property>
  <name>dfs.webhdfs.enabled</name>
  <value>true</value>
 </property>
</configuration>
```

## 配置 mapred-site.xml
没有这个文件
mv mapred-site.xml.template mapred-site.xml
> vim $HADOOP_HOME/etc/hadoop/mapred-site.xml


``` xml

<configuration>
  <property>
    <name>mapreduce.framework.name</name>
    <value>yarn</value>
  </property>
</configuration>

```

## 配置yarn-env.sh

> vim $HADOOP_HOME/etc/hadoop/yarn-env.sh

```
export JAVA_HOME=/usr/java/jdk1.8.0_73
```

## 配置yarn-site.xml

> vim $HADOOP_HOME/etc/hadoop/yarn-site.xml


``` xml

<configuration>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.resourcemanager.hostname</name>
        <value>o1</value>
    </property>
</configuration>

```

## 配置 slaves

> vim $HADOOP_HOME/etc/hadoop/slaves

```
o2
o3
```

## 将配置文件拷贝到各机器


## 格式化hdfs

> $HADOOP_HOME/bin/hdfs namenode -format


## 启动
启动hdfs
> $HADOOP_HOME/sbin/start-dfs.sh

停止hdfs
> $HADOOP_HOME/sbin/stop-dfs.sh

启动yarn
> $HADOOP_HOME/sbin/start-yarn.sh

打开
http://o1:50070/
