---
title: CentOS6.8安装SSH
categories: 操作系统
tags:
  - hexo
description: CentOS6.8安装SSH
date: 2018-08-16 17:20:00
---




## 1.1 网络配置

设置机器名称(分别修改为o1,o2,o3)
> sudo vim /etc/sysconfig/network

设置host
> sudo vim /etc/hosts

```

192.168.126.134 o1
192.168.126.135 o2
192.168.126.136 o3

```

## hadoop用户SSH免密登录

在三台机器上生成密钥
> ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa

> ssh-keygen -t dsa -P '' -f /root/.ssh/id_dsa

拷贝两台机器的公钥到第一台

```
scp ~/.ssh/id_dsa.pub hadoop@o1:/home/hadoop/.ssh/id_dsa.pub.o2
scp ~/.ssh/id_dsa.pub hadoop@o1:/home/hadoop/.ssh/id_dsa.pub.o3

```
将3台机器的公钥合在一起
```
cat ~/.ssh/id_dsa.pub >> ~/.ssh/authorized_keys
cat ~/.ssh/id_dsa.pub.o2 >> ~/.ssh/authorized_keys
cat ~/.ssh/id_dsa.pub.o3 >> ~/.ssh/authorized_keys

```

将公钥信息拷贝到其他两台机器
```
scp /home/hadoop/.ssh/authorized_keys hadoop@o2:/home/hadoop/.ssh/authorized_keys
scp /home/hadoop/.ssh/authorized_keys hadoop@o3:/home/hadoop/.ssh/authorized_keys

chmod 600 /home/hadoop/.ssh/authorized_keys

chown hadoop:hadoop /home/hadoop/.ssh/authorized_keys

```


查看ssh的配置文件

> sudo vim /etc/ssh/sshd_config

要确保下面这三个项目前面没有#
```
RSAAuthentication yes
PubkeyAuthentication yes
AuthorizedKeysFile %h/.ssh/authorized_keys

#为了安全性，可以修改SSH端口
Port 222

#禁用root账户登录，非必要，但为了安全性，请配置
PermitRootLogin no

#有了证书登录了，就禁用密码登录吧，安全要紧
PasswordAuthentication no
```

重启一下ssh服务，这样ssh配置才能生效：
sudo /sbin/service sshd restart

遇到坑1：配置ssh免密码登录后，仍提示输入密码
解决方法：

首先我们就要去查看系统的日志文件
> tail /var/log/secure -n 20

发现问题的所在：Authentication refused: bad ownership or modes for file
从字面上可以看出是目录的属主和权限配置不当，查找资料得知：SSH不希望home目录和~/.ssh目录对组有写权限，通过下面几条命令改下

> chmod g-w /home/hadoop
> chmod 700 /home/hadoop/.ssh
> chmod 600 /home/hadoop/.ssh/authorized_keys

然后我们再去登录，就能不用密码进入了。
有木有很高兴呀！
