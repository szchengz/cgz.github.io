---
  title: Hive的Join操作
  date: 2018-12-18 11:20:00
  categories: 大数据
  tags: [Hive]
  description: Hive的Join操作
---

## 0.构造数据

```sql
create table a(id int, name string)
  row format delimited fields terminated by ',';

create table b(id int, name string)
  row format delimited fields terminated by ',';  

```

导入数据
load data local inpath '/home/hadoop/a.txt' overwrite into table a;
load data local inpath '/home/hadoop/b.txt' overwrite into table b;

a.txt
```
1,a
2,b
3,c
4,d
```

b.txt
```
2,bb
3,cc
4,dd
5,ee
6,ff
```


## 1.inner join
```sql
select * from a inner join b on a.id=b.id;
```

输出结果：
```
+-------+---------+
| a.id  | a.name  |
+-------+---------+
| 2     | b       |
| 3     | c       |
| 4     | d       |
+-------+---------+
```
## 2.left join
```sql
select * from a left join b on a.id=b.id;
```

输出结果
```
+-------+---------+-------+---------+
| a.id  | a.name  | b.id  | b.name  |
+-------+---------+-------+---------+
| 1     | a       | NULL  | NULL    |
| 2     | b       | 2     | bb      |
| 3     | c       | 3     | cc      |
| 4     | d       | 4     | dd      |
+-------+---------+-------+---------+
```

## 3.right join
```sql
select * from a right join b on a.id=b.id;
```
输出结果
```
+-------+---------+-------+---------+
| a.id  | a.name  | b.id  | b.name  |
+-------+---------+-------+---------+
| 2     | b       | 2     | bb      |
| 3     | c       | 3     | cc      |
| 4     | d       | 4     | dd      |
| NULL  | NULL    | 5     | ee      |
| NULL  | NULL    | 6     | ff      |
+-------+---------+-------+---------+
```

## 3.full outer join
```sql
select * from a full outer join b on a.id=b.id;
```

输出结果：
```
+-------+---------+-------+---------+
| a.id  | a.name  | b.id  | b.name  |
+-------+---------+-------+---------+
| 1     | a       | NULL  | NULL    |
| 2     | b       | 2     | bb      |
| 3     | c       | 3     | cc      |
| 4     | d       | 4     | dd      |
| NULL  | NULL    | 5     | ee      |
| NULL  | NULL    | 6     | ff      |
+-------+---------+-------+---------+
```

## 4.left semi join
```sql
select * from a left semi join b on a.id=b.id;
```
输出结果：
```
+-------+---------+
| a.id  | a.name  |
+-------+---------+
| 2     | b       |
| 3     | c       |
| 4     | d       |
+-------+---------+
```
