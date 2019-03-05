---
  title: 在Linux服务相关的配置
  date: 2019-01-16 17:20:00
  categories: 大数据
  tags: [linux]
  description: 在Linux服务相关的配置
---

## 1.CentOS
- 查看服务

> chkconfig --list

- 只查看MySQL服务

> chkconfig --list mysqld

```
[mysql@localhost bin]$ chkconfig --list mysqld
mysqld         	0:off	1:off	2:on	3:on	4:on	5:on	6:off
```

可以看到mysql的2~5为on，说明mysql服务会随机器启动而自动启动。


- 配置MySQL的开机自动启动

> chkconfig --add mysql
> chkconfig mysqld on
