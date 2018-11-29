---
title: MySql存储过程常用笔记
date: 2018-09-16 11:19:42
categories: 数据库
tags: [MySql,存储过程,SQL]
description:
---

# 0.测试数据
```sql

DROP TABLE IF EXISTS `t1`;
CREATE TABLE `t1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL COMMENT '代号',
  `name` varchar(20) DEFAULT '0',
  `stime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `etime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1;

INSERT INTO `t1` VALUES ('1', 'income', '收入', '2018-08-06 09:09:02', '2018-09-15 10:09:02');
INSERT INTO `t1` VALUES ('2', 'flow', '流量', '2018-09-01 10:02:05', '2018-09-16 10:09:05');
INSERT INTO `t1` VALUES ('3', 'exeu', '运行', '2018-09-02 10:12:05', '2018-09-16 10:09:05');
INSERT INTO `t1` VALUES ('4', 'try', '试运行', '2018-09-03 10:13:05', '2018-09-16 10:09:05');
INSERT INTO `t1` VALUES ('5', 'pay', '支付', '2018-09-04 11:02:05', '2018-09-16 10:09:05');
INSERT INTO `t1` VALUES ('6', 'item', '项目', '2018-09-05 12:08:05', '2018-09-16 10:09:05');
INSERT INTO `t1` VALUES ('7', 'create', '创建', '2018-09-06 13:22:05', '2018-09-16 10:09:05');
INSERT INTO `t1` VALUES ('8', 'delete', '删除', '2018-09-07 14:02:05', '2018-09-16 10:09:05');

```


# 1.创建存储过程

定义无参数的存储过程

```sql
drop procedure if exists sp;
create procedure sp()
begin

	select * from t1;

end
```

定义带参数的存储过程，
其中p1、p2为输入参数，p3既可能是输入参数也可以是输出参数，p4为输出参数
```sql
drop procedure if exists sp1;
create procedure sp1(in p1 int, in p2 varchar, inout p3 date, out p4 int)
begin

	select p1;
	select p2;
	select p3;
	set p4 = 100;
end

```



# 2.定义变量

在存储过程中定义变量需要用declare关键词，而且在最前面声明，这个和其他语言有定不同
```sql
drop procedure if exists sp;
create procedure sp()
begin
	declare code1 varchar(20);
	declare stime1 datetime;

	select code, stime from t1;

end

```

# 3.将查询结果放到变量中

将查询结果放到变量中，有两种方式
- 方式一
```sql
drop procedure if exists sp3_1;
create procedure sp3_1()
begin
	declare cou int;
	set cou = (select count(*) from t1);

  select cou;

end

```


- 方式二 多个参数时，只能用第二种方式
```sql
drop procedure if exists sp3_2;
create procedure sp3_2()
begin

  declare code1 varchar(20);
  declare stime1 datetime;

  select code, stime into code1,stime1 from t1 limit 1 ;

  select code1,stime1;

end


```

# 4.条件语句

```sql
drop procedure if exists sp4_1;
create procedure sp4_1()
begin
  declare code1 varchar(20);
  declare stime1 datetime;

  select code, stime into code1,stime1 from t1 limit 1 ;

  select code1,stime1;

  if code1 = 'income' then
    select * from t1 where code=code1;
  elseif code1 = 'item' then
    update t1 set etime=now() where code=code1;
    select * from t1 where code=code1;
  else
    select count(*) from t1;
  end if ;

end
;


```

# 5.循环语句

方式一：
```sql

drop procedure if exists sp5_1;
create procedure sp5_1()
begin
  declare sum int default 0;  -- default 是指定该变量的默认值
  declare i int default 1;
  while i<=10 DO -- 循环开始
    set sum=sum+i;
    set i=i+1;
  end while; -- 循环结束
  select sum;  -- 输出结果
end
;
call sp5_1();

```

方式二
```sql

drop procedure if exists sp5_2;
create procedure sp5_2()
begin
  declare sum int default 0;  -- default 是指定该变量的默认值
  declare i int default 1;

  loop_name:loop
	-- 满足条件时离开循环
	if i > 10 then
		-- 和 break 差不多都是结束训话
		leave loop_name;
	end if;

	set sum=sum+i;
    set i=i+1;
  end loop;


  select sum;  -- 输出结果
end
;
call sp5_2();

```

方式三：
```sql

drop procedure if exists sp5_3;
create procedure sp5_3()
begin
    declare sum int default 0;
    declare i int default 1;

	repeat -- 循环开始
        set sum=sum+i;
        set i=i+1;
    until i > 10 end repeat; -- 循环结束

	select sum; -- 输出结果
end
;
call sp5_3();


```

# 6.循环结果集
```sql

drop procedure if exists sp6_1;
create procedure sp6_1()
begin

	DECLARE _done varchar(20) default '';
	declare rest varchar(1000) default '';

	declare id1 int;
	declare sum1 int default 0;
	declare code1 varchar(20) default '';
	declare name1 varchar(20) default '';
	declare stime1 datetime default now();
	declare etime1 datetime default now();

  DECLARE _cur CURSOR FOR
         SELECT id, code, name, stime, etime FROM t1;
	delete from t2;
	open _cur;
		REPEAT

			fetch _cur into id1, code1, name1, stime1, etime1;

			set _done = code1;

			set sum1=sum1+id1;
			select id1,code1,name1;
			set rest = concat(rest, name1, '-');

			insert into t2(code,name) values(code1,name1);
		UNTIL _done='item' END REPEAT;

	close _cur;

	select sum1;
	select rest;

end
;

```

# 7.Mysql prepare预处理语句

```sql
create procedure sp7_1()
begin
	set @stmt1 = 'select code,name,stime,etime from t1 where code=?';
	SET @code = 'item';
	prepare sql1 from @stmt1;
	execute sql1 using @code;
end
;

```


# 常用函数

- IFNULL(expr1,expr2)
假如expr1 不为 NULL，则 IFNULL() 的返回值为 expr1; 否则其返回值为 expr2。IFNULL()的返回值是数字或是字符串，具体情况取决于其所使用的语境。

```sql
SELECT IFNULL(1,0);
SELECT IFNULL(NULL,10);
SELECT IFNULL(1/0,10);
SELECT IFNULL(1/0,'yes');
```

- IF(expr1,expr2,expr3)
如果 expr1 是TRUE (expr1 <> 0 and expr1 <> NULL)，则 IF()的返回值为expr2; 否则返回值则为 expr3。IF() 的返回值为数字值或字符串值，具体情况视其所在语境而定。
```sql
select if(sva=1,"男","女") as ssva from taname where id = '111'
```

```sql
select CASE sva WHEN 1 THEN '男' ELSE '女' END as ssva from taname where id = '1'
```

```sql
SELECT CASE 1 WHEN 1 THEN 'one'
  WHEN 2 THEN 'two'
   ELSE 'more' END
as testCol
```

- LPAD(str,len,padstr)

用字符串 padstr对 str进行左边填补直至它的长度达到 len个字符长度，然后返回 str。如果 str的长度长于 len'，那么它将被截除到 len个字符。
```sql
SELECT LPAD('hi',4,'??'); -> '??hi'
```


- RPAD(str,len,padstr)
用字符串 padstr对 str进行右边填补直至它的长度达到 len个字符长度，然后返回 str。如果 str的长度长于 len'，那么它将被截除到 len个字符。
```sql
SELECT RPAD('hi',5,'?'); -> 'hi???'
```

- date_format(date, format) 函数，MySQL日期格式化函数date_format()

时间转字符串
```sql
select date_format(now(), '%Y-%m-%d');
#2016-09-16
```

时间转时间戳
```sql
select unix_timestamp(now());
#结果：1452001082
```

 字符串转时间
```sql
select str_to_date('2016-01-02', '%Y-%m-%d %H');
#结果：2016-01-02 00:00:00
```

字符串转时间戳
```sql
select unix_timestamp('2016-01-02');
#结果：1451664000
```

时间戳转时间
```sql
select from_unixtime(1451997924);
#结果：2016-01-05 20:45:24
```

时间戳转字符串
```sql
select from_unixtime(1451997924,'%Y-%d');
//结果：2016-01-05 20:45:24
```

日期减法
```sql
select DATE_SUB(curdate(),INTERVAL 0 DAY) ;
```

日期加法
```sql
select date_add(curdate(),INTERVAL 0 DAY) ;
```

- 常用日期
```sql
-- MySQL日期时间处理函数
-- 当前日期：2017-05-12（突然发现今天512，是不是会拉防空警报）
SELECT NOW() FROM DUAL;-- 当前日期时间：2017-05-12 11:41:47
-- 在MySQL里也存在和Oracle里类似的dual虚拟表：官方声明纯粹是为了满足select ... from...这一习惯问题，mysql会忽略对该表的引用。
-- 那么MySQL中就不用DUAL了吧。
SELECT NOW();-- 当前日期时间：2017-05-12 11:41:55
-- 除了 now() 函数能获得当前的日期时间外，MySQL 中还有下面的函数：
SELECT CURRENT_TIMESTAMP();-- 2017-05-15 10:19:31
SELECT CURRENT_TIMESTAMP;-- 2017-05-15 10:19:51
SELECT LOCALTIME();-- 2017-05-15 10:20:00
SELECT LOCALTIME;-- 2017-05-15 10:20:10
SELECT LOCALTIMESTAMP();-- 2017-05-15 10:20:21(v4.0.6)
SELECT LOCALTIMESTAMP;-- 2017-05-15 10:20:30(v4.0.6)
-- 这些日期时间函数，都等同于 now()。鉴于 now() 函数简短易记，建议总是使用 now()来替代上面列出的函数。

SELECT SYSDATE();-- 当前日期时间：2017-05-12 11:42:03
-- sysdate() 日期时间函数跟 now() 类似，
-- 不同之处在于：now() 在执行开始时值就得到了;sysdate() 在函数执行时动态得到值。
-- 看下面的例子就明白了：
SELECT NOW(), SLEEP(3), NOW();
SELECT SYSDATE(), SLEEP(3), SYSDATE();


SELECT CURDATE();-- 当前日期：2017-05-12
SELECT CURRENT_DATE();-- 当前日期：等同于 CURDATE()
SELECT CURRENT_DATE;-- 当前日期：等同于 CURDATE()

SELECT CURTIME();-- 当前时间：11:42:47
SELECT CURRENT_TIME();-- 当前时间：等同于 CURTIME()
SELECT CURRENT_TIME;-- 当前时间：等同于 CURTIME()

-- 获得当前 UTC 日期时间函数
SELECT UTC_TIMESTAMP(), UTC_DATE(), UTC_TIME()
-- MySQL 获得当前时间戳函数：current_timestamp, current_timestamp()
SELECT CURRENT_TIMESTAMP, CURRENT_TIMESTAMP();-- 2017-05-15 10:32:21 | 2017-05-15 10:32:21


-- MySQL 日期时间 Extract（选取） 函数
SET @dt = '2017-05-15 10:37:14.123456';
SELECT DATE(@dt);-- 获取日期：2017-05-15
SELECT TIME('2017-05-15 10:37:14.123456');-- 获取时间：10:37:14.123456
SELECT YEAR('2017-05-15 10:37:14.123456');-- 获取年份
SELECT MONTH('2017-05-15 10:37:14.123456');-- 获取月份
SELECT DAY('2017-05-15 10:37:14.123456');-- 获取日
SELECT HOUR('2017-05-15 10:37:14.123456');-- 获取时
SELECT MINUTE('2017-05-15 10:37:14.123456');-- 获取分
SELECT SECOND('2017-05-15 10:37:14.123456');-- 获取秒
SELECT MICROSECOND('2017-05-15 10:37:14.123456');-- 获取毫秒
SELECT QUARTER('2017-05-15 10:37:14.123456');-- 获取季度
SELECT WEEK('2017-05-15 10:37:14.123456');-- 20 (获取周)
SELECT WEEK('2017-05-15 10:37:14.123456', 7);-- ****** 测试此函数在MySQL5.6下无效
SELECT WEEKOFYEAR('2017-05-15 10:37:14.123456');-- 同week()
SELECT DAYOFYEAR('2017-05-15 10:37:14.123456');-- 135 (日期在年度中第几天)
SELECT DAYOFMONTH('2017-05-15 10:37:14.123456');-- 5 (日期在月度中第几天)
SELECT DAYOFWEEK('2017-05-15 10:37:14.123456');-- 2 (日期在周中第几天；周日为第一天)
SELECT WEEKDAY('2017-05-15 10:37:14.123456');-- 0
SELECT WEEKDAY('2017-05-21 10:37:14.123456');-- 6(与dayofweek()都表示日期在周的第几天，只是参考标准不同，weekday()周一为第0天，周日为第6天)
SELECT YEARWEEK('2017-05-15 10:37:14.123456');-- 201720(年和周)

SELECT EXTRACT(YEAR FROM '2017-05-15 10:37:14.123456');
SELECT EXTRACT(MONTH FROM '2017-05-15 10:37:14.123456');
SELECT EXTRACT(DAY FROM '2017-05-15 10:37:14.123456');
SELECT EXTRACT(HOUR FROM '2017-05-15 10:37:14.123456');
SELECT EXTRACT(MINUTE FROM '2017-05-15 10:37:14.123456');
SELECT EXTRACT(SECOND FROM '2017-05-15 10:37:14.123456');
SELECT EXTRACT(MICROSECOND FROM '2017-05-15 10:37:14.123456');
SELECT EXTRACT(QUARTER FROM '2017-05-15 10:37:14.123456');
SELECT EXTRACT(WEEK FROM '2017-05-15 10:37:14.123456');
SELECT EXTRACT(YEAR_MONTH FROM '2017-05-15 10:37:14.123456');
SELECT EXTRACT(DAY_HOUR FROM '2017-05-15 10:37:14.123456');
SELECT EXTRACT(DAY_MINUTE FROM '2017-05-15 10:37:14.123456');-- 151037(日时分)
SELECT EXTRACT(DAY_SECOND FROM '2017-05-15 10:37:14.123456');-- 15103714(日时分秒)
SELECT EXTRACT(DAY_MICROSECOND FROM '2017-05-15 10:37:14.123456');-- 15103714123456(日时分秒毫秒)
SELECT EXTRACT(HOUR_MINUTE FROM '2017-05-15 10:37:14.123456');-- 1037(时分)
SELECT EXTRACT(HOUR_SECOND FROM '2017-05-15 10:37:14.123456');-- 103714(时分秒)
SELECT EXTRACT(HOUR_MICROSECOND FROM '2017-05-15 10:37:14.123456');-- 103714123456(日时分秒毫秒)
SELECT EXTRACT(MINUTE_SECOND FROM '2017-05-15 10:37:14.123456');-- 3714(分秒)
SELECT EXTRACT(MINUTE_MICROSECOND FROM '2017-05-15 10:37:14.123456');-- 3714123456(分秒毫秒)
SELECT EXTRACT(SECOND_MICROSECOND FROM '2017-05-15 10:37:14.123456');-- 14123456(秒毫秒)
-- MySQL Extract() 函数除了没有date(),time() 的功能外，其他功能一应具全。
-- 并且还具有选取‘day_microsecond' 等功能。
-- 注意这里不是只选取 day 和 microsecond，而是从日期的 day 部分一直选取到 microsecond 部分。


SELECT DAYNAME('2017-05-15 10:37:14.123456');-- Monday(返回英文星期)
SELECT MONTHNAME('2017-05-15 10:37:14.123456');-- May(返回英文月份)
SELECT LAST_DAY('2016-02-01');-- 2016-02-29 (返回月份中最后一天)
SELECT LAST_DAY('2016-05-01');-- 2016-05-31

-- DATE_ADD(date,INTERVAL expr type) 从日期加上指定的时间间隔
-- type参数可参考：http://www.w3school.com.cn/sql/func_date_sub.asp
SELECT DATE_ADD('2017-05-15 10:37:14.123456',INTERVAL 1 YEAR);-- 表示：2018-05-15 10:37:14.123456
SELECT DATE_ADD('2017-05-15 10:37:14.123456',INTERVAL 1 QUARTER);-- 表示：2017-08-15 10:37:14.123456
SELECT DATE_ADD('2017-05-15 10:37:14.123456',INTERVAL 1 MONTH);-- 表示：2017-06-15 10:37:14.123456
SELECT DATE_ADD('2017-05-15 10:37:14.123456',INTERVAL 1 WEEK);-- 表示：2017-05-22 10:37:14.123456
SELECT DATE_ADD('2017-05-15 10:37:14.123456',INTERVAL 1 DAY);-- 表示：2017-05-16 10:37:14.123456
SELECT DATE_ADD('2017-05-15 10:37:14.123456',INTERVAL 1 HOUR);-- 表示：2017-05-15 11:37:14.123456
SELECT DATE_ADD('2017-05-15 10:37:14.123456',INTERVAL 1 MINUTE);-- 表示：2017-05-15 10:38:14.123456
SELECT DATE_ADD('2017-05-15 10:37:14.123456',INTERVAL 1 SECOND);-- 表示：2017-05-15 10:37:15.123456
SELECT DATE_ADD('2017-05-15 10:37:14.123456',INTERVAL 1 MICROSECOND);-- 表示：2017-05-15 10:37:14.123457


-- DATE_SUB(date,INTERVAL expr type) 从日期减去指定的时间间隔
SELECT DATE_SUB('2017-05-15 10:37:14.123456',INTERVAL 1 YEAR);-- 表示：2016-05-15 10:37:14.123456
SELECT DATE_SUB('2017-05-15 10:37:14.123456',INTERVAL 1 QUARTER);-- 表示：2017-02-15 10:37:14.123456
SELECT DATE_SUB('2017-05-15 10:37:14.123456',INTERVAL 1 MONTH);-- 表示：2017-04-15 10:37:14.123456
SELECT DATE_SUB('2017-05-15 10:37:14.123456',INTERVAL 1 WEEK);-- 表示：2017-05-08 10:37:14.123456
SELECT DATE_SUB('2017-05-15 10:37:14.123456',INTERVAL 1 DAY);-- 表示：2017-05-14 10:37:14.123456
SELECT DATE_SUB('2017-05-15 10:37:14.123456',INTERVAL 1 HOUR);-- 表示：2017-05-15 09:37:14.123456
SELECT DATE_SUB('2017-05-15 10:37:14.123456',INTERVAL 1 MINUTE);-- 表示：2017-05-15 10:36:14.123456
SELECT DATE_SUB('2017-05-15 10:37:14.123456',INTERVAL 1 SECOND);-- 表示：2017-05-15 10:37:13.123456
SELECT DATE_SUB('2017-05-15 10:37:14.123456',INTERVAL 1 MICROSECOND);-- 表示：2017-05-15 10:37:14.123455

-- 经特殊日期测试，DATE_SUB(date,INTERVAL expr type)可放心使用
SELECT DATE_SUB(CURDATE(),INTERVAL 1 DAY);-- 前一天：2017-05-11
SELECT DATE_SUB(CURDATE(),INTERVAL -1 DAY);-- 后一天：2017-05-13
SELECT DATE_SUB(CURDATE(),INTERVAL 1 MONTH);-- 一个月前日期：2017-04-12
SELECT DATE_SUB(CURDATE(),INTERVAL -1 MONTH);-- 一个月后日期：2017-06-12
SELECT DATE_SUB(CURDATE(),INTERVAL 1 YEAR);-- 一年前日期：2016-05-12
SELECT DATE_SUB(CURDATE(),INTERVAL -1 YEAR);-- 一年后日期：20178-06-12
-- MySQL date_sub() 日期时间函数 和 date_add() 用法一致，并且可以用INTERNAL -1 xxx的形式互换使用；
-- 另外，MySQL 中还有两个函数 subdate(), subtime()，建议，用 date_sub() 来替代。

-- MySQL 另类日期函数：period_add(P,N), period_diff(P1,P2)
-- 函数参数“P” 的格式为“YYYYMM” 或者 “YYMM”，第二个参数“N” 表示增加或减去 N month（月）。
-- MySQL period_add(P,N)：日期加/减去N月。
SELECT PERIOD_ADD(201705,2), PERIOD_ADD(201705,-2);-- 201707  20170503
-- period_diff(P1,P2)：日期 P1-P2，返回 N 个月。
SELECT PERIOD_DIFF(201706, 201703);--
-- datediff(date1,date2)：两个日期相减 date1 - date2，返回天数
SELECT DATEDIFF('2017-06-05','2017-05-29');-- 7
-- TIMEDIFF(time1,time2)：两个日期相减 time1 - time2，返回 TIME 差值
SELECT TIMEDIFF('2017-06-05 19:28:37', '2017-06-05 17:00:00');-- 02:28:37


-- MySQL日期转换函数
SELECT TIME_TO_SEC('01:00:05'); -- 3605
SELECT SEC_TO_TIME(3605);-- 01:00:05

-- MySQL （日期、天数）转换函数：to_days(date), from_days(days)
SELECT TO_DAYS('0000-00-00'); -- NULL
SELECT TO_DAYS('2017-06-05'); -- 736850
SELECT FROM_DAYS(0);           -- '0000-00-00'
SELECT FROM_DAYS(736850);      -- '2017-06-05'

-- MySQL Str to Date （字符串转换为日期）函数：str_to_date(str, format)

SELECT STR_TO_DATE('06.05.2017 19:40:30', '%m.%d.%Y %H:%i:%s');-- 2017-06-05 19:40:30
SELECT STR_TO_DATE('06/05/2017', '%m/%d/%Y');                  -- 2017-06-05
SELECT STR_TO_DATE('2017/12/3','%Y/%m/%d')             -- 2017-12-03
SELECT STR_TO_DATE('20:09:30', '%h:%i:%s')             -- NULL(超过12时的小时用小写h，得到的结果为NULL)

-- 日期时间格式化
SELECT DATE_FORMAT('2017-05-12 17:03:51', '%Y年%m月%d日 %H时%i分%s秒');-- 2017年05月12日 17时03分51秒(具体需要什么格式的数据根据实际情况来;小写h为12小时制;)
SELECT TIME_FORMAT('2017-05-12 17:03:51', '%Y年%m月%d日 %H时%i分%s秒');-- 0000年00月00日 17时03分51秒(time_format()只能用于时间的格式化)
-- STR_TO_DATE()和DATE_FORMATE()为互逆操作

-- MySQL 获得国家地区时间格式函数：get_format()
-- MySQL get_format() 语法：get_format(date|time|datetime, 'eur'|'usa'|'jis'|'iso'|'internal'
-- MySQL get_format() 用法的全部示例：
SELECT GET_FORMAT(DATE,'usa');      -- '%m.%d.%Y'
SELECT GET_FORMAT(DATE,'jis');      -- '%Y-%m-%d'
SELECT GET_FORMAT(DATE,'iso');      -- '%Y-%m-%d'
SELECT GET_FORMAT(DATE,'eur');      -- '%d.%m.%Y'
SELECT GET_FORMAT(DATE,'internal');     -- '%Y%m%d'
SELECT GET_FORMAT(DATETIME,'usa');      -- '%Y-%m-%d %H.%i.%s'
SELECT GET_FORMAT(DATETIME,'jis');      -- '%Y-%m-%d %H:%i:%s'
SELECT GET_FORMAT(DATETIME,'iso');      -- '%Y-%m-%d %H:%i:%s'
SELECT GET_FORMAT(DATETIME,'eur');      -- '%Y-%m-%d %H.%i.%s'
SELECT GET_FORMAT(DATETIME,'internal'); -- '%Y%m%d%H%i%s'
SELECT GET_FORMAT(TIME,'usa');      -- '%h:%i:%s %p'
SELECT GET_FORMAT(TIME,'jis');      -- '%H:%i:%s'
SELECT GET_FORMAT(TIME,'iso');      -- '%H:%i:%s'
SELECT GET_FORMAT(TIME,'eur');      -- '%H.%i.%s'
SELECT GET_FORMAT(TIME,'internal');     -- '%H%i%s'


-- MySQL 拼凑日期、时间函数：makdedate(year,dayofyear), maketime(hour,minute,second)
SELECT MAKEDATE(2017,31);   -- '2017-01-31'
SELECT MAKEDATE(2017,32);   -- '2017-02-01'
SELECT MAKETIME(19,52,35);  -- '19:52:35'

-- MySQL 时区（timezone）转换函数：convert_tz(dt,from_tz,to_tz)
SELECT CONVERT_TZ('2017-06-05 19:54:12', '+08:00', '+00:00'); -- 2017-06-05 11:54:12


-- MySQL （Unix 时间戳、日期）转换函数
-- unix_timestamp(), unix_timestamp(date), from_unixtime(unix_timestamp), from_unixtime(unix_timestamp,format)
-- 将具体时间时间转为timestamp
SELECT UNIX_TIMESTAMP();-- 当前时间的时间戳：1494815779
SELECT UNIX_TIMESTAMP('2017-05-15');-- 指定日期的时间戳：1494777600
SELECT UNIX_TIMESTAMP('2017-05-15 10:37:14');-- 指定日期时间的时间戳：1494815834

-- 将时间戳转为具体时间
SELECT FROM_UNIXTIME(1494815834);-- 2017-05-15 10:37:14
SELECT FROM_UNIXTIME(1494815834, '%Y年%m月%d日 %h时%分:%s秒');-- 获取时间戳对应的格式化日期时间

-- MySQL 时间戳（timestamp）转换、增、减函数
SELECT TIMESTAMP('2017-05-15');-- 2017-05-15 00:00:00
SELECT TIMESTAMP('2017-05-15 08:12:25', '01:01:01');-- 2017-05-15 09:13:26
SELECT DATE_ADD('2017-05-15 08:12:25', INTERVAL 1 DAY);-- 2017-05-16 08:12:25
SELECT TIMESTAMPADD(DAY, 1, '2017-05-15 08:12:25');-- 2017-05-16 08:12:25; MySQL timestampadd() 函数类似于 date_add()。

SELECT TIMESTAMPDIFF(YEAR, '2017-06-01', '2016-05-15');-- -1
SELECT TIMESTAMPDIFF(MONTH, '2017-06-01', '2016-06-15');-- -11
SELECT TIMESTAMPDIFF(DAY, '2017-06-01', '2016-06-15');-- -351
SELECT TIMESTAMPDIFF(HOUR, '2017-06-01 08:12:25', '2016-06-15 00:00:00');-- -8432
SELECT TIMESTAMPDIFF(MINUTE, '2017-06-01 08:12:25', '2016-06-15 00:00:00');-- -505932
SELECT TIMESTAMPDIFF(SECOND, '2017-06-01 08:12:25', '2016-06-15 00:00:00');-- -30355945
```

# 常用工具

- 生成1到100的随机数

（mysql的rand函数可以生成一个0到1之间的随机数）

```sql
select FLOOR(1 + (RAND() * 101));
```
- 随机取一条记录
```sql
select * from t1 order by rand() limit 1;
```
- 随机取多条记录
```sql
select * from t1 order by rand() limit 4;
```
- 随机生成日期
生成2000年到2018年之间的日期
```sql
select CONCAT(FLOOR(2000 + (RAND() * 19)),'-',LPAD(FLOOR(1 + (RAND() * 12)),2,0),'-',LPAD(FLOOR(3 + (RAND() * 8)),2,0))
```

```sql
select CONCAT(LPAD(FLOOR(0 + (RAND() * 23)),2,0),':',LPAD(FLOOR(0 + (RAND() * 59)),2,0),':',LPAD(FLOOR(0 + (RAND() * 59)),2,0))
```
