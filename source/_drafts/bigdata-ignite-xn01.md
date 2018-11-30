---
  title: ignite性能测试-通过Sql查询千万级别数据
  date: 2018-11-28 17:20:00
  categories: 大数据
  tags: [ignite]
  description: ignite性能测试-通过Sql查询千万级别数据
---


## 0.前言



## 1.准备条件

连接sqlline创建表

> bin/sqlline.sh –color=true –verbose=true -u jdbc:ignite:thin://bdp04


- 一个维度表
```sql


!sql drop table if EXISTS `dim_date`;
!sql CREATE TABLE `dim_date` (
  `date_id` varchar,
  `date_key` date,
  `day` int,
  `month` int,
  `month_name` varchar,
  `year` int,
  `yearmonth` int,
  `quarter` int,
  `week` int,
  `week_name` varchar,
  PRIMARY KEY (`date_id`)
)
WITH "template=replicated";

!sql CREATE INDEX idx_month ON dim_date (yearmonth);
!sql CREATE INDEX idx_year ON dim_date (year);

!sql select * from `dim_date`;


```

- 一个实事表
```sql

!sql drop table if EXISTS tb_less_idx;
!sql CREATE TABLE tb_less_idx (

  id LONG PRIMARY KEY
  , event_type int
  , pay_type int
  , out_mode int
  , out_time TIMESTAMP
  , basic_tc_id int
  , type int
  , subsystem_code varchar
  , create_time timestamp
  , update_time timestamp
  )
  WITH "template=replicated";

!sql CREATE INDEX idx_event_type ON tb_less_idx (event_type);
!sql CREATE INDEX idx_pay_type ON tb_less_idx (pay_type);
!sql CREATE INDEX idx_subsystem_code ON tb_less_idx (subsystem_code);
!sql CREATE INDEX idx_event_subsystem ON tb_less_idx (event_type, subsystem_code);

select * from tb_less_idx;
select count(*) from tb_less_idx;

```


## 2.操作步骤

查看集群
bin/ignitevisorcmd.sh

open打开连接
node查看节点运行情况（资源）
