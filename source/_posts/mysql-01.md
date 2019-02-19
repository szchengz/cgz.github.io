---
title: MySql笔记
tags:
  - MySql
date: 2018-12-09 11:12:22
categories:
description: MySql笔记
---

## 备份数据


mysqldump -h10.10.203.11 -P3306 -uroot -pJht123456 dw1204  --default-character-set=utf8 > dw1204.sql


mysql -u dw1 -pdw1 -h10.10.203.131 --default-character-set=utf8
mysql -u test -pJht123456 -h10.10.203.11 --default-character-set=utf8
10.10.203.11


## 跨主机备份

使用下面的命令可以将host1上的sourceDb复制到host2的targetDb，前提是host2主机上已经创建targetDb数据库：
> mysqldump --host=host1 --opt sourceDb| mysql --host=host2 -C targetDb

例子1：
mysqldump --host=10.10.203.11 --opt dw1204 -u test -pJht123456 --default-character-set=utf8 | mysql --host=localhost -C dw1 -uroot -p123456

例子2：
mysqldump --host=10.10.203.132 --opt sync_ctmg -u sync_ctmg -psync_ctmg --default-character-set=utf8 | mysql --host=localhost -C sync_ctmg -uroot -p123456


NE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://sync_ctmg:sync_ctmg@10.10.203.132:3306/sync_ctmg/NP_DWD_PARK_OUT';
