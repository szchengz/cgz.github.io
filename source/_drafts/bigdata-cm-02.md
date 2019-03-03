---
title: 配置CM
tags:
  - hexo
date: 2018-12-06 18:19:03
categories:
description:
---


## 网络配置

设置机器名称(分别修改为bdp01,bdp02,bdp03)
> sudo vim /etc/sysconfig/network

设置host
> sudo vim /etc/hosts

```

10.10.203.105    dev01
10.10.203.106    dev02
10.10.203.107    dev03

```

## 配置SSH

在所有节点上操作以下命令
> ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa

此时将会在~/.ssh目录下面生成公私秘钥对
```
[jht@bdp.bdp01 .ssh]$ ll
total 8
-rw------- 1 jht jht 672 Dec  6 18:32 id_dsa
-rw-r--r-- 1 jht jht 599 Dec  6 18:32 id_dsa.pub
```

所有节点的公钥拷贝到第一个节点
scp ~/.ssh/id_dsa.pub jht@dev01:/home/jht/.ssh/id_dsa.pub.dev02
scp ~/.ssh/id_dsa.pub jht@dev01:/home/jht/.ssh/id_dsa.pub.dev03
……


将3台机器的公钥合在一起
```
cat ~/.ssh/id_dsa.pub >> ~/.ssh/authorized_keys
cat ~/.ssh/id_dsa.pub.dev02 >> ~/.ssh/authorized_keys
cat ~/.ssh/id_dsa.pub.dev03 >> ~/.ssh/authorized_keys

```


将公钥信息拷贝到其他节点机器

```
scp /home/jht/.ssh/authorized_keys jht@dev02:/home/jht/.ssh/authorized_keys
scp /home/jht/.ssh/authorized_keys jht@dev03:/home/jht/.ssh/authorized_keys

```

配置公钥文件的权限
chmod 600 /home/jht/.ssh/authorized_keys

## 配置JDK

解压JDK到/usr/java

设置软链接
ln -s /usr/java/jdk1.8.0_73 /usr/java/jdk

```sh
# jdk
export JAVA_HOME=/usr/java/jdk/
export JRE_HOME=$JAVA_HOME/jre/
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:/lib/tools.jar:$JRE_HOME/lib:$CLASSPATH
export PATH=$PATH:$JAVA_HOME/bin/

```

```
scp -r /usr/java/jdk1.8.0_73 jht@dev02:/usr/java/
scp -r /usr/java/jdk1.8.0_73 jht@dev03:/usr/java/
```


## 配置NTP时间同步

### 安装ntp
sudo yum install ntp

检查版本
> rpm -qa |grep ntp

### 配置服务端

```sh

# restrict表示规则，增加允许内网其他机器同步时间
restrict 10.10.203.0 mask 255.255.255.0 nomodify notrap

# server表示时间服务器 中国这边最活跃的时间服务器 :
server 210.72.145.44 perfer   # 中国国家受时中心
server 202.112.10.36             # 1.cn.pool.ntp.org
server 59.124.196.83             # 0.asia.pool.ntp.org


# allow update time by the upper server

# 允许上层时间服务器主动修改本机时间
restrict 210.72.145.44 nomodify notrap noquery
restrict 202.112.10.36 nomodify notrap noquery
restrict 59.124.196.83 nomodify notrap noquery  
# 外部时间服务器不可用时，以本地时间作为时间服务
server  127.127.1.0     # local clock
fudge   127.127.1.0 stratum 10
```


### 配置客服端
