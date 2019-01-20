---
  title: Hive案例-添加新增数据到Hive表
  date: 2018-12-18 11:20:00
  categories: 大数据
  tags: [Hive]
  description: 添加新增数据到Hive表
---

# 0.准备数据

- 构造一个车辆识别的业务

- 字段说明
car_no 车牌
car_color 车辆颜色
score 识别度

- 建表语句
```sql
-- 增量数据
create table t_kfk(id string, car_no string, car_color string, score DECIMAL(10,5))
row format delimited fields terminated by ','
stored as textfile;

-- 原数据
create table t_info(id string, car_no string, car_color string)
row format delimited fields terminated by ','
stored as textfile;
```

- 构造数据

vim /home/hadoop/t_kfk.txt
```
1,a,红色,0.3
2,a,蓝色,0.6
3,a,白色,0.4
4,b,灰色,0.5
5,b,红色,0.8
6,b,黄色,0.4
7,c,蓝色,0.5
8,c,黑色,0.4
9,c,绿色,0.2
```

vim /home/hadoop/t_info.txt
```
1,a,蓝色
10,aa,军色
```


- 导入数据

```sql
load data local inpath '/home/hadoop/t_kfk.txt' overwrite into table t_kfk;
load data local inpath '/home/hadoop/t_info.txt' overwrite into table t_info;
```

# 1.ETL过程


- 1.1 新增的数据取出最高分的放入临时表tmp_kfk
```sql
drop table if exists tmp_kfk;
create table tmp_kfk
as
select t1.id,t1.car_no,t1.car_color from
(
  select id,car_no,car_color,score, row_number() over (partition by car_no order by score desc ) ind
  from t_kfk
) as t1 where t1.ind=1
;
```



- 1.2 最高分的和旧数据合在一起
（新的数据与旧的数据一样用新的覆盖旧的）
```sql
drop table if exists tmp_all;
create table tmp_all
as

select * from tmp_kfk  

union

-- 找出旧数据中不存在新的
select info.* from t_info info
left join tmp_kfk kfk on kfk.car_no=info.car_no
where kfk.car_no is null
;
```

- 1.3 从临时表导入到正式表
```sql
insert overwrite table t_info
select * from tmp_all;
```
