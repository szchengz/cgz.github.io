---
title: MySql自定义函数返回星期几
date: 2018-09-16 17:00:07
categories: 数据库
tags: [MySql,自定义函数]
description:
---

语法：Create function function_name(参数列表)returns返回值类型

创建一个自定义函数返回星期几

```sql
drop function if exists day2week;
CREATE FUNCTION day2week( thedate datetime)  returns varchar (20)
BEGIN
	declare rs varchar(20) default '';

	select case WEEKDAY(thedate)
	when 0 then '星期一'
	when 1 then '星期二'
	when 2 then '星期三'
	when 3 then '星期四'
	when 4 then '星期五'
	when 5 then '星期六'
	when 6 then '星期日'
	end
	into rs;

    return rs;
END;
```
