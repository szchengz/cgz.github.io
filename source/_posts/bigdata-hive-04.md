---
  title: Hive笔记-hive中的窗口函数
  date: 2018-12-18 11:20:00
  categories: 大数据
  tags: [Hive]
  description: hive中的窗口函数
---

## row_number()over(partition by xx)的使用

vim student.txt
```
1,1,88
2,1,66
3,1,75
4,2,30
5,2,70
6,2,80
7,2,60
8,3,90
9,3,70
10,3,80
11,3,80
```

学生成绩表
```sql
drop table if exists student;
create table student(id int, grade int, score int)
row format delimited fields terminated by ',';

-- 导入数据
load data local inpath '/home/hadoop/student.txt' into table student;

```

##

- 1.不分班按学生成绩排名
```sql
select *,row_number() over(order by Score desc) as Sequence from student;
```

输出结果：
```
+-------------+----------------+----------------+-----------+
| student.id  | student.grade  | student.score  | sequence  |
+-------------+----------------+----------------+-----------+
| 8           | 3              | 90             | 1         |
| 1           | 1              | 88             | 2         |
| 11          | 3              | 80             | 3         |
| 10          | 3              | 80             | 4         |
| 6           | 2              | 80             | 5         |
| 3           | 1              | 75             | 6         |
| 9           | 3              | 70             | 7         |
| 5           | 2              | 70             | 8         |
| 2           | 1              | 66             | 9         |
| 7           | 2              | 60             | 10        |
| 4           | 2              | 30             | 11        |
+-------------+----------------+----------------+-----------+

```

- 2.分班后按学生成绩排名

```sql
select *,row_number() over(PARTITION BY grade ORDER BY score desc) as sequence from Student;
```
输出结果：
```
+-------------+----------------+----------------+-----------+
| student.id  | student.grade  | student.score  | sequence  |
+-------------+----------------+----------------+-----------+
| 1           | 1              | 88             | 1         |
| 3           | 1              | 75             | 2         |
| 2           | 1              | 66             | 3         |
| 6           | 2              | 80             | 1         |
| 5           | 2              | 70             | 2         |
| 7           | 2              | 60             | 3         |
| 4           | 2              | 30             | 4         |
| 8           | 3              | 90             | 1         |
| 11          | 3              | 80             | 2         |
| 10          | 3              | 80             | 3         |
| 9           | 3              | 70             | 4         |
+-------------+----------------+----------------+-----------+
```
- 3.获取每个班的前1(几)名
```sql
select * from
(
  select *,row_number() over(partition by Grade order by Score desc) as Sequence from Student
)T where T.Sequence<=1
;
```

输出结果：
```
+-------+----------+----------+-------------+
| t.id  | t.grade  | t.score  | t.sequence  |
+-------+----------+----------+-------------+
| 1     | 1        | 88       | 1           |
| 6     | 2        | 80       | 1           |
| 8     | 3        | 90       | 1           |
+-------+----------+----------+-------------+
```

## 分区函数Partition By与排序rank()的用法

```sql

select *, rank() over(partition by Grade order by Score desc) as Sequence from Student
;
```

输出结果：
```

+-------------+----------------+----------------+-----------+
| student.id  | student.grade  | student.score  | sequence  |
+-------------+----------------+----------------+-----------+
| 1           | 1              | 88             | 1         |
| 3           | 1              | 75             | 2         |
| 2           | 1              | 66             | 3         |
| 6           | 2              | 80             | 1         |
| 5           | 2              | 70             | 2         |
| 7           | 2              | 60             | 3         |
| 4           | 2              | 30             | 4         |
| 8           | 3              | 90             | 1         |
| 11          | 3              | 80             | 2         |
| 10          | 3              | 80             | 2         |
| 9           | 3              | 70             | 4         |
+-------------+----------------+----------------+-----------+
```

```sql
select * from
(
  select *,rank() over(partition by Grade order by Score desc) as Sequence from Student
)T where T.Sequence<=2
;
```

# hive中取top n时常用方法row_number,rank,dense_rank
vim /home/hadoop/salary.txt
```
1,a,10
2,a,12
3,b,13
4,b,12
5,a,14
6,a,15
7,a,13
8,b,11
9,a,16
10,b,17
11,a,14
```

建表，导入数据
```sql
create table salary(id int, name string, sal int)
row format delimited fields terminated by ','
;
-- 加载数据
load  data local inpath '/home/hadoop/salary.txt' into table salary;

select id,name,sal,
rank()over(partition by name order by sal desc ) rank,
dense_rank() over(partition by name order by sal desc ) dense_rank,
row_number()over(partition by name order by sal desc) row_number
from salary
```
输出数据
```
+-----+-------+------+-------+-------------+-------------+
| id  | name  | sal  | rank  | dense_rank  | row_number  |
+-----+-------+------+-------+-------------+-------------+
| 9   | a     | 16   | 1     | 1           | 1           |
| 6   | a     | 15   | 2     | 2           | 2           |
| 11  | a     | 14   | 3     | 3           | 3           |
| 5   | a     | 14   | 3     | 3           | 4           |
| 7   | a     | 13   | 5     | 4           | 5           |
| 2   | a     | 12   | 6     | 5           | 6           |
| 1   | a     | 10   | 7     | 6           | 7           |
| 10  | b     | 17   | 1     | 1           | 1           |
| 3   | b     | 13   | 2     | 2           | 2           |
| 4   | b     | 12   | 3     | 3           | 3           |
| 8   | b     | 11   | 4     | 4           | 4           |
+-----+-------+------+-------+-------------+-------------+
```

结论：
> rank() -排序相同时会重复，总数不会变  （有并列，并列的下一名空开）
> dense_rank() -排序相同时会重复，总数会减少（有并列，并列的下一名不空开）
> row_number() -会根据顺序计算  (没有并列)
