---
  title: Hive笔记-hive中的数据类型
  date: 2019-04-18 08:20:00
  categories: 大数据
  tags: [Hive]
  description: hive中的数据类型，map、struct、array 这3种的用法
---

# 0.概述


# 1.Array的使用

## 1.1. 数据准备

```
biansutao	["beijing","shanghai","tianjin","hangzhou"]
linan	["changchu","chengdu","wuhan"]
```

## 1.2. 创建表

```sql
create table person(name string,work_locations array<string>)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
COLLECTION ITEMS TERMINATED BY ',';
```

## 1.3. 入库数据
```sql
LOAD DATA LOCAL INPATH 'person.txt' OVERWRITE INTO TABLE person;
```
