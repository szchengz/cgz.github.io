---
  title: Hive笔记-Ntile使用
  date: 2018-12-18 11:20:00
  categories: 大数据
  tags: [Hive]
  description: Ntile使用
---

# 0.概述

可以看成是：它把有序的数据集合平均分配到指定的数量（num）个桶中, 将桶号分配给每一行。如果不能平均分配，则优先分配较小编号的桶，并且各个桶中能放的行数最多相差1。
语法是：ntile (num)  over ([partition_clause]  order_by_clause)  as your_bucket_num
然后可以根据桶号，选取前或后 n分之几的数据。
数据会完整展示出来，只是给相应的数据打标签；具体要取几分之几的数据，需要再嵌套一层根据标签取出。

# 1.案例说明

## 1.1 准备数据

id 用户ID
name 消费类型名称
sal 消费金额
+------------+--------------+-------------+
| salary.id  | salary.name  | salary.sal  |
+------------+--------------+-------------+
| 1          | a            | 10          |
| 2          | a            | 12          |
| 3          | b            | 13          |
| 4          | b            | 12          |
| 5          | a            | 14          |
| 6          | a            | 15          |
| 7          | a            | 13          |
| 8          | b            | 11          |
| 9          | a            | 16          |
| 10         | b            | 17          |
| 11         | a            | 14          |
+------------+--------------+-------------+


- 找出消费前50%的记录

根据sal这个字段排序，将记录分成2个桶，第一个桶的记录即是前50%

```sql
select  id, name, sal
,ntile(2) over (order by sal desc) as num
from salary;
```

输出记录
+-----+-------+------+----+
| id  | name  | sal  | num  |
+-----+-------+------+----+
| 10  | b     | 17   | 1  |
| 9   | a     | 16   | 1  |
| 6   | a     | 15   | 1  |
| 11  | a     | 14   | 1  |
| 5   | a     | 14   | 1  |
| 7   | a     | 13   | 1  |
| 3   | b     | 13   | 2  |
| 4   | b     | 12   | 2  |
| 2   | a     | 12   | 2  |
| 8   | b     | 11   | 2  |
| 1   | a     | 10   | 2  |
+-----+-------+------+----+
由上记录可看num=1的记录即是前50%的记录，要前50%的即加一个字查询把num=1的记录选出来即可。
(不过这样的做法如果刚好有中间的记录是一样的，会分中间的记录，比如上面sal=13的记录。第一条分到了第一个桶，第二条分到了第2个桶)
```sql
select * from
(
  select  id, name, sal
  ,ntile(2) over (order by sal desc) as num
  from salary
) as a where a.num=1;
```

- 根据类型找出消费前50%的记录
```sql
select * from
(
  select  id, name, sal
  ,ntile(2) over (partition by name order by sal desc) as num
  from salary
) as a where a.num=1;
```
