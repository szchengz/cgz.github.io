---
title: bigdata-cm-01
tags:
  - MySql笔记-错误编号和SQLSTATE码
date: 2018-12-09 11:12:22
categories:
description: MySql笔记 错误编号和SQLSTATE码
---

http://www.cnblogs.com/geaozhang/p/6814567.html

- 关于错误编号和SQLSTATE码：

　　每个MySQL错误都有一个唯一的数字错误编号(mysql_error_code)，每个错误又对应一个5字符的SQLSTATE码(ANSI SQL 采用)。

- SQLSTATE码对应的处理程序：

　　1、SQLWARNING处理程序：以‘01’开头的所有sqlstate码与之对应；

　　2、NOT FOUND处理程序：以‘02’开头的所有sqlstate码与之对应；

　　3、SQLEXCEPTION处理程序：不以‘01’或‘02’开头的所有sqlstate码，也就是所有未被SQLWARNING或NOT FOUND捕获的SQLSTATE(常遇到的MySQL错误就是非‘01’、‘02’开头的)

注意：‘01’、‘02’开头和‘1’、‘2’开头是有区别的，是不一样的错误sqlsate码。

当不想为每个错误都定义一个处理程序时，可以使用3个处理程序

　　e.g：DECLARE CONTINUE HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION
