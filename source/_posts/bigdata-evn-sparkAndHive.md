---
  title: Spark与Hive集成
  date: 2018-08-16 17:20:00
  categories: 大数据
  tags: [hive,Spark]
  description: Spark与Hive集成
---


# 1.前提

安装MySql
安装Hive
安装Spark


# 2.配置

## 拷贝hive安装目录下面的hive-site.xml文件到Spark的conf下面

内容如下：

``` xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
    <property>
        <name>hive.metastore.uris</name>
        <value>thrift://master:9083</value>
    </property>
</configuration>
```

启动Hive元数据服务
> ./bin/hive --service metastore &

# 3.测试

启动spark-sql
> ./spark-sql --master://spark:7077


```
create table if not exists student(
 id int,
 name string,
 gender string,
 age int);
```

```
insert into student values(1,'Xueqian','F',23); //插入一条记录
insert into student values(2,'Weiliang','M',24); //再插入一条记录
select * from student; //显示student表中的记录
```
