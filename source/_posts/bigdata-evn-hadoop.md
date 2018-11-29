---
  title: Hadoop环境集群环境配置
  date: 2018-08-16 17:20:00
  categories: 大数据
  tags: [Hadoop,集群]
  description: Hadoop环境集群环境配置
---

# 0. 安装VMware和Ubuntn
略

# 1.Linux环境设置

## 1.1 安装配置SSH

### 1.1.1 安装
```
sudo apt-get install openssh-server openssh-client
```

查看SSH是否启动

> sudo ps -e |grep ssh
运行上面命令，如果有sshd，则表示ssh服务已经启动。没有启动，运行如下
> sudo service ssh start

设置允许Root用户登录
> sudo gedit /etc/ssh/sshd_config

修改如下：
```
PermitRootLogin yes
```

重启SSH
> sudo /etc/init.d/ssh restart

### 1.1.2 配置ssh无需密码访问


生成key
> ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa

拷贝所有从机的公钥到master

在node1上操作
> scp ~/.ssh/id_dsa.pub hadoop@master:/home/hadoop/.ssh/id_dsa.pub.node1

在node2上操作
> scp ~/.ssh/id_dsa.pub hadoop@master:/home/hadoop/.ssh/id_dsa.pub.node2

所有公钥文件追加到authorized_keys

> cat ~/.ssh/id_dsa.pub >> ~/.ssh/authorized_keys
> cat id_dsa.pub.node1 >> authorized_keys
> cat id_dsa.pub.node2 >> authorized_keys

生成的公钥文件拷贝到各机

scp authorized_keys hadoop@node1:/home/hadoop/.ssh/authorized_keys
scp authorized_keys hadoop@node2:/home/hadoop/.ssh/authorized_keys


## 修改主机名

> sudo vim /etc/hostname

## 修改hosts文件

> sudo vim /etc/hosts


# 2.JDK安装配置

解压安装包，修改环境变量

> sudo vim /etc/profile

```
export JAVA_HOME=/usr/soft/jdk1.8
export PATH=$PATH:${JAVA_HOME}/bin:
```

# 3.Scala安装配置

# 4.Hadoop安装配置

## 配置环境变量

> sodu vim /etc/profile

```
export HADOOP_HOME=/usr/soft/hadoop-2.6.0
export PATH=$PATH:${HADOOP_HOME}/bin:
```

## 配置hadoop-env.sh

> vim /usr/soft/hadoop-2.6.0/etc/hadoop/hadoop-env.sh

```
export JAVA_HOME=/usr/soft/jdk1.8
export HADOOP_PREFIX=/usr/soft/hadoop-2.6.0
```

## 配置 core-site.xml

> vim /usr/soft/hadoop-2.6.0/etc/hadoop/core-site.xml

``` xml
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://master:9000</value>
    </property>
    <property>
        <name>hadoop.tmp.dir</name>
        <value>/usr/soft/hadoop-2.6.0/tmp</value>
    </property>
</configuration>
```

## 配置 hdfs-site.xml

``` xml
<configuration>
 <property>
  <name>dfs.namenode.secondary.http-address</name>
  <value>master:9001</value>
 </property>
 <property>
   <name>dfs.namenode.name.dir</name>
   <value>file:/usr/soft/hadoop-2.6.0/dfs/name</value>
 </property>
 <property>
  <name>dfs.datanode.data.dir</name>
  <value>file:/usr/soft/hadoop-2.6.0/dfs/data</value>
  </property>
 <property>
  <name>dfs.replication</name>
  <value>2</value>
 </property>
 <property>
  <name>dfs.webhdfs.enabled</name>
  <value>true</value>
 </property>
</configuration>
```

## 配置 mapred-site.xml
``` xml
<configuration>
  <property>
    <name>mapreduce.framework.name</name>
    <value>yarn</value>
  </property>
</configuration>
```

## 配置yarn-env.sh
```
export JAVA_HOME=/usr/soft/jdk1.8
```

## 配置yarn-site.xml
``` xml
<configuration>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.resourcemanager.hostname</name>
        <value>master</value>
    </property>
</configuration>
```

## 配置 slaves
```
node1
node2
```

## 将配置文件拷贝到各机器
```
scp /usr/soft/hadoop-2.6.0/etc/hadoop/hadoop-env.sh hadoop@node1:/usr/soft/hadoop-2.6.0/etc/hadoop/
scp /usr/soft/hadoop-2.6.0/etc/hadoop/hadoop-env.sh hadoop@node2:/usr/soft/hadoop-2.6.0/etc/hadoop/

scp /usr/soft/hadoop-2.6.0/etc/hadoop/core-site.xml hadoop@node1:/usr/soft/hadoop-2.6.0/etc/hadoop/
scp /usr/soft/hadoop-2.6.0/etc/hadoop/core-site.xml hadoop@node2:/usr/soft/hadoop-2.6.0/etc/hadoop/

scp /usr/soft/hadoop-2.6.0/etc/hadoop/hdfs-site.xml hadoop@node1:/usr/soft/hadoop-2.6.0/etc/hadoop/
scp /usr/soft/hadoop-2.6.0/etc/hadoop/hdfs-site.xml hadoop@node2:/usr/soft/hadoop-2.6.0/etc/hadoop/

scp /usr/soft/hadoop-2.6.0/etc/hadoop/mapred-site.xml hadoop@node1:/usr/soft/hadoop-2.6.0/etc/hadoop/
scp /usr/soft/hadoop-2.6.0/etc/hadoop/mapred-site.xml hadoop@node2:/usr/soft/hadoop-2.6.0/etc/hadoop/

scp /usr/soft/hadoop-2.6.0/etc/hadoop/yarn-site.xml hadoop@node1:/usr/soft/hadoop-2.6.0/etc/hadoop/
scp /usr/soft/hadoop-2.6.0/etc/hadoop/yarn-site.xml hadoop@node2:/usr/soft/hadoop-2.6.0/etc/hadoop/

scp /usr/soft/hadoop-2.6.0/etc/hadoop/slaves hadoop@node1:/usr/soft/hadoop-2.6.0/etc/hadoop/
scp /usr/soft/hadoop-2.6.0/etc/hadoop/slaves hadoop@node1:/usr/soft/hadoop-2.6.0/etc/hadoop/

```

## 格式化hdfs

> /usr/soft/hadoop-2.6.0/bin/hdfs namenode -format

## 启动
> /usr/soft/hadoop-2.6.0/sbin/start-dfs.sh
> /usr/soft/hadoop-2.6.0/sbin/stop-dfs.sh
