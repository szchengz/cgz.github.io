---
  title: Hive笔记-hive中的窗口函数
  date: 2018-12-18 11:20:00
  categories: 大数据
  tags: [Hive]
  description: hive中的窗口函数
---

# 0.概述

窗口函数 Lag, Lead, First_value,Last_value

LAG(col,n,DEFAULT) 用于统计窗口内往上第n行值
LEAD(col,n,DEFAULT) 用于统计窗口内往下第n行值, 与LAG相反
FIRST_VALUE, LAST_VALUE
first_value:  取分组内排序后，截止到当前行，第一个值
last_value:  取分组内排序后，截止到当前行，最后一个值

https://www.cnblogs.com/skyEva/p/5730531.html

http://lxw1234.com/archives/tag/hive-window-functions
