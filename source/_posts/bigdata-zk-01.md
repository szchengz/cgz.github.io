---
  title: zookeeper学习笔记
  date: 2019-01-20 15:20:00
  categories: 大数据
  tags: [zookeeper]
  description: zookeeper学习笔记
---

- bin/zkCli.sh
- bin/zkCli.sh -server localhost:2181

启动本机的zookeeper

进去入执行 help 可以查看帮助

ZooKeeper -server host:port cmd args
	stat path [watch]
	set path data [version]
	ls path [watch]
	delquota [-n|-b] path
	ls2 path [watch]
	setAcl path acl
	setquota -n|-b val path
	history
	redo cmdno
	printwatches on|off
	delete path [version]
	sync path
	listquota path
	rmr path
	get path [watch]
	create [-s] [-e] path data acl
	addauth scheme auth
	quit
	getAcl path
	close
	connect host:port

## 创建节点
 create [-s] [-e] path data acl
-s 表示是顺序节点
-e 标识是临时节点
path 节点路径
data 节点数据
acl 节点权限

 create /seq 0
