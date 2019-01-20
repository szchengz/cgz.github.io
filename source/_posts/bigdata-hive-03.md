---
  title: Hive笔记
  date: 2018-12-18 11:20:00
  categories: 大数据
  tags: [Hive]
  description: Hive笔记
---

## 保存查询结果数据的几种方式
- 将查询结果保存到一张新的hive表

```sql
create table t_tmp
  as
  select * from t1;
```

- 将查询结果保存到一线已经存在的hive表

```sql
insert into table tb1
select * from t1;
```

- 将查询结果保存到指定的文件目录 （可以本地，可以HDFS）

```sql
insert overwrite local directory '/home/hadoop/t1'
  select * from t1;

```

```sql

insert overwrite directory 'hdfs://o1:9000/home/hadoop/t1'
  select * from t1;

```
