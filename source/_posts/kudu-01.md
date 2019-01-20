---
  title: 001-Kudu初识
  date: 2018-08-16 17:20:00
  categories: 大数据
  tags: [Kudu]
  description: Kudu集群环境配置
---

# Kudu简介

A new addition to the open source Apache Hadoop ecosystem, Apache Kudu completes Hadoop's storage layer to enable fast analytics on fast data.

Apache Kudu是开源Apache Hadoop生态系统的新成员，它完成了Hadoop的存储层，支持对快速数据进行快速分析。

# Kudu安装

## 硬件

- 主节点Kudu Master
One or more hosts to run Kudu masters. It is recommended to have either one master (no fault tolerance), or three masters (can tolerate one failure). The number of masters must be odd.

- 数据节点Tablet Server
One or more hosts to run Kudu tablet servers. When using replication, a minimum of three tablet servers is necessary.
------------
使用一台以上的节点做为Kudu的table Server 如果要使用副本，则在3台以下的节点

- 总结
要使用奇数台主节点。

## 安装步骤

| 节点 | 角色    | --- |
| ---- | ------- | --- |
| os1  | master  |     |
| os2  | tserver |     |
| os3  | tserver |     |
| os4  | tserver |     |


到官网下载 https://kudu.apache.org/docs/installation.html
kudu-master节点安装：

sudo rpm -ivh kudu-1.4.0+cdh5.12.2+0-1.cdh5.12.2.p0.8.el6.x86_64.rpm
sudo rpm -ivh kudu-client0-1.4.0+cdh5.12.2+0-1.cdh5.12.2.p0.8.el6.x86_64.rpm
sudo rpm -ivh kudu-client-devel-1.4.0+cdh5.12.2+0-1.cdh5.12.2.p0.8.el6.x86_64.rpm
sudo rpm -ivh kudu-debuginfo-1.4.0+cdh5.12.2+0-1.cdh5.12.2.p0.8.el6.x86_64.rpm
sudo rpm -ivh kudu-master-1.4.0+cdh5.12.2+0-1.cdh5.12.2.p0.8.el6.x86_64.rpm
sudo rpm -ivh kudu-tserver-1.4.0+cdh5.12.2+0-1.cdh5.12.2.p0.8.el6.x86_64.rpm

systemctl start ntpd.service
/etc/init.d/kudu-master start
/etc/init.d/kudu-tserver start
 sudo service kudu-tserver start
