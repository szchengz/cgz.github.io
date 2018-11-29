---
  title: centos6.5配置SSH免密码登录
  date: 2018-08-16 17:20:00
  categories: linux
  tags: [centos6.5]
  description: centos6.5配置SSH免密码登录
---

# 0.基本设置

修改主机名(把hostname改为你的主机名)

> sudo vim /etc/sysconfig/network

修改hosts,免得每次输IP
> sudo vim /etc/hosts

10.10.203.93    bdp01
10.10.203.95    bdp02
10.10.203.96    bdp03

没有安装scp的需要安装以下
> sudo yum install openssh-clients

# 1.配置SSH

检查是否安装ssh
> sudo rpm -qa |grep ssh
没有的话安装
> sudo yum install openssh-server


> sudo vim /etc/ssh/sshd_config

找到以下内容，并去掉注释符“#”

```
　　RSAAuthentication yes
　　PubkeyAuthentication yes
　　AuthorizedKeysFile      .ssh/authorized_keys
```

> sudo /sbin/service sshd restart

> sudo ssh-keygen -t rsa

生成公私钥
> sudo ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa


将所有的机器的公钥拷贝到一台机器上

> scp ~/.ssh/id_dsa.pub jht@bdp01:/home/jht/.ssh/id_dsa.pub.bdp02
> scp ~/.ssh/id_dsa.pub jht@bdp01:/home/jht/.ssh/id_dsa.pub.bdp03

所有公钥合成一个文件
```
cat ~/.ssh/id_dsa.pub >> ~/.ssh/authorized_keys
cat id_dsa.pub.bdp02 >> authorized_keys
cat id_dsa.pub.bdp03 >> authorized_keys
```

合并后的公钥拷贝到所有机器
```
scp authorized_keys jht@bdp02:/home/jht/.ssh/authorized_keys
scp authorized_keys jht@bdp03:/home/jht/.ssh/authorized_keys
```

修改权限
```
sudo chmod 700 ~/.ssh
sudo chmod 600 ~/.ssh/authorized_keys
```
