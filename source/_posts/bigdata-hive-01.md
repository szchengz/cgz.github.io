---
  title: Hive实现WordCount
  date: 2018-08-16 17:20:00
  categories: 大数据
  tags: [Hive]
  description: Hive实现WordCount
---

# 1、常用命令

## 1.1 建表
```
#建表(默认是内部表)
create table trade_detail(id bigint, account string, income double, expenses double, time string)
row format delimited fields terminated by '\t';

create table test(id string,name string) row format delimited fields terminated by ',' stored as textfile;


#建分区表
#普通表和分区表区别：有大量数据增加的需要建分区表
create table td_part(id bigint, account string, income double, expenses double, time string)
partitioned by (logdate string)
row format delimited fields terminated by '\t';

#建外部表
create external table td_ext(id bigint, account string, income double, expenses double, time string)
row format delimited fields terminated by '\t' location '/td_ext';


#创建分区表（table中的列不能和partition中的列重合了）
create table ptest(ename string) partitioned by (deptno int) row format delimited fields terminated by '\t';



#显示有多少数据库
show databases;

#查看有多少表
show tables;

#查看表信息
describe tablename;
desc tablename

#创建数据库
create database wordcount;

```

## 1.2 加载数据到表

```sql

#把本地数据装载到数据表，也就是在metastore上创建信息
load data local inpath '/testdata/sogouq1' into table sogouq1_1;
load data local inpath '/root/employees.txt' into table employee;

#把HDFS上的数据装载到数据表
load data inpath '/testdata/SogouQ1/SogouQ1.txt' into table sogouq1_1;
load data inpath '/test/employees.txt' OVERWRITE into table employee;

#加载数据到分区表必须指明所属分区
load data local inpath './book.txt' overwrite into table book
partition (pubdate='2010-08-22');

//导数据到分区表
load Data Local inpath '/home/licz/data/ptest20' overwrite Into Table ptest Partition(deptno=20);
load Data Local inpath '/home/licz/data/ptest30' Into Table ptest Partition(deptno=30);

#从原有的表employee中追加数据到表copy_employee：
insert INTO table copy_employee select * from employee

#从原有的表employee中加载数据到表copy_employee，并替换掉原来的数据
insert OVERWRITE table copy_employee select * from employee;

#创建表的时候通过select加载数据：
create table cr_employee as SELECT * from employee;

#创建表的时候通过select 指定建立的字段并加载指定字段的数据
create table cr_employee1 as SELECT name from employee;

#直接把与表结构相符的数据文件复制到表指定的存储位置
dfs -put /root/employees.txt /user/hive/warehouse/employee;

```

查看表结构：describe employee;
SHOW TABLES; # 查看所有的表
SHOW TABLES 'TMP'; #支持模糊查询
SHOW PARTITIONS TMP_TABLE; #查看表有哪些分区
DESCRIBE TMP_TABLE; #查看表结构

# 2 Hive实现WordCount

``` sql
  // 创建一个数据库
  create database wordcount;
  //选择这个数据库
  use wordcount;

  //创建一个外部表，数据存在HDFS下的worddata目录
  create external table word_data(line string) row format delimited fields terminated by '\n' stored as textfile location '/worddata';

  create table words(word string);

    //根据MapReduce的规则，我们需要进行拆分，把每行数据拆分成单词，这里需要用到一个hive的内置表生成函数（UDTF）：explode(array)，参数是array，其实就是行变多列：
    //split是拆分函数，跟java的split功能一样，这里是按照空格拆分，所以执行完hql语句，words表里面就全部保存的单个单词
    这样基本实现了，因为hql可以group by，所以最后统计语句为：

    insert into table words select explode(split(line, " ")) as word from word_data;

//wordcount.words 库名称.表名称，group by word这个word是create table words(word string) 命令创建的word string
    select word, count(*) from wordcount.words group by word;

```
